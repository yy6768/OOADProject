package client;

import game.Card;
import game.CardController;
import game.Deck;
import game.Place;
import server.BlackJackGame;

import java.util.Scanner;

/**
 * Player类
 * 管理一个Player的所有信息
 * 身份标识符 Id
 * 赌资 bet
 * Player的状态 status
 * Player控制的排堆 place
 * Player所在的游戏 game
 */
public class Player implements CardController {
    /*
        Player类逻辑的基础信息
     */
    private Integer id; //游戏id
    private Integer bet; // 当前的赌资

    /*
        status状态共有一下几种
        idle 空闲状态
        waiting 等待多人游戏
        playing 正在游戏
        win 获胜
        lose 失败
     */
    private String status;
    private Place place;
    private BlackJackGame game;
    private Deck deck;
//    //当前玩家的游戏界面
//    public static final JFrame playerFrame = new JFrame("BlackJack");


    /**
     * 构造函数
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
     * 下注
     */
    public void makeBet(){
        System.out.println("你有$1000");
        Scanner scanner = new Scanner(System.in);
        System.out.println("请下注（最低为50）");
        Integer gameBet = scanner.nextInt();
        while(gameBet < 50 || bet < gameBet) {
            if(gameBet < 50) System.out.println("最低为50");
            else System.out.println("没有这么多筹码");
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
     * 抽取初始的2张手牌
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
