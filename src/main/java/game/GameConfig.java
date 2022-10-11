package game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Game的相关配置
 */
public class GameConfig {
    //   所有牌面值
    public static final String[] CARD_VALUES = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    //   所有花色
    public static final String[] CARD_COLORS = {"spade", "heart", "heart", "diamond"};
    //  所有牌面值和分值的映射关系
    public static final Map<String, Integer> VALUE_TO_POINT = Collections.unmodifiableMap(new HashMap<String, Integer>() {
        {
            put("A", 11);
            put("2", 2);
            put("3", 3);
            put("4", 4);
            put("5", 5);
            put("6", 6);
            put("7", 7);
            put("8", 8);
            put("9", 9);
            put("10", 10);
            put("J", 10);
            put("Q", 10);
            put("K", 10);
        }
    });
}
