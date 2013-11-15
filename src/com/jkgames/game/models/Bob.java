package com.jkgames.game.models;

import com.badlogic.androidgames.framework.DynamicGameObject;
import com.badlogic.androidgames.framework.math.Vector2;

import com.jkgames.game.controllers.World;

public class Bob extends DynamicGameObject{
	public static final int BOB_STATE_JUMP = 0;
    public static final int BOB_STATE_FALL = 1;
    public static final int BOB_STATE_HIT = 2;
	public static final int BOB_STATE_STAND = 3;
    public static final float BOB_JUMP_VELOCITY = 5;
    public static final float BOB_MOVE_VELOCITY = 10;
    public static final float BOB_BOUNDS_WIDTH = 0.8f;
    public static final float BOB_BOUNDS_HEIGHT = 0.8f;
    public static final float BOB_WIDTH = 0.8f;
    public static final float BOB_HEIGHT = 0.8f;
	public static final int BOB_DIRECTION_RIGHT = 1;
	public static final int BOB_DIRECTION_LEFT = -1;
	
	public static final Vector2 BOB_TOP_LEFT_COLISSION = new Vector2(-BOB_BOUNDS_WIDTH/4, BOB_BOUNDS_HEIGHT/2);
	public static final Vector2 BOB_TOP_RIGHT_COLISSION = new Vector2(BOB_BOUNDS_WIDTH/4, BOB_BOUNDS_HEIGHT/2);
	public static final Vector2 BOB_BOTTOM_LEFT_COLISSION = new Vector2(-BOB_BOUNDS_WIDTH/4, -BOB_BOUNDS_HEIGHT/2);
	public static final Vector2 BOB_BOTTOM_RIGHT_COLISSION = new Vector2(BOB_BOUNDS_WIDTH/4, -BOB_BOUNDS_HEIGHT/2);
	
	public static final Vector2 BOB_FRONT_TOP_COLISSION = new Vector2(BOB_BOUNDS_WIDTH/2, BOB_BOUNDS_HEIGHT/4);
	public static final Vector2 BOB_FRONT_BOTTOM_COLISSION = new Vector2(BOB_BOUNDS_WIDTH/2, -BOB_BOUNDS_HEIGHT/4);
	public static final Vector2 BOB_BACK_TOP_COLISSION = new Vector2(-BOB_BOUNDS_WIDTH/2, BOB_BOUNDS_HEIGHT/4);
	public static final Vector2 BOB_BACK_BOTTOM_COLISSION = new Vector2(-BOB_BOUNDS_WIDTH/2, -BOB_BOUNDS_HEIGHT/4);
	
    public int state;
    float stateTime;
	int direction;
	
    public Bob(float x, float y) {
        super(x, y, BOB_WIDTH, BOB_HEIGHT);
        state = BOB_STATE_FALL;
        stateTime = 0;
		direction = BOB_DIRECTION_LEFT;
    }

    public void update(float deltaTime) {
		float previous_xVel = velocity.x;
        velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		
		if(previous_xVel >= 0 && velocity.x < previous_xVel)
			direction = BOB_DIRECTION_LEFT;
		else if(previous_xVel <= 0 && velocity.x > previous_xVel)
			direction = BOB_DIRECTION_RIGHT;
		
		if(position.y < 1)
			position.y = 1;
		
			
        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
        
        if(velocity.y > 0 && state != BOB_STATE_HIT) {
            if(state != BOB_STATE_JUMP) {
                state = BOB_STATE_JUMP;
                //stateTime = 0;
            }
        }
        
		if(velocity.y == 0 && state != BOB_STATE_HIT)
		{
			state = BOB_STATE_STAND;
			//stateTime = 0;
		}
		
        if(velocity.y < 0 && state != BOB_STATE_HIT) {
            if(state != BOB_STATE_FALL) {
                state = BOB_STATE_FALL;
                //stateTime = 0;
            }
        }
        //if(position.x < 0)
        //    position.x = World.WORLD_WIDTH;
        //if(position.x > World.WORLD_WIDTH)
        //    position.x = 0;
        //stateTime += deltaTime;
    }

    public void hitSquirrel() {
        velocity.set(0,0);
        state = BOB_STATE_HIT;        
        stateTime = 0;
    }
    
    public void hitPlatform() {		
        //velocity.y = BOB_JUMP_VELOCITY;
        //state = BOB_STATE_JUMP;
        stateTime = 0;
    }

    public void hitSpring() {
        velocity.y = BOB_JUMP_VELOCITY * 1.5f;
        state = BOB_STATE_JUMP;
        stateTime = 0;   
    }
}
