package com.jkgames.game.controllers;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGraphics;

import com.jkgames.game.models.Bob;
import com.jkgames.game.models.ZombieBob;
import com.jkgames.game.models.Platform;
import com.jkgames.game.models.Sword;
import com.jkgames.game.models.VerticalPlatform;
import com.jkgames.game.models.Assets;

public class WorldRenderer {
    static final float FRUSTUM_WIDTH = 15;
    static final float FRUSTUM_HEIGHT = 10;    
    GLGraphics glGraphics;
    World world;
    Camera2D cam;
    SpriteBatcher batcher;    
    
    public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
        this.glGraphics = glGraphics;
        this.world = world;
        this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.batcher = batcher;
    }
    
    public void render() {
        //if(world.bob.position.x > cam.position.x )
        	cam.position.x = world.bob.position.x;
        cam.setViewportAndMatrices();
        renderBackground();
        renderObjects();
    }

    public void renderBackground() {
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(cam.position.x, cam.position.y,
                           FRUSTUM_WIDTH, FRUSTUM_HEIGHT, 
                           Assets.backgroundRegion);
        batcher.endBatch();
    }

    public void renderObjects() {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        batcher.beginBatch(Assets.items);
        renderBob();
        renderPlatforms();
		renderVerticalPlatforms();
        //renderItems();
        renderEvilBobs();
        //renderCastle();
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }

    private void renderBob() {
        TextureRegion keyFrame;
        //switch(world.bob.state) {
        //case Bob.BOB_STATE_FALL:
        //    keyFrame = Assets.bobFall.getKeyFrame(world.bob.stateTime, Animation.ANIMATION_LOOPING);
        //    break;
        //case Bob.BOB_STATE_JUMP:
        //    keyFrame = Assets.bobJump.getKeyFrame(world.bob.stateTime, Animation.ANIMATION_LOOPING);
        //    break;
        //case Bob.BOB_STATE_HIT:
        //default:
        //    keyFrame = Assets.bobHit;                       
        //}
        
        float side = world.bob.velocity.x < 0? -1: 1;
        batcher.drawSprite(world.bob.position.x, world.bob.position.y, side * Bob.BOB_WIDTH, Bob.BOB_HEIGHT, Assets.bob);
        
        //if(world.bob.isAttacking)
        	keyFrame = Assets.bobSwordAttack.getKeyFrame(world.bobSword.getStateTime(), Animation.ANIMATION_NONLOOPING);
       // else
        	//keyFrame = Assets.verticalSword;
        
        batcher.drawSprite(world.bobSword.position.x, world.bobSword.position.y, side * Sword.SWORD_WIDTH, Sword.SWORD_HEIGHT, keyFrame);
        
    }
    

    private void renderPlatforms() {
        int len = world.platforms.size();
        for(int i = 0; i < len; i++) {
            Platform platform = world.platforms.get(i);
            TextureRegion keyFrame = Assets.platform;
            //if(platform.state == Platform.PLATFORM_STATE_PULVERIZING) {                
            //    keyFrame = Assets.brakingPlatform.getKeyFrame(platform.stateTime, Animation.ANIMATION_NONLOOPING);
            //}            
                                   
            batcher.drawSprite(platform.position.x, platform.position.y, 
                               5, 1, keyFrame);            
        }
    }
	
	private void renderVerticalPlatforms()
	{
		int len = world.vPlatforms.size();
        for(int i = 0; i < len; i++) 
		{
			VerticalPlatform vPlatform = world.vPlatforms.get(i);
				batcher.drawSprite(vPlatform.position.x, vPlatform.position.y, 1, 3, Assets.vPlatform); 
		}
	}
    //private void renderItems() {
    //    int len = world.springs.size();
    //    for(int i = 0; i < len; i++) {
    //        Spring spring = world.springs.get(i);            
    //        batcher.drawSprite(spring.position.x, spring.position.y, 1, 1, Assets.spring);
    //    }
        
    //    len = world.coins.size();
    //    for(int i = 0; i < len; i++) {
    //        Coin coin = world.coins.get(i);
    //        TextureRegion keyFrame = Assets.coinAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
    //        batcher.drawSprite(coin.position.x, coin.position.y, 1, 1, keyFrame);
    //    }
    //}

    private void renderEvilBobs() {
       int len = world.evilBobs.size();
       for(int i = 0; i < len; i++) {
    	   ZombieBob eBob = world.evilBobs.get(i);
           //TextureRegion keyFrame = Assets.bob.getKeyFrame(eBob.stateTime, Animation.ANIMATION_LOOPING);
           float side = eBob.velocity.x < 0?-1:1;
           batcher.drawSprite(eBob.position.x, eBob.position.y, side * 1, 1, Assets.bob);
       }
    }

    //private void renderCastle() {
    //    Castle castle = world.castle;
    //    batcher.drawSprite(castle.position.x, castle.position.y, 2, 2, Assets.castle);
    //}
}

