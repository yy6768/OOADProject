package com.ant.ant;

import java.util.ArrayList;
import java.util.List;

/**
    启动类
    继承PlayingRoomFrame
    重写PlayingRoomFrame的类
 **/
public class PlayRoom {
    private static final List<Integer> timestamps = new ArrayList<>();
    public static void start() {
        for(int i = 0; i < 32; i++) {
            CreepingGame creepingGame = new CreepingGame(i,0);
            Integer res = creepingGame.start();
            timestamps.add(res);
        }
        timestamps.forEach(integer -> System.out.println(integer));
    }

    public static void main(String[] args) {
        PlayRoom.start();
    }
}
