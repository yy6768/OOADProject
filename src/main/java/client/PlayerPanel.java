package client;

import interfaces.Panel;
import utils.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PlayerPanel 用户面板
 * 用于显示用户的基本信息
 * 继承Swing包里的JPanel
 * 实现了我们自己定义的接口Panel
 */
public class PlayerPanel extends JPanel implements Panel, Runnable {
    //对应的玩家id
    private Integer id;
    //当前游戏人数
    private final Integer playerNum;
    //当前客户端
    private final PlayerClient client;
    //存储界面中的所有button
    private final List<JButton> buttons;
    //玩家的Place和庄家的Place
    private final List<PlacePanel> playerPlaces;
    private PlacePanel dealerPlace;
    //联系game的socket
    private final Socket playerSocket;
    //
    private String dealerStatus;
    //玩家状态
    private final Map<Integer,String> playerStatuses;
    /**
     * 构造函数
     */
    public PlayerPanel(Integer num, PlayerClient playerClient, Socket socket) {
        playerNum = num;
        client = playerClient;
        this.setLayout(null);
        buttons = new ArrayList<>();
        playerPlaces = new ArrayList<>();
        dealerStatus = "未获得BlackJack";
        playerStatuses = new HashMap<>();
        for (int i = 0; i < playerNum; i++) {
            playerPlaces.add(new PlacePanel());
            playerStatuses.put(i,"点数：0");
        }
        dealerPlace = new PlacePanel();
        setBackground(new Color(0, 139, 69));
        //设置按钮
        renderButtons();

        //设置Place
        renderPlace();
        playerSocket = socket;

    }

    /**
     * 设置玩家操作的buttons;
     */
    public void renderButtons() {
        int buttonX = client.getPlayerFrame().getWidth() / 20;
        int buttonY = client.getPlayerFrame().getHeight() * 7 / 8;
        int buttonWidth = client.getPlayerFrame().getWidth() / 6;
        int buttonHeight = client.getPlayerFrame().getHeight() / 20;
        int fontSize = client.getPlayerFrame().getHeight() / 30;

        for (int i = 0; i < PlayerConfig.LABEL_NAME.length; i++) {
            JButton button = new JButton(PlayerConfig.LABEL_NAME[i]);
            button.setFont(new Font("Lucida Handwriting", Font.BOLD | Font.ITALIC, fontSize));
            button.setBounds(buttonX + i * (buttonWidth + buttonWidth / 2), buttonY, buttonWidth, buttonHeight);
            add(button);

            switch (i) {
                case 0:
                    button.addActionListener(e -> {
                        button.setEnabled(false);
                        OutputStream out;
                        try {
                            out = playerSocket.getOutputStream();
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                            bw.write("double\r\n");
                            bw.flush();
                        } catch (IOException ex) {
                            System.out.println("服务器异常");
                        }
                    });
                    break;
                case 1:
                    button.addActionListener(e -> {
                        buttons.get(0).setEnabled(false);
                        OutputStream out;
                        try {
                            out = playerSocket.getOutputStream();
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                            bw.write("hit\r\n");
                            bw.flush();
                        } catch (IOException ex) {
                            System.out.println("服务器异常");
                        }
                    });
                    break;
                case 2:
                    button.addActionListener(e -> {
                        for (JButton jButton : buttons) {
                            jButton.setEnabled(false);
                        }
                        OutputStream out;
                        try {
                            out = playerSocket.getOutputStream();
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                            bw.write("stand\r\n");
                            bw.flush();
                        } catch (IOException ex) {
                            System.out.println("服务器异常");
                        }
                    });
                    break;
                case 3:
                    button.addActionListener(e -> {
                        for (JButton jButton : buttons) {
                            jButton.setEnabled(false);
                        }
                        OutputStream out;
                        try {
                            out = playerSocket.getOutputStream();
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                            bw.write("surrender\r\n");
                            bw.flush();
                        } catch (IOException ex) {
                            System.out.println("服务器异常");
                        }
                    });
            }
            buttons.add(button);
        }
    }

    /**
     * 绘制玩家和庄家的Place
     */
    public void renderPlace() {
        int placeWidth = client.getPlayerFrame().getWidth() / 4;
        int placeHeight = client.getPlayerFrame().getHeight() / 5;
        int playerPlaceX = client.getPlayerFrame().getWidth() / 16;
        int dealerPlaceX = client.getPlayerFrame().getWidth() * 3 / 8;
        int dealerY = client.getPlayerFrame().getHeight() / 12;
        int playerY = client.getPlayerFrame().getHeight() / 2;

        dealerPlace.setBounds(dealerPlaceX, dealerY, placeWidth, placeHeight);
        add(dealerPlace);

        if (playerNum == 1) {
            PlacePanel place = playerPlaces.get(0);
            place.setBounds(dealerPlaceX, playerY, placeWidth, placeHeight);
            add(place);
        } else {
            int i = 0;
            for (PlacePanel playerPlace : playerPlaces) {
                playerPlace.setBounds(playerPlaceX + i * (placeWidth + playerPlaceX), playerY, placeWidth, placeHeight);
                add(playerPlace);
                i++;
            }
        }
    }

    /**
     * 显示结果
     */
    public void showResultDialog(String status) {
        ResultDialog dialog = new ResultDialog(client, status);
        dialog.setVisible(true);
    }


    /**
     * 绘制玩家信息和庄家信息
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int fontSize = client.getPlayerFrame().getHeight() / 30;
        int dealerY = client.getPlayerFrame().getHeight() / 3;
        int playerY = client.getPlayerFrame().getHeight() * 3 / 4;
        int dealerX = client.getPlayerFrame().getWidth() * 3 / 8;
        int informationX = client.getPlayerFrame().getWidth() / 12;

        setFont(new Font("华文正楷", Font.ITALIC, fontSize));

        g.drawString(dealerStatus, dealerX , dealerY);
        if(playerNum == 1){
            g.drawString(playerStatuses.get(0), dealerX , playerY);
        } else{
            for(int i = 0; i < playerNum;i++){
                g.drawString(playerStatuses.get(i), informationX + client.getPlayerFrame().getWidth() / 3 , playerY);
            }
        }



    }

    /**
     * 所有界面继承，当调整窗口大小时启用
     * 移除当前所有组件并重新绘制其他组件
     */
    @Override
    public void resize() {
        boolean[] status = new boolean[buttons.size()];
        int i = 0;
        for (JButton button : buttons) {
            status[i] = button.isEnabled();
            i++;
            remove(button);
        }
        buttons.clear();
        for (PlacePanel panel : playerPlaces) {
            remove(panel);
        }
        i = 0;
        remove(dealerPlace);
        renderButtons();
        for (JButton button : buttons) {
            button.setEnabled(status[i]);
            i++;
        }
        renderPlace();
        repaint();
    }


    private int makeBet() {
        String input;
        Integer gameBet;
        while (true) {
            input = JOptionPane.showInputDialog(
                    client.getPlayerFrame(),
                    "您是" + id + "号玩家，" + "当前有赌注：" + client.getBet() + "\n请输入赌注"
            );
            try {
                gameBet = Integer.parseInt(input);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(client.getPlayerFrame(), "输入异常", "错误", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            if (gameBet < 50) {
                JOptionPane.showMessageDialog(client.getPlayerFrame(), "最低赌注为50", "错误", JOptionPane.ERROR_MESSAGE);
            } else if (client.getBet() < gameBet) {
                JOptionPane.showMessageDialog(client.getPlayerFrame(), "没有那么多赌注", "错误", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }
        return gameBet;
    }


    @Override
    public void run() {
        try {
            InputStream in = playerSocket.getInputStream();
            OutputStream out = playerSocket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            String command;
            while (true) {
                command = br.readLine();
                if (command != null && command.length() != 0) {
                    String[] infos = command.split(" ");
                    if (infos[0].equalsIgnoreCase("makeBet")) {
                        id = Integer.parseInt(infos[1]);
                        int bet = makeBet();
                        System.out.println(id + " player make bet:" + bet);
                        bw.write(bet + "\r\n");
                        bw.flush();
                    } else if (infos[0].equalsIgnoreCase("dealer")) {
                        System.out.println("set Dealer");
                        List<Image> placeCard = dealerPlace.getCardImages();
                        placeCard.clear();
                        for (int i = 1; i < infos.length - 1; i++) {
                            System.out.println(infos[i]);
                            Image image = ImageUtil.getImage("card/" + infos[i]);
                            placeCard.add(image);
                        }
                        int sum = Integer.parseInt(infos[infos.length-1]);
                        if(sum < 21) dealerStatus = "未获得BlackJack";
                        else dealerStatus = "BlackJack";
                        updateUI();
                        resize();
                    } else if (infos[0].equalsIgnoreCase("player")) {
                        System.out.println("set Player " + infos[1]);
                        int id = Integer.parseInt(infos[1]);
                        PlacePanel place = playerPlaces.get(id);
                        List<Image> placeCard = place.getCardImages();
                        placeCard.clear();
                        for (int i = 2; i < infos.length - 1; i++) {
                            System.out.println(infos[i]);
                            Image image = ImageUtil.getImage("card/" + infos[i]);
                            placeCard.add(image);
                        }
                        int sum = Integer.parseInt(infos[infos.length-1]);
                        playerStatuses.put(id,"点数："+sum);
                        resize();
                        updateUI();
                    } else if (infos[0].equalsIgnoreCase("operate")) {
                        System.out.println("operate" + infos[1]);
                        int id = Integer.parseInt(infos[1]);
                        if (id == this.id) {
                            for (JButton button : buttons) {
                                button.setEnabled(true);
                            }
                        }
                    } else if (infos[0].equalsIgnoreCase("end")) {
                        int id = Integer.parseInt(infos[1]);
                        if (id == this.id) {
                            for (JButton button : buttons) {
                                button.setEnabled(false);
                            }
                        }
                    } else if (infos[0].equalsIgnoreCase("result")) {
                        System.out.println("result");
                        StringBuilder builder = new StringBuilder();
                        for (int i = 1; i < infos.length; i++) {
                            builder.append("Player").append(infos[i]);
                            int id = Integer.parseInt(infos[i]);
                            i++;
                            builder.append(" ").append(infos[i]);
                            String result = infos[i];
                            if ("draw".equalsIgnoreCase(infos[i])) {
                                i++;
                                continue;
                            }
                            i++;
                            builder.append("$").append(infos[i]);
                            int bet = Integer.parseInt(infos[i]);
                            if(id == this.id){
                                if(result.equalsIgnoreCase("win")) client.setBet(bet+ client.getBet());
                                else client.setBet(client.getBet() - bet);
                            }
                        }
                        showResultDialog(builder.toString());
                    } else {
                        System.out.println("错误的指令");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("主游戏界面未能连接上服务器");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("卡牌类型转换错误");
        }

    }


}
