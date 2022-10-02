package game;


import client.PlayerConfig;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Place类
 * 用于表示玩家面前的排堆
 */
@Data
@NoArgsConstructor
public class Place {
    private List<Card> cards;
    private CardController controller;
    private Integer bet;

    public Place(CardController controller){
        this.cards = new ArrayList<>();
        this.controller = controller;
        this.bet = PlayerConfig.GAME_INIT_BET;
    }

    public void addCard(Card card){
        if(cards.size() != 0) cards.get(cards.size() - 1).setIsShow(true);
        cards.add(card);
    }

    /**
     * 返回当前place的总和
     * @return
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

    public void showCards() {
        for(Card card:cards){
            if(card.getIsShow()) System.out.println(card.getValue() + " of " + card.getColor());
            else System.out.println("Hidden");
        }
    }

}
