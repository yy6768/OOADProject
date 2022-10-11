package client;

import game.GameConfig;
import interfaces.Panel;
import utils.ImageUtil;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 首页页面
 * 通过按钮来供用户选择模式
 * 单人模式
 * 多人模式
 */
public class IndexPanel extends JPanel implements Panel {

    private static final long serialVersionUID = 1L;

    private final PlayerClient client;

    private final List<JLabel> labels = new ArrayList<>();
    private final Image cardImg = ImageUtil.getImage("card/clubA.JPG");

    public IndexPanel(PlayerClient playerClient) {
        this.client = playerClient;
        this.setLayout(null);
        setBackground(new Color(0, 139, 69));
        renderTitle();
        renderLabels();
        repaint();
    }

    /**
     * 绘制标题
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
     * 绘制labels
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

        JLabel ruleButton = new JLabel("Rule");
        ruleButton.setBounds(buttonX, buttonY + buttonDistance, buttonWidth, buttonHeight);
        ruleButton.addMouseListener(new LabelClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(client.getPlayerFrame(),
                        PlayerConfig.RULE,
                        "规则",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        ruleButton.setFont(new Font("Lucida Handwriting", Font.BOLD | Font.ITALIC, fontSize));

        add(playButton);
        labels.add(playButton);
        add(ruleButton);
        labels.add(ruleButton);
    }

    /**
     * 绘制卡牌图片
     * 绘制当前赌注
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int imageX = 3 * client.getPlayerFrame().getWidth() / 5;
        int imageY = 2 * client.getPlayerFrame().getHeight() / 5;
        int imageHeight = client.getPlayerFrame().getHeight() * 3 / 10;
        int imageWidth = imageHeight * 2 / 3;
        g.drawImage(cardImg, imageX, imageY, imageWidth, imageHeight, null, null);

        int betX = 3 * client.getPlayerFrame().getWidth() / 5;
        int betY = 4 * client.getPlayerFrame().getHeight() / 5;
        int fontSize = client.getPlayerFrame().getHeight() / 20;
        setFont(new Font("华文正楷", Font.BOLD | Font.ITALIC, fontSize));
        g.drawString("当前赌资：$"+client.getBet(), betX, betY);
    }

    /**
     * 游戏界面共有函数
     * 当窗口大小调整时启用
     */
    @Override
    public void resize() {
        for (JLabel label : labels) {
            remove(label);
        }
        renderTitle();
        renderLabels();
        repaint();
    }
}

/**
 * JLabel的事件监听器
 * 添加了鼠标上移特效
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
