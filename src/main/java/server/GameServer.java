package server;

/**
 * 整个游戏系统的总控制
 * 每当有用户发起请求时，创建与之对应的游戏
 */
public class GameServer {



    public static void main(String[] args){
        BlackJackGame game = new BlackJackGame(1);
        game.play();
    }
}
