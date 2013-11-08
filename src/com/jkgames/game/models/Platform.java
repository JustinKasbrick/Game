package com.jkgames.game.models;

import com.badlogic.androidgames.framework.GameObject;

public class Platform extends GameObject
{
	public static final float PLATFORM_WIDTH = 5f;
    public static final float PLATFORM_HEIGHT = 1f;

    public Platform(float x, float y) {
        super(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
    }
}
