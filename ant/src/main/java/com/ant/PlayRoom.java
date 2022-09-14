package com.ant;

import javax.swing.*;
import java.awt.*;

/*
    启动类
 */
public class PlayRoom {

    public static void start() {
    
        for(int i = 0; i < 32; i++){
            CreepingGame creepingGame = new CreepingGame(i,0);
            creepingGame.start();
        }
    }

    public static void main(String[] args) {
        PlayRoom.start();
    }
}
