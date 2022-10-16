package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Deck 牌堆类
 * 管理剩余Card对象
 * cards 牌库
 * curr 当前牌顶的指针
 *
 * init 牌库初始化
 * shuffle 洗牌操作
 */
public class Deck {
    private final List<Card> cards;
    private Integer curr;
    public Deck(){
        cards = new ArrayList<>();
        curr = 51;
    }
    /**
     * 排堆进行初始化
     */
    public void init() {
        for (int i = 0; i < GameConfig.CARD_VALUES.length; i++) {
            for (int j = 0; j < GameConfig.CARD_COLORS.length; j++) {
                cards.add(new Card(GameConfig.CARD_VALUES[i], GameConfig.CARD_COLORS[j], false));
            }
        }
        shuffle();
    }

    /**
        洗混牌库
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * draw
     * 抽一张牌
     * 这里考虑使用ArrayList模拟队列
     * curr表示当前的队头
     * 当排堆抽完时洗混牌库并将队头重置
     */
    public Card draw(){
        curr -= 1;
        if(curr < 0) {
            curr += 52;
            shuffle();
        }
        return cards.get(curr);
    }
}
