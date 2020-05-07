package hitesh.asimplegame;

/**
 * Created by H on 7/12/2015.
 */


import java.util.List;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static hitesh.asimplegame.MyService.setMainBgm;

public class QuestionActivity extends Activity {
    private static final String TAG = QuestionActivity.class.getSimpleName();

    private List<Question> questionList;
    private int score = 0;
    private long timeScore=0;
    private int questionID = 0;
    private int gameTime=60000;
    private int oneSec=1000;
    private int hintLeft=0;
    private int numOfQuestion = 21; // for testing
    private boolean hintUsed = true;

    private Question currentQ;
    private TextView txtQuestion, times, scored;
    private Button button1, button2, button3, hintBtn;

    // A timer of 60 seconds to play for, with an interval of 1 second (1000 milliseconds)
    CounterClass timer = new CounterClass(gameTime, oneSec);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuizDBOpenHelper db = new QuizDBOpenHelper(this);  // my question bank class
        questionList = db.getAllQuestions();  // this will fetch all quetonall questions
        currentQ = questionList.get(questionID); // the current question
        setMainBgm();
        startService(new Intent(QuestionActivity.this,MyService.class));

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        // the textview in which the question will be displayed

        // the three buttons,
        // the idea is to set the text of three buttons with the options from question bank
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        hintBtn = (Button) findViewById(R.id.hint_btn);

        // the textview in which score will be displayed
        scored = (TextView) findViewById(R.id.score);

        // the timer
        times = (TextView) findViewById(R.id.timers);


        // method which will set the things up for our game
        setQuestionView();
        times.setText("00:02:00");

        timer.start();

        // button click listeners
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // passing the button text to other method
                // to check whether the anser is correct or not
                // same for all three buttons
                hintUsed=true;
                getAnswer(button1.getText().toString());
                button1.setBackgroundColor(Color.WHITE);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintUsed=true;
                getAnswer(button2.getText().toString());
                button2.setBackgroundColor(Color.WHITE);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintUsed=true;
                getAnswer(button3.getText().toString());
                button3.setBackgroundColor(Color.WHITE);
            }
        });
        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(hintUsed==true) {
                    if (hintLeft < 3) {
                        hintLeft++;
                        hintUsed=false;
                        if (hintResult(button1.getText().toString())) {
                            button1.setBackgroundColor(Color.GREEN);
                        } else if (hintResult(button2.getText().toString())) {
                            button2.setBackgroundColor(Color.GREEN);
                        } else if (hintResult(button3.getText().toString())) {
                            button3.setBackgroundColor(Color.GREEN);
                        }
                    } else {
                        Toast.makeText(QuestionActivity.this, "you don't have any hint", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(QuestionActivity.this,"green is answer",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean hintResult(String a){
        return a.equals(currentQ.getANSWER());
    }

    public void getAnswer(String AnswerString) {
        if (currentQ.getANSWER().equals(AnswerString)) {

            // if conditions matches increase the int (score) by 1
            // and set the text of the score view
            score++;
            scored.setText("Score : " + score);
        } else {
            // if unlucky start activity and finish the game

            stopService(new Intent(QuestionActivity.this,MyService.class));
            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);

            // passing the int value
            Bundle b = new Bundle();
            b.putInt("score", score); // Your score
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);
            finish();
            timer.cancel();

        }

        if (questionID < numOfQuestion) {
            // if questions are not over then do this
            currentQ = questionList.get(questionID);
            setQuestionView();
        } else {
            // if over do this
            stopService(new Intent(QuestionActivity.this,MyService.class));
            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score+(int)timeScore); // Your score
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);
            finish();
            timer.cancel();
        }
    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer
    {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            times.setText("Time is up");
            // if unlucky start activity and finish the game
            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
            stopService(new Intent(QuestionActivity.this,MyService.class));
            // passing the int value
            Bundle b = new Bundle();
            b.putInt("score", score); // Your score
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;

            //남은 시간을 점수로 계산
            //timeScore=hour*3600+min*60+sec
            timeScore=(TimeUnit.MILLISECONDS.toHours(millis))*3600+(TimeUnit.MILLISECONDS.toMinutes(millis)
                    - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                    .toHours(millis)))*60+(TimeUnit.MILLISECONDS.toSeconds(millis)
                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                    .toMinutes(millis)));

            String hms = String.format( "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millis)));

            Log.d(TAG, "current time: " + hms);
            times.setText(hms);
            if ((int)TimeUnit.MILLISECONDS.toSeconds(millis) <= 10) {
                String hms1 = String.format("%d",TimeUnit.MILLISECONDS.toSeconds(millis));
                times.setTextColor(Color.BLACK);
                times.setTextSize(40);
                times.setText(hms1);

            }
        }


    }

    private void setQuestionView() {
        // the method which will put all things together
        txtQuestion.setText(currentQ.getQUESTION());
        button1.setText(currentQ.getOPTA());
        button2.setText(currentQ.getOPTB());
        button3.setText(currentQ.getOPTC());

        questionID++;
    }
}
