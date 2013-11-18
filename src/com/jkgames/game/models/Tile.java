package com.jkgames.game.models;

import com.badlogic.androidgames.framework.GameObject;
import com.badlogic.androidgames.framework.gl.TextureRegion;

public class Tile extends GameObject
{
	public static final int TILE_TYPE_NONE = 0;
	public static final int TILE_TYPE_SOLID_WATER = 1;
	public static final int TILE_TYPE_WATER = 2;
	public static final int TILE_TYPE_GRASS = 3;
	public static final int TILE_TYPE_DIRT = 4;
	public static final int TILE_TYPE_BRIDGE = 5;
    
	public int type;
	public boolean collidable;
	TextureRegion texture;
	
	
	public Tile(float x, float y, float width, float height, int type) {
		super(x, y, width, height);
		this.type = type;
		switch(type)
		{
		case 1:
			texture = Assets.solidWaterTile;
			collidable = false;
			break;
		case 2:
			texture = Assets.waterTile;
			collidable = false;
			break;
		case 3:
			texture = Assets.grassTile;
			collidable = true;
			break;
		case 4:
			texture = Assets.dirtTile;
			collidable = true;
			break;
		case 5:
			texture = Assets.bridgeTile;
			collidable = true;
			break;
		default:
			texture = null;
			collidable = false;
		}
	}
	
	public TextureRegion getTexture()
	{
		return texture;
	}
}
