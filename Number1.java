package program;


public class Number1 {

    boolean isEnd = false;
    public String[] hitJudge(String correctAnswer, String cliantWord) {
        //System.out.println("入力文字は" + cliantWord);
        String[] returnHit; // ����
        returnHit = new String[cliantWord.length()];
        int hitCount = 0;

        char[] correctSplit = correctAnswer.toCharArray(); //��������ꂸ�ɕ���
        char[] splitCliant = cliantWord.toCharArray();// ���͕����𕪗�

        if(cliantWord.equals(correctAnswer)){
            isEnd = true;
        }
        for (int i = 0; i < correctSplit.length; i++) {
            if (correctSplit[i] == splitCliant[i]) {
                hitCount++;
                returnHit[i]= "hit";  //�q�b�g����
            } else {
                boolean blow = false;
                for(int j = 0; j < correctSplit.length; j++){
                    if(correctSplit[j] == splitCliant[i]){
                        returnHit[i]= "blow"; //�u���[����
                        blow = true;
                        break;
                    }
                }
                if(!blow){
                    returnHit[i]= "noblow";  // �m�[�u���[����
                }
            }



        }
        /*
       for(int i=0; i<returnHit.length; i++) {
            System.out.print(returnHit[i] + ",");
        }
       var marge = new StringJoiner(",");
       for(String word :returnHit){
           marge.add(word);
       }
       */

       return returnHit;



    }

    public boolean isEnd(){
        return isEnd;
    }


}
