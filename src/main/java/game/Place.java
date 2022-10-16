package game;


import client.PlacePanel;
import interfaces.CardController;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Place类
 * 用于表示玩家面前的排堆
 * cards 表示当前玩家（庄家）面前的排堆
 * controller 表示对应的玩家或者庄家
 * bet表示当前place对应的赌注
 * panel表示对应的PlacePanel
 */
@Data
@NoArgsConstructor
public class Place {
    private List<Card> cards;
    private CardController controller;
    private Integer bet;
    private PlacePanel panel;

    public Place(CardController controller){
        this.cards = new ArrayList<>();
        this.controller = controller;
    }

    public void addCard(Card card){
        if(cards.size() != 0) cards.get(cards.size() - 1).setIsShow(true);
        cards.add(card);
    }

    /**
     * 返回当前place的总和
     * @return 当前place的总和
     */
    public Integer calculate(){
        Integer res = 0;
        for(Card card:cards){
            res += GameConfig.VALUE_TO_POINT.get(card.getValue());
        }
        for(Card card:cards){
            if(res > 21 && "A".equals(card.getValue()))
                res -= 10;
        }
        return res;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(Card card:cards){
            if(!card.getIsShow())
                builder.append("back.JPG ");
            else
                builder.append(card.getColor()).append(card.getValue()).append(".JPG ");
        }
        builder.append(calculate());
        return builder.toString();
    }

}
