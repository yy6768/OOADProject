package game;

import client.PlayerClient;
import client.PlayerPanel;

import javax.swing.*;
import java.util.List;

/**
 * BlackJackGame 游戏总控类
 * 管理一局游戏的所有信息
 * player 包括Player对象列表
 * deck 一副牌堆对象
 * dealer 庄家对象
 */
public class BlackJackGame extends Thread {

    private final Player player;
    private final Deck deck;
    private final Dealer dealer;
    private final PlayerClient client;

    /**
     * constructor
     * 负责创建游戏所需的对象
     */
    public BlackJackGame(PlayerClient client) {
        this.deck = new Deck();
        this.player = new Player(client, deck);
        this.dealer = new Dealer(this, this.deck);
        this.client = client;
    }

    /**
     * 计算游戏结果
     */
    private void judge() {
        if(!"completed".equalsIgnoreCase(player.getStatus())) return;

        List<Card> cards = dealer.getPlace().getCards();
        cards.get(cards.size() - 1).setIsShow(true);
        PlayerPanel panel = (PlayerPanel) this.client.getCurrentPanel();
        for(JButton button:panel.getButtons()){
            button.setEnabled(false);
        }

        int dealerSum = dealer.getPlace().calculate();
        int dealerDis = Math.abs(dealerSum - 21);
        int playerSum = player.getPlace().calculate();
        if (dealerSum > 21) {
            player.setStatus("win");
        } else {
            int playerDis = Math.abs(playerSum - 21);
            if (playerDis < dealerDis) player.setStatus("win");
            else if (playerDis == dealerDis) player.setStatus("draw");
            else player.setStatus("lose");
        }
    }

    /**
     * 游戏总控制函数
     * 对所有游戏中的对象进行操作
     */
    @Override
    public void run() {
        super.run();
        player.makeBet();
        deck.init();
        dealer.start();
        player.start();
        player.operate();
        if("completed".equalsIgnoreCase(player.getStatus())) dealer.operate();
        judge();
        player.calculateBet();
        PlayerPanel panel = (PlayerPanel) client.getCurrentPanel();
        panel.showResultDialog();
    }

    public Player getPlayer(){
        return player;
    }

    public Dealer getDealer(){
        return dealer;
    }

}
