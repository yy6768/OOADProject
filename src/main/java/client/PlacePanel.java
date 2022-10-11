package client;

import game.Card;
import game.Place;
import interfaces.Panel;
import utils.ImageUtil;

import javax.swing.*;
import java.awt.*;

public class PlacePanel extends JPanel implements Panel {
    private Place place;

    public PlacePanel(Place place) {
        super(null);

        setBackground(new Color(0, 139, 69));
        setBorder(BorderFactory.createLineBorder(Color.white, 3));
        this.place = place;
        place.setPanel(this);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (place == null) return;
        int cardX = getWidth() / 20;
        int cardY = getHeight() / 12;
        int cardHeight = getHeight() * 7 / 8;
        int cardWidth = cardHeight * 2 / 3;
        int i = 0;

        for (Card card : place.getCards()) {
            Image image;
            if (!card.getIsShow())
                image = ImageUtil.getImage("card/back.JPG");
            else
                image = ImageUtil.getImage("card/"+ card.getColor() +card.getValue()+".JPG");
            g.drawImage(image, cardX + i * cardWidth / 5, cardY, cardWidth, cardHeight, null);
            i ++;
        }
    }

    @Override
    public void resize() {
        repaint();
    }
}
