package com.jkgames.game.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.badlogic.androidgames.framework.FileIO;
import com.jkgames.game.models.GameState;
import com.jkgames.game.models.SaveFile;

public class Settings 
{
	public static boolean soundEnabled = false;
	public static GameState gameState = new GameState(1);
	public static SaveFile[] saveFiles = {new SaveFile(), new SaveFile(), new SaveFile()};

	public static void load(FileIO files) 
	{
		BufferedReader in = null;
		try 
		{
			in = new BufferedReader(new InputStreamReader(
			files.readFile(".game")));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			for (int i = 0; i < 3; i++) {
                saveFiles[i].numCoinsCollected = Integer.parseInt(in.readLine());
                saveFiles[i].percentComplete = Float.parseFloat(in.readLine());
                saveFiles[i].currentLevel = Integer.parseInt(in.readLine());
                saveFiles[i].empty = Boolean.parseBoolean(in.readLine());
				saveFiles[i].setSummaryData(in.readLine());
			}
//			int temp;
//			for(int i = 0; i < 1; i++)
//			{
//				temp =  Integer.parseInt(in.readLine());
//				for (int j = 0; j < 4; j++)
//				{
//					gameState.levelArray[i][j] = temp % 10;
//				}
//					//gameState.levelArray[i][j] = Integer.parseInt(in.readLine());
//			}
		} 
		catch (IOException e) {
			// :( It's OK we have defaults
		} 
		catch (NumberFormatException e) {
			// :/ It's OK, defaults save our day
		} 
		finally 
		{
			try 
			{
				if (in != null)
				in.close();
			} 
			catch (IOException e) {
			}
		}
	}

	public static void save(FileIO files) 
	{
		BufferedWriter out = null;
		try 
		{
			out = new BufferedWriter(new OutputStreamWriter(
			files.writeFile(".game")));
			
			// write sound settings
			out.write(Boolean.toString(soundEnabled));

            // write saveFile
            for (int i = 0; i < 3; i++) {
                out.write(Integer.toString(saveFiles[i].numCoinsCollected));
                out.write(Float.toString(saveFiles[i].percentComplete));
                out.write(Integer.toString(saveFiles[i].currentLevel));
                out.write(Boolean.toString(saveFiles[i].empty));
                out.write(saveFiles[i].summaryData);
            }

			// write high scores
//			for (int i = 0; i < 5; i++) {
//				out.write(saveFiles[i].summaryData + "\n");
//			}
			// write level state
//			for (int i = 0; i < 1; i++)
//			{
//				for (int j = 0; j < 4; j++)
//				{
//					out.write(Integer.toString(gameState.levelArray[i][j]));
//				}
//				out.write("\n");
//			}
		} 
		catch (IOException e) 
		{
		} 
		finally 
		{
			try 
			{
				if (out != null)
				out.close();
			} 
			catch (IOException e) {
			}
		}
	}

    public static void saveFile(SaveFile file, int index)
    {

    }
}