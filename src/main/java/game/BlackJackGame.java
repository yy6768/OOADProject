package game;

import client.PlayerClient;
import client.PlayerPanel;

import javax.swing.*;
import java.util.List;

/**
 * BlackJackGame ��Ϸ�ܿ���
 * ����һ����Ϸ��������Ϣ
 * player ����Player�����б�
 * deck һ���ƶѶ���
 * dealer ׯ�Ҷ���
 */
public class BlackJackGame extends Thread {

    private final Player player;
    private final Deck deck;
    private final Dealer dealer;
    private final PlayerClient client;

    /**
     * constructor
     * ���𴴽���Ϸ����Ķ���
     */
    public BlackJackGame(PlayerClient client) {
        this.deck = new Deck();
        this.player = new Player(client, deck);
        this.dealer = new Dealer(this, this.deck);
        this.client = client;
    }

    /**
     * ������Ϸ���
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
     * ��Ϸ�ܿ��ƺ���
     * ��������Ϸ�еĶ�����в���
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
