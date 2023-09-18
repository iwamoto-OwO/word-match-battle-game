package kadai14;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Paints {
}

class Wordpaint extends JPanel {
    String word;
    int width;
    int height;

    public Wordpaint(String word, int w, int h){
        this.word = word;
        width = w;
        height = h;
    }
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        for(int i = 0; i < word.length(); i++){
            g.drawLine(width/2+i*60-(60*word.length()-20)/2, 50, width/2+i*60+40-(60*word.length()-20)/2 , 50);
        }
        g.setFont(new Font("MS ゴシック", Font.PLAIN, 20));
        g.setColor(Color.black);
        g.drawString("自分", width/2-175, 90);
        g.drawString("相手", width/2+135, 90);
    }
}

class MyTargetpaint extends JPanel {
    ArrayList<String[]> myList;
    int width;
    int height;
    String[] target;
    ArrayList<String>  wordList;

    public MyTargetpaint(ArrayList<String[]> list,ArrayList<String> wordList, int w, int h){
        width = w;
        height = h;
        myList = list;
        this.wordList = wordList;
    }
    public void paintComponent(Graphics g) {

        g.setFont(new Font("MS ゴシック", Font.PLAIN, 25));
        for(int i = 0; i < myList.size(); i++){
            target = wordList.get(i).split("");
            for(int j = 0; j < myList.get(i).length; j++){
                if(myList.get(i)[j].equals("noblow")){
                    g.setColor(Color.gray);
                    g.fillRect(j*40+width/2-40* target.length-50, i*40+100, 30, 30);
                    g.setColor(Color.white);
                    g.drawString(target[j], j*40+width/2-40* target.length-50+10, i*40+122);
                }else if(myList.get(i)[j].equals("blow")){
                    g.setColor(Color.yellow);
                    g.fillRect(j*40+width/2-40*target.length-50, i*40+100, 30, 30);
                    g.setColor(Color.white);
                    g.drawString(target[j], j*40+width/2-40* target.length-50+10, i*40+122);
                }else if(myList.get(i)[j].equals("hit")){
                    g.setColor(Color.green);
                    g.fillRect(j*40+width/2-40*target.length-50, i*40+100, 30, 30);
                    g.setColor(Color.white);
                    g.drawString(target[j], j*40+width/2-40* target.length-50+10, i*40+122);
                }
            }
        }
    }
}

class YourTargetpaint extends JPanel {
    ArrayList<String[]> yourList;
    int width;
    int height;
    String[] target;

    public YourTargetpaint(ArrayList<String[]> list, int w, int h){
        width = w;
        height = h;
        yourList = list;
    }
    public void paintComponent(Graphics g) {

        g.setFont(new Font("Arial", Font.PLAIN, 25));
        for(int i = 0; i < yourList.size(); i++){
            for(int j = 0; j < yourList.get(i).length; j++){
                if(yourList.get(i)[j].equals("noblow")){
                    g.setColor(Color.gray);
                    g.fillRect(j*40+width/2+60, i*40+100, 30, 30);
                }else if(yourList.get(i)[j].equals("blow")){
                    g.setColor(Color.yellow);
                    g.fillRect(j*40+width/2+60, i*40+100, 30, 30);
                }else if(yourList.get(i)[j].equals("hit")){
                    g.setColor(Color.green);
                    g.fillRect(j*40+width/2+60, i*40+100, 30, 30);
                }

            }
        }
    }
}


class Finishpaint extends JPanel {
    int width;
    int height;
    String mes;
    String word;

    public Finishpaint(String word, String mes, int w, int h){
        width = w;
        height = h;
        this.mes = mes;
        this.word = word;
    }
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("MS ゴシック", Font.PLAIN, 30));
        String[] wordSplit = word.split("");
        for(int i=0; i < wordSplit.length; i++){
            g.drawString(wordSplit[i],width/2+i*60-(60*word.length()-40)/2,40);
        }
        g.setFont(new Font("MS ゴシック", Font.PLAIN, 40));
        g.setColor(new Color(0, 0, 0, 50));
        g.fillRect(0, 0, width, height);
        g.setColor(Color.white);
        g.drawString(mes,width/2-mes.length()*20,height/2-40);
    }
}
