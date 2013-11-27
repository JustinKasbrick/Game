package com.jkgames.game.controllers;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.gl.*;
import com.badlogic.androidgames.framework.impl.GLGraphics;

import com.jkgames.game.models.Bob;
import com.jkgames.game.models.BridgeSwitch;
import com.jkgames.game.models.Castle;
import com.jkgames.game.models.CollectorCoin;
import com.jkgames.game.models.DrawBridge;
import com.jkgames.game.models.GameTile;
import com.jkgames.game.models.ZombieBob;
import com.jkgames.game.models.Sword;
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
        if(world.bob.position.x > cam.frustumWidth/2 )
        {
        	if(world.bob.position.x > World.WORLD_WIDTH-(cam.frustumWidth/2)-1 )
        		cam.position.x = World.WORLD_WIDTH-(cam.frustumWidth/2)-1;
        	else
        		cam.position.x = world.bob.position.x;
        }
        else
        	cam.position.x = (cam.frustumWidth/2)+0.5f;
        
        if(world.bob.position.y > cam.frustumHeight/2 )
        {
        	if(world.bob.position.y > World.WORLD_HEIGHT-(cam.frustumHeight/2)-1 )
        		cam.position.y = World.WORLD_HEIGHT-(cam.frustumHeight/2)-1;
        	else
        		cam.position.y = world.bob.position.y;
        }
        else
        	cam.position.y = (cam.frustumHeight/2)+0.5f;
        
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
        renderTiles();
        renderBob();
        //renderPlatforms();
		//renderVerticalPlatforms();
        renderCollectorCoins();
        //renderDrawBridge();
        //renderBridgeSwitch();
        //renderEvilBobs();
        renderCastle();
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }
    public void renderBob(float alpha)
    {
    	GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        batcher.beginBatch(Assets.items);
        
        batcher.drawSprite(world.bob.position.x, world.bob.position.y, world.bob.direction * Bob.BOB_WIDTH, Bob.BOB_HEIGHT, Assets.bob, alpha);
        
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }
    private void renderTiles() {
		for(int i=(int)(cam.position.y-(cam.frustumHeight/2)); i<(int)(cam.position.y+(cam.frustumHeight/2)+1); i++)
			for(int j=(int)(cam.position.x-(cam.frustumWidth/2)); j<(int)(cam.position.x+(cam.frustumWidth/2)+1); j++)
			{
				if(world.tileArray[i][j] != GameTile.TILE_TYPE_NONE)
					batcher.drawSprite(j+0.5f, i+0.5f, 1, 1, world.tileFactory.getTile(world.tileArray[i][j]).getTextureRegion());
			}
		
	}

	private void renderBridgeSwitch() {
    	int len = world.bridgeSwitches.size();
        for(int i = 0; i < len; i++) {
        	BridgeSwitch bridgeSwitch = world.bridgeSwitches.get(i);
            batcher.drawSprite(bridgeSwitch.position.x, bridgeSwitch.position.y, 
            		BridgeSwitch.BRIDGE_SWITCH_WIDTH, BridgeSwitch.BRIDGE_SWITCH_HEIGHT, Assets.bridgeSwitch);
        }
	}

	private void renderDrawBridge() {
    	int len = world.drawBridges.size();
        for(int i = 0; i < len; i++) {
        	DrawBridge drawBridge = world.drawBridges.get(i);
            batcher.drawSprite(drawBridge.position.x, drawBridge.position.y, 
                               DrawBridge.DRAW_BRIDGE_WIDTH, DrawBridge.DRAW_BRIDGE_HEIGHT, Assets.drawBridge);            
        }
	}

	private void renderCollectorCoins() 
    {
    	// there is always 3 coins
        for(int i = 0; i < 3; i++) 
        {
            CollectorCoin coin = world.collectorCoins.get(i);
            TextureRegion t;
            if(coin.Collected)
                t = Assets.collectorCoin;
            else
                t = Assets.emptyCoin;
            if(!coin.Collected)
            	batcher.drawSprite(coin.position.x, coin.position.y, 
            			CollectorCoin.COLLECTOR_COIN_WIDTH, CollectorCoin.COLLECTOR_COIN_HEIGHT, t);
        }
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
        
        batcher.drawSprite(world.bob.position.x, world.bob.position.y, world.bob.direction * Bob.BOB_WIDTH, Bob.BOB_HEIGHT, Assets.bob);
        
        //if(world.bob.isAttacking)
        	keyFrame = Assets.bobSwordAttack.getKeyFrame(world.bobSword.getStateTime(), Animation.ANIMATION_NONLOOPING);
       // else
        	//keyFrame = Assets.verticalSword;

		if(world.bobSword.stateTime < 0.2f && world.bobSword.stateTime >= 0)
		{
			world.bobSword.position.x += (0.5 * world.bob.direction);
		}
        batcher.drawSprite(world.bobSword.position.x, world.bobSword.position.y, world.bob.direction * Sword.SWORD_WIDTH, Sword.SWORD_HEIGHT, keyFrame);
        
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
       int len = world.zombieBobs.size();
       for(int i = 0; i < len; i++) {
    	   ZombieBob eBob = world.zombieBobs.get(i);
           //TextureRegion keyFrame = Assets.bob.getKeyFrame(eBob.stateTime, Animation.ANIMATION_LOOPING);
           float side = eBob.velocity.x < 0?-1:1;
           batcher.drawSprite(eBob.position.x, eBob.position.y, side * 1, 1, Assets.zombieBob);
       }
    }

    private void renderCastle() {
        Castle castle = world.castle;
        batcher.drawSprite(castle.position.x, castle.position.y, 2, 2, Assets.castle);
    }
}

