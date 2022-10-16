package client;

import game.GameConfig;
import interfaces.Panel;
import utils.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ��ҳҳ��
 * ���ڿ�ʼ��Ϸ����Ϸ����ť
 */
public class IndexPanel extends JPanel implements Panel {
    //��ǰ����Ŀͻ���
    private final PlayerClient client;
    //��ǰ��������Label
    private final List<JLabel> labels = new ArrayList<>();
    private Image cardImg;


    public IndexPanel(PlayerClient playerClient) {
        this.client = playerClient;
        this.setLayout(null);
        setBackground(new Color(0, 139, 69));
        renderTitle();
        renderLabels();
        Random random  = new Random();
        String randomColor = GameConfig.CARD_COLORS[random.nextInt(4)];
        String randomValue = GameConfig.CARD_VALUES[random.nextInt(13)];
        cardImg = ImageUtil.getImage("card/"+randomColor+randomValue+".JPG");
        repaint();
    }

    /**
     * ���Ʊ���Label
     */
    public void renderTitle() {
        int titleX = client.getPlayerFrame().getWidth() / 10;
        int titleY = client.getPlayerFrame().getHeight() / 10;
        int titleWidth = client.getPlayerFrame().getWidth() / 2;
        int fontSize = client.getPlayerFrame().getHeight() / 10;
        JLabel title = new JLabel("BlackJack");
        title.setFont(new Font("Lucida Handwriting", Font.BOLD | Font.ITALIC, fontSize));
        title.setBounds(titleX, titleY, titleWidth, fontSize);
        add(title);
        labels.add(title);
    }

    /**
     * ����label ��ť
     * ����ӵ���¼�
     */
    public void renderLabels() {
        int buttonX = client.getPlayerFrame().getWidth() / 5;
        int buttonY = 2 * client.getPlayerFrame().getHeight() / 5;
        int buttonWidth = 3 * client.getPlayerFrame().getWidth() / 10;
        int buttonHeight = client.getPlayerFrame().getHeight() / 10;
        int buttonDistance = buttonWidth / 3;
        int fontSize = client.getPlayerFrame().getHeight() / 20;

        JLabel playButton = new JLabel("Play");
        playButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        playButton.addMouseListener(new LabelClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                client.startGame();
            }
        });
        playButton.setFont(new Font("Lucida Handwriting", Font.BOLD | Font.ITALIC, fontSize));
        add(playButton);
        labels.add(playButton);

        JLabel multiPlayButton = new JLabel("MultiPlayer");
        multiPlayButton.setBounds(buttonX, buttonY + buttonDistance, buttonWidth, buttonHeight);
        multiPlayButton.addMouseListener(new LabelClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                client.startMultiGame();
            }
        });
        multiPlayButton.setFont(new Font("Lucida Handwriting", Font.BOLD | Font.ITALIC, fontSize));
        add(multiPlayButton);
        labels.add(multiPlayButton);

        JLabel ruleButton = new JLabel("Rule");
        ruleButton.setBounds(buttonX, buttonY + 2 * buttonDistance, buttonWidth, buttonHeight);
        ruleButton.addMouseListener(new LabelClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(client.getPlayerFrame(),
                        PlayerConfig.RULE,
                        "����",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        ruleButton.setFont(new Font("Lucida Handwriting", Font.BOLD | Font.ITALIC, fontSize));


        add(ruleButton);
        labels.add(ruleButton);
    }

    /**
     * ���ƿ���ͼƬ
     * ���Ƶ�ǰ��ע
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        //���ƿ���ͼƬ
        super.paint(g);
        int imageX = 3 * client.getPlayerFrame().getWidth() / 5;
        int imageY = 2 * client.getPlayerFrame().getHeight() / 5;
        int imageHeight = client.getPlayerFrame().getHeight() * 3 / 10;
        int imageWidth = imageHeight * 2 / 3;
        g.drawImage(cardImg, imageX, imageY, imageWidth, imageHeight, null, null);

        //���Ƶ�ǰ��ע
        int betX = 3 * client.getPlayerFrame().getWidth() / 5;
        int betY = 4 * client.getPlayerFrame().getHeight() / 5;
        int fontSize = client.getPlayerFrame().getHeight() / 20;
        setFont(new Font("��������", Font.BOLD | Font.ITALIC, fontSize));
        g.drawString("��ǰ���ʣ�$"+client.getBet(), betX, betY);
    }

    /**
     * ��Ϸ���湲�к���
     * ���ڵ�����Сʱ���Ƴ�����������»���
     */
    @Override
    public void resize() {
        for (JLabel label : labels) {
            remove(label);
        }
        renderTitle();
        renderLabels();
        Random random  = new Random();
        String randomColor = GameConfig.CARD_COLORS[random.nextInt(4)];
        String randomValue = GameConfig.CARD_VALUES[random.nextInt(13)];
        cardImg = ImageUtil.getImage("card/"+randomColor+randomValue+".JPG");
        repaint();
    }
}

/**
 * JLabel���¼�������
 * ��������������Ч
 */
class LabelClickListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel src = (JLabel) e.getSource();
        src.setForeground(Color.white);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel src = (JLabel) e.getSource();
        src.setForeground(Color.black);
    }
}
