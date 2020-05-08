package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static hitesh.asimplegame.MyService.setMainBgm;

public class ButtonActivity extends Activity {
    private Button reset;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        Intent intent = new Intent(ButtonActivity.this, DragAndDropActivity.class);
        setMainBgm();
        startService(new Intent(ButtonActivity.this,MyService.class));
        startActivity(intent);
        finish();
    }

    public void reset(View o) {

    }

}
