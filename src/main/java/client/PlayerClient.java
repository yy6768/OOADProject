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
 * �û��Ŀͻ�����,����JFrame����
 * ����û�������л�
 */
public class PlayerClient extends Thread{
    //��ǰ��ҵ���Ϸ����
    private final JFrame playerFrame = new JFrame("BlackJack");
    //��ǰ��ҵ���Ϸ����
    private Panel currentPanel;
    //��ǰ��ҵĶ���
    private Integer bet;

    /**
     * ���캯��
     */
    public PlayerClient() {
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
        if (bet <= 50) {
            JOptionPane.showMessageDialog(playerFrame, "����û������޶�Ķ�ע");
            playerFrame.dispose();
            return;
        }
        PlayerPanel panel;
        try {
            //���socket
            Socket socket = new Socket("localhost", ServerConfig.SOCKET_PORT);
            OutputStream out = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            bw.write("s" + "\r\n");
            bw.flush();
            //�½���Ϸ����
            panel = new PlayerPanel(1, this, socket);
            //�滻��ҳ���浽��Ϸ����
            playerFrame.remove((Component) currentPanel);
            playerFrame.add(panel);
            currentPanel = panel;
            panel.updateUI();
            new Thread(panel).start();
        } catch (IOException e) {
            System.out.println("���ӷ�����ʧ�ܣ�������");
        }
    }

    public void startMultiGame() {
        // �ж�
        if (bet <= 50) {
            JOptionPane.showMessageDialog(playerFrame, "����û������޶�Ķ�ע");
            playerFrame.dispose();
            return;
        }
        PlayerPanel panel;
        try {
            //���socket
            Socket socket = new Socket("localhost", ServerConfig.SOCKET_PORT);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            bw.write("m" + "\r\n");
            bw.flush();
            br.readLine();
            //�½���Ϸ����
            panel = new PlayerPanel(3, this, socket);
            //�滻��ҳ���浽��Ϸ����
            playerFrame.remove((Component) currentPanel);
            playerFrame.add(panel);
            currentPanel = panel;
            panel.updateUI();
            new Thread(panel).start();
        } catch (IOException e) {
            System.out.println("���ӷ�����ʧ�ܣ�������");
        }
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
     * main�����������ͻ���
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
