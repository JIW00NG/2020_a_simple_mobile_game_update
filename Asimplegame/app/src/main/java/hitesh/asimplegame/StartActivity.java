package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;

import static hitesh.asimplegame.MyService.setStartBgm;

public class StartActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setStartBgm();
        final Intent bgmIntent = new Intent(StartActivity.this, MyService.class);
        startService(bgmIntent);
        Button but1 = (Button)findViewById(R.id.but1);
        Button but2= (Button)findViewById(R.id.but2);
        Button but3 = (Button)findViewById(R.id.but3);
        Button but4 =(Button)findViewById(R.id.but4);
        Button but5 = (Button)findViewById(R.id.but5);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, GradeActivity.class);
                startActivity(i);
                stopService(bgmIntent);
                finish();
            }
        });
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(bgmIntent);
                finish();
            }
        });
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, RankingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, UserRecord.class);
                startActivity(intent);
                finish();
            }
        });

        but5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ButtonActivity.class);
                startActivity(intent);
                stopService(bgmIntent);
                finish();
            }
        });
    }
}

