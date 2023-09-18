package kadai14;

import kadai6.ExThreadsMainTest;

import java.io.*;
import java.net.ServerSocket; //ネットワーク関連のパッケージを利用する
import java.net.Socket;

//　ユーティリティパッケージを利用する

public class Server{

    /*
     * メイン・メソッド 接続要求のあったクライアントに対して接続を行い クライアントから送られる年齢を受信し、その年齢を元に
     * 飲酒の可否を判定した結果をクライアントに対して送信する
     */

    public static void main(String arg[]) {

        ServerSocket server = null;
        Socket socket = null;

        try {
            /* 通信の準備をする */
            System.out.println("ServerSocketを準備します。");
            server = new ServerSocket(5003); // ポート番号を指定し、クライアントとの接続の準備を行う
            socket = server.accept(); // クライアントからの接続要求を待ち、
            new ThreadsServer(socket).start();
            ServerSend threadSend = new ServerSend(socket);
            ServerGive threadGive = new ServerGive(socket);

            threadSend.start();
            threadGive.start();

            System.out.println("サーバ側での接続完了");
            // 要求があればソケットを取得し接続を行う
        } catch (Exception e) {
            System.err.println("接続時にエラーが発生したのでプログラムを終了します");
            e.printStackTrace();
        }

    }

}


class ServerSend extends Thread{

    Socket socket = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    ServerSend(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try {
            while(true) {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject("resultmessage");
                oos.flush();

                sleep(1000);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

class ServerGive extends Thread{
    Socket socket = null;
    ServerGive(Socket socket) {
        this.socket = socket;
    }
    public void run() {

        try {

            while(true) {

                /* サーバから判定結果を受信する */
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                String result = (String) ois.readObject();// 返事を文字列型でキャストする。
                /* 判定結果をディスプレイに表示する */

                if((ois.read()) != -1) {
                    String data = null;
                    try {
                        data = (String) ois.readObject();
                    } catch (java.io.EOFException eof) {
                        eof.printStackTrace();
                    }
                    if (data == null) {
                        return;
                    }

                    System.out.println("from client" + data);
                }
                sleep(1000);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
