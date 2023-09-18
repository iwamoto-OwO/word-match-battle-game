package program;

import java.util.Random;

public class RandomWord {
    String answerWord="";
    public RandomWord(){
        Random random = new Random();
        int i = random.nextInt(10);
        switch (i) {
            case 0:
                answerWord = "apple";
                break;
            case 1:
                answerWord = "candy";
                break;
            case 2:
                answerWord = "fruit";
                break;
            case 3:
                answerWord = "grape";
                break;
            case 4:
                answerWord = "honey";
                break;
            case 5:
                answerWord = "lemon";
                break;
            case 6:
                answerWord = "peach";
                break;
            case 7:
                answerWord = "bread";
                break;
            case 8:
                answerWord = "jelly";
                break;
            case 9:
                answerWord = "berry";
                break;
        }
    }
    public String getAnswer(){
        return answerWord;
    }
}