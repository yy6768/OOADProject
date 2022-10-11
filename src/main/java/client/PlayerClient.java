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
 * �û��Ŀͻ�����,����JFrame����
 * ����û�������л�
 */
public class PlayerClient {
    //��ǰ��ҵ���Ϸ����
    private final JFrame playerFrame = new JFrame("BlackJack");
    //��ǰ��ҵ���Ϸ����
    private Panel currentPanel;
    //��ǰ��ҵĶ���
    private Integer bet;

    /**
     * ���캯��
     */
    public PlayerClient(){
        bet = PlayerConfig.INIT_BET;
    }

    /**
     * ��ʼ����ǰ����Ϸ����
     * ��Ҫ��
     * 1�������������ԣ���Ӵ���resize������
     * 2����ӵ�ǰҳ��
     * 3����ʾ����
     */
    private void initFrame() {
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


        IndexPanel indexPanel = new IndexPanel(this);
        currentPanel = indexPanel;
        playerFrame.add(indexPanel);
        indexPanel.updateUI();

        playerFrame.setVisible(true);
    }

    /**
     * ������Ϸ
     * 1���жϵ�ǰ�����Ƿ���֧��һ����Ϸ
     * 2���滻��ǰ����Ϊ1���µ���Ϸ����
     * 3������һ������Ϸ
     */
    public void startGame() {
        // �ж�
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

        //������Ϸ��
        game.start();
    }

    /**
     * ����ǰ�����˻ص���ҳ
     */
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


    /**
     * main�����������ͻ���
     * @param args
     */
    public static void main(String[] args) {
        PlayerClient client = new PlayerClient();
        client.initFrame();
    }


}
