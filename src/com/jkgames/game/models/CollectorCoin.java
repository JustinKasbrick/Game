package com.jkgames.game.models;

import com.badlogic.androidgames.framework.GameObject;

public class CollectorCoin extends GameObject 
{
	public static final float COLLECTOR_COIN_WIDTH = 1f;
    public static final float COLLECTOR_COIN_HEIGHT = 1f;
    
    public boolean Collected;
    public int orderInLevel;
    
	public CollectorCoin(float x, float y, int order) {
		super(x, y, COLLECTOR_COIN_WIDTH, COLLECTOR_COIN_HEIGHT);
		Collected = false;
        orderInLevel = order;
	}
}
