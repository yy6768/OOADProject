package com.ant.ant;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Stick{
    private Integer leftEnd;
    private Integer rightEnd;

    public void draw(Graphics g, Integer y){
        g.setColor(Color.BLACK);
        g.drawLine(leftEnd * 4, y, rightEnd *  4, y);
    }


}
