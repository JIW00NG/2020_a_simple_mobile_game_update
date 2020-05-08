package hitesh.asimplegame;

import java.util.Random;

public class RandomQuestion extends Question{

    //DND game attribute
    public int answerNum;
    public int[] correctNum = new int[3];
    public int[] disCorrectNum = new int[3];
    public char[] oper = new char[2];    // 3개의 부등호
    public int[] listNum = new int [6]; // 모든 숫자 카드

    //random defult game attribute
    Random random = new Random();

    int questionNum1;
    int questionNum2;
    int result;

    int gapNum1;
    int gapNum2;
    int gapNum3;

    int option1;
    int option2;
    int option3;

    public RandomQuestion(String game){

        if(game=="DND"){
            makeQuestion();
        }else{
            questionNum1=random.nextInt(10);
            questionNum2=random.nextInt(10);
            result=questionNum1+questionNum2;

            gapNum1=random.nextInt(4);

            do{
                gapNum2 =random.nextInt(6);
            }while(gapNum1 == gapNum2);
            do{
                gapNum3 =random.nextInt(10);
            }while(gapNum1 == gapNum3 || gapNum2 == gapNum3);
            if(gapNum1 !=0&& gapNum2 !=0){
                gapNum3 =0;
            }

            option1 = result+ gapNum1;
            option2 = result+ gapNum2;
            option3 = result+ gapNum3;
        }


    }

    public void makeQuestion() {
        Random random = new Random();
        answerNum = 0;

        for(int i=0; i<3; i++) {
            correctNum[i]=random.nextInt(9)+1;
            listNum[i] = correctNum[i];
        }

        for(int i=0; i<3; i++) {
            disCorrectNum[i] = random.nextInt(9)+1;
            listNum[i+3] = disCorrectNum[i];
        }
        for(int i=0; i<2; i++) {
            int r = random.nextInt(2);
            if( r == 0) {
                oper[i] = '+';
            }
            else if (r == 1){
                oper[i] = '-';
            }
        }

        if(oper[0]=='+')
            answerNum = correctNum[0] + correctNum[1];
        else if
        (oper[0]=='-') answerNum = correctNum[0] - correctNum[1];

        if(oper[1]=='+')
            answerNum += correctNum[2];
        else if
        (oper[1]=='-') answerNum -= correctNum[2];

    }

    public int getAnswerNum() {
        return this.answerNum;
    }

    @Override
    public String getQUESTION() {
        return String.valueOf(questionNum1)+"+"+String.valueOf(questionNum2)+" = ?";
    }

    @Override
    public String getANSWER() {
        return String.valueOf(result);
    }

    @Override
    public String getOPTA() {
        return String.valueOf(option1);
    }

    @Override
    public String getOPTB() {
        return String.valueOf(option2);
    }

    @Override
    public String getOPTC() {
        return String.valueOf(option3);
    }
}
