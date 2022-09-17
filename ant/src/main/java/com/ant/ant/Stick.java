package com.ant.ant;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;


/**
 * 木棍类 依赖lombok自动添加了无参构造函数和全参构造函数
 * 记录两端点的坐标
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Stick{
    private Integer leftEnd;
    private Integer rightEnd;

    /**
     * 木棍类的绘制函数
     * 需要根据当前框体的大小来对木棍进行绘制
     * 需要换算当前坐标来计算当前木棍长度
     * @param g
     * @param y
     */
    public void draw(Graphics g, Integer y){
        g.setColor(Color.BLACK);
        Integer frameWidth = PlayRoom.gameRoomFrame.getWidth();
        Integer ratio = frameWidth / 300 + 1;
        g.drawLine(leftEnd * ratio, y, rightEnd *  ratio, y);
    }


}
