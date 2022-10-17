package client;


import interfaces.Panel;
import server.ServerConfig;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;
import java.net.Socket;

/**
 * 用户的客户端类,管理JFrame窗口
 * 完成用户界面的切换
 */
public class PlayerClient extends Thread{
    //当前玩家的游戏窗口
    private final JFrame playerFrame = new JFrame("BlackJack");
    //当前玩家的游戏界面
    private Panel currentPanel;
    //当前玩家的赌资
    private Integer bet;

    /**
     * 构造函数
     */
    public PlayerClient() {
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
        if (bet <= 50) {
            JOptionPane.showMessageDialog(playerFrame, "您已没有最低限额的赌注");
            playerFrame.dispose();
            return;
        }
        PlayerPanel panel;
        try {
            //添加socket
            Socket socket = new Socket("localhost", ServerConfig.SOCKET_PORT);
            OutputStream out = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            bw.write("s" + "\r\n");
            bw.flush();
            //新建游戏界面
            panel = new PlayerPanel(1, this, socket);
            //替换首页界面到游戏界面
            playerFrame.remove((Component) currentPanel);
            playerFrame.add(panel);
            currentPanel = panel;
            panel.updateUI();
            new Thread(panel).start();
        } catch (IOException e) {
            System.out.println("连接服务器失败，请重试");
        }
    }

    public void startMultiGame() {
        // 判断
        if (bet <= 50) {
            JOptionPane.showMessageDialog(playerFrame, "您已没有最低限额的赌注");
            playerFrame.dispose();
            return;
        }
        PlayerPanel panel;
        try {
            //添加socket
            Socket socket = new Socket("localhost", ServerConfig.SOCKET_PORT);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            bw.write("m" + "\r\n");
            bw.flush();
            br.readLine();
            //新建游戏界面
            panel = new PlayerPanel(3, this, socket);
            //替换首页界面到游戏界面
            playerFrame.remove((Component) currentPanel);
            playerFrame.add(panel);
            currentPanel = panel;
            panel.updateUI();
            new Thread(panel).start();
        } catch (IOException e) {
            System.out.println("连接服务器失败，请重试");
        }
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

    public void setBet(Integer bet) {
        this.bet = bet;
    }

    public Integer getBet() {
        return bet;
    }


    @Override
    public void run() {
        initFrame();
    }

    /**
     * main函数，启动客户端
     *
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            PlayerClient playerClient = new PlayerClient();
            playerClient.start();
        }
    }


}
