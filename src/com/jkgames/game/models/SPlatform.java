package com.jkgames.game.models;

import com.badlogic.androidgames.framework.GameObject;

public class SPlatform extends GameObject
{
	public static final float S_PLATFORM_WIDTH = 5f;
    public static final float S_PLATFORM_HEIGHT = 3f;

    public SPlatform(float x, float y) {
        super(x, y, S_PLATFORM_WIDTH, S_PLATFORM_HEIGHT);
    }
}