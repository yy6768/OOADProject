package client;

/**
 * PlayerConfig类
 * 存储玩家相关的配置
 */
public class PlayerConfig {
    public static final Integer FRAME_WIDTH = 1200;
    public static final Integer FRAME_HEIGHT = 800;
    public static final String[] LABEL_NAME= {"Double", "Hit", "Stand", "Surrender"};

    public static final String RULE = "1、该游戏由1名玩家对战电脑扮演的庄家，使用除大小王之外的52张牌，游戏者的目标是使手中的牌的点数之和不超过21点且尽量大。当使用1副牌时，以下每种牌各一张（没有大小王）\n"
            +"2、大家手中扑克点数的计算是：2至9牌，按其原点数计算；\n" +
            "   K、Q、J和10牌都算作10点（一般记作T，即ten之意）；\n" +
            "   A 牌（ace）既可算作1点也可算作11点，由玩家自己决定（当玩家停牌时，点数一律视为最大而尽量不爆，如A+9为20，A+4+8为13，A+3+A视为15）。\n"
            +"3、开局时，庄家（dealer）给玩家牌面向上发两张牌（明牌），再给庄家自己发两张牌，一张明牌，一张暗牌（牌面朝下）。\n"
            +"4、当所有的初始牌分发完毕后，如果玩家拿到的是A和T（无论顺序），就拥有黑杰克（Black Jack）；\n若庄家的明牌为T，且暗牌为A，应直接翻开并拥有Black Jack；\n" +
            "   此时，如果庄家的暗牌为10点牌（构成Black Jack）如果庄家没有Black Jack则保持暗牌，玩家继续游戏。\n" +
            "   若玩家为Black Jack且庄家为其他，玩家赢得1.5倍赌注；若庄家为Black Jack且玩家为其他，庄家赢得赌注；若庄家和玩家均为Black Jack，平局，玩家拿回自己的赌注。\n" +
            "5、玩家选择拿牌（Hit）、停牌（Stand）、加倍（Double）、投降（Surrender，庄家赢得一半赌注\n" +
            "   拿牌(HIT) ：再拿一张牌。闲家只要手上牌相加点数小于21点都可要牌。庄家视规则强制拿牌或选择拿牌。\n" +
            "   停牌(STAND) ：不再拿牌。在任何情况下，玩家可选择停止要牌。\n" +
            "   双倍下注(DOUBLE) ：玩家在拿到前两张牌之后，可以再下一注与原赌注相等的赌金（如果觉得少可以加倍），然后只能再拿一张牌。如果拿到黑杰克，则不许双倍下注。";
}
