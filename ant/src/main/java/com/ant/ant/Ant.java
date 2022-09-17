package com.ant.ant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * Ant类依赖 lombok自动添加了无参构造函数和全参构造函数
 * 需要记录蚂蚁的方向、速度、位置、管理画布中蚂蚁的图片
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ant {
    private Integer id;//蚂蚁在一局游戏的标识符
    private Integer direction;//-1表示向左，1表示向右
    private Integer pos;//蚂蚁的位置
    private Integer velocity;
    private Image image;

    /**
     * 蚂蚁爬行函数
     * 更新pos
     */
    public void creep() {
        pos = pos + direction * velocity;
    }


    /**
     *
     * @param g
     * @param y
     * 绘制函数
     * 根据当前的矩形框大小定长度：根据推算蚂蚁最大可以是碰撞前一秒时两只蚂蚁的相对距离也就是蚂蚁的速度*2的距离
     * 所以根据当前窗口大小计算出蚂蚁1s运动的距离r
     * 绘制蚂蚁的id和图片
     */
    public void draw(Graphics g, Integer y) {
        Integer r = PlayRoom.gameRoomFrame.getWidth() / CreepingGame.STICK_LENGTH  * CreepingGame.ANT_VELOCITY;
        Integer axisX = pos * PlayRoom.gameRoomFrame.getWidth() / CreepingGame.STICK_LENGTH - r;
        g.drawString(id.toString(), axisX, y - 2 * r);
        g.drawImage(image, axisX, y - r, r * 2, r, Color.black, null);
    }

}
