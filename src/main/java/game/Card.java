package game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Card类
 * 只一张扑克牌
 * @String value 面值
 * @String color 花色
 * @Boolean isShow 是否显示
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private String value;
    private String color;
    private Boolean isShow;
}
