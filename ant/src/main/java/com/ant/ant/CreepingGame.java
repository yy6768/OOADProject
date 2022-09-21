package com.ant.ant;

import com.ant.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 游戏类
 * 需要管理每一次游戏的所有对象：5只蚂蚁和木棍
 * 需要记录时间：timestamp
 * 需要管理画面的绘制：继承了JPanel类相当于框体里的画布
 */
public class CreepingGame extends JPanel {
    /*
        地图应该有的变量：id
        蚂蚁对象的列表：ants
        木棍对象：stick
        绘制时间：inc_time
        当前时间：timestamp
     */
    private Integer id;
    private List<Ant> ants;
    private Stick stick;
    private Integer inc_time;
    private Integer timestamp;

    /*
        需要的常量
        开始的位置：START_POSITION
        木棍的起始长度：STICK_LENGTH
        蚂蚁的数量：ANT_NUM
        蚂蚁的图片：LEFT_ANT和RIGHT_ANT
     */
    public static final Integer[] START_POSITION = {30, 80, 110, 160, 250};
    public static final Integer STICK_LENGTH = 300;
    public static final Integer ANT_NUM = 5;
    public static final Integer ANT_VELOCITY = 5;
    public static final Image LEFT_ANT = ImageUtil.getImage("Ant_0.jpg");
    public static final Image RIGHT_ANT = ImageUtil.getImage("Ant_1.jpg");


    /**
     * 游戏类的构造函数
     *
     * @param id
     * @param inc_time 负责初始化蚂蚁和木棍对象
     *                 根据游戏id化成2进制字符串来获得不同蚂蚁的起始方向
     */
    public CreepingGame(Integer id,
                        Integer inc_time) {
        //初始化
        this.id = id;
        this.timestamp = 0;
        this.inc_time = inc_time;
        String directions = String.format("%5s", Integer.toBinaryString(this.id));
        directions = directions.replaceAll("\\s", "0");
        ants = new LinkedList<>();
        for (int i = 0; i < ANT_NUM; i++) {
            int d = directions.charAt(i) - '0';
            Image image = RIGHT_ANT;
            if (d == 0) {
                d = -1;
                image = LEFT_ANT;
            }
            ants.add(new Ant(i, d, START_POSITION[i], ANT_VELOCITY, image));
        }
        stick = new Stick(0, STICK_LENGTH);
    }

    /**
     * 终止函数
     *
     * @return 返回是否结束
     */
    private boolean isDone() {
        return ants.isEmpty();
    }

    /**
     * 启动函数
     * 判断是否结束->isDone函数
     * 如果没技术就进行更新->update函数
     * 值得注意的是开始之前要对JFrame做一次更新->updateUI() JPanel类的内置函数
     *
     * @return
     */
    public Integer start() {
        updateUI();//重新刷新框体
        while (!isDone()) {
            update();
        }
        return timestamp;
    }

    /**
     * 判断是否发生碰撞
     * 先计算出蚂蚁碰撞前和碰撞后的位置
     * 枚举两只蚂蚁A和B，如果上一秒A在B左侧，下一秒B在A左侧认为发生了碰撞
     *
     * @param old_position
     * @param new_position
     */
    private void checkCollision(Integer[] old_position, Integer[] new_position) {
        for (int i = 0; i < ants.size(); i++) {
            for (int j = i + 1; j < ants.size(); j++) {
                Ant antA = ants.get(i);
                Ant antB = ants.get(j);
                if (antA.getDirection() * antB.getDirection() == -1
                        && ((old_position[i] < old_position[j] && new_position[i] >= new_position[j]) ||
                        old_position[j] < old_position[i] && new_position[j] >= new_position[i])) {
                    antA.setDirection(-1 * antA.getDirection());
                    if (antA.getImage().equals(RIGHT_ANT)) antA.setImage(LEFT_ANT);
                    else antA.setImage(RIGHT_ANT);
                    antB.setDirection(-1 * antB.getDirection());
                    if (antB.getImage().equals(RIGHT_ANT)) antB.setImage(LEFT_ANT);
                    else antB.setImage(RIGHT_ANT);
                }
            }
        }
    }

    /**
     * 判断是否有蚂蚁越界
     * 如果发现越界，就直接从ants中移除该蚂蚁
     */
    private void checkBeyondEdge() {
        int leftEnd = stick.getLeftEnd();
        int rightEnd = stick.getRightEnd();

        for (Iterator<Ant> it = ants.iterator(); it.hasNext(); ) {
            Ant ant = it.next();
            int pos = ant.getPos();
            if (pos <= leftEnd || pos >= rightEnd) {
                it.remove();
            }
        }
    }

    /**
     * 在当前Panel上绘制文字信息
     * 绘制最大、最小和当前游戏的时间戳信息
     *
     * @param g
     */
    private void drawInfo(Graphics g) {
        String maxStr = "最长的时间:" + PlayRoom.maxTimestamp;
        String minStr = "最短的时间:" + PlayRoom.minTimestamp;
        String timeStr = "当前的时间:" + timestamp;
        g.setColor(Color.black);
        Integer frameHeight = PlayRoom.gameRoomFrame.getHeight();
        Integer fontSize = Math.max(40, frameHeight / 20);
        g.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
        g.drawString(maxStr, 0, fontSize);
        g.drawString(minStr, 0, fontSize * 2);
        g.drawString(timeStr, 0, fontSize * 3);
    }


    /**
     * 绘制函数
     * 每一次需要绘制的对象有：
     * 文字drawInfo
     * 蚂蚁 ants.get(i).draw
     * 木棍stick.draw
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawInfo(g);
        Integer frameHeight = PlayRoom.gameRoomFrame.getHeight();
        for (int i = 0; i < ants.size(); i++) {
            ants.get(i).draw(g, frameHeight / 2);
        }
        stick.draw(g, frameHeight / 2);
    }

    /**
     * 更新函数
     * 更新时间戳 timestamp++;
     * 计算蚂蚁新位置和旧位置
     * 判断是否碰撞 checkCollision(old_position, new_position);
     * 每只蚂蚁更新 ant.creep();
     * 判断是否越界 checkBeyondEdge();
     * 绘制  repaint();
     * 等待 Thread.sleep(inc_time);
     */
    private void update() {
        //时间戳加1
        timestamp++;
        Integer[] old_position = new Integer[ANT_NUM];
        Integer[] new_position = new Integer[ANT_NUM];
        for (int i = 0; i < ants.size(); i++) {
            Ant ant = ants.get(i);
            old_position[i] = ant.getPos();
            new_position[i] = old_position[i] + ant.getVelocity() * ant.getDirection();
        }
        checkCollision(old_position, new_position);
        for (Ant ant : ants) ant.creep();
        checkBeyondEdge();
        repaint();
        try {
            Thread.sleep(inc_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
