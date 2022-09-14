package com.ant;

import java.util.LinkedList;
import java.util.List;

/*
    渲染对象基类
    static objectList存放所有需要渲染的对象，每个对象生成时，存放在列表中
    每次渲染的时候需要将所有列表的对象取出进行一次渲染即可
*/

public class RenderObject {

    //存放所有渲染对象的常量
    public static final List<RenderObject> OBJECT_LIST= new LinkedList<>();

    //每个对象的帧时长
    public Integer timeDelta, lastDelta;
    //是否初始化渲染过
    public boolean called_before;

    public RenderObject(){
        OBJECT_LIST.add(this);
        timeDelta = 0;
        called_before =false;
    }

    public void start(){

    }

    //每一帧执行的函数
    public void update(){

    }

    //对象销毁前的函数
    public void on_destroy(){

    }

    public void destroy(){
        this.on_destroy();
        for(int i = 0; i < OBJECT_LIST.size(); i++){
            RenderObject item = OBJECT_LIST.get(i);
            if(item.equals(this)){
                OBJECT_LIST.remove(i);
                break;
            }
        }
    }


}
