package com.jkgames.game.views;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;
import com.jkgames.game.controllers.Settings;
import com.jkgames.game.models.Assets;
import com.jkgames.game.models.SaveFile;

public class LoadGameScreen extends GLScreen 
{
	Camera2D guiCam;
    SpriteBatcher batcher;
    //Rectangle soundBounds;
    Rectangle playBounds;
    Rectangle file1Bounds;
    Rectangle file2Bounds;
    Rectangle file3Bounds;
    ArrayList<SaveFile> saveFiles;
	Vector2 touchPoint;
    
	public LoadGameScreen(Game game) {
		super(game);
		
		guiCam = new Camera2D(glGraphics, 800, 480);
        batcher = new SpriteBatcher(glGraphics, 100, false);
        //soundBounds = new Rectangle(0, 0, 64, 64);
        playBounds = new Rectangle(400 - 128, 240 - 18, 256, 36);
        touchPoint = new Vector2();
//        for(int i = 0;  i < 3; i++)
//        {
//        	saveFiles.add(new SaveFile("SaveFile" + (i+1)));
//        	saveFiles.get(i).LoadDisplayData();
//        }
	}
	
	@Override
    public void update(float deltaTime) 
	{
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);                        
            if(event.type == TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
                
                if(OverlapTester.pointInRectangle(playBounds, touchPoint)) {
                    //Assets.playSound(Assets.clickSound);
                    game.setScreen(new GameScreen(game));
                    return;
                }
//                if(OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
                    //Assets.playSound(Assets.clickSound);
//                    Settings.soundEnabled = !Settings.soundEnabled;
                    //if(Settings.soundEnabled) 
                    //    Assets.music.play();
                    //else
                    //    Assets.music.pause();
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();        
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();
        
        gl.glEnable(GL10.GL_TEXTURE_2D);
        
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(400, 240, 800, 480, Assets.backgroundRegion);
        batcher.endBatch();
        
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);               
        
        batcher.beginBatch(Assets.items);                 
        
        //batcher.drawSprite(480 - 10 - 71, 160, 274, 142, Assets.logo);
        batcher.drawSprite(400, 240, 128, 32, Assets.mainMenu);
        //batcher.drawSprite(32, 32, 64, 64, Settings.soundEnabled?Assets.soundOn:Assets.soundOff);
                
        batcher.endBatch();
        
        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void pause() {        
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {        
    }       

    @Override
    public void dispose() {        
    }
}
