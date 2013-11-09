package com.jkgames.game.models;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

import com.badlogic.androidgames.framework.FileIO;

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
