package com.jkgames.game.models;

import java.util.ArrayList;
import java.util.List;

public class TileFactory 
{
	private List<Tile> tilePool;
	
	public TileFactory()
	{
		tilePool = new ArrayList<Tile>();
	}

	public Tile getTile(int type)
	{
		//check if we've already created a tile with this type
		for(Tile tile: tilePool)
		{
			if(type == tile.getType())
			{
				return tile;
			}
		}
		//if not, create one and save it to the pool
		Tile tile;
		switch(type)
		{
		case 1:
			tile = new GameTile(type, false, Assets.solidWaterTile);
			break;
		case 2:
			tile = new GameTile(type, false, Assets.waterTile);
			break;
		case 3:
			tile = new GameTile(type, true, Assets.grassTile);
			break;
		case 4:
			tile = new GameTile(type, true, Assets.dirtTile);
			break;
		case 5:
			tile = new GameTile(type, true, Assets.bridgeTile);
			break;
		default:
			tile = new GameTile(type, false, null);
		}
		
		tilePool.add(tile);
		return tile;
	}
}
