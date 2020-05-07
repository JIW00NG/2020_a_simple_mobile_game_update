package hitesh.asimplegame;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static hitesh.asimplegame.QuestionActivity.getLevel;

public class User {

    private String name,email;
    private int easyBest, easyLast, normalBest, normalLast, hardBest, hardLast;

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


    public void userRegist(String email,String username){

        databaseReference.child(name).child("easy_best").setValue(0);
        databaseReference.child(name).child("normal_best").setValue(0);
        databaseReference.child(name).child("hard_best").setValue(0);
        databaseReference.child(name).child("easy_last").setValue(0);
        databaseReference.child(name).child("normal_last").setValue(0);
        databaseReference.child(name).child("hard_last").setValue(0);
        databaseReference.child(name).child("email").setValue(email);
        databaseReference.child(name).child("user_name").setValue(username);
    }


    public void setBestScore(final String level){
        databaseReference.child(name).child(level+"_best").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue(Object.class);
                String scoreStr = value.toString();
                setScore(scoreStr,level);
                databaseReference.child(name).child(level+"_last").setValue(ResultActivity.getfirstScore(getLevel()));
                if(Integer.parseInt(scoreStr)<ResultActivity.getfirstScore(getLevel())){
                    databaseReference.child(name).child(level+"_best").setValue(ResultActivity.getfirstScore(getLevel()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setScore(String score,String level){
//        if(level=="easy"){
//
//        }else if(level=="normal"){
//
//        }else if(level=="hard"){
//
//        }
        easyBest=Integer.parseInt(score);
    }

    public String getName(){
        return name;
    }
//
//    public void setEasyBest(int easyBest) {
//        this.easyBest = easyBest;
//    }
//
//    public int getEasyBest() {
//        return easyBest;
//    }
//
//    public void setEasyLast(int easyLast) {
//        this.easyLast = easyLast;
//    }
//
//    public int getEasyLast() {
//        return easyLast;
//    }
//
//    public void setNormalBest(int normalBest) {
//        this.normalBest = normalBest;
//    }
//
//    public int getNormalBest() {
//        return normalBest;
//    }
//
//    public void setNormalLast(int normalLast) {
//        this.normalLast = normalLast;
//    }
//
//    public int getNormalLast() {
//        return normalLast;
//    }
//
//    public void setHardBest(int hardBest) {
//        this.hardBest = hardBest;
//    }
//
//    public int getHardBest() {
//        return hardBest;
//    }
//
//    public void setHardLast(int hardLast) {
//        this.hardLast = hardLast;
//    }
//
//    public int getHardLast() {
//        return hardLast;
//    }
}
