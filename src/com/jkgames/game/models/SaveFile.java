package com.jkgames.game.models;

import android.R;

public class SaveFile {

    int currentLevel;
    float percentComplete;
    boolean empty;
    public String summaryData;

	public SaveFile() {
		empty = true;
        summaryData = "Empty";
	}

    public void setSummaryData(String summary)
    {
        if(summary != null)
            summaryData = summary;
    }

	public void LoadDisplayData()
	{
	}
}
