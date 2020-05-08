package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import static hitesh.asimplegame.MyService.setResultBgm;
import static hitesh.asimplegame.QuestionActivity.getLevel;
import static hitesh.asimplegame.QuizDBOpenHelper.setDatabaseRandoming;

public class ResultActivity extends Activity {

	private static int firstEasyScore, firstNormalScore, firstHardScore;
	private static int easyScore,normalScore,hardScore;
	private static int dndScore,firstDndScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		setResultBgm();
		final Intent intent = new Intent(this,MyService.class);
		startService(intent);

		TextView textResult = (TextView) findViewById(R.id.textResult);
		Bundle b = getIntent().getExtras();
        User user = new User();
		if(getLevel()=="easy"){
			easyScore = b.getInt("score");
        	textResult.setText("Your Easy level score is " + " " + easyScore + ". Thanks for playing my game.");
        	user.setBestScore("easy");
		}else if(getLevel()=="normal"){
			normalScore=b.getInt("score");
        	textResult.setText("Your normal level score is " + " " + normalScore + ". Thanks for playing my game.");
        	user.setBestScore("normal");
		}else if(getLevel()=="hard"){
			hardScore=b.getInt("score");
        	textResult.setText("Your hard level score is " + " " + hardScore + ". Thanks for playing my game.");
        	user.setBestScore("hard");
		}


	}

	public void playagain(View o) {

		//다시시작시 데이터베이스 갱신
		setDatabaseRandoming();
		Intent bgmIntent = new Intent(this,MyService.class);
		stopService(bgmIntent);
		Intent intent = new Intent(this, QuestionActivity.class);
		startActivity(intent);
		finish();
	}

	public void exitToTitle(View view){
		Intent bgmIntent = new Intent(this,MyService.class);
		stopService(bgmIntent);
		Intent intent = new Intent(this,StartActivity.class);
		startActivity(intent);
		finish();
	}

	public static int getFirstScore(String level){
		if(level=="easy"){
			if(easyScore>firstEasyScore){
				firstEasyScore=easyScore;
				return firstEasyScore;
			}else{
				return firstEasyScore;
			}
		}else if(level=="normal"){
			if(normalScore>firstNormalScore){
				firstNormalScore=normalScore;
				return firstNormalScore;
			}else{
				return firstNormalScore;
			}
		}else if(level=="hard"){
			if(hardScore>firstHardScore){
				firstHardScore=hardScore;
				return firstHardScore;
			}else{
				return firstHardScore;
			}
		}else{
			if(dndScore>firstDndScore){
				firstDndScore=dndScore;
				return firstDndScore;
			}else{
				return firstDndScore;
			}
		}
	}
}