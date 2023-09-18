package program;

////import kadai14.*;

//import basic.NumberGuessingGame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Server {
    JFrame jf;

    RandomWord randomWord = new RandomWord();
    String correctWord = randomWord.getAnswer();



    public static void main(String arg[]) {
        Server server = new Server();
        server.testServer();
    }



    public void testServer(){
        ServerSocket server = null;
        Socket socket = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        BufferedReader reader = null;
        MyTargetpaint myTargetpaint;
        YourTargetpaint yourTargetpaint;
        MiddlePaint middle = new MiddlePaint();  //



        try {
            /* 通信の準備をする */
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("ServerSocketを準備します。");
            server = new ServerSocket(5002); // ポート番号を指定し、クライアントとの接続の準備を行う
            socket = server.accept(); // クライアントからの接続要求を待ち、
            System.out.println("サーバ側での接続完了");

            //ウィンドウを生成
            jf = new JFrame("Server");
            jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
            jf.setSize(720,480);

            System.out.println("~相手のターン~");

            jf.add(middle);
            middle.revalidate(); // コンポーネントの再配置
            middle.repaint(); // パネルの再描画*/
            jf.setVisible(true);


            // 要求があればソケットを取得し接続を行う
            if (oos == null)
                oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(correctWord);
            oos.flush();
        } catch (Exception e) {
            System.err.println("接続時にエラーが発生したのでプログラムを終了します");
            e.printStackTrace();
        }





        Wordpaint wordpaint = new Wordpaint(correctWord,720,480);

        jf.add(wordpaint);
        jf.setVisible(true);




        Number1 num1 = new Number1();
        Number1 num2 = new Number1();
        String result=null;
        String data;
        String sendMessage;
        ArrayList<String[]> myList = new ArrayList<>();
        ArrayList<String> myWordList = new ArrayList<>();
        ArrayList<String[]> yourList = new ArrayList<>();

        try {

            if (ois == null)
                ois = new ObjectInputStream(socket.getInputStream());

            while (true) {
                try{
                    result = (String) ois.readObject();// Integerクラスでキャスト。
                }catch(EOFException eof){
                    eof.printStackTrace();
                }
                if(result==null)
                    return;
                num2.hitJudge(correctWord,result);
                String[] newYourList = num2.hitJudge(correctWord,result);
                yourList.add(newYourList);

                //相手の結果を出力
                System.out.println("相手の正解率");


                /*for(String[] nowList:yourList){
                    for(int i = 0; i < nowList.length;i++){
                        System.out.print(nowList[i] + " ");
                    }
                    System.out.println();
                }*/

                for(int i=0; i<newYourList.length; i++){
                    System.out.print(newYourList[i] + " ");
                }
                System.out.println();
                System.out.println("-あなたのターン-");

                //ウィンドウに描画
                yourTargetpaint = new YourTargetpaint(yourList,720,280);
                jf.add(yourTargetpaint);
                jf.setVisible(true);

                // MiddlePaint middle = new MiddlePaint(700,720);  //amidoru
                middle.removeAll();
                jf.remove(middle);
                jf.revalidate(); // コンポーネントの再配置
                jf.repaint(); // パネルの再描画
                jf.setVisible(true);

                //どちらかが完全正答した場合、結果を描画
                if(num1.isEnd() && num2.isEnd()){
                    System.out.println("引き分け！");
                    Finishpaint finishpaint = new Finishpaint(correctWord,"引き分け",720,1000);
                    jf.remove(middle);
                    jf.add(finishpaint);
                    jf.setVisible(true);
                    break;
                } else if(num1.isEnd() && myList.size()==yourList.size()){
                    System.out.println("相手の勝利！");
                    Finishpaint finishpaint = new Finishpaint(correctWord,"相手の勝利！",720,1000);
                    jf.remove(middle);
                    jf.add(finishpaint);
                    jf.setVisible(true);
                    break;
                }else if(num2.isEnd() && myList.size()==yourList.size()){
                    System.out.println("あなたの勝利！");
                    Finishpaint finishpaint = new Finishpaint(correctWord,"あなたの勝利！",720,1000);
                    jf.remove(middle);
                    jf.add(finishpaint);
                    jf.setVisible(true);
                    break;
                }

                //文字を入力
                while(true){

                    System.out.print("判定する文字 >");
                    data = reader.readLine();
                    if(data.length() == correctWord.length()){
                        break;
                    }else{
                        System.out.print("入力文字が答えと一致しません");
                    }
                }
                //文字のhitblowを判定し、一覧に追加
                String[] newMyList = num1.hitJudge(correctWord,data);
                myList.add(newMyList);
                myWordList.add(data);

               /* for(String[] nowList:myList){
                    for(int i = nowList.length-5; i < nowList.length;i++){
                        System.out.print(nowList[i] + " ");
                    }
                   System.out.println();

                }*/

                for(int i=0; i<newMyList.length; i++){
                    System.out.print(newMyList[i] + " ");
                }
                System.out.println();
                System.out.println("相手のターン");


              jf.add(middle);
                middle.revalidate(); // コンポーネントの再配置
                middle.repaint(); // パネルの再描画*/



                jf.setVisible(true);

                //ウィンドウに描画
                myTargetpaint = new MyTargetpaint(myList,myWordList,720,280);
                jf.add(myTargetpaint);
                jf.setVisible(true);
                sendMessage = data;

                //結果をClientに送信
                if (oos == null)
                    oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject(sendMessage);
                oos.flush();

                //どちらかが完全正答した場合、結果を描画
                if(num1.isEnd() && num2.isEnd()){
                    System.out.println("引き分け！");
                    Finishpaint finishpaint = new Finishpaint(correctWord,"引き分け",720,480);
                    jf.remove(middle);
                    jf.revalidate(); // コンポーネントの再配置
                    jf.repaint(); //
                    jf.add(finishpaint);
                    jf.setVisible(true);
                    break;
                } else if(num1.isEnd() && myList.size()==yourList.size()){
                    System.out.println("あなたの勝利！");
                    Finishpaint finishpaint = new Finishpaint(correctWord,"あなたの勝利！",720,480);
                    jf.remove(middle);
                    jf.revalidate(); // コンポーネントの再配置
                    jf.repaint(); //
                    jf.add(finishpaint);
                    jf.setVisible(true);
                    break;
                }else if(num2.isEnd() && myList.size()==yourList.size()){
                    System.out.println("相手の勝利！");
                    Finishpaint finishpaint = new Finishpaint(correctWord,"相手の勝利！",720,480);
                    jf.remove(middle);
                    jf.revalidate(); // コンポーネントの再配置
                    jf.repaint(); //
                    jf.add(finishpaint);
                    jf.setVisible(true);
                    break;
                }
            }

        }// エラーが発生したらエラーメッセージを表示してプログラムを終了する
        catch (EOFException eof) {
            eof.printStackTrace();
        } catch (java.net.SocketException e) {
            e.printStackTrace();

        } catch (IOException e) {
            System.err.println("通信時エラーが発生したのでプログラムを終了します");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        // 修了処理
        if (ois != null) {

            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (oos != null) {
            try {
                oos.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}