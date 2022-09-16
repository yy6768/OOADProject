package com.ant.ant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
    Ant类依赖 lombok有set和get函数
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ant {
    private Integer id;//蚂蚁在一局游戏的标识符
    private Integer direction;//-1表示向左，1表示向右
    private Integer pos;//蚂蚁的位置
    private Integer velocity;


    /*
       蚂蚁爬行
     */
    public void creep() {
        pos = pos + direction * velocity;
    }

    public void draw(Graphics g, Integer y){
        g.setColor(Color.RED);
        int r = 40;
        g.drawOval(pos * 4 - r , y - 20 - r, r ,r);
    }



}
