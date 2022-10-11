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
 * 用户的客户端类，继承Thread类
 * 每一个PlayerClient管理一个线程，这个线程管理一个窗口
 */
public class PlayerClient {
    //当前玩家的游戏窗口
    private final JFrame playerFrame = new JFrame("BlackJack");
    //当前玩家的游戏界面
    private Panel currentPanel;

    private Integer bet;

    public PlayerClient(){
        bet = GameConfig.INIT_BET;
    }

    /**
     * 初始化当前的游戏界面
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
        //设置全局字体
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("宋体", Font.ITALIC, 20)));


        playerFrame.setVisible(true);
    }

    /**
     * 启动游戏
     */
    public void startGame() {
        if(bet <= 50){
            JOptionPane.showMessageDialog(playerFrame,"您已没有最低限额的赌注");
            playerFrame.dispose();
            return;
        }
        //替换首页界面到游戏界面
        playerFrame.remove((Component) currentPanel);
        BlackJackGame game = new BlackJackGame(this);
        PlayerPanel panel = new PlayerPanel(this, game);
        playerFrame.add(panel);
        currentPanel = panel;

        panel.updateUI();
        playerFrame.setVisible(true);
        //启动游戏类
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
        get、set函数
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
