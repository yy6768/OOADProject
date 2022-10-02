package game;


/**
 * 玩家和庄家需要实现的接口
 * 需要实现对牌库抽牌的操作
 */
public interface CardController {
    void drawCard();
    void start();
    void operate();
}
