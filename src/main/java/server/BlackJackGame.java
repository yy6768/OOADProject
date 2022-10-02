package server;

import client.Player;
import game.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * BlackJackGame 游戏总控类
 * 管理一局游戏的所有信息
 * players 包括Player对象列表
 * deck 一副牌堆对象
 * dealer 庄家对象
 */
public class BlackJackGame {
    private final Integer id;
    private final List<Player> players;
    private final Deck deck;
    private final Dealer dealer;
    private final Integer playerSize;

    public static Integer cntId = 0;

    /**
     * constructor
     * @param playerSize
     * 负责创建游戏所需的对象
     */
    public BlackJackGame(Integer playerSize) {
        this.id = cntId++;
        this.playerSize = playerSize;
        this.deck = new Deck();
        this.players = new ArrayList<>();
        for (int i = 0; i < this.playerSize; i++) {
            players.add(new Player(this,this.deck));
        }
        this.dealer = new Dealer(this,this.deck);
    }

    /**
     * 计算游戏结果
     */
    private void judge() {
        int dealerSum = dealer.getPlace().calculate();
        int dealerDis = Math.abs(dealerSum - 21);
        for(Player player : players){
            int playerSum = player.getPlace().calculate();
            if(playerSum > 21){
                player.lose();
            }
            else if(dealerSum > 21) {
                player.win();
            }
            else{
                int playerDis = Math.abs(playerSum - 21);
                if(playerDis < dealerDis) player.win();
                else if(playerDis == dealerDis) player.draw();
                else player.lose();
            }
        }
    }

    /**
     * 初始化函数
     * 负责将一下几个对象进行初始化
     *
     */
    public void play() {
        System.out.println("游戏开始");
        for (Player player:players) {
            player.makeBet();
        }
        deck.init();
        System.out.println("New hand");
        dealer.start();
        for (Player player : this.players) {
            player.start();
        }
        for (Player player:players) {
            player.operate();
        }
        dealer.operate();
        judge();
    }


}
