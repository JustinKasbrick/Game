package com.jkgames.game.models;

import com.badlogic.androidgames.framework.DynamicGameObject;
import com.jkgames.game.controllers.World;

public class ZombieBob extends DynamicGameObject{
	public static final int ZOMBIE_BOB_STATE_ALIVE = 1;
    public static final int ZOMBIE_BOB_STATE_HIT = 2;
    public static final int ZOMBIE_BOB_STATE_DEAD = 3;
    public static final float ZOMBIE_BOB_WIDTH = 1f;
    public static final float ZOMBIE_BOB_HEIGHT = 1f;
    
    public static final float HIT_VELOCITY_Y = 5;
	
    public int state;
	float minX;
	float maxX;
	
    public ZombieBob(float x, float y, float minX, float maxX) {
        super(x, y, ZOMBIE_BOB_WIDTH, ZOMBIE_BOB_HEIGHT);
        state = ZOMBIE_BOB_STATE_ALIVE;
		velocity.x = 2.5f;
		velocity.y = 0;
		this.minX = minX;
		this.maxX = maxX;
    }

    public void update(float deltaTime) {
    	if(state == ZOMBIE_BOB_STATE_HIT)
    	{
    		velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
    		
    		if(position.y <= -0.5f)
            	state = ZOMBIE_BOB_STATE_DEAD;
    	}
    	
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
        
        if(velocity.x > 0 && position.x > maxX)
			velocity.x = -velocity.x;
		if(velocity.x < 0 && position.x < minX)
			velocity.x = -velocity.x;
    }
}
