package com.jkgames.game.models;

import android.R;
import com.jkgames.game.controllers.Settings;

public class SaveFile {

    static final float TOTAL = 4*3;

    public int currentLevel;
    public float percentComplete;
    public boolean empty;
    public int numCoinsCollected;
    public String summaryData;

	public SaveFile() {
		empty = true;
        currentLevel = 0;
        percentComplete = 0;
        numCoinsCollected = 0;
        summaryData = "Empty";
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

        percentComplete = (numCoinsCollected + currentLevel)/TOTAL;

        summaryData = "Level " + currentLevel + "; Coins " + numCoinsCollected + "; " + percentComplete + "% Complete";
    }

    public void incrementCoinsCollect()
    {
        numCoinsCollected++;
    }



	public void LoadDisplayData()
	{
	}
}
