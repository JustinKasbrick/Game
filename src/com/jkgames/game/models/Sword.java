package com.jkgames.game.models;

import com.badlogic.androidgames.framework.GameObject;
import com.badlogic.androidgames.framework.math.Vector2;

public class Sword extends GameObject
{
	public static final float SWORD_WIDTH = 1f;
    public static final float SWORD_HEIGHT = 1f;
	public static final int SWORD_STATE_IDOL = 0;
    public static final int SWORD_STATE_ATTACK = 1;
	
	float stateTime;
	public int state;
	
    public Sword(float x, float y) 
	{
        super(x, y, SWORD_WIDTH, SWORD_HEIGHT);
		state = SWORD_STATE_IDOL;
		stateTime = 2;
    }
	
	public void update(float deltaTime, Vector2 position) 
	{
		this.position.x = position.x;
		this.position.y = position.y;
		
		if(state == SWORD_STATE_ATTACK)
		{
			stateTime = 0;
			state = SWORD_STATE_IDOL;
		}
		
		stateTime += deltaTime;
	}
	 
	public float getStateTime()
	{
		return stateTime;
	}
}
