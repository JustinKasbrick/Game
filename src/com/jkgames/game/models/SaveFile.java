package com.jkgames.game.models;

import android.R;
import com.jkgames.game.controllers.Settings;

import java.io.Serializable;

public class SaveFile implements Serializable {
    private static final long serialVersionUID = 3L;
    static final float TOTAL = 4*3;

    public int currentLevel;
    public int percentComplete;
    public boolean empty;
    public int numCoinsCollected;
    public String summaryData;
    public byte[] coinsCollected;

	public SaveFile() {
		empty = true;
        currentLevel = 0;
        percentComplete = 0;
        numCoinsCollected = 0;
        summaryData = "Empty";
        coinsCollected = new byte[100];
	}

    public void setSummaryData(String summary)
    {
        if(summary != null || summary != "")
            summaryData = summary;
    }

    public void save(int currentLevel)
    {
        if(empty)
            empty = false;
        this.currentLevel = currentLevel;

        percentComplete = (int)((numCoinsCollected + currentLevel)/TOTAL * 100);

        summaryData = "Level " + currentLevel + " C:" + numCoinsCollected + " " + percentComplete + "%";
    }

    public void coinCollected(int level, int coinPositionInLevel)
    {
        coinsCollected[((level) * 4) + coinPositionInLevel] = 1;
        numCoinsCollected++;
    }



	public void LoadDisplayData()
	{
	}
}
