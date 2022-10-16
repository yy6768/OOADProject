package game;

import interfaces.CardController;

/**
 * Player��
 * ����һ��Player��������Ϣ
 * ���� bet
 * Player��״̬ status
 * Player���Ƶ��Ŷ� place
 * Player���ڵ���Ϸ game
 */
public class Player implements CardController {
    //player�ڱ��ֶ�ս�е�id
    private Integer id;
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
     * @param id �������Ϸ�е�id
     * @param deck   ��ǰ���ƿ�
     */
    public Player(Integer id, Deck deck) {
        this.id = id;
        this.status = "idle";
        this.place = new Place(this);
        this.deck = deck;
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

    /**
     * double����
     * ��ע����
     * ״̬�滻��double(stand��hitֻ��ִ��һ�Σ�
     */
    public void doubleOperation() {
        place.setBet(place.getBet() * 2);
        status = "double";
    }

    /**
     * hit����
     * 1������
     * 2����ֹ�ӱ�
     * 3���ж��Ƿ���
     */
    public void hitOperation() {
        drawCard();
        if (place.calculate() > 21) status = "lose";
        else if (place.calculate() == 21) status = "completed";
        if ("double".equalsIgnoreCase(status)) status = "completed";
    }

    public void standOperation() {
        status = "completed";
    }

    public void surrenderOperation() {
        place.setBet(place.getBet() / 2);
        this.status = "lose";
    }




    /**
     * ���������ҵĵ���
     */


    public Place getPlace() {
        return place;
    }

    public Integer getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
