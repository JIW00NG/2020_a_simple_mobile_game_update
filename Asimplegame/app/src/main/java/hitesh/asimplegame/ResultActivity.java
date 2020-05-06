package hitesh.asimplegame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import static hitesh.asimplegame.QuizDBOpenHelper.setDatabaseRandoming;

public class ResultActivity extends Activity {

	private static int score;
	private static int firstScore;

	int easyScore,mediumScore,hardScore;

	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		final Intent intent = new Intent(this,MyService3.class);
		startService(intent);

		TextView textResult = (TextView) findViewById(R.id.textResult);
		Bundle b = getIntent().getExtras();
		score = b.getInt("score");
        textResult.setText("Your score is " + " " + score + ". Thanks for playing my game.");

        User user = new User();
        user.getBestScore("easy");

	}

	public void playagain(View o) {

		//다시시작시 데이터베이스 갱신
		setDatabaseRandoming();
		Intent bgmIntent = new Intent(this,MyService3.class);
		stopService(bgmIntent);
		Intent intent = new Intent(this, QuestionActivity.class);
		startActivity(intent);
		finish();
	}

	public void exitToTitle(View view){
		Intent bgmIntent = new Intent(this,MyService3.class);
		stopService(bgmIntent);
		Intent intent = new Intent(this,StartActivity.class);
		startActivity(intent);
		finish();
	}

	public static int getfirstScore(){
		if(score>firstScore){
			firstScore=score;
			return firstScore;
		}else{
			return firstScore;
		}
	}
}