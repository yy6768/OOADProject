package game;

import client.PlayerClient;
import client.PlayerPanel;
import interfaces.CardController;

import javax.swing.*;

/**
 * Player��
 * ����һ��Player��������Ϣ
 * ���� bet
 * Player��״̬ status
 * Player���Ƶ��Ŷ� place
 * Player���ڵ���Ϸ game
 */
public class Player implements CardController {
    private final PlayerClient client;
    /*
        Player���߼��Ļ�����Ϣ
     */

    /*
        status״̬����һ�¼���
        idle ����״̬
        waiting �ȴ�����
        playing ���ڲ���
        double �ӱ�
        completed ������ɵȴ������˲���
        win ��ʤ
        lose ʧ��
     */
    private String status;
    private final Place place;
    private final Deck deck;

    /**
     * ���캯��
     *
     * @param client �ͻ�����
     * @param deck   ��ǰ���ƿ�
     */
    public Player(PlayerClient client, Deck deck) {
        this.client = client;
        this.status = "idle";
        this.place = new Place(this);
        this.deck = deck;
    }


    /**
     * ��ע����
     * �û������Լ�����ע�ĳ�����
     */
    public void makeBet() {
        String input;
        Integer gameBet;
        while (true) {
            input = JOptionPane.showInputDialog(
                    client.getPlayerFrame(),
                    "����ǰ�ж�ע��" + client.getBet() + "\n�������ע"
            );
            try {
                gameBet = Integer.parseInt(input);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(client.getPlayerFrame(), "�����쳣", "����", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            if (gameBet < 50) {
                JOptionPane.showMessageDialog(client.getPlayerFrame(), "��Ͷ�עΪ50", "����", JOptionPane.ERROR_MESSAGE);
            } else if (client.getBet() < gameBet) {
                JOptionPane.showMessageDialog(client.getPlayerFrame(), "û����ô���ע", "����", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }
        place.setBet(gameBet);
    }

    @Override
    public void drawCard() {
        Card card = deck.draw();
        card.setIsShow(true);
        place.addCard(card);
    }

    /**
     * ��ҳ�ȡ��ʼ��2������
     */
    @Override
    public void start() {
        for (int i = 0; i < 2; i++) {
            drawCard();
        }
        Integer sum = place.calculate();
        if (sum == 21) {
            place.setBet(place.getBet() + place.getBet() / 2);
        }
    }

    public void doubleOperation() {
        place.setBet(place.getBet() * 2);
        System.out.println(place.getBet());
        PlayerPanel panel = (PlayerPanel) this.client.getCurrentPanel();
        panel.getButtons().get(0).setEnabled(false);
        status = "double";
    }

    public void hitOperation() {
        drawCard();
        PlayerPanel panel = (PlayerPanel) this.client.getCurrentPanel();
        panel.getButtons().get(0).setEnabled(false);
        if (place.calculate() > 21) status = "lose";
        else if (place.calculate() == 21) status = "completed";
        if ("double".equalsIgnoreCase(status)) status = "completed";
    }

    public void standOperation() {
        PlayerPanel panel = (PlayerPanel) this.client.getCurrentPanel();
        for (JButton button : panel.getButtons()) {
            button.setEnabled(false);
        }
        status = "completed";
    }

    public void surrenderOperation() {
        place.setBet(place.getBet() / 2);
        PlayerPanel panel = (PlayerPanel) this.client.getCurrentPanel();
        for (JButton button : panel.getButtons()) {
            button.setEnabled(false);
        }
        this.status = "lose";
    }

    @Override
    public void operate() {
        if (place.calculate() == 21) {
            status = "completed";
            return;
        }
        status = "playing";
        PlayerPanel panel = (PlayerPanel) client.getCurrentPanel();
        for (JButton button : panel.getButtons()) {
            button.setEnabled(true);
        }
        while ("playing".equalsIgnoreCase(status) || "double".equalsIgnoreCase(status)) ;
    }

    public Place getPlace() {
        return place;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    /**
     * ���������ҵĵ���
     */
    public void calculateBet() {
        if ("win".equalsIgnoreCase(status)) client.setBet(client.getBet() + place.getBet());
        else if ("draw".equalsIgnoreCase(status)) client.setBet(client.getBet());
        else if ("lose".equalsIgnoreCase(status)) client.setBet(client.getBet() - place.getBet());
        System.out.println(client.getBet());
    }
}
