package client;

import interfaces.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayerPanel的子Panel
 * 用于绘制当前Place（卡牌容器）的卡牌
 */
public class PlacePanel extends JPanel implements Panel {
    private final List<Image> cardImages;
    public PlacePanel() {
        super(null);
        cardImages = new ArrayList<>();
        setBackground(new Color(0, 139, 69));
        setBorder(BorderFactory.createLineBorder(Color.white, 3));
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int cardX = getWidth() / 20;
        int cardY = getHeight() / 12;
        int cardHeight = getHeight() * 7 / 8;
        int cardWidth = cardHeight * 2 / 3;

        for (int i = 0; i < cardImages.size(); i++) {
            Image image = cardImages.get(i);
            g.drawImage(image, cardX + i * cardWidth / 5, cardY, cardWidth, cardHeight, null);
        }
    }


    @Override
    public void resize() {
        repaint();
    }

    public List<Image> getCardImages() {
        return cardImages;
    }
}
