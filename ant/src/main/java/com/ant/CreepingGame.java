package com.ant;

import java.util.LinkedList;
import java.util.List;

public class CreepingGame {
    private Integer id;
    private List<Ant> ants;
    private Stick stick;
    private Integer timestamp;

    private static final Integer[] START_POSITION = {30,80,110,160,250};
    private static final Integer STICK_LENGTH = 300;
    private static final Integer ANT_NUM = 5;
    private static final Integer ANT_VELOCITY = 5;
    private static final Integer incTime = 1000;

    public CreepingGame(Integer id,
                        Integer timestamp){
        //初始化
        this.id = id;
        this.timestamp = timestamp;
        String directions = id.toString();
        ants = new LinkedList<>();
        for (int i = 0; i < ANT_NUM; i++) {
            int d = directions.charAt(0) - '0';
            if(d == 0) d = -1;
            ants.add(new Ant(0,d,START_POSITION[i],ANT_VELOCITY));
        }
        stick = new Stick(0,STICK_LENGTH);
    }



    public void start(){
        for (int i = 0; i < ANT_NUM; i++) {
            ants.get(i).start();
        }
        stick.start();
    }

    private void checkCollision(Integer[] old_position, Integer[] new_position){
        for (int i = 0; i < ANT_NUM; i++) {
            for (int j = i + 1; j < ANT_NUM; j++) {
                Ant antA = ants.get(i);
                Ant antB = ants.get(j);
                if(antA.getDirection() * antB.getDirection() == -1
                    && ((old_position[i] < old_position[j] && new_position[i] >= new_position[j]) ||
                    old_position[j] < old_position[i] && new_position[j] >= new_position[i])){
                    antA.setDirection(-1 * antA.getDirection());
                    antB.setDirection(-1 * antB.getDirection());
                }
            }
        }
    }

    private void checkBeyondEdge(){
        int leftEnd = stick.getLeftEnd();
        int rightEnd = stick.getRightEnd();
        for (Ant ant: ants) {
            int pos = ant.getPos();
            if(pos <= leftEnd || pos >= rightEnd){
                ant.destroy();
            }
        }
    }

    public void update(){
        Integer[] old_position = new Integer[ANT_NUM];
        Integer[] new_position = new Integer[ANT_NUM];
        for(int i = 0; i < ANT_NUM; i++){
            Ant ant = ants.get(i);
            old_position[i] = ant.getPos();
            new_position[i] = old_position[i] + ant.getVelocity() * ant.getDirection();
        }
        checkCollision(old_position,new_position);
        for (Ant ant:ants)
            ant.update();
        checkBeyondEdge();
        render();
    }

    public void render(){

    }


}
