package hitesh.asimplegame;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    static boolean isPlay;
    MediaPlayer player;
    private static boolean startActBgm = true, mainActBgm = false, resultActBgn = false;

    @Override
    public void onDestroy() {
        player.stop();
        isPlay=player.isPlaying();
        player.release();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            if(!isPlay){
                player.start();
                isPlay=player.isPlaying();
            }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(startActBgm){
            player = MediaPlayer.create(this, R.raw.sound_bgm_lobby);
            System.out.println("브금");
        }else if(mainActBgm){
            player = MediaPlayer.create(this, R.raw.sound_bgm_stage01);
        }else if(resultActBgn){
            player = MediaPlayer.create(this, R.raw.sound_bgm_gameover);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void setStartBgm(){
        startActBgm =true;
        mainActBgm = false;
        resultActBgn = false;
    }

    public static void setMainBgm(){
        mainActBgm =true;
        startActBgm =false;
        resultActBgn = false;
    }

    public static void setResultBgm(){
        resultActBgn =true;
        mainActBgm =false;
        startActBgm =false;
    }


}
