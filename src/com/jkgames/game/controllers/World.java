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
import com.jkgames.game.models.BridgeSwitch;
import com.jkgames.game.models.Castle;
import com.jkgames.game.models.CollectorCoin;
import com.jkgames.game.models.DrawBridge;
import com.jkgames.game.models.TileFactory;
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

    public static final int WORLD_WIDTH = 200;
    public static final int WORLD_HEIGHT = 25;    
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
	public static final int WORLD_TILE_SKY = 3;
	public static final int WORLD_TILE_PLATFORM = 4;
    public static final Vector2 gravity = new Vector2(0, -12);

    public final Bob bob;
	public final Sword bobSword;
    
    public final List<Platform> platforms;
	public final List<ZombieBob> zombieBobs;
	public final List<VerticalPlatform> vPlatforms;
    public final List<CollectorCoin> collectorCoins;
    public final List<DrawBridge> drawBridges;
    public final List<BridgeSwitch> bridgeSwitches;
    public Castle castle;
    public final WorldListener listener;
    public final Random rand;
    
	public Rectangle platformBounds;
	Vector2 tempVector2A = new Vector2(0, 0);
	Vector2 tempVector2B = new Vector2(0, 0);
	
    public float heightSoFar;
    public int score;
    public int state;
	Level level;
	Level tileLevel;
	
    SpatialHashGrid grid;
    public int tileArray[][];
    public TileFactory tileFactory;
    public String saveFile;
    int currentLevel;
    String tiles;
    
    public World(WorldListener listener, String tiles, Level tileLevel, int curLevel) {
    	grid = new SpatialHashGrid(WORLD_WIDTH, WORLD_HEIGHT, 25f);
    	tileArray = new int[WORLD_HEIGHT][WORLD_WIDTH];
    	this.bob = new Bob(0, 0);
		this.bobSword = new Sword(0, 0);
        this.platforms = new ArrayList<Platform>();
		this.zombieBobs = new ArrayList<ZombieBob>();
		this.vPlatforms = new ArrayList<VerticalPlatform>();
		this.collectorCoins = new ArrayList<CollectorCoin>();
		this.drawBridges = new ArrayList<DrawBridge>();
		this.bridgeSwitches = new ArrayList<BridgeSwitch>();
        this.listener = listener;
		this.tiles = tiles;
		this.tileLevel = tileLevel;
		this.tileFactory = new TileFactory();
		currentLevel = curLevel;
        rand = new Random();
        generateLevel();
        
        this.heightSoFar = 0;
        this.score = 0;
        this.state = WORLD_STATE_RUNNING;
        
    }

    private void generateLevel() {
    	// read in tiles
    	int start = 0;
    	for(int i=24; i>-1; i--)
    	{
    		for(int j=0; j<WORLD_WIDTH; j++)
    		{
    			tileArray[i][j] = Integer.parseInt(tiles.substring(start, start+2));
    			start += 2;
    		}
    	}

//		bob.position.x = tileArray[1][1].position.x;
//		bob.position.y = tileArray[1][1].position.y + tileArray[1][1].bounds.height/2 + bob.bounds.height/2;
    	bob.position.x = 1;
    	bob.position.y = 15;
		bobSword.position.x = bob.position.x;
		bobSword.position.y = bob.position.y + 0.2f;

//		// read all zombie bobs
//		numObjs = Integer.parseInt(level.text.substring(12, 14));
//		start = level.text.indexOf("eb")+3;
//		for(int i = 0; i<numObjs; i++)
//		{			
//			zombieBobs.add(new ZombieBob(Float.parseFloat(level.text.substring(start, start+4)), Float.parseFloat(level.text.substring(start+6, start+10)),
//			Float.parseFloat(level.text.substring(start+12, start+16)), Float.parseFloat(level.text.substring(start+18, start+22))));
//			start += 24;
//		}
//		
//        for(int i=0; i<numObjs; i++)
//        	grid.insertDynamicObject(zombieBobs.get(i));

        // read all collector coins (there is always exactly 3)
        int numObjs = 3;
		start = tileLevel.text.indexOf("cc")+3;
		for(int i = 0; i<numObjs; i++)
		{			
			collectorCoins.add(new CollectorCoin(Float.parseFloat(tileLevel.text.substring(start, start+4)), Float.parseFloat(tileLevel.text.substring(start+6, start+10)), i+1));
			start += 12;
		}
		
        for(int i=0; i<numObjs; i++)
        {   // level is null;
        	if(level.coinsArray[i] != 1)
        		grid.insertStaticObject(collectorCoins.get(i));
//        	else
//        		collectorCoins.get(i).Collected = true;
        }

//        // read all drawbridges
//        numObjs = Integer.parseInt(level.text.substring(17, 19));
//		start = level.text.indexOf("db")+3;
//		for(int i = 0; i<numObjs; i++)
//		{
//			drawBridges.add(new DrawBridge(Float.parseFloat(level.text.substring(start, start+4)), Float.parseFloat(level.text.substring(start+6, start+10))));
//			start += 12;
//		}
//
//        for(int i=0; i<numObjs; i++)
//        	grid.insertDynamicObject(drawBridges.get(i));
//        
//        // read all drawbridge switches (same amount as bridges)
//        start = level.text.indexOf("sw")+3;
//		for(int i = 0; i<numObjs; i++)
//		{			
//			bridgeSwitches.add(new BridgeSwitch(Float.parseFloat(level.text.substring(start, start+4)), Float.parseFloat(level.text.substring(start+6, start+10))));
//			start += 12;
//		}
//		
//        for(int i=0; i<numObjs; i++)
//        	grid.insertStaticObject(bridgeSwitches.get(i));
        
        // read Castle (there is only 1 castle in each level)
		start = tileLevel.text.indexOf("ca")+3;
		castle = (new Castle(Float.parseFloat(tileLevel.text.substring(start, start+4)), Float.parseFloat(tileLevel.text.substring(start+6, start+10))));
		grid.insertStaticObject(castle);
    }

    public void update(float deltaTime, float accelX, boolean jump, boolean attack) 
    {
    	//drawBridges.get(0).Update(deltaTime);
        updateBob(deltaTime, accelX, jump, attack);
        //updateEvilbobs(deltaTime);
        if (bob.state != Bob.BOB_STATE_HIT)
        {
        	checkPlatformCollisions();
            checkCollisions();
        }
		
        updateBobWeapon(deltaTime, attack);
        if(bobSword.stateTime < 0.2f)
        	checkWeaponCollisions(bobSword);
        checkGameOver();
    }

	private void checkWeaponCollisions(Sword weapon) {
		// TODO create bad guy abstract class
    	List<GameObject> colliders = grid.getPotentialColliders(weapon);
    	int len = colliders.size();
    	for(int i = 0; i < len; i++) {
    		GameObject collider = colliders.get(i);
			if(collider instanceof ZombieBob)
			{
				if(((ZombieBob) collider).state == ZombieBob.ZOMBIE_BOB_STATE_ALIVE)
				{
					if(OverlapTester.overlapRectangles(weapon.bounds, collider.bounds)) 
		    		{
						((ZombieBob) collider).state = ZombieBob.ZOMBIE_BOB_STATE_HIT;
						((ZombieBob) collider).velocity.x *= -1;
						((ZombieBob) collider).velocity.y = ZombieBob.HIT_VELOCITY_Y;
						grid.removeObject(collider);
		    		}
				}
			}
    	}
	}

	private void updateBobWeapon(float deltaTime, boolean attack) {
    	if(attack)
			bobSword.state = Sword.SWORD_STATE_ATTACK;
		bobSword.update(deltaTime, bob.position);
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
			
        bob.update(deltaTime);
    }

    private void updateEvilbobs(float deltaTime) {
       int len = zombieBobs.size();
       for (int i = 0; i < len; i++) {
    	   ZombieBob eBob = zombieBobs.get(i);
           eBob.update(deltaTime);
           if(eBob.state == ZombieBob.ZOMBIE_BOB_STATE_DEAD)
           {
        	   zombieBobs.remove(eBob);
        	   len = zombieBobs.size();
           }
       }
    }

    //private void updateCoins(float deltaTime) {
    //    int len = coins.size();
    //    for (int i = 0; i < len; i++) {
    //        Coin coin = coins.get(i);
    //        coin.update(deltaTime);
    //    }
    //}

    private void checkCollisions() {
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
				
    			else if(collider instanceof DrawBridge)
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
    			else if(collider instanceof CollectorCoin)
    			{
    				//increase score
    				grid.removeObject(collider);
    				((CollectorCoin) collider).Collected = true;
    				Settings.saveFiles[0].coinCollected(currentLevel, ((CollectorCoin) collider).orderInLevel);
    			}
    			else if(collider instanceof BridgeSwitch)
    			{
    				collider.position.y = bob.position.y - bob.bounds.height/2 - collider.bounds.height/2;
    				if(drawBridges.get(0).state == DrawBridge.BRIDGE_STATE_CLOSED)
    					drawBridges.get(0).state = DrawBridge.BRIDGE_STATE_OPENING;
    			}
    			else if (collider instanceof Castle)
    			{
    				state = WORLD_STATE_NEXT_LEVEL;
    			}
    		}
    	}
    	//checkPlatformCollisions(originalBobLocation);
        //checkSquirrelCollisions();
        //checkItemCollisions();
        //checkLevelEnd();
    }

    private void checkPlatformCollisions() {
    	int x = (int)bob.bounds.lowerLeft.x;
    	int y = (int)bob.bounds.lowerLeft.y;
    	if(tileFactory.getTile(tileArray[y][x]).isCollidable())
    	{
    		checkCollisionPoints(x, y);
//    		checkCollision(x, y);
    	}
    	if(tileFactory.getTile(tileArray[y][x+1]).isCollidable())
    	{
    		checkCollisionPoints(x+1, y);
//    		checkCollision(x+1, y);
    	}
    	if(tileFactory.getTile(tileArray[y+1][x]).isCollidable())
    	{
    		checkCollisionPoints(x, y+1);
//    		checkCollision(x, y+1);
    	}
    	if(tileFactory.getTile(tileArray[y+1][x+1]).isCollidable())
    	{
    		checkCollisionPoints(x+1, y+1);
//    		checkCollision(x+1, y+1);
    	}
    }

	private void checkCollisionPoints(int x, int y) {
		
		if(bob.velocity.y < 0)
		{
			tempVector2A.set(bob.position);
			tempVector2B.set(bob.position);
			if(OverlapTester.pointInRectangle(new Rectangle(x, y, 1f, 1f), 
					tempVector2A.add(Bob.BOB_BOTTOM_LEFT_COLLISION))
					|| OverlapTester.pointInRectangle(new Rectangle(x, y, 1f, 1f), 
							tempVector2B.add(Bob.BOB_BOTTOM_RIGHT_COLLISION)))
			{
				bob.velocity.y = 0;
				bob.position.y = y+1+(bob.bounds.height/2);
				bob.state = Bob.BOB_STATE_STAND;
			}
		}
		else if(bob.velocity.y > 0)
		{
			tempVector2A.set(bob.position);
			tempVector2B.set(bob.position);
			if(OverlapTester.pointInRectangle(new Rectangle(x, y, 1f, 1f), 
					tempVector2A.add(Bob.BOB_TOP_LEFT_COLLISION))
					|| OverlapTester.pointInRectangle(new Rectangle(x, y, 1f, 1f), 
							tempVector2B.add(Bob.BOB_TOP_RIGHT_COLLISION)))
			{
				bob.velocity.y = 0;
				bob.position.y = y-(bob.bounds.height/2);
			}
		}
		if(bob.velocity.x > 0)
		{
			tempVector2A.set(bob.position);
			tempVector2B.set(bob.position);
			if(OverlapTester.pointInRectangle(new Rectangle(x, y, 1f, 1f), 
					tempVector2A.add(Bob.BOB_FRONT_BOTTOM_COLLISION))
					|| OverlapTester.pointInRectangle(new Rectangle(x, y, 1f, 1f), 
							tempVector2B.add(Bob.BOB_FRONT_TOP_COLLISION)))
					{    						
						bob.position.x = x-(bob.bounds.width/2);
					}
		}
		else if(bob.velocity.x < 0)
		{
			tempVector2A.set(bob.position);
			tempVector2B.set(bob.position);
			if(OverlapTester.pointInRectangle(new Rectangle(x, y, 1f, 1f), 
					tempVector2A.add(Bob.BOB_BACK_BOTTOM_COLLISION))
					|| OverlapTester.pointInRectangle(new Rectangle(x, y, 1f, 1f), 
							tempVector2B.add(Bob.BOB_BACK_TOP_COLLISION)))
					{    						
						bob.position.x = x+1+(bob.bounds.width/2);
					}
		}
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

//    private void checkLevelEnd() {
//       if (bob.position.x > 65) {
//           state = WORLD_STATE_NEXT_LEVEL;
//       }
//    }

    private void checkGameOver() {
        if (bob.position.y < 1 || bob.state == Bob.BOB_STATE_HIT) {
            state = WORLD_STATE_GAME_OVER;
        }
    }
}
