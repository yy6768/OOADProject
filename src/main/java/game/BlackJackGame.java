package game;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BlackJackGame 游戏总控类
 * 管理一局游戏的所有信息
 * player 包括Player对象列表
 * deck 一副牌堆对象
 * dealer 庄家对象
 * client 该局游戏对应的客户端
 */
public class BlackJackGame extends Thread {
    private Integer playerNum;
    private final Map<Integer, Player> players;
    private final Map<Integer, Socket> playerSockets;
    private final Deck deck;
    private final Dealer dealer;

    /**
     * constructor
     * 负责创建游戏所需的对象
     * 初始化游戏
     */
    public BlackJackGame(List<Socket> playerSockets) {
        //初始化排堆
        this.deck = new Deck();
        this.players = new HashMap<>();
        this.playerSockets = new HashMap<>();
        this.playerNum = 0;
        for (Socket s : playerSockets) {
            this.players.put(playerNum, new Player(playerNum, deck));
            this.playerSockets.put(playerNum, s);
            this.playerNum++;
        }
        this.dealer = new Dealer(deck);
    }


    /**
     * 计算游戏结果
     */
    private void judge() {

        List<Card> cards = dealer.getPlace().getCards();
        cards.get(cards.size() - 1).setIsShow(true);
        String dealerInfo = dealer.getPlace().toString();
        sendInfo("dealer " + dealerInfo);

        int dealerSum = dealer.getPlace().calculate();
        int dealerDis = Math.abs(dealerSum - 21);
        for (int i = 0; i < playerNum; i++){
            Player player = players.get(i);
            if(player.getStatus().equalsIgnoreCase("lose")) continue;
            int playerSum = player.getPlace().calculate();
            if (dealerSum > 21) {
                player.setStatus("win");
            } else {
                int playerDis = Math.abs(playerSum - 21);
                if (playerDis < dealerDis) player.setStatus("win");
                else if (playerDis == dealerDis) player.setStatus("draw");
                else player.setStatus("lose");
            }
        }
    }

    /**
     * 游戏总控制函数
     * 对所有游戏中的对象进行操作
     */
    @Override
    public void run() {
        super.run();
        System.out.println("start");
        makeBet();
        deck.init();
        dealer.start();
        String dealerInfo = dealer.getPlace().toString();
        sendInfo("dealer " + dealerInfo);
        for (int i = 0; i < playerNum; i++) {
            Player player = players.get(i);
            player.start();
            String playerInfo = player.getPlace().toString();
            sendInfo("player " + i + " " + playerInfo);
        }
        for (int i = 0; i < playerNum; i++) {
            Player player = players.get(i);
            sendInfo("operate " + i);
            operate(player);
            String playerInfo = player.getPlace().toString();
            sendInfo("player " + i + " " + playerInfo);
        }
        dealer.operate();
        dealerInfo = dealer.getPlace().toString();
        sendInfo("dealer " + dealerInfo);
        judge();
        sendResult();
    }

    private void sendResult() {
        System.out.println("send result");
        StringBuilder res = new StringBuilder();
        res.append("result ");
        for(int i = 0; i < playerNum; i++){
            Player player = players.get(i);
            res.append(player.getId()).append(" ").append(player.getStatus()).append(" ").append(player.getPlace().getBet()).append(" ");
        }
        sendInfo(res.toString());
    }

    private void operate(Player player) {
        System.out.println("player" + player.getId() + " operate");
        Socket socket = playerSockets.get(player.getId());
        boolean flag = true;
        player.setStatus("playing");
        try {
            InputStream in = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String op;
            while (flag) {
                op = br.readLine();
                if (op != null && op.length() != 0) {
                    if (op.equalsIgnoreCase("double")) {
                        System.out.println("double operation!");
                        player.doubleOperation();
                        sendInfo("player " + player.getId() + " " + player.getPlace().toString());
                    } else if (op.equalsIgnoreCase("hit")) {
                        System.out.println("hit operation!");
                        player.hitOperation();
                        sendInfo("player " + player.getId() + " " + player.getPlace().toString());
                    } else if (op.equalsIgnoreCase("stand")) {
                        System.out.println("stand operation!");
                        player.standOperation();
                    } else if (op.equalsIgnoreCase("surrender")) {
                        System.out.println("surrender operation!");
                        player.surrenderOperation();
                    } else {
                        System.out.println("无效的指令");
                    }
                }

                if (!("playing".equalsIgnoreCase(player.getStatus()) || "double".equalsIgnoreCase(player.getStatus()))) {
                    System.out.println("end!");
                    System.out.println(player.getStatus());
                    sendInfo("end " + player.getId());
                    flag = false;
                }
            }
            //用户无法操作说明掉线了或者关闭了客户端
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("操作失败");
            player.setStatus("lose");
            sendInfo("end " + player.getId());
        }
    }

    private void sendInfo(String s) {
        System.out.println("send Info:" + s);
        for (int i = 0; i < playerNum; i++) {
            Socket playerSocket = playerSockets.get(i);
            try {
                OutputStream out = playerSocket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                bw.write(s + "\r\n");
                bw.flush();
            } catch (IOException e) {
                System.out.println("客户端连接失败");
            }
        }
    }

    private void makeBet() {
        System.out.println("make bet");
        for (int i = 0; i < playerNum; i++) {
            Player player = players.get(i);
            Socket playerSocket = playerSockets.get(i);
            try {
                InputStream in = playerSocket.getInputStream();
                OutputStream out = playerSocket.getOutputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                bw.write("makeBet " + i + "\r\n");
                bw.flush();
                String bet = br.readLine();
                System.out.println(bet);
                if (bet != null && bet.length() != 0) {
                    System.out.println("bet:" + bet);
                    Integer gameBet = Integer.parseInt(bet);
                    player.getPlace().setBet(gameBet);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("服务器连接失败");
            }

        }
    }

}
