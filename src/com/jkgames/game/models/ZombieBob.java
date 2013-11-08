package com.jkgames.game.models;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class ZombieBob extends DynamicGameObject{
	public static final int BOB_STATE_ALIVE = 1;
    public static final int BOB_STATE_HIT = 2;
    public static final float BOB_WIDTH = 1f;
    public static final float BOB_HEIGHT = 1f;
	
    public int state;
	float minX;
	float maxX;
	
    public ZombieBob(float x, float y, float minX, float maxX) {
        super(x, y, BOB_WIDTH, BOB_HEIGHT);
        state = BOB_STATE_ALIVE;
		velocity.x = 2.5f;
		velocity.y = 0;
		this.minX = minX;
		this.maxX = maxX;
    }

    public void update(float deltaTime) {
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
        
        if(velocity.x > 0 && position.x > maxX)
			velocity.x = -velocity.x;
		if(velocity.x < 0 && position.x < minX)
			velocity.x = -velocity.x;
    }
}
