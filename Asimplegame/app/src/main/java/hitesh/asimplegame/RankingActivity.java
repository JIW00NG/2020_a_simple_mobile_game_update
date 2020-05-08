package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static hitesh.asimplegame.MyService.setStartBgm;
import static hitesh.asimplegame.ResultActivity.getFirstScore;

public class RankingActivity extends Activity {

    private TextView easy1thUser, normal1thUser, hard1thUser;
    private TextView titleEasyBestScore, titleNormalBestScore, titleHardBestScore;

    private TextView easyLastScoreValue, normalLastScoreValue, hardLastScoreValue;
    private TextView easyBestScoreValue, normalBestScoreValue, hardBestScoreValue;

    private TextView userName;
    private Button backBtn;

    User user = new User();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("ranking");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        easy1thUser = (TextView) findViewById(R.id.easy_last_score);
        normal1thUser = (TextView) findViewById(R.id.normal_last_score);
        hard1thUser = (TextView) findViewById(R.id.hard_last_score);
        easy1thUser.setText("score: ");
        normal1thUser.setText("score: ");
        hard1thUser.setText("score: ");

        titleEasyBestScore = (TextView) findViewById(R.id.easy_best_score);
        titleNormalBestScore = (TextView) findViewById(R.id.normal_best_score);
        titleHardBestScore = (TextView) findViewById(R.id.hard_best_score);
        titleEasyBestScore.setText("user name: ");
        titleNormalBestScore.setText("user name: ");
        titleHardBestScore.setText("user name: ");

        easyLastScoreValue = (TextView) findViewById(R.id.easy_last_score_value);
        easyBestScoreValue = (TextView) findViewById(R.id.easy_best_score_value);
        normalLastScoreValue = (TextView) findViewById(R.id.normal_last_score_value);
        normalBestScoreValue = (TextView) findViewById(R.id.normal_best_score_value);
        hardLastScoreValue = (TextView) findViewById(R.id.hard_last_score_value);
        hardBestScoreValue = (TextView) findViewById(R.id.hard_best_score_value);

        backBtn = (Button) findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        databaseReference.child("easy_1th_user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                easyBestScoreValue.setText(userValue.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child("easy_1th_user_score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                if(Integer.parseInt(userValue.toString())<getFirstScore("easy")){
                    databaseReference.child("easy_1th_user_score").setValue(ResultActivity.getFirstScore("easy"));
                    databaseReference.child("easy_1th_user").setValue(user.getName());
                }
                easyLastScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child("normal_1th_user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                normalBestScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child("normal_1th_user_score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                normalLastScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child("hard_1th_user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                hardBestScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child("hard_1th_user_score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                hardLastScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }
}
