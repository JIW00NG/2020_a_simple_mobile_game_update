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

import static hitesh.asimplegame.GeneralResultActivity.getFirstScore;

public class RankingActivity extends Activity {

    private TextView easyBestUser, normalBestUser, hardBestUser;
    private TextView easyBestScoreValue, normalBestScoreValue, hardBestScoreValue;

    private TextView dndBestScoreValue;
    private TextView dndBestUser;

    private TextView userName;
    private Button backBtn;

    User user = new User();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("ranking");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        easyBestUser = (TextView) findViewById(R.id.easy_1th_user);
        easyBestScoreValue = (TextView) findViewById(R.id.easy_1th_score);
        normalBestUser = (TextView) findViewById(R.id.normal_1th_user);
        normalBestScoreValue = (TextView) findViewById(R.id.normal_1th_score);
        hardBestUser = (TextView) findViewById(R.id.hard_1th_user);
        hardBestScoreValue = (TextView) findViewById(R.id.hard_1th_score);
        dndBestUser = (TextView) findViewById(R.id.dnd_1th_user);
        dndBestScoreValue = (TextView) findViewById(R.id.dnd_1th_score);

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
                easyBestUser.setText(userValue.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child("easy_1th_user_score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                if(Integer.parseInt(userValue.toString())<getFirstScore("easy")){
                    databaseReference.child("easy_1th_user_score").setValue(GeneralResultActivity.getFirstScore("easy"));
                    databaseReference.child("easy_1th_user").setValue(user.getName());
                }
                easyBestScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child("normal_1th_user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                normalBestUser.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child("normal_1th_user_score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                if(Integer.parseInt(userValue.toString())<getFirstScore("normal")){
                    databaseReference.child("normal_1th_user_score").setValue(GeneralResultActivity.getFirstScore("normal"));
                    databaseReference.child("normal_1th_user").setValue(user.getName());
                }
                normalBestScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child("hard_1th_user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                hardBestUser.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child("hard_1th_user_score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                if(Integer.parseInt(userValue.toString())<getFirstScore("hard")){
                    databaseReference.child("hard_1th_user_score").setValue(GeneralResultActivity.getFirstScore("hard"));
                    databaseReference.child("hard_1th_user").setValue(user.getName());
                }
                hardBestScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child("dnd_1th_user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                dndBestUser.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child("dnd_1th_user_score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                dndBestScoreValue.setText(String.valueOf(userValue.toString()));
                if(Integer.parseInt(userValue.toString())<getFirstScore("dnd")){
                    databaseReference.child("dnd_1th_user_score").setValue(GeneralResultActivity.getFirstScore("dnd"));
                    databaseReference.child("dnd_1th_user").setValue(user.getName());
                }
                dndBestScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }
}
