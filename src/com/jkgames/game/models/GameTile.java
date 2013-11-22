package com.jkgames.game.models;

import com.badlogic.androidgames.framework.gl.TextureRegion;

public class GameTile implements Tile
{
	public static final int TILE_TYPE_NONE = 0;
	public static final int TILE_TYPE_SOLID_WATER = 1;
	public static final int TILE_TYPE_WATER = 2;
	public static final int TILE_TYPE_GRASS = 3;
	public static final int TILE_TYPE_DIRT = 4;
	public static final int TILE_TYPE_BRIDGE = 5;
    
	private int type;
	private boolean collidable;
	private TextureRegion texture;
	
	
	public GameTile(int type, boolean collidable, TextureRegion textureRegion) {
		this.type = type;
		this.collidable = collidable;
		this.texture = textureRegion;
	}
	
	public TextureRegion getTextureRegion()
	{
		return texture;
	}

	@Override
	public int getType() 
	{
		return type;
	}

	@Override
	public boolean isCollidable() {
		return collidable;
	}

}
