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

public class UserRecord extends Activity {

    private TextView titleEasyLastScore, titleNormalLastScore, titleHardLastScore;
    private TextView titleEasyBestScore, titleNormalBestScore, titleHardBestScore;

    private TextView titleDndLastScore,titleDndBestScore;
    private TextView dndLastScoreValue,dndBestScoreValue;

    private TextView easyLastScoreValue, normalLastScoreValue, hardLastScoreValue;
    private TextView easyBestScoreValue, normalBestScoreValue, hardBestScoreValue;

    private TextView userName;
    private Button backBtn;

    User user = new User();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_record);

        userName = (TextView) findViewById(R.id.user_name);
        userName.setText("User: "+user.getName());

        easyLastScoreValue = (TextView) findViewById(R.id.easy_last_score_value);
        easyBestScoreValue = (TextView) findViewById(R.id.easy_best_score_value);
        normalLastScoreValue = (TextView) findViewById(R.id.normal_last_score_value);
        normalBestScoreValue = (TextView) findViewById(R.id.normal_best_score_value);
        hardLastScoreValue = (TextView) findViewById(R.id.hard_last_score_value);
        hardBestScoreValue = (TextView) findViewById(R.id.hard_best_score_value);
        dndLastScoreValue = (TextView) findViewById(R.id.dnd_1th_score);
        dndBestScoreValue = (TextView) findViewById(R.id.dnd_best_score_value);

        backBtn = (Button) findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserRecord.this,StartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        databaseReference.child(user.getName()).child("easy_best").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                easyBestScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child(user.getName()).child("easy_last").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                easyLastScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child(user.getName()).child("normal_best").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                normalBestScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child(user.getName()).child("normal_last").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                normalLastScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child(user.getName()).child("hard_best").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                hardBestScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child(user.getName()).child("hard_last").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                hardLastScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child(user.getName()).child("dnd_best").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                dndBestScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference.child(user.getName()).child("dnd_last").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object userValue = dataSnapshot.getValue(Object.class);
                dndLastScoreValue.setText(String.valueOf(userValue.toString()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }
}