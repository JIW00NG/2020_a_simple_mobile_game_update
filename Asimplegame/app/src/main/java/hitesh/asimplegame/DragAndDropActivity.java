package hitesh.asimplegame;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import static hitesh.asimplegame.MyService.setMainBgm;
import static hitesh.asimplegame.QuizDBOpenHelper.setDatabaseRandoming;
import static hitesh.asimplegame.StartActivity.setGame;

public class DragAndDropActivity extends Activity {
    private TextView[] num = new TextView[6] ;

    private TextView oper1;
    private TextView oper2;
    private TextView answer;
    private TextView scoreView;
    private TextView lifeView;
    private static RandomQuestionHandler RQHandler = new RandomQuestionHandler();
    private RandomQuestion RQ;
    private static int score = 0;
    private static int life;
    private static final String UNUSED_TEXTVIEW_TAG = "0";
    private static final String USED_TEXTVIEW_TAG = "1";
    private Object CharSequence;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        setMainBgm();
        startService(new Intent(DragAndDropActivity.this,MyService.class));
        life = 1;

        if(RQHandler.getResetRQ()==null) {    // 새로운 문제를 받아도 된다면 랜덤 문제 적용
            RQ = RQHandler.getNewRQ();
        }
        else {
            RQ = RQHandler.getResetRQ(); // 리셋 버튼을 눌렀을경우 원래의 문제를 다시 배치
            RQHandler.setResetRQ(null);
        }
        initializeValues();

        Button next =  (Button) findViewById(R.id.nextButton);       // 버툰 설정
        Button reset = (Button) findViewById(R.id.resetButton);
        scoreView = (TextView) findViewById(R.id.ScoreValueView);    // 점수 설정
        lifeView = (TextView) findViewById(R.id.LifeViewValue);      // 목숨 설정
        scoreView.setText(String.valueOf(score));
        lifeView.setText(String.valueOf(life));


        next.setOnClickListener(new View.OnClickListener() {           //next button
            @Override
            public void onClick(View view) {

                if(isCorrect()) {      // 맞았을때
                    score++;
                }
                else life--;          // 틀렸을때

                if(life == 0) {       // life가 0으로 게임이 끝날때
                    Intent intent = new Intent(DragAndDropActivity.this, GeneralResultActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("score", score); // 스코어 보내기
                    intent.putExtras(b); //
                    startActivity(intent);
                    score = 0;
                    finish();
                    //다시시작시 데이터베이스 갱신
                }
                else {                // 다음문제로 넘어갈때
                    Intent intent = new Intent(DragAndDropActivity.this, ButtonActivity.class);
                    startActivity(intent);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {          // reset button
            public void onClick(View view) {
                RQHandler.setResetRQ(RQ);
                Intent intent = new Intent(DragAndDropActivity.this, ButtonActivity.class);
                startActivity(intent);
            }
        });

    }
    public boolean isCorrect() {
        int result = 0;
        boolean answer;
        try {
            TextView targetView1 = (TextView) findViewById(R.id.targetView1);
            TextView targetView2 = (TextView) findViewById(R.id.targetView2);
            TextView targetView3 = (TextView) findViewById(R.id.targetView3);
            TextView targetView4 = (TextView) findViewById(R.id.targetView4);
            TextView targetView5 = (TextView) findViewById(R.id.targetView5);
            TextView answerView = (TextView) findViewById(R.id.answerTextView);

            int targetNum1 = Integer.parseInt((String) targetView1.getText());
            int targetNum2 = Integer.parseInt((String) targetView3.getText());
            int targetNum3 = Integer.parseInt((String) targetView5.getText());
            int answerNum = Integer.parseInt((String) answerView.getText());
            char[] targetOper = new char[2];
            targetOper[0] = targetView2.getText().charAt(0);
            targetOper[1] = targetView4.getText().charAt(0);

            if (targetOper[0] == '+') {
                result = targetNum1 + targetNum2;
            } else if (targetOper[0] == '-') {
                result = targetNum1 - targetNum2;
            }

            if (targetOper[1] == '+') {
                result += targetNum3;
            } else if (targetOper[1] == '-') {
                result -= targetNum3;
            }

            if (result == answerNum) answer = true;
            else answer = false;
        }

        catch(Exception e) {
            answer = false;
            e.printStackTrace();
        }

        return answer;
    }
    public void initializeValues() {

        int[] number = new int[6];

        Random random = new Random();

        for(int i=0; i<6; i++) {
            number[i] = random.nextInt(6);
            for(int j=0; j<i; j++) {
                if(number[i] == number[j]) {
                    i--;
                }
            }
        }

        num[number[0]] = (TextView) findViewById(R.id.NumberView1);
        num[number[1]] = (TextView) findViewById(R.id.NumberView2);
        num[number[2]] = (TextView) findViewById(R.id.NumberView3);
        num[number[3]] = (TextView) findViewById(R.id.NumberView4);
        num[number[4]] = (TextView) findViewById(R.id.NumberView5);
        num[number[5]] = (TextView) findViewById(R.id.NumberView6);
        oper1 = (TextView) findViewById(R.id.operView1);
        oper2 = (TextView) findViewById(R.id.operView2);
        answer = (TextView) findViewById(R.id.answerTextView);

        for(int i=0; i<6; i++) {
            num[i].setText(String.valueOf(RQ.listNum[i]));
            num[i].setTag(UNUSED_TEXTVIEW_TAG);
            num[i].setOnLongClickListener(new LongClickListener());
        }

        oper1.setText(String.valueOf(RQ.oper[0]));
        oper2.setText(String.valueOf(RQ.oper[1]));
        answer.setText(String.valueOf(RQ.answerNum));

        oper1.setTag(UNUSED_TEXTVIEW_TAG); //oper1.setText(RQ.oper[0]);
        oper2.setTag(UNUSED_TEXTVIEW_TAG); //oper2.setText(RQ.oper[1]);
        //answer.setText(RQ.answerNum);

        oper1.setOnLongClickListener(new LongClickListener());
        oper2.setOnLongClickListener(new LongClickListener());

        findViewById(R.id.targetView1).setTag(UNUSED_TEXTVIEW_TAG);
        findViewById(R.id.targetView2).setTag(UNUSED_TEXTVIEW_TAG);
        findViewById(R.id.targetView3).setTag(UNUSED_TEXTVIEW_TAG);
        findViewById(R.id.targetView4).setTag(UNUSED_TEXTVIEW_TAG);
        findViewById(R.id.targetView5).setTag(UNUSED_TEXTVIEW_TAG);
        findViewById(R.id.targetView1).setOnDragListener(new DragListener());
        findViewById(R.id.targetView2).setOnDragListener(new DragListener());
        findViewById(R.id.targetView3).setOnDragListener(new DragListener());
        findViewById(R.id.targetView4).setOnDragListener(new DragListener());
        findViewById(R.id.targetView5).setOnDragListener(new DragListener());

    }
    private final class LongClickListener implements OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

            Button shadowButton = (Button) findViewById(R.id.shadowButton);
            TextView shadowText = (TextView) view;
            shadowButton.setText(shadowText.getText());

            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(findViewById(R.id.shadowButton));

            view.startDrag(data, shadowBuilder, view, 0);
            //view.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    class DragListener implements OnDragListener{
        //Drawable normalShape = getResources().getDrawable(R.drawable.normal_shape);
        //Drawable targetShape = getResources().getDrawable(R.drawable.target_shape);

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public boolean onDrag(View v, DragEvent event) {
            MotionEvent motionEvent;
            switch(event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("DragClickListener", "ACTION_DRAG_STARTED");
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENTERED");
                    //v.setBackground(targetShape);
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("DragClickListener", "ACTION_DRAG_EXITED");
                    //v.setBackground(normalShape);
                    break;

                case DragEvent.ACTION_DROP:
                    Log.d("DragClickListener", "ACTION_DROP");

                    if (v == findViewById(R.id.targetView1) ) {

                        if (findViewById(R.id.targetView1).getTag().equals(UNUSED_TEXTVIEW_TAG)) {  // 넣을 View가 비어있다면

                            TextView text = (TextView) v.findViewById(R.id.targetView1);
                            TextView draggedTextView = (TextView) event.getLocalState(); // 드레그로 가져온 View
                            text.setTag(USED_TEXTVIEW_TAG);
                            draggedTextView.setTag(USED_TEXTVIEW_TAG);

                            text.setText(draggedTextView.getText());  // draggedTextView 숫자로 targetView 변경

                            draggedTextView.setVisibility(View.INVISIBLE); // draggedTextView INVISIBLE
                        }

                        else {
                            TextView draggedTextView = (TextView) event.getLocalState();
                            draggedTextView.setVisibility(View.VISIBLE);
                        }

                        /*LinearLayout containView = (LinearLayout) v;
                        containView.addView(view);
                        view.setVisibility(View.VISIBLE);*/
                    } else if (v == findViewById(R.id.targetView2)) {
                        if (findViewById(R.id.targetView2).getTag().equals(UNUSED_TEXTVIEW_TAG)) {  // 넣을 View가 비어있다면

                            TextView text = (TextView) v.findViewById(R.id.targetView2);
                            TextView draggedTextView = (TextView) event.getLocalState(); // 드레그로 가져온 View
                            text.setTag(USED_TEXTVIEW_TAG);
                            draggedTextView.setTag(USED_TEXTVIEW_TAG);

                            text.setText(draggedTextView.getText());  // draggedTextView 숫자로 targetView 변경

                            draggedTextView.setVisibility(View.INVISIBLE); // draggedTextView INVISIBLE
                        }

                        else {
                            TextView draggedTextView = (TextView) event.getLocalState();
                            draggedTextView.setVisibility(View.VISIBLE);
                        }
                    }
                    else if (v == findViewById(R.id.targetView3)) {
                        if (findViewById(R.id.targetView3).getTag().equals(UNUSED_TEXTVIEW_TAG)) {  // 넣을 View가 비어있다면

                            TextView text = (TextView) v.findViewById(R.id.targetView3);
                            TextView draggedTextView = (TextView) event.getLocalState(); // 드레그로 가져온 View
                            text.setTag(USED_TEXTVIEW_TAG);
                            draggedTextView.setTag(USED_TEXTVIEW_TAG);

                            text.setText(draggedTextView.getText());  // draggedTextView 숫자로 targetView 변경

                            draggedTextView.setVisibility(View.INVISIBLE); // draggedTextView INVISIBLE
                        }

                        else {
                            TextView draggedTextView = (TextView) event.getLocalState();
                            draggedTextView.setVisibility(View.VISIBLE);
                        }
                    }
                    else if (v == findViewById(R.id.targetView4)) {
                        if (findViewById(R.id.targetView4).getTag().equals(UNUSED_TEXTVIEW_TAG)) {  // 넣을 View가 비어있다면

                            TextView text = (TextView) v.findViewById(R.id.targetView4);
                            TextView draggedTextView = (TextView) event.getLocalState(); // 드레그로 가져온 View
                            text.setTag(USED_TEXTVIEW_TAG);
                            draggedTextView.setTag(USED_TEXTVIEW_TAG);

                            text.setText(draggedTextView.getText());  // draggedTextView 숫자로 targetView 변경

                            draggedTextView.setVisibility(View.INVISIBLE); // draggedTextView INVISIBLE
                        }

                        else {
                            TextView draggedTextView = (TextView) event.getLocalState();
                            draggedTextView.setVisibility(View.VISIBLE);
                        }
                    }
                    else if (v == findViewById(R.id.targetView5)) {
                        if (findViewById(R.id.targetView5).getTag().equals(UNUSED_TEXTVIEW_TAG)) {  // 넣을 View가 비어있다면

                            TextView text = (TextView) v.findViewById(R.id.targetView5);
                            TextView draggedTextView = (TextView) event.getLocalState(); // 드레그로 가져온 View
                            text.setTag(USED_TEXTVIEW_TAG);
                            draggedTextView.setTag(USED_TEXTVIEW_TAG);

                            text.setText(draggedTextView.getText());  // draggedTextView 숫자로 targetView 변경

                            draggedTextView.setVisibility(View.INVISIBLE); // draggedTextView INVISIBLE
                        } else {
                            TextView draggedTextView = (TextView) event.getLocalState();
                            draggedTextView.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        TextView draggedTextView = (TextView) event.getLocalState();
                        draggedTextView.setVisibility(View.VISIBLE);

                        break;
                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENDED");
                    //v.setBackground(normalShape);

                default:
                    break;

            }
            return true;
        }
    }

}
