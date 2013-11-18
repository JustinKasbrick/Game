package com.jkgames.game.models;

import com.badlogic.androidgames.framework.gl.TextureRegion;

public interface Tile
{	
	public int getType();
	public boolean isCollidable();
	public TextureRegion getTextureRegion();
}
