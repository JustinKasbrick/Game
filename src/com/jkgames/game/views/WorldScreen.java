package com.jkgames.game.views;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;
import com.jkgames.game.controllers.Settings;
import com.jkgames.game.models.Assets;
import com.jkgames.game.models.Level;

import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;
import java.util.List;

public class WorldScreen extends GLScreen
{
    static final int NUMBER_OF_LEVELS = 4;
    Vector2[] levelPositionArray;
    Rectangle[] levelBoundsArray;
    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle soundBounds;
    Vector2 touchPoint;
    public final List<Level> levels;
    int starOffSet;
    int saveFileNumber;

    public WorldScreen(Game game, int saveFileNumber) {
        super(game);
        levelPositionArray = new Vector2[]{new Vector2(120, 90), new Vector2(400, 140), new Vector2(600, 110), new Vector2(350, 295)};
        levelBoundsArray = new Rectangle[]{new Rectangle(120-16, 90-16, 32, 32), new Rectangle(400-16, 140-16, 32, 32),
                                           new Rectangle(600-16, 110 -16, 32, 32), new Rectangle(350-16, 295-16, 32, 32)};
        guiCam = new Camera2D(glGraphics, 800, 480);
        batcher = new SpriteBatcher(glGraphics, 100, false);
        soundBounds = new Rectangle(0, 0, 64, 64);
        touchPoint = new Vector2();
        this.saveFileNumber = saveFileNumber;
        levels = new ArrayList<Level>();
    }


    @Override
    public void update(float deltaTime) {
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

                for(int j=0; i<levelBoundsArray.length; j++)
                {
                    if(OverlapTester.pointInRectangle(levelBoundsArray[j], touchPoint)) {
                        //Assets.playSound(Assets.clickSound);

                        levels.get(j).load(game.getFileIO(), "Level"+j+".txt");
                        game.setScreen(new GameScreen(game, saveFileNumber, levels.get(j)));
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

        batcher.beginBatch(Assets.world);
        batcher.drawSprite(400, 240, 800, 480, Assets.worldRegion);
        batcher.endBatch();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.items);

        starOffSet = -15;
        for(int i=0; i<levels.size(); i++)
        {
            batcher.drawSprite(levels.get(i).position.x, levels.get(i).position.y, 32, 32, Assets.levelCircle);
            for(int j=0; j<3; j++)
            {
                if(levels.get(i).coinsArray[j] == 1)
                    batcher.drawSprite(levels.get(i).position.x + starOffSet, levels.get(i).position.y, 15, 15, Assets.star);
                starOffSet += 15;
            }
        }
        batcher.drawSprite(levels.get(Settings.saveFiles[saveFileNumber].currentLevel).position.x+5,
                levels.get(Settings.saveFiles[saveFileNumber].currentLevel).position.y+15,
                32, 32, Assets.bob);
        //batcher.drawSprite(32, 32, 64, 64, Settings.soundEnabled?Assets.soundOn:Assets.soundOff);

        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void pause() {
        Settings.writeSaveFile(game.getFileIO(), saveFileNumber);
    }

    @Override
    public void resume() {
        int coinOffset = 4;
        Settings.readSaveFile(game.getFileIO(), saveFileNumber);
        for (int i=0; i<NUMBER_OF_LEVELS; i++)

                levels.add(i, new Level(Settings.saveFiles[saveFileNumber].coinsCollected[i*coinOffset],
                        Settings.saveFiles[saveFileNumber].coinsCollected[i*coinOffset+1],
                        Settings.saveFiles[saveFileNumber].coinsCollected[i*coinOffset+2],
                        Settings.saveFiles[saveFileNumber].coinsCollected[i*coinOffset+3],
                        levelPositionArray[i]));
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
