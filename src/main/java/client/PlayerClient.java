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
 * 用户的客户端类,管理JFrame窗口
 * 完成用户界面的切换
 */
public class PlayerClient {
    //当前玩家的游戏窗口
    private final JFrame playerFrame = new JFrame("BlackJack");
    //当前玩家的游戏界面
    private Panel currentPanel;
    //当前玩家的赌资
    private Integer bet;

    /**
     * 构造函数
     */
    public PlayerClient(){
        bet = PlayerConfig.INIT_BET;
    }

    /**
     * 初始化当前的游戏界面
     * 需要：
     * 1、调整窗口属性，添加窗口resize监听器
     * 2、添加当前页面
     * 3、显示窗口
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
        //设置全局字体
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("宋体", Font.ITALIC, 20)));


        IndexPanel indexPanel = new IndexPanel(this);
        currentPanel = indexPanel;
        playerFrame.add(indexPanel);
        indexPanel.updateUI();

        playerFrame.setVisible(true);
    }

    /**
     * 启动游戏
     * 1、判断当前赌资是否还能支撑一局游戏
     * 2、替换当前界面为1个新的游戏界面
     * 3、启动一局新游戏
     */
    public void startGame() {
        // 判断
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

        //启动游戏类
        game.start();
    }

    /**
     * 将当前界面退回到主页
     */
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


    /**
     * main函数，启动客户端
     * @param args
     */
    public static void main(String[] args) {
        PlayerClient client = new PlayerClient();
        client.initFrame();
    }


}
