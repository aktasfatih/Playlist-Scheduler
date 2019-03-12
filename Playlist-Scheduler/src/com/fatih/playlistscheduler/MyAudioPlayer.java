package com.fatih.playlistscheduler;

import java.io.*;
import javazoom.jl.player.*;

public class MyAudioPlayer extends Thread {

    private String fileLocation;
    private boolean loop;
    private Player prehravac;
    public int Playing;

    public MyAudioPlayer(String fileLocation, boolean loop) {
        this.fileLocation = fileLocation;
        this.loop = loop;
    }

    public void run() {

        try {
            do {
                FileInputStream buff = new FileInputStream(fileLocation);
                prehravac = new Player(buff);
                prehravac.play();
            } while (loop);
        } catch (Exception ioe) {
            // TODO error handling
        }
    }

    public void close(){
        loop = false;
        prehravac.close();
        this.interrupt();
    }
}