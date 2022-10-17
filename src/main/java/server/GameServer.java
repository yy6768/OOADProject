package server;

import game.BlackJackGame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 整个游戏系统的服务器，负责启动游戏
 * 每当有用户发起请求时，创建与之对应的游戏
 */
public class GameServer extends ServerSocket {
    // 当前服务器状态对应对的玩家交互线程
    private final List<Socket> waitingList = new ArrayList<>();

    public GameServer(int port) throws IOException {
        super(port);
    }


    public static void main(String[] args) {
        try (GameServer s = new GameServer(ServerConfig.SOCKET_PORT)) {
            while (true) {
                Socket incoming = s.accept();
                System.out.println("connect!");
                InputStream inputStream = incoming.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String command;
                if ((command = br.readLine()) != null) {
                    System.out.println(command);
                    if ("s".equalsIgnoreCase(command)) {
                        List<Socket> players = new ArrayList<>();
                        players.add(incoming);
                        BlackJackGame game = new BlackJackGame(players);
                        game.start();
                    } else if ("m".equalsIgnoreCase(command)) {
                        s.waitingList.add(incoming);
                        System.out.println("m " + s.waitingList.size() );
                        if (s.waitingList.size() == 3) {
                            List<Socket> sockets = new ArrayList<>(s.waitingList);
                            sendInfo(sockets);
                            BlackJackGame game = new BlackJackGame(sockets);
                            s.waitingList.clear();
                            System.out.println("multiPlayer");
                            game.start();
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendInfo(List<Socket> sockets) {
        for(Socket socket: sockets){
            try{
                OutputStream out = socket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                bw.write("ok\r\n");
                bw.flush();
            } catch (IOException e){
                System.out.println("匹配失败");
            }
        }
    }
}
