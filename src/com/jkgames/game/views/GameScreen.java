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

import com.jkgames.game.controllers.World.WorldListener;
import com.jkgames.game.controllers.World;
import com.jkgames.game.controllers.Settings;
import com.jkgames.game.controllers.WorldRenderer;

import com.jkgames.game.models.Level;
import com.jkgames.game.models.Assets;

public class GameScreen extends GLScreen {
    static final int GAME_READY = 0;    
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;
	static final int NUM_LEVELS = 2;
    static final Vector2 JOY_STICK_ORIGIN = new Vector2(154, 96);
    
    int state;
	int currentLevel;
	String[] levelArray;
    Camera2D guiCam;
    Vector2 touchPoint;
    SpriteBatcher batcher;    
    World world;
	Level level;
    WorldListener worldListener;
    WorldRenderer renderer;    
    Rectangle pauseBounds;
    Rectangle resumeBounds;
    Rectangle quitBounds;
	
    Rectangle jumpBounds;
	Rectangle attackBounds;
	
	boolean jump;
	boolean attack;
	Vector2 joyStickLocation;
	Rectangle joyStickBounds;
	int joyStickId;
	
    int lastScore;
    String scoreString;

    public GameScreen(Game game) {
        super(game);
        state = GAME_READY;
        guiCam = new Camera2D(glGraphics, 800, 480);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 1000);
        worldListener = new WorldListener() {
            public void jump() {            
                //Assets.playSound(Assets.jumpSound);
            }

            public void highJump() {
                //Assets.playSound(Assets.highJumpSound);
            }

            public void hit() {
                //Assets.playSound(Assets.hitSound);
            }

            public void coin() {
                //Assets.playSound(Assets.coinSound);
            }                      
        };
		levelArray = new String[NUM_LEVELS];
		currentLevel = 0;
		for(int i=0; i<NUM_LEVELS; i++)
			levelArray[i] = "Level" + (i+1) + ".txt";
        level = new Level(game.getFileIO(), levelArray[currentLevel++]);
		world = new World(worldListener, level);
        renderer = new WorldRenderer(glGraphics, batcher, world);
        pauseBounds = new Rectangle(800- 64, 480- 64, 64, 64);
        resumeBounds = new Rectangle(400, 240 - 96, 192, 36);
        quitBounds = new Rectangle(240 - 36, 400 - 96, 192, 36);
		jumpBounds = new Rectangle(704, 64, 96, 96);
		attackBounds = new Rectangle(608, 0, 96, 96);
        lastScore = 0;
        scoreString = "score: 0";
		jump = false;
		attack = false;
		joyStickLocation = new Vector2(JOY_STICK_ORIGIN.x, JOY_STICK_ORIGIN.y);
		joyStickBounds = new Rectangle(JOY_STICK_ORIGIN.x - 64, JOY_STICK_ORIGIN.y - 64, 128, 128);
		joyStickId = -1;
    }

    @Override
    public void update(float deltaTime) {
        if(deltaTime > 0.1f)
            deltaTime = 0.1f;
        
        switch(state) {
        case GAME_READY:
            updateReady();
            break;
        case GAME_RUNNING:
            updateRunning(deltaTime);
            break;
        case GAME_PAUSED:
            updatePaused();
            break;
        case GAME_LEVEL_END:
            updateLevelEnd();
            break;
        case GAME_OVER:
            updateGameOver();
            break;
        }
    }

    private void updateReady() {
        if(game.getInput().getTouchEvents().size() > 0) {
            state = GAME_RUNNING;
        }
    }

    private void updateRunning(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            
			if(event.type == TouchEvent.TOUCH_UP)
			{	if(event.pointer == joyStickId)
				{
					joyStickLocation.x = JOY_STICK_ORIGIN.x;
					joyStickLocation.y = JOY_STICK_ORIGIN.y;
					
					joyStickId = -1;
				}
			}
			else {
				//if(event.type != TouchEvent.TOUCH_UP)
				//    continue;
				
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				
				//if(event.type == TouchEvent.TOUCH_DOWN)
				//{
//				if(OverlapTester.pointInRectangle(joyStickBounds, touchPoint))
//				{	joyStickLocation.x = touchPoint.x;
//					joyStickId = event.pointer;
//				}
				//}
				
				if(event.type == TouchEvent.TOUCH_DRAGGED)
				{
					if(OverlapTester.pointInRectangle(joyStickBounds, touchPoint))
					{
						joyStickLocation.x = touchPoint.x;
						joyStickId = event.pointer;
					}
				}
				
				if(OverlapTester.pointInRectangle(pauseBounds, touchPoint)) {
					//Assets.playSound(Assets.clickSound);
					state = GAME_PAUSED;
					return;
				}
				
				if(OverlapTester.pointInRectangle(jumpBounds, touchPoint) && jump == false) {
					//Assets.playSound(Assets.clickSound);
					jump = true;
				}
				if(OverlapTester.pointInRectangle(attackBounds, touchPoint) && attack == false) {
					//Assets.playSound(Assets.clickSound);
					attack = true;
				}
			}			
        }
        
        world.update(deltaTime, (joyStickLocation.x - JOY_STICK_ORIGIN.x)/4, jump, attack);
		jump = false;
		attack = false;
        if(world.score != lastScore) {
            lastScore = world.score;
            scoreString = "" + lastScore;
        }
        if(world.state == World.WORLD_STATE_NEXT_LEVEL) {
            state = GAME_LEVEL_END;        
        }
        if(world.state == World.WORLD_STATE_GAME_OVER) {
            state = GAME_OVER;
            if(lastScore >= Settings.highscores[4]) 
                scoreString = "new highscore: " + lastScore;
            else
                scoreString = "score: " + lastScore;
            Settings.addScore(lastScore);
            Settings.save(game.getFileIO());
        }
    }

    private void updatePaused() {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type != TouchEvent.TOUCH_UP)
                continue;
            
            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);
            
            if(OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
                //Assets.playSound(Assets.clickSound);
                state = GAME_RUNNING;
                return;
            }
            
            if(OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
                //Assets.playSound(Assets.clickSound);
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }
    }

    private void updateLevelEnd() {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {                   
            TouchEvent event = touchEvents.get(i);
            if(event.type != TouchEvent.TOUCH_UP)
                continue;
            world = new World(worldListener, new Level(game.getFileIO(), levelArray[currentLevel++]));
            renderer = new WorldRenderer(glGraphics, batcher, world);
            world.score = lastScore;
            state = GAME_READY;
        }
    }

    private void updateGameOver() {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {                   
            TouchEvent event = touchEvents.get(i);
            if(event.type != TouchEvent.TOUCH_UP)
                continue;
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        
        renderer.render();
        
        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        batcher.beginBatch(Assets.items);
        switch(state) {
        case GAME_READY:
            presentReady();
            break;
        case GAME_RUNNING:
            presentRunning();
            break;
        case GAME_PAUSED:
            presentPaused();
            break;
        case GAME_LEVEL_END:
            presentLevelEnd();
            break;
        case GAME_OVER:
            presentGameOver();
            break;
        }
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }

    private void presentReady() {
        batcher.drawSprite(400, 240, 192, 32, Assets.ready);
    }

    private void presentRunning() {
        //batcher.drawSprite(800 - 32, 480 - 32, 64, 64, Assets.pause);
		batcher.drawSprite(joyStickLocation.x, joyStickLocation.y, 128, 128, Assets.joyStick);
		batcher.drawSprite(752, 112, 96, 96, Assets.jumpButton);
		batcher.drawSprite(656, 48, 96, 96, Assets.attackButton);
        Assets.font.drawText(batcher, scoreString, 16, 480-20);
    }

    private void presentPaused() {        
        batcher.drawSprite(400, 240, 192, 96, Assets.pauseMenu);
        Assets.font.drawText(batcher, scoreString, 16, 800-20);
    }

    private void presentLevelEnd() {
        String topText = "the princess is ...";
        String bottomText = "in another castle!";
        float topWidth = Assets.font.glyphWidth * topText.length();
        float bottomWidth = Assets.font.glyphWidth * bottomText.length();
        Assets.font.drawText(batcher, topText, 160 - topWidth / 2, 480 - 40);
        Assets.font.drawText(batcher, bottomText, 160 - bottomWidth / 2, 40);
    }

    private void presentGameOver() {
        batcher.drawSprite(400, 240, 160, 96, Assets.gameOver);        
        float scoreWidth = Assets.font.glyphWidth * scoreString.length();
        Assets.font.drawText(batcher, scoreString, 240 - scoreWidth / 2, 800-20);
    }

    @Override
    public void pause() {
        if(state == GAME_RUNNING)
            state = GAME_PAUSED;
    }

    @Override
    public void resume() {        
    }

    @Override
    public void dispose() {       
    }
}
