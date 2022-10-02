package server;

import game.Card;
import game.CardController;
import game.Deck;
import game.Place;

/**
 * Dealer 庄家类
 * 庄家类会管理牌
 *
 *
 */
public class Dealer implements CardController {
    private Place place;
    private BlackJackGame game;
    private Deck deck;
    public Dealer(BlackJackGame game, Deck deck){
        this.place = new Place(this);
        this.game = game;
        this.deck = deck;
    }
    @Override
    public void drawCard() {
        Card card = deck.draw();
        card.setIsShow(false);
        place.addCard(card);
    }

    /**
     * Dealer 起始抽两张牌
     */
    @Override
    public void start() {
        for(int i = 0; i < 2; i++){
            drawCard();
        }
        System.out.println("Dealer:");
        place.showCards();
    }

    @Override
    public void operate() {
        while(place.calculate() < 17){
            drawCard();
        }
        System.out.println("Dealer:");
        place.getCards().get(place.getCards().size() - 1).setIsShow(true);
        place.showCards();
    }

    public Place getPlace() {
        return place;
    }
}
