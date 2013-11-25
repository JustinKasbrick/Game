package com.jkgames.game.models;

import com.badlogic.androidgames.framework.Sound;
import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.Font;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

import com.jkgames.game.controllers.Settings;

public class Assets {
    public static Texture background;
    public static TextureRegion backgroundRegion;

    public static Texture world;
    public static TextureRegion worldRegion;

    public static Texture items;        
    public static TextureRegion mainMenu;
    public static TextureRegion pauseMenu;
    public static TextureRegion ready;
    public static TextureRegion gameOver;
    public static TextureRegion newGame;
    public static TextureRegion continueGame;

    public static TextureRegion saveBar;
    public static TextureRegion levelCircle;
    public static TextureRegion star;
    //public static TextureRegion logo;
    public static TextureRegion soundOn;
    public static TextureRegion soundOff;
    //public static TextureRegion arrow;
    public static TextureRegion pause;    
    //public static TextureRegion spring;
    //public static Animation coinAnim;
    //public static Animation bobJump;
    //public static Animation bobFall;
    public static Animation bobSwordAttack;
    
    public static TextureRegion bob;
    public static TextureRegion bob_wSword;
    public static TextureRegion zombieBob;
    
    public static TextureRegion castle;
    public static TextureRegion collectorCoin;
    public static TextureRegion emptyCoin;
    public static TextureRegion drawBridge;
    public static TextureRegion bridgeSwitch;
    
    public static TextureRegion joyStick;
    public static TextureRegion jumpButton;
    public static TextureRegion attackButton;
    
    public static TextureRegion verticalSword;
    public static TextureRegion horizontalSword;
    
    public static TextureRegion platform;
	public static TextureRegion vPlatform;
	public static TextureRegion shortPlatform;
	public static TextureRegion bridgeTile;
	public static TextureRegion dirtTile;
	public static TextureRegion grassTile;
	public static TextureRegion waterTile;
	public static TextureRegion solidWaterTile;
	
    public static Font font;
    
    //public static Music music;
    //public static Sound jumpSound;
    //public static Sound highJumpSound;
    //public static Sound hitSound;
    //public static Sound coinSound;
    //public static Sound clickSound;

    public static void load(GLGame game) {
        background = new Texture(game, "glBackground.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 800, 480);

        world = new Texture(game, "world.png");
        worldRegion = new TextureRegion(world, 0, 0, 512, 256);

        items = new Texture(game, "items.png");        
        mainMenu = new TextureRegion(items, 0, 161, 128, 32);
        pauseMenu = new TextureRegion(items, 192, 128, 192, 64);
        ready = new TextureRegion(items, 0, 128, 192, 32);
        gameOver = new TextureRegion(items, 0, 194, 128, 64);
        newGame = new TextureRegion(items, 160, 228, 170, 31);
        continueGame = new TextureRegion(items, 191, 258, 152, 32);
        saveBar = new TextureRegion(items, 0, 323, 168, 32);
        levelCircle = new TextureRegion(items, 165, 165, 32, 28);
        star = new TextureRegion(items, 356, 202, 15, 15);
        //highScoresRegion = new TextureRegion(Assets.items, 0, 257, 300, 110 / 3);
        //logo = new TextureRegion(items, 0, 352, 274, 142);
        //soundOff = new TextureRegion(items, 0, 0, 64, 64);
        //soundOn = new TextureRegion(items, 64, 0, 64, 64);
        //arrow = new TextureRegion(items, 0, 64, 64, 64);
        //pause = new TextureRegion(items, 64, 64, 64, 64);
        
        //spring = new TextureRegion(items, 128, 0, 32, 32);
        //castle = new TextureRegion(items, 128, 64, 64, 64);
        //coinAnim = new Animation(0.2f,                                 
        //                         new TextureRegion(items, 128, 32, 32, 32),
        //                         new TextureRegion(items, 160, 32, 32, 32),
        //                         new TextureRegion(items, 192, 32, 32, 32),
        //                         new TextureRegion(items, 160, 32, 32, 32));
        //bobJump = new Animation(0.2f,
        //                        new TextureRegion(items, 0, 128, 32, 32),
        //                        new TextureRegion(items, 32, 128, 32, 32));
        //bobFall = new Animation(0.2f,
        //                        new TextureRegion(items, 64, 128, 32, 32),
        //                        new TextureRegion(items, 96, 128, 32, 32));
        bobSwordAttack = new Animation(0.065f,
									new TextureRegion(items, 129, 194, 32, 33),
        							//new TextureRegion(items, 161, 193, 33, 33),
        							//new TextureRegion(items, 194, 193, 33, 33),
        							new TextureRegion(items, 230, 194, 32, 33),
        							new TextureRegion(items, 129, 194, 32, 33));
        bob = new TextureRegion(items, 419, 1, 28, 30);
        bob_wSword = new TextureRegion(items, 385, 128, 56, 55);
        zombieBob = new TextureRegion(items, 421, 70, 24, 29);
        castle = new TextureRegion(items, 265, 193, 46, 33);
        collectorCoin = new TextureRegion(items, 319, 202, 17, 17);
        emptyCoin = new TextureRegion(items, 337, 202, 17, 17);
        drawBridge = new TextureRegion(items, 0, 259, 160, 32);
        bridgeSwitch = new TextureRegion(items, 0, 291, 32, 32);
        //squirrelFly = new Animation(0.2f, 
        //                            new TextureRegion(items, 0, 160, 32, 32),
        //                            new TextureRegion(items, 32, 160, 32, 32));
        platform = new TextureRegion(items, 0, 0, 160, 32);
		vPlatform = new TextureRegion(items, 64, 32, 32, 96);
		shortPlatform = new TextureRegion(items, 96, 32, 64, 32);
		bridgeTile = new TextureRegion(items, 128, 228, 32, 31);
		dirtTile = new TextureRegion(items, 0, 32, 32, 32);
		grassTile = new TextureRegion(items, 0, 64, 32, 32);
		waterTile = new TextureRegion(items, 32, 32, 32, 32);
		solidWaterTile = new TextureRegion(items, 32, 64, 32, 32);
        
        joyStick = new TextureRegion(items, 448, 0, 64, 64);
        jumpButton = new TextureRegion(items, 448, 64, 64, 64);
        attackButton = new TextureRegion(items, 448, 128, 64, 64);
        verticalSword = new TextureRegion(items, 437, 32, 10, 34);
        horizontalSword = new TextureRegion(items, 128, 162, 34, 10);
        font = new Font(items, 160, 0, 16, 16, 20);
        
        //music = game.getAudio().newMusic("music.mp3");
        //music.setLooping(true);
        //music.setVolume(0.5f);
        //if(Settings.soundEnabled)
        //    music.play();
        //jumpSound = game.getAudio().newSound("jump.ogg");
        //highJumpSound = game.getAudio().newSound("highjump.ogg");
        //hitSound = game.getAudio().newSound("hit.ogg");
        //coinSound = game.getAudio().newSound("coin.ogg");
        //clickSound = game.getAudio().newSound("click.ogg");       
    }       

    public static void reload() {
        background.reload();
        items.reload();
        //if(Settings.soundEnabled)
        //    music.play();
    }

    public static void playSound(Sound sound) {
        if(Settings.soundEnabled)
            sound.play(1);
    }
}
