package com.jkgames.game.models;

import com.badlogic.androidgames.framework.GameObject;

public class VerticalPlatform extends GameObject
{
	public static final float V_PLATFORM_WIDTH = 1f;
    public static final float V_PLATFORM_HEIGHT = 3f;

    public VerticalPlatform(float x, float y) {
        super(x, y, V_PLATFORM_WIDTH, V_PLATFORM_HEIGHT);
    }
}
