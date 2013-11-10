package com.jkgames.game.models;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class DrawBridge extends DynamicGameObject
{
	public static final float DRAW_BRIDGE_WIDTH = 5f;
    public static final float DRAW_BRIDGE_HEIGHT = 1f;
    
    public static final int BRIDGE_STATE_CLOSED = 0;
    public static final int BRIDGE_STATE_OPENING = 1;
    public static final int BRIDGE_STATE_OPEN = 2;
    
    public int state;
    float openPositionX;
    
	public DrawBridge(float closedPositionX, float closedPositionY) {
		super(closedPositionX, closedPositionY, DRAW_BRIDGE_WIDTH, DRAW_BRIDGE_HEIGHT);
		state = BRIDGE_STATE_CLOSED;
		openPositionX = closedPositionX - 5;
		velocity.x = -2.5f;
		velocity.y = 0;
	}
	
	public void Update(float deltaTime)
	{
		switch(state)
		{
			case BRIDGE_STATE_CLOSED:
			case BRIDGE_STATE_OPEN:
				break;
			case BRIDGE_STATE_OPENING:
				position.add(velocity.x * deltaTime, velocity.y * deltaTime);
				if(position.x < openPositionX)
				{
					position.x = openPositionX;
					state = BRIDGE_STATE_OPEN;
				}
				bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
				break;
		}
		
		
	}
}
