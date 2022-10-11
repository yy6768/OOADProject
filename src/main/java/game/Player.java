package game;

import client.PlayerClient;
import client.PlayerPanel;
import interfaces.CardController;

import javax.swing.*;

/**
 * Player类
 * 管理一个Player的所有信息
 * 赌资 bet
 * Player的状态 status
 * Player控制的排堆 place
 * Player所在的游戏 game
 */
public class Player implements CardController {
    private final PlayerClient client;
    /*
        Player类逻辑的基础信息
     */

    /*
        status状态共有一下几种
        idle 空闲状态
        waiting 等待操作
        playing 正在操作
        double 加倍
        completed 操作完成等待其他人操作
        win 获胜
        lose 失败
     */
    private String status;
    private final Place place;
    private final Deck deck;

    /**
     * 构造函数
     *
     * @param client 客户端类
     * @param deck   当前的牌库
     */
    public Player(PlayerClient client, Deck deck) {
        this.client = client;
        this.status = "idle";
        this.place = new Place(this);
        this.deck = deck;
    }


    /**
     * 下注函数
     * 用户输入自己想下注的筹码数
     */
    public void makeBet() {
        String input;
        Integer gameBet;
        while (true) {
            input = JOptionPane.showInputDialog(
                    client.getPlayerFrame(),
                    "您当前有赌注：" + client.getBet() + "\n请输入赌注"
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
        place.setBet(gameBet);
    }

    @Override
    public void drawCard() {
        Card card = deck.draw();
        card.setIsShow(true);
        place.addCard(card);
    }

    /**
     * 玩家抽取初始的2张手牌
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
     * 计算最后玩家的点数
     */
    public void calculateBet() {
        if ("win".equalsIgnoreCase(status)) client.setBet(client.getBet() + place.getBet());
        else if ("draw".equalsIgnoreCase(status)) client.setBet(client.getBet());
        else if ("lose".equalsIgnoreCase(status)) client.setBet(client.getBet() - place.getBet());
        System.out.println(client.getBet());
    }
}
