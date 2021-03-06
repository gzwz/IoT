package wlz.mqtt.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerDemo {
    public static void main(String[] args) {
        test();
    }

    private static void test() {
        int port = 8888;

        ExecutorService pool = Executors.newFixedThreadPool(2);

        ServerSocket serverSocket = null;
        try {

            serverSocket = new ServerSocket(port);
            System.out.println( "服务启动成功："+pool);
            while (true){
                Socket socket = serverSocket.accept();
                pool.submit(new MyWorker(socket));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

