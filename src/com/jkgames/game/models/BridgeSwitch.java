package com.jkgames.game.models;

import com.badlogic.androidgames.framework.GameObject;

public class BridgeSwitch extends GameObject
{
	public static final float BRIDGE_SWITCH_WIDTH = 1f;
    public static final float BRIDGE_SWITCH_HEIGHT = 1f;
    
	public BridgeSwitch(float x, float y) 
	{
		super(x, y, BRIDGE_SWITCH_WIDTH, BRIDGE_SWITCH_HEIGHT);
	}
}
