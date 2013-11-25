package com.jkgames.game.views;

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

import com.jkgames.game.models.Assets;

import com.jkgames.game.controllers.Settings;

public class MainMenuScreen extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle soundBounds;
    Rectangle newGameBounds;
    Rectangle continueBounds;
    Vector2 touchPoint;

    public MainMenuScreen(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 800, 480);
        batcher = new SpriteBatcher(glGraphics, 100, false);
        soundBounds = new Rectangle(0, 0, 64, 64);
        newGameBounds = new Rectangle(400 - 170, 280 -32, 340, 64);
        continueBounds = new Rectangle(400 - 170, 200 - 32, 340, 64);
        //highscoresBounds = new Rectangle(160 - 150, 240 - 18, 300, 36);
        touchPoint = new Vector2();
    }       

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);                        
            if(event.type == TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
                
                if(OverlapTester.pointInRectangle(continueBounds, touchPoint)) {
                    //Assets.playSound(Assets.clickSound);
                    game.setScreen(new ContinueScreen(game));
                    return;
                }

                if(OverlapTester.pointInRectangle(newGameBounds, touchPoint)) {
                    //Assets.playSound(Assets.clickSound);
                    game.setScreen(new NewGameScreen(game));
                    return;
                }

                //if(OverlapTester.pointInRectangle(helpBounds, touchPoint)) {
                    //Assets.playSound(Assets.clickSound);
                    //game.setScreen(new HelpScreen(game));
                //    return;
                //}
                if(OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
                    //Assets.playSound(Assets.clickSound);
                    Settings.soundEnabled = !Settings.soundEnabled;
                    //if(Settings.soundEnabled) 
                    //    Assets.music.play();
                    //else
                    //    Assets.music.pause();
                }
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
        batcher.drawSprite(400, 280, 340, 62, Assets.newGame);
        batcher.drawSprite(400, 200, 304, 64, Assets.continueGame);
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
