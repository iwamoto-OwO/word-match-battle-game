package kadai14;


import kadai6.ExThreadsMainTest;

import java.io.BufferedReader; //　入出力関連パッケージを利用する
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket; //ネットワーク関連のパッケージを利用する
//　ユーティリティパッケージを利用する

public class Client {

    public static void main(String arg[]) {

        BufferedReader reader = null;
        Socket socket = null;

        try {
            /* 通信の準備をする */
            reader = // キーボードから接続するサーバ名を読み込む
                    new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Server name(localhost or 133.27.....)? >");
            String serverName = reader.readLine();
            socket = // 指定されたサーバの5000番ポートに接続を要求する
                    new Socket(serverName, 5003);
            System.out.print("クライアントからの接続成功");
        } catch (Exception e) {

            e.printStackTrace();
        }

        ClientSend threadSend = new ClientSend(socket);
        ClientGive threadGive = new ClientGive(socket);

        threadSend.start();
        //threadGive.start();

    }
}

class ClientSend extends Thread{

    Socket socket = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    String result = "";
    ClientSend(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        System.out.println("a");
        try {
            System.out.println("b");
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("bc");
            while(true) {
                System.out.println("c");
                /* キーボードから年齢を読み込む */
                System.out.print("Your age? >");
                String input = reader.readLine();
                //String input = "testMessage";
                /* サーバに年齢を送信する */
                oos.writeObject(input);
                System.out.println(input);
                oos.flush();

                result = (String) ois.readObject();// 返事を文字列型でキャストする。
                System.out.println(result);
                sleep(1000);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

class ClientGive extends Thread{
    Socket socket = null;
    ClientGive(Socket socket) {
        this.socket = socket;

    }
    public void run() {
            try {
                while(true) {
                    /* サーバから判定結果を受信する */
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    String result = (String) ois.readObject();// 返事を文字列型でキャストする。
                    /* 判定結果をディスプレイに表示する */
                    System.out.println(result);
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
