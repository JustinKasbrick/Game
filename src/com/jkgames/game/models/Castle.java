package com.jkgames.game.models;

import com.badlogic.androidgames.framework.GameObject;

public class Castle extends GameObject
{
	public static final float CASTLE_WIDTH = 2f;
    public static final float CASTLE_HEIGHT = 2f;

    public Castle(float x, float y) {
        super(x, y, CASTLE_WIDTH, CASTLE_HEIGHT);
    }
}
