package client;

import game.Card;
import game.CardController;
import game.Deck;
import game.Place;
import server.BlackJackGame;

import java.util.Scanner;

/**
 * Player��
 * ����һ��Player��������Ϣ
 * ��ݱ�ʶ�� Id
 * ���� bet
 * Player��״̬ status
 * Player���Ƶ��Ŷ� place
 * Player���ڵ���Ϸ game
 */
public class Player implements CardController {
    /*
        Player���߼��Ļ�����Ϣ
     */
    private Integer id; //��Ϸid
    private Integer bet; // ��ǰ�Ķ���

    /*
        status״̬����һ�¼���
        idle ����״̬
        waiting �ȴ�������Ϸ
        playing ������Ϸ
        win ��ʤ
        lose ʧ��
     */
    private String status;
    private Place place;
    private BlackJackGame game;
    private Deck deck;
//    //��ǰ��ҵ���Ϸ����
//    public static final JFrame playerFrame = new JFrame("BlackJack");


    /**
     * ���캯��
     * @param game
     * @param deck
     */
    public Player(BlackJackGame game, Deck deck) {
        id = 0;
        bet = PlayerConfig.INIT_BET;
        status = "playing";
        this.place = new Place(this);
        this.game = game;
        this.deck = deck;
    }



    /**
     * ��ע
     */
    public void makeBet(){
        System.out.println("����$1000");
        Scanner scanner = new Scanner(System.in);
        System.out.println("����ע�����Ϊ50��");
        Integer gameBet = scanner.nextInt();
        while(gameBet < 50 || bet < gameBet) {
            if(gameBet < 50) System.out.println("���Ϊ50");
            else System.out.println("û����ô�����");
            gameBet = scanner.nextInt();
        }
        bet -= gameBet;
        place.setBet(gameBet);
    }

    @Override
    public void drawCard() {
        Card card = deck.draw();
        card.setIsShow(true);
        place.addCard(card);
    }

    /**
     * ��ȡ��ʼ��2������
     *
     */
    @Override
    public void start() {
        for(int i = 0; i < 2; i++){
            drawCard();
        }
        System.out.println("Player" + id + ":");
        place.showCards();
        Integer sum = place.calculate();
        if(sum == 21){
            place.setBet(place.getBet() + place.getBet() / 2);
            System.out.println("Black Jack!");
        }
    }


    private void doubleOperation() {
        place.setBet(place.getBet() * 2);
        drawCard();
        System.out.println("Player" + id + ":");
        place.showCards();
    }

    private void hitOperation() {
        drawCard();
        System.out.println("Player" + id + ":");
        place.showCards();
        while(place.calculate() <= 21){
            System.out.println("Hit or stay");
            Scanner scanner = new Scanner(System.in);
            String op = scanner.next();
            while(!"hit".equalsIgnoreCase(op) && !"stay".equalsIgnoreCase(op)){
                System.out.println("Please Enter hit or stay");
                op = scanner.next();
            }
            if("hit".equalsIgnoreCase(op)) {
                drawCard();
                System.out.println("Player" + id + ":");
                place.showCards();
            }
            else if("stay".equalsIgnoreCase(op)){
                break;
            }
        }
    }

    @Override
    public void operate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Double, Hit or stay");
        String op = scanner.next();
        while(!"hit".equalsIgnoreCase(op) && !"stay".equalsIgnoreCase(op) && !"double".equalsIgnoreCase(op)){
            System.out.println("Please Enter double,hit or stay");
            op = scanner.next();
        }
        if("double".equalsIgnoreCase(op)) {
           doubleOperation();
        }
        if("hit".equalsIgnoreCase(op)){
            hitOperation();
        }

    }

    public Place getPlace(){
        return place;
    }

    public void win() {
        bet += place.getBet() * 2;
        System.out.println("Player " + id + " win!");
        System.out.println("Obtain: $"+ place.getBet());
    }

    public void lose(){
        System.out.println("Player " + id + " loss!");
        System.out.println("Lose: $"+ place.getBet());
    }

    public void draw() {
        bet += place.getBet();
        System.out.println("Player" + id + "draw.");
    }



}
