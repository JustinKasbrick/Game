package com.jkgames.game.views;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;
import com.jkgames.game.controllers.Settings;
import com.jkgames.game.models.Assets;
import com.jkgames.game.models.SaveFile;

import javax.microedition.khronos.opengles.GL10;
import java.util.List;

public class ContinueScreen extends GLScreen
{
    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle soundBounds;
    Vector2 touchPoint;
    Rectangle[] saveFileBounds = {new Rectangle(400-168, 336-32, 336, 64),
            new Rectangle(400-168, 240-32, 336, 64),
            new Rectangle(400-168, 144-32, 336, 64)};

    public ContinueScreen(Game game) {
        super(game);

        for(int i=0; i<3; i++)
        {
            Settings.saveFiles[i] = (SaveFile)Settings.readSaveFile(i);
        }
        guiCam = new Camera2D(glGraphics, 800, 480);
        batcher = new SpriteBatcher(glGraphics, 100, false);
        soundBounds = new Rectangle(0, 0, 64, 64);
        touchPoint = new Vector2();
    }

    @Override
    public void update(float deltaTime)
    {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);

                if(OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
                    //Assets.playSound(Assets.clickSound);
                    Settings.soundEnabled = !Settings.soundEnabled;
                    //if(Settings.soundEnabled)
                    //    Assets.music.play();
                    //else
                    //    Assets.music.pause();
                }

                for(int j=0; i<3; j++)
                {
                    if(OverlapTester.pointInRectangle(saveFileBounds[j], touchPoint)) {
                        //Assets.playSound(Assets.clickSound);

                        game.setScreen(new GameScreen(game, j));
                        return;
                    }
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


        batcher.drawSprite(400, 336, 336, 64, Assets.saveBar);
        Assets.font.drawText(batcher, Settings.saveFiles[0].summaryData, 250, 336);
        batcher.drawSprite(400, 240, 336, 64, Assets.saveBar);
        Assets.font.drawText(batcher, Settings.saveFiles[1].summaryData, 250, 240);
        batcher.drawSprite(400, 144, 336, 64, Assets.saveBar);
        Assets.font.drawText(batcher, Settings.saveFiles[2].summaryData, 250, 144);

        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void pause() {
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {
        for(int i=0; i<3; i++)
        {
            Settings.saveFiles[i] = (SaveFile)Settings.readSaveFile(i);
        }
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
