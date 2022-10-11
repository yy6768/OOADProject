package client;

import interfaces.Panel;
import game.BlackJackGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayerPanel �û����
 * ������ʾ�û��Ļ�����Ϣ
 * �̳�Swing�����JPanel
 * ʵ���������Լ�����Ľӿ�Panel
 */
public class PlayerPanel extends JPanel implements Panel {
    //��ǰ�ͻ���
    private final PlayerClient client;
    //��ǰ
    private final BlackJackGame game;
    //�洢�����е�����button
    private final List<JButton> buttons;
    //��ҵ�Place��ׯ�ҵ�Place
    private final PlacePanel playerPlace;
    private final PlacePanel dealerPlace;

    /**
     * ���캯��
     */
    public PlayerPanel(PlayerClient playerClient, BlackJackGame blackJackGame) {
        client = playerClient;
        game = blackJackGame;
        this.setLayout(null);
        buttons = new ArrayList<>();
        playerPlace = new PlacePanel(game.getPlayer().getPlace());
        dealerPlace = new PlacePanel(game.getDealer().getPlace());
        setBackground(new Color(0, 139, 69));
        //���ð�ť
        renderButtons();
        //����Place
        renderPlace();
    }

    /**
     * ������Ҳ�����buttons;
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
     * ������Һ�ׯ�ҵ�Place
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
     * ��ʾ���
     */
    public void showResultDialog() {
        String status = "Player  " + game.getPlayer().getStatus();
        ResultDialog dialog = new ResultDialog(client, status);
        dialog.setVisible(true);
    }


    /**
     * ���������Ϣ��ׯ����Ϣ
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int fontSize = client.getPlayerFrame().getHeight() / 20;
        int dealerY = client.getPlayerFrame().getHeight() / 3;
        int playerY = client.getPlayerFrame().getHeight() * 2 / 3;
        int informationX = client.getPlayerFrame().getWidth() * 3 / 4;

        setFont(new Font("��������", Font.ITALIC, fontSize));


        String playerInformation = game.getPlayer().getPlace().calculate().toString();
        String dealerStatus;
        if (game.getDealer().getPlace().calculate() == 21)
            dealerStatus = "BlackJack";
        else
            dealerStatus = "С��21��";

        g.drawString("��ҷ���:", informationX, playerY);
        g.drawString(playerInformation, informationX, playerY + fontSize);
        g.drawString("ׯ��״̬:", informationX, dealerY);
        g.drawString(dealerStatus, informationX, dealerY + fontSize);
    }

    /**
     * ���н���̳У����������ڴ�Сʱ����
     * �Ƴ���ǰ������������»����������
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

    //get��set����
    public List<JButton> getButtons() {
        return buttons;
    }
}
