package com.jkgames.game.models;

import com.badlogic.androidgames.framework.GameObject;

public class DrawBridge extends GameObject
{
	public static final float DRAW_BRIDGE_WIDTH = 11f;
    public static final float DRAW_BRIDGE_HEIGHT = 1f;
    
	public DrawBridge(float x, float y) {
		super(x, y, DRAW_BRIDGE_WIDTH, DRAW_BRIDGE_HEIGHT);
	}
}
