package client;

import interfaces.Panel;
import game.BlackJackGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayerPanel 用户面板
 * 用于显示用户的基本信息
 * 继承Swing包里的JPanel
 * 实现了我们自己定义的接口Panel
 */
public class PlayerPanel extends JPanel implements Panel {
    //当前客户端
    private final PlayerClient client;
    //当前
    private final BlackJackGame game;
    //存储界面中的所有button
    private final List<JButton> buttons;
    //玩家的Place和庄家的Place
    private final PlacePanel playerPlace;
    private final PlacePanel dealerPlace;

    /**
     * 构造函数
     */
    public PlayerPanel(PlayerClient playerClient, BlackJackGame blackJackGame) {
        client = playerClient;
        game = blackJackGame;
        this.setLayout(null);
        buttons = new ArrayList<>();
        playerPlace = new PlacePanel(game.getPlayer().getPlace());
        dealerPlace = new PlacePanel(game.getDealer().getPlace());
        setBackground(new Color(0, 139, 69));
        //设置按钮
        renderButtons();
        //设置Place
        renderPlace();
    }

    /**
     * 设置玩家操作的buttons;
     */
    public void renderButtons() {

        int buttonX = client.getPlayerFrame().getWidth() / 20;
        int buttonY = client.getPlayerFrame().getHeight() * 3 / 5;
        int buttonWidth = client.getPlayerFrame().getWidth() / 6;
        int buttonHeight = client.getPlayerFrame().getHeight() / 20;
        int fontSize = client.getPlayerFrame().getHeight() / 30;

        for (int i = 0; i < PlayerConfig.LABEL_NAME.length; i++) {
            JButton button = new JButton(PlayerConfig.LABEL_NAME[i]);
            button.setFont(new Font("Lucida Handwriting", Font.BOLD | Font.ITALIC, fontSize));
            button.setBounds(buttonX, buttonY + i * 2 * fontSize, buttonWidth, buttonHeight);
            button.setEnabled(false);
            add(button);
            switch (i) {
                case 0:
                    button.addActionListener(e -> game.getPlayer().doubleOperation());
                    break;
                case 1:
                    button.addActionListener(e -> game.getPlayer().hitOperation());
                    break;
                case 2:
                    button.addActionListener(e -> game.getPlayer().standOperation());
                    break;
                case 3:
                    button.addActionListener(e -> game.getPlayer().surrenderOperation());
            }
            buttons.add(button);
        }
    }

    /**
     * 绘制玩家和庄家的Place
     */
    public void renderPlace() {
        int placeWidth = client.getPlayerFrame().getWidth() / 2;
        int placeHeight = client.getPlayerFrame().getHeight() / 3;
        int placeX = client.getPlayerFrame().getWidth() / 4;
        int dealerY = client.getPlayerFrame().getHeight() / 12;
        int playerY = client.getPlayerFrame().getHeight() / 2;


        playerPlace.setBounds(placeX, playerY, placeWidth, placeHeight);
        dealerPlace.setBounds(placeX, dealerY, placeWidth, placeHeight);

        add(playerPlace);
        add(dealerPlace);
    }

    /**
     * 显示结果
     */
    public void showResultDialog() {
        String status = "Player  " + game.getPlayer().getStatus();
        ResultDialog dialog = new ResultDialog(client, status);
        dialog.setVisible(true);
    }


    /**
     * 绘制玩家信息和庄家信息
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int fontSize = client.getPlayerFrame().getHeight() / 20;
        int dealerY = client.getPlayerFrame().getHeight() / 3;
        int playerY = client.getPlayerFrame().getHeight() * 2 / 3;
        int informationX = client.getPlayerFrame().getWidth() * 3 / 4;

        setFont(new Font("华文正楷", Font.ITALIC, fontSize));


        String playerInformation = game.getPlayer().getPlace().calculate().toString();
        String dealerStatus;
        if (game.getDealer().getPlace().calculate() == 21)
            dealerStatus = "BlackJack";
        else
            dealerStatus = "小于21点";

        g.drawString("玩家分数:", informationX, playerY);
        g.drawString(playerInformation, informationX, playerY + fontSize);
        g.drawString("庄家状态:", informationX, dealerY);
        g.drawString(dealerStatus, informationX, dealerY + fontSize);
    }

    /**
     * 所有界面继承，当调整窗口大小时启用
     * 移除当前所有组件并重新绘制其他组件
     */
    @Override
    public void resize() {
        for (JButton button : buttons) {
            remove(button);
        }
        remove(playerPlace);
        remove(dealerPlace);
        renderButtons();
        renderPlace();
        repaint();
    }

    //get和set函数
    public List<JButton> getButtons() {
        return buttons;
    }
}
