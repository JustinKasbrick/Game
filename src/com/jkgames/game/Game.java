package com.jkgames.game;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;

import com.jkgames.game.views.MainMenuScreen;
import com.jkgames.game.models.Assets;
import com.jkgames.game.controllers.Settings;

public class Game extends GLGame
{
	boolean firstTimeCreate = true;
	
	public Screen getStartScreen() 
	{		
		return new MainMenuScreen(this);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
		super.onSurfaceCreated(gl, config);
		if(firstTimeCreate) {
			Settings.load(getFileIO());
		Assets.load(this);
		firstTimeCreate = false;
		} 
		else {
			Assets.reload();
		}
	}
	
	@Override
	public void onPause() 
	{
		super.onPause();
		//if(Settings.soundEnabled)
			//Assets.music.pause();
	}
}
