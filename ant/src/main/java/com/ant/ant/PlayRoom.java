package com.ant.ant;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 启动类
 * 生成一个JFrame的窗口并且启动一个CreepingGame的实例
 * 会记录max_timestamp和minTimestamp
 **/
public class PlayRoom {

    /*
        4个常量：
        resultTimestamp指向一个数组，管理当前所有的游戏时间结果
        HEIGHT表示绘制时默认的窗口高度
        WIDTH表示绘制时默认的窗口宽度
        INC_TIME表示显示画面两帧之间的间隔
        gameRoomFrame表示当前的显示框体
     */
    private static final List<Integer> resultTimestamp = new ArrayList<>();
    public static final Integer HEIGHT = 800;
    public static final Integer WIDTH = 1200;
    public static final Integer INC_TIME = 100;
    public static final JFrame gameRoomFrame = new JFrame();

    /*
        2个timestamp分别记录最小和最大的时间戳
     */
    public static Integer maxTimestamp = null;
    public static Integer minTimestamp = null;

    public static void start() {
        /*
            创建一个窗体
            设置窗体的高度和宽度
         */
        gameRoomFrame.setSize(WIDTH, HEIGHT);
        gameRoomFrame.setVisible(true);
        /*
            枚举32种朝向的情况
            每一场游戏创建一个creepGame示例
            CreepGame继承了JPanel
            每一场游戏作为一个组件加入到窗体中
         */
        for (int i = 0; i < 32; i++) {
            CreepingGame creepingGame = new CreepingGame(i, INC_TIME);
            gameRoomFrame.add(creepingGame);
            Integer res = creepingGame.start();
            gameRoomFrame.remove(creepingGame);
            /*
                更新所有结果
             */
            resultTimestamp.add(res);
            if (maxTimestamp == null || maxTimestamp < res) maxTimestamp = res;
            if (minTimestamp == null || minTimestamp > res) minTimestamp = res;
        }
    }


    public static void main(String[] args) {
        PlayRoom.start();
    }
}
