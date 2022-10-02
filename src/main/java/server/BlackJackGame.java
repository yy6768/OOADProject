package server;

import client.Player;
import game.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * BlackJackGame ��Ϸ�ܿ���
 * ����һ����Ϸ��������Ϣ
 * players ����Player�����б�
 * deck һ���ƶѶ���
 * dealer ׯ�Ҷ���
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
     * ���𴴽���Ϸ����Ķ���
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
     * ������Ϸ���
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
     * ��ʼ������
     * ����һ�¼���������г�ʼ��
     *
     */
    public void play() {
        System.out.println("��Ϸ��ʼ");
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
