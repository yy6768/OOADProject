package game;

import interfaces.CardController;

/**
 * Player类
 * 管理一个Player的所有信息
 * 赌资 bet
 * Player的状态 status
 * Player控制的排堆 place
 * Player所在的游戏 game
 */
public class Player implements CardController {
    //player在本局对战中的id
    private Integer id;
    /*
        status状态共有一下几种
        idle 空闲状态
        waiting 等待操作
        playing 正在操作
        double 加倍
        completed 操作完成等待其他人操作
        win 获胜
        lose 失败
     */
    private String status;
    private final Place place;
    private final Deck deck;
    /**
     * 构造函数
     *
     * @param id 玩家在游戏中的id
     * @param deck   当前的牌库
     */
    public Player(Integer id, Deck deck) {
        this.id = id;
        this.status = "idle";
        this.place = new Place(this);
        this.deck = deck;
    }


    @Override
    public void drawCard() {
        Card card = deck.draw();
        card.setIsShow(true);
        place.addCard(card);
    }

    /**
     * 玩家抽取初始的2张手牌
     */
    @Override
    public void start() {
        for (int i = 0; i < 2; i++) {
            drawCard();
        }
        Integer sum = place.calculate();
        if (sum == 21) {
            place.setBet(place.getBet() + place.getBet() / 2);
        }
    }

    /**
     * double操作
     * 赌注翻倍
     * 状态替换成double(stand和hit只能执行一次）
     */
    public void doubleOperation() {
        place.setBet(place.getBet() * 2);
        status = "double";
    }

    /**
     * hit操作
     * 1、抽牌
     * 2、禁止加倍
     * 3、判断是否爆牌
     */
    public void hitOperation() {
        drawCard();
        if (place.calculate() > 21) status = "lose";
        else if (place.calculate() == 21) status = "completed";
        if ("double".equalsIgnoreCase(status)) status = "completed";
    }

    public void standOperation() {
        status = "completed";
    }

    public void surrenderOperation() {
        place.setBet(place.getBet() / 2);
        this.status = "lose";
    }




    /**
     * 计算最后玩家的点数
     */


    public Place getPlace() {
        return place;
    }

    public Integer getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
