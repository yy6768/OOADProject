package game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Card类
 * 只一张扑克牌
 *  value 面值
 *  color 花色
 *  isShow 是否显示
 *  所对应的图片
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private String value;
    private String color;
    private Boolean isShow;
}
