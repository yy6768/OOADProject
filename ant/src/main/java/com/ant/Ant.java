package com.ant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*
    Ant 类依赖 lombok
    需要继承RenderObject对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ant extends RenderObject{
    private Integer id;
    private Integer direction;//-1表示向左，1表示向右
    private Integer pos;//蚂蚁的位置
    private Integer velocity;


    private void creep(){

    }

    public void update(){
        this.creep();
        this.render();
    }



    public void destroy(){

    }

    private void render(){

    }

}
