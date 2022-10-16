package game;

import interfaces.CardController;

/**
 * Dealer 庄家类
 * 庄家类需要继承CardController接口
 */
public class Dealer implements CardController {
    //庄家所控制的牌堆
    private final Place place;
    //这句游戏的牌库对象
    private final Deck deck;
    //初始函数
    public Dealer(Deck deck){
        this.place = new Place(this);
        this.deck = deck;
    }

    /**
     * 卡牌管理接口所定义的函数
     * 庄家抽牌时需要设置暗牌
     */
    @Override
    public void drawCard() {
        Card card = deck.draw();
        card.setIsShow(false);
        place.addCard(card);
    }

    /**
     * Dealer 起始抽两张牌
     */
    @Override
    public void start() {
        for(int i = 0; i < 2; i++){
            drawCard();
        }
    }

    /**
     * 庄家的操作：当点数小于17时一直抽牌
     */
    public void operate() {
        while(place.calculate() < 17){
            drawCard();
        }
    }

    //get/set函数
    public Place getPlace() {
        return place;
    }
}
