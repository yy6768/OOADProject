package client;


import game.BlackJackGame;
import game.GameConfig;
import interfaces.Panel;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * �û��Ŀͻ����࣬�̳�Thread��
 * ÿһ��PlayerClient����һ���̣߳�����̹߳���һ������
 */
public class PlayerClient {
    //��ǰ��ҵ���Ϸ����
    private final JFrame playerFrame = new JFrame("BlackJack");
    //��ǰ��ҵ���Ϸ����
    private Panel currentPanel;

    private Integer bet;

    public PlayerClient(){
        bet = GameConfig.INIT_BET;
    }

    /**
     * ��ʼ����ǰ����Ϸ����
     */
    private void initFrame() {
        IndexPanel indexPanel = new IndexPanel(this);
        currentPanel = indexPanel;
        playerFrame.add(indexPanel);
        indexPanel.updateUI();
        playerFrame.setSize(PlayerConfig.FRAME_WIDTH, PlayerConfig.FRAME_HEIGHT);
        playerFrame.setLocationRelativeTo(null);
        playerFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        playerFrame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                currentPanel.resize();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
        //����ȫ������
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("����", Font.ITALIC, 20)));


        playerFrame.setVisible(true);
    }

    /**
     * ������Ϸ
     */
    public void startGame() {
        if(bet <= 50){
            JOptionPane.showMessageDialog(playerFrame,"����û������޶�Ķ�ע");
            playerFrame.dispose();
            return;
        }
        //�滻��ҳ���浽��Ϸ����
        playerFrame.remove((Component) currentPanel);
        BlackJackGame game = new BlackJackGame(this);
        PlayerPanel panel = new PlayerPanel(this, game);
        playerFrame.add(panel);
        currentPanel = panel;

        panel.updateUI();
        playerFrame.setVisible(true);
        //������Ϸ��
        game.start();

    }

    public void backToIndex() {
        playerFrame.remove((Component) currentPanel);
        IndexPanel indexPanel = new IndexPanel(this);
        currentPanel = indexPanel;
        playerFrame.add(indexPanel);
        indexPanel.updateUI();
    }

    /*
        get��set����
     */
    public JFrame getPlayerFrame() {
        return playerFrame;
    }

    public Panel getCurrentPanel(){ return currentPanel; }

    public void setBet(Integer bet) {
        this.bet = bet;
    }

    public Integer getBet() {
        return bet;
    }

    public static void main(String[] args) {
        PlayerClient client = new PlayerClient();
        client.initFrame();
    }


}
