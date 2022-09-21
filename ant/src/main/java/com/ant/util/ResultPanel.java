package com.ant.util;

import com.ant.ant.PlayRoom;

import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        String maxStr = "最长的时间:" + PlayRoom.maxTimestamp;
        String minStr = "最短的时间:" + PlayRoom.minTimestamp;
        g.setColor(Color.black);
        Integer frameHeight = PlayRoom.gameRoomFrame.getHeight();
        Integer frameWidth = PlayRoom.gameRoomFrame.getWidth();
        Integer fontSize = Math.max(40, frameHeight / 20);
        g.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
        g.drawString("游戏结束！",frameWidth/3,frameHeight/3 - fontSize);
        g.drawString(maxStr, frameWidth/3, frameHeight/3);
        g.drawString(minStr, frameWidth/3, frameHeight/3 + fontSize);
    }


}
