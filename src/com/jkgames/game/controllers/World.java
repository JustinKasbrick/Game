package com.jkgames.game.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.androidgames.framework.GameObject;
import com.badlogic.androidgames.framework.SpatialHashGrid;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Vector2;
import com.badlogic.androidgames.framework.math.Rectangle;

import com.jkgames.game.models.Bob;
import com.jkgames.game.models.ZombieBob;
import com.jkgames.game.models.Level;
import com.jkgames.game.models.Platform;
import com.jkgames.game.models.Sword;
import com.jkgames.game.models.VerticalPlatform;

public class World {
    public interface WorldListener {
        public void jump();
        public void highJump();
        public void hit();
        public void coin();
    }

    public static final int WORLD_WIDTH = 100;
    public static final int WORLD_HEIGHT = 15;    
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
	public static final int WORLD_TILE_SKY = 3;
	public static final int WORLD_TILE_PLATFORM = 4;
    public static final Vector2 gravity = new Vector2(0, -12);

    public final Bob bob;
	public final Sword bobSword;
    
    public final List<Platform> platforms;
	public final List<ZombieBob> evilBobs;
	public final List<VerticalPlatform> vPlatforms;
    //public final List<Squirrel> squirrels;
    //public final List<Coin> coins;
    //public Castle castle;
    public final WorldListener listener;
    public final Random rand;
    
	public Rectangle platformBounds;
	
    public float heightSoFar;
    public int score;    
    public int state;
	Level level;
    SpatialHashGrid grid;

    public World(WorldListener listener, Level level) {
    	grid = new SpatialHashGrid(WORLD_WIDTH, WORLD_HEIGHT, 5f);
    	this.bob = new Bob(0, 0);
		this.bobSword = new Sword(0, 0);
        this.platforms = new ArrayList<Platform>();
		this.evilBobs = new ArrayList<ZombieBob>();
		this.vPlatforms = new ArrayList<VerticalPlatform>();
        //this.squirrels = new ArrayList<Squirrel>();
        //this.coins = new ArrayList<Coin>();
        this.listener = listener;
		this.level = level;
        rand = new Random();
        generateLevel();
        
        this.heightSoFar = 0;
        this.score = 0;
        this.state = WORLD_STATE_RUNNING;
    }

    private void generateLevel() {
		//read all regular platforms
		int numObjs = Integer.parseInt(level.text.substring(2, 4));
		//String[] objs = new String[15];
		int start = level.text.indexOf("rp")+3;
		for(int i = 0; i<numObjs; i++)
		{			
			platforms.add(new Platform(Float.parseFloat(level.text.substring(start, start+4)), Float.parseFloat(level.text.substring(start+6, start+10))));
			start += 12;
		}
		
        for(int i=0; i<numObjs; i++)
        	grid.insertStaticObject(platforms.get(i));
        
		numObjs = Integer.parseInt(level.text.substring(7, 9));
		start = level.text.indexOf("vp")+3;
		for(int i = 0; i<numObjs; i++)
		{			
			vPlatforms.add(new VerticalPlatform(Float.parseFloat(level.text.substring(start, start+4)), Float.parseFloat(level.text.substring(start+6, start+10))));
			start += 12;
		}
		
        for(int i=0; i<numObjs; i++)
        	grid.insertStaticObject(vPlatforms.get(i));
		
		bob.position.x = platforms.get(0).position.x;
		bob.position.y = platforms.get(0).position.y + platforms.get(0).bounds.height/2 + bob.bounds.height/2;
		bobSword.position.x = bob.position.x + 0.2f;
		bobSword.position.y = bob.position.y + 0.2f;
		
		numObjs = Integer.parseInt(level.text.substring(12, 14));
		start = level.text.indexOf("eb")+3;
		for(int i = 0; i<numObjs; i++)
		{			
			evilBobs.add(new ZombieBob(Float.parseFloat(level.text.substring(start, start+4)), Float.parseFloat(level.text.substring(start+6, start+10)),
			Float.parseFloat(level.text.substring(start+12, start+16)), Float.parseFloat(level.text.substring(start+18, start+22))));
			start += 24;
		}
		
        for(int i=0; i<numObjs; i++)
        	grid.insertDynamicObject(evilBobs.get(i));
    }

    public void update(float deltaTime, float accelX, boolean jump, boolean attack) 
    {
		Vector2 originalBobLocation = bob.position;
        updateBob(deltaTime, accelX, jump, attack);
        updateEvilbobs(deltaTime);
        //updateCoins(deltaTime);
        if (bob.state != Bob.BOB_STATE_HIT)
            checkCollisions(originalBobLocation);
		//correct bobSword position in case there was a collision
		bobSword.position.x = bob.position.x;
		bobSword.position.y = bob.position.y;
		
        checkGameOver();
    }

    private void updateBob(float deltaTime, float accelX, boolean jump, boolean attack) {
        //if (bob.state != Bob.BOB_STATE_HIT && bob.position.y <= 0.5f)
        //    bob.hitPlatform();
        if (bob.state != Bob.BOB_STATE_HIT)
            bob.velocity.x = accelX / 20 * Bob.BOB_MOVE_VELOCITY;
		if(jump && bob.state != Bob.BOB_STATE_HIT)
		{
			bob.velocity.y = Bob.BOB_JUMP_VELOCITY;
			bob.state = Bob.BOB_STATE_JUMP;
		}
		if(attack)
			bobSword.state = Sword.SWORD_STATE_ATTACK;
			
        bob.update(deltaTime);
		
		if(attack)
			bobSword.state = Sword.SWORD_STATE_ATTACK;
		bobSword.update(deltaTime, bob.position);
    }

    private void updateEvilbobs(float deltaTime) {
       int len = evilBobs.size();
       for (int i = 0; i < len; i++) {
    	   ZombieBob eBob = evilBobs.get(i);
           eBob.update(deltaTime);
       }
    }

    //private void updateCoins(float deltaTime) {
    //    int len = coins.size();
    //    for (int i = 0; i < len; i++) {
    //        Coin coin = coins.get(i);
    //        coin.update(deltaTime);
    //    }
    //}

    private void checkCollisions(Vector2 originalBobLocation) {
    	List<GameObject> colliders = grid.getPotentialColliders(bob);
    	int len = colliders.size();
    	for(int i = 0; i < len; i++) {
    		GameObject collider = colliders.get(i);
			
    		if(OverlapTester.overlapRectangles(bob.bounds, collider.bounds)) 
    		{
				if(collider instanceof ZombieBob)
				{
					bob.state = Bob.BOB_STATE_HIT;
				}
				
    			else if(collider instanceof Platform || collider instanceof VerticalPlatform)
    			{    				
    				if(bob.velocity.y < 0 && bob.position.y > collider.position.y)
    				{
    					bob.position.y = collider.position.y + collider.bounds.height/2 + bob.bounds.height/2;
    					bob.velocity.y = 0;
    				}
					else if(bob.velocity.y > 0 && bob.position.y < collider.position.y)
					{
						bob.position.y = collider.position.y - collider.bounds.height/2 - bob.bounds.height/2;
    					bob.velocity.y = 0;
					}
    				if(bob.velocity.x > 0 && bob.position.x < collider.position.x && bob.position.y <= collider.position.y)
    					bob.position.x = collider.position.x - collider.bounds.width/2 - bob.bounds.width/2;
    				
    				else if(bob.velocity.x < 0 && bob.position.x > collider.position.x && bob.position.y <= collider.position.y)
						bob.position.x = collider.position.x + collider.bounds.width/2 + bob.bounds.width/2;
					
    			}
				
    		}
    	}
    	//checkPlatformCollisions(originalBobLocation);
        //checkSquirrelCollisions();
        //checkItemCollisions();
        checkLevelEnd();
    }

    private void checkPlatformCollisions(Vector2 originalBobLocation) {
    }

    //private void checkSquirrelCollisions() {
    //    int len = squirrels.size();
    //    for (int i = 0; i < len; i++) {
    //        Squirrel squirrel = squirrels.get(i);
    //        if (OverlapTester.overlapRectangles(squirrel.bounds, bob.bounds)) {
    //            bob.hitSquirrel();
    //            listener.hit();
    //        }
    //    }
    //}

    //private void checkItemCollisions() {
    //    int len = coins.size();
    //    for (int i = 0; i < len; i++) {
    //        Coin coin = coins.get(i);
    //        if (OverlapTester.overlapRectangles(bob.bounds, coin.bounds)) {
    //            coins.remove(coin);
    //            len = coins.size();
    //            listener.coin();
    //            score += Coin.COIN_SCORE;
    //        }
	//
    //    }

    //    if (bob.velocity.y > 0)
    //        return;        
    //}

    private void checkLevelEnd() {
       if (bob.position.x > 65) {
           state = WORLD_STATE_NEXT_LEVEL;
       }
    }

    private void checkGameOver() {
        if (bob.position.y < 1 || bob.state == Bob.BOB_STATE_HIT) {
            state = WORLD_STATE_GAME_OVER;
        }
    }
}
