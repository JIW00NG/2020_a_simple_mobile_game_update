package hitesh.asimplegame;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User {

    private String name,email;
    private String easyBest;

    FirebaseUser user = FirebaseAuth.getInstance(). getCurrentUser();

    public User(){
        if (user != null) {
            // Name, email address, and profile photo Url
            email = user.getEmail();
        }
        name = email.substring(0,email.indexOf("@"));
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("user");


    public void userRegist(String email){

        databaseReference.child(name).child("easy_best").setValue(0);
        databaseReference.child(name).child("normal_best").setValue(0);
        databaseReference.child(name).child("hard_best").setValue(0);
        databaseReference.child(name).child("easy_last").setValue(0);
        databaseReference.child(name).child("normal_last").setValue(0);
        databaseReference.child(name).child("hard_last").setValue(0);
        databaseReference.child(name).child("email").setValue(email);
    }


    public void getBestScore(final String level){
        databaseReference.child(name).child(level+"_best").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue(Object.class);
                String scoreStr = value.toString();
                setScore(scoreStr);
                databaseReference.child(name).child(level+"_last").setValue(ResultActivity.getfirstScore());
                if(Integer.parseInt(scoreStr)<ResultActivity.getfirstScore()){
                    databaseReference.child(name).child(level+"_best").setValue(ResultActivity.getfirstScore());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setScore(String score){
        easyBest=score;
    }
}
