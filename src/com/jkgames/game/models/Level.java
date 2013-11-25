package com.jkgames.game.models;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

import com.badlogic.androidgames.framework.FileIO;
import com.badlogic.androidgames.framework.math.Vector2;

public class Level {
    public String text;
    boolean accessible;
    public byte coinsArray[];
    public Vector2 position;
    
    public Level(byte accessible, byte c1, byte c2, byte c3, Vector2 pos) {
		this.text = "";
        if(accessible == 1)
            this.accessible = true;
        else
            this.accessible = false;

        coinsArray = new byte[] {c1, c2, c3};
        position = pos;
    }
    
    public void load(FileIO fileIO, String fileName) {
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
    }

	private String loadTextFile(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[4096];
        int len = 0;
        while ((len = inputStream.read(bytes)) > 0)
            byteStream.write(bytes, 0, len);
        return new String(byteStream.toByteArray(), "UTF8");
    }

    public boolean isAccessible() {
        return accessible;
    }
}

