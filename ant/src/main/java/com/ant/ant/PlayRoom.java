package com.ant.ant;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 启动类
 * 继承PlayingRoomFrame
 * 重写PlayingRoomFrame的类
 **/
public class PlayRoom {
    private static final List<Integer> resultTimestamp = new ArrayList<>();
    private static final Integer HEIGHT = 800;
    private static final Integer WIDTH = 1200;
    private static final Integer INC_TIME = 1000;
    private static Integer max_timestamp = null;
    private static Integer min_timestamp = null;

    public static void start() {
        JFrame frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        for (int i = 0; i < 32; i++) {
            CreepingGame creepingGame = new CreepingGame(i, INC_TIME,max_timestamp,min_timestamp);
            frame.add(creepingGame);
            Integer res = creepingGame.start();
            frame.remove(creepingGame);
            resultTimestamp.add(res);
            if(max_timestamp == null || max_timestamp < res) max_timestamp = res;
            if(min_timestamp == null || min_timestamp > res) min_timestamp = res;
        }
    }

    public static void updateInfo(){


    }

    public static void main(String[] args) {
        PlayRoom.start();
    }
}
