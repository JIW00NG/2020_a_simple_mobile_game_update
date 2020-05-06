package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;

public class StartActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final Intent intent = new Intent(StartActivity.this, MyService1.class);
        startService(intent);
        Button but1 = (Button)findViewById(R.id.but1);
        Button but2= (Button)findViewById(R.id.but2);
        Button but3 = (Button)findViewById(R.id.but3);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, QuestionActivity.class);
                startActivity(i);
                stopService(intent);
                finish();
            }
        });
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
                finish();
            }
        });
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

