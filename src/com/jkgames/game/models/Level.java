package com.jkgames.game.models;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.badlogic.androidgames.framework.FileIO;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;

public class Level {
    FileIO fileIO;
    String fileName;
	public String text;
    
    
    public Level(FileIO f_IO, String fileName) {
        this.fileIO = f_IO;
        this.fileName = fileName;
		this.text = "";
        load();
    }
    
    private void load() {
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
}
