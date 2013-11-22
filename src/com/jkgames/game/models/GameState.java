package com.jkgames.game.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.badlogic.androidgames.framework.FileIO;
import com.badlogic.androidgames.framework.math.Vector2;

public class GameState 
{
	FileIO fileIO;
    String fileName;
	public String text;
	int numLevels;
	public int[][] levelArray;
	public Vector2 bobPosition;
	public boolean inLevel = false;
	
	public GameState (int numLevels)
	{
		this.numLevels = numLevels;
		levelArray = new int[numLevels][4];
	}
	
	public void updateLevelCoinState(int level, int coin, int value)
	{
		levelArray[level][coin] = value;
	}
	
	public void LoadGameState(String fileName)
	{
		InputStream in = null;
        try {
            in = fileIO.readAsset(fileName);
            text = loadTextFile(in);
        } catch(IOException e) {
            throw new RuntimeException("Couldn't load file '" + fileName +"'", e);
        } finally {
            if(in != null)
                try { in.close(); } catch (IOException e) { }
        }
        
        int start = 0;
        for(int i=0; i<numLevels; i++)
        {
        	for(int j=0; j<4; j++)
        	{
        		levelArray[i][j] = Integer.parseInt(text.substring(start, start+4));
        		start += 4;
        	}
        }
	}
	
	public void SaveGameState()
	{
		
	}
	
	private String loadTextFile(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[4096];
        int len = 0;
        while ((len = inputStream.read(bytes)) > 0)
            byteStream.write(bytes, 0, len);
        return new String(byteStream.toByteArray(), "UTF8");
    }
}
