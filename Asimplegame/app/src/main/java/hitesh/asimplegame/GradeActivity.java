package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import static hitesh.asimplegame.QuestionActivity.setLevel;
import static hitesh.asimplegame.QuizDBOpenHelper.setDatabaseRandoming;
import static hitesh.asimplegame.StartActivity.setGame;

public class GradeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_grade);
        super.onCreate(savedInstanceState);

        setGame("general");
        setDatabaseRandoming();

        Button button1 = findViewById(R.id.easy);
        Button button2 = findViewById(R.id.normal);
        Button button3 = findViewById(R.id.hard);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //easy 난이도
                setLevel("easy");
                Intent intent = new Intent(GradeActivity.this, QuestionActivity.class);
//                inputStream=getResources().openRawResource(R.raw.grade);
//                try {
//                    properties.load(inputStream);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    if(inputStream!=null){
//                        try {
//                            inputStream.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                Bundle bundle =new Bundle();
//                bundle.putInt("x", Integer.parseInt(properties.getProperty("x")));
//                bundle.putInt("hint", Integer.parseInt(properties.getProperty("hint")));
//                intent.putExtras(bundle);
                stopService(new Intent(GradeActivity.this,MyService.class));
                startActivity(intent);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //normal 난이도
                setLevel("normal");
                Intent intent = new Intent(GradeActivity.this,QuestionActivity.class);
                stopService(new Intent(GradeActivity.this,MyService.class));

                startActivity(intent);
                finish();

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hard 난이도
                setLevel("hard");
                Intent intent = new Intent(GradeActivity.this,QuestionActivity.class);
                stopService(new Intent(GradeActivity.this,MyService.class));

                startActivity(intent);
                finish();
            }
        });
    }
}
