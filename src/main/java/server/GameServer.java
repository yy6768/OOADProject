package server;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 整个游戏系统的总控制
 * 每当有用户发起请求时，创建与之对应的游戏
 */
public class GameServer{
    private static ServerSocket serverSocket;


    public static void main(String[] args) throws IOException{
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            try(Socket socket = serverSocket.accept()){
                socket.getInputStream();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
