package program;

//import kadai14.*;

//import basic.SystemOutTest;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Client {
    JFrame jf;
    String correctWord = "";

    public static void main(String arg[]) {
        Client client = new Client();
        client.testClient();
    }


    public void testClient(){
        BufferedReader reader = null;
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        MyTargetpaint myTargetpaint;
        ArrayList<String> myWordList = new ArrayList<>();
        YourTargetpaint yourTargetpaint;

        try {
            /* 通信の準備をする */
            reader = // キーボードから接続するサーバ名を読み込む
                    new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Server name(localhost or 133.27.....)? >");
            String serverName = reader.readLine();
            socket = // 指定されたサーバの5000番ポートに接続を要求する
                    new Socket(serverName, 5002);
            System.out.println("クライアントからの接続成功");
            if(ois == null){
                ois = new ObjectInputStream(socket.getInputStream());
            }
            correctWord = (String) ois.readObject();
        } catch (Exception e) {

            e.printStackTrace();
        }

        //ウィンドウを生成
        jf = new JFrame("Client");
        jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jf.setSize(720,480);
        Wordpaint wordpaint = new Wordpaint(correctWord,720,480);
        jf.add(wordpaint);
        jf.setVisible(true);

        Number1 num1 = new Number1();
        Number1 num2 = new Number1();
       // MiddlePaint middle = new MiddlePaint(300,720);  //u
        String result;
        String data;
        String sendMessage;
        ArrayList<String[]> myList = new ArrayList<>();
        ArrayList<String[]> yourList = new ArrayList<>();

        try {

            //文字を入力
            oos = new ObjectOutputStream(socket.getOutputStream());
            while(true){
                System.out.print("判定する文字 >");
                data = reader.readLine();
                if(data.length() == correctWord.length()){  //答えと入力文字の文字数が一致しないとき
                    break;
                }else{
                    System.out.println("入力文字数が答えと一致しません");
                }
            }
            //文字のhitblowを判定し、一覧に追加
            String[] newMyList = num2.hitJudge(correctWord,data);
            myList.add(newMyList);
            myWordList.add(data);
            /*for(String[] nowList:myList){
                for(int i = 0; i < nowList.length;i++){
                    System.out.print(nowList[i] + " ");
                }
                System.out.println();
                System.out.println("相手が入力しています");
            }*/
            for(int i=0; i<newMyList.length; i++){
                System.out.print(newMyList[i] + " ");
            }

            //ウィンドウに描画
            myTargetpaint = new MyTargetpaint(myList,myWordList,720,280);
            jf.add(myTargetpaint);
            jf.setVisible(true);
            sendMessage = data;

            //Serverに送信
            oos.writeObject(sendMessage);
            oos.flush();

            System.out.println();
            System.out.println("-相手のターン-");
            MiddlePaint middle = new MiddlePaint();  //u
            jf.add(middle);
            jf.setVisible(true);



            while (true) {

                //Serverの入力を取得
                if(ois == null){
                    ois = new ObjectInputStream(socket.getInputStream());
                }
                result = (String) ois.readObject();// 返事を文字列型でキャストする。
                String[] newYourList = num1.hitJudge(correctWord, result);
                //一覧に追加
                yourList.add(newYourList);

                //相手の結果を表示
                System.out.println("相手の正解率");
               for(int i=0; i<newYourList.length; i++){
                    System.out.print(newYourList[i] + " ");
                }
              /* for(String[] nowList:yourList){
                    for(int i = 0; i < nowList.length;i++){
                        System.out.print(nowList[i] + " ");
                    }
                    System.out.println();

                }*/
                System.out.println();
                System.out.println("~あなたのターン~");

                //ウィンドウに描画
                yourTargetpaint = new YourTargetpaint(yourList,720,1000);
                jf.add(yourTargetpaint);
                jf.setVisible(true);

                middle.removeAll();
                jf.remove(middle);
                jf.revalidate(); // コンポーネントの再配置
                jf.repaint(); // フレームの再描画
                jf.setVisible(true);

                //どちらかが完全正答した場合、結果を描画
                if(num1.isEnd() && num2.isEnd()){
                    System.out.println("引き分け！");
                    Finishpaint finishpaint = new Finishpaint(correctWord,"引き分け",720,480);
                    jf.add(finishpaint);
                    jf.setVisible(true);
                    break;
                } else if(num1.isEnd() && myList.size()==yourList.size()){
                    System.out.println("相手の勝利！");
                    Finishpaint finishpaint = new Finishpaint(correctWord,"相手の勝利！",720,480);
                    jf.add(finishpaint);
                    jf.setVisible(true);
                    break;
                }else if(num2.isEnd() && myList.size()==yourList.size()){
                    System.out.println("あなたの勝利！");
                    Finishpaint finishpaint = new Finishpaint(correctWord,"あなたの勝利！",720,480);
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
                newMyList = num2.hitJudge(correctWord,data);
                myList.add(newMyList);
                myWordList.add(data);

                //文字のhitblowを判定し、一覧に追加
               /* for(String[] nowList:myList){
                    for(int i = 0; i < nowList.length;i++){
                        System.out.print(nowList[i] + " ");
                    }
                    System.out.println();
                }*/
                for(int i=0; i<newMyList.length; i++){
                    System.out.print(newMyList[i] + " ");
                }

                System.out.println();
                System.out.println("-相手のターン-");
                ;




                jf.add(middle);
                middle.revalidate(); // コンポーネントの再配置
                middle.repaint(); // パネルの再描画*/
                jf.setVisible(true);
                //ウィンドウに描画
                myTargetpaint = new MyTargetpaint(myList,myWordList,720,280);
                jf.add(myTargetpaint);
                jf.setVisible(true);

                //結果をServerに送信
                sendMessage = data;
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
                    System.out.println("相手の勝利！");
                    Finishpaint finishpaint = new Finishpaint(correctWord,"相手の勝利！",720,480);
                    jf.remove(middle);
                    jf.revalidate(); // コンポーネントの再配置
                    jf.repaint(); //
                    jf.add(finishpaint);
                    jf.setVisible(true);
                    break;
                }else if(num2.isEnd() && myList.size()==yourList.size()){
                    System.out.println("あなたの勝利！");
                    Finishpaint finishpaint = new Finishpaint(correctWord,"あなたの勝利！",720,480);
                    jf.remove(middle);
                    jf.revalidate(); // コンポーネントの再配置
                    jf.repaint(); //
                    jf.add(finishpaint);
                    jf.setVisible(true);
                    break;
                }
            }

        }// エラーが発生したらエラーメッセージを表示してプログラムを終了する
        catch (java.net.SocketException soe) {
            soe.printStackTrace();
        } catch (IOException e) {
            System.err.println("エラーが発生したのでプログラムを終了します");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        if (oos != null) {

            try {
                oos.close();
            } catch (IOException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
        if (ois != null) {

            try {
                ois.close();
            } catch (IOException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }

        if (socket != null) {

            try {
                socket.close();
            } catch (IOException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
    }
}

