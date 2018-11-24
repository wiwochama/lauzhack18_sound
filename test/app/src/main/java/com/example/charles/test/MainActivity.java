package com.example.charles.test;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MediaPlayer player1;
    MediaPlayer player2;
    MediaPlayer player3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void play1(View v) {
        //play the first track after having checked if it needed initialization
        if (player1 == null) {
            player1 = MediaPlayer.create(this, R.raw.song);
            player1.setLooping(true);
            //player1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            //   @Override
            //    public void onCompletion(MediaPlayer mp) {
            //       stopPlayer();
            //   }
            //});
        }

        player1.start();
    }

    public void play2(View v) {
        if (player2 == null) {
            player2 = MediaPlayer.create(this, R.raw.high_freak);
            player2.setLooping(true);
            //player2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            //    @Override
            //   public void onCompletion(MediaPlayer mp) {
            //        stopPlayer();
            //    }
            //});
        }

        player2.start();
    }

    public void play3(View v) {
        if (player3 == null) {
            player3 = MediaPlayer.create(this, R.raw.mid_freak);
            player3.setLooping(true);
            //player3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            //    @Override
            //   public void onCompletion(MediaPlayer mp) {
            //        stopPlayer();
            //    }
            //});
        }

        player3.start();
    }

    public void start_music(View v){
        //start the music in the three tracks
        play1(v);
        play2(v);
        play3(v);
    }

    public void pause(View v) {
        //pause the music
        if (player1 != null) {
            player1.pause();
        }
        if (player2 != null) {
            player2.pause();
        }
        if (player3 != null){
            player3.pause();
        }
    }

    public void stop(View v) {
        stopPlayer();
    }

    private void stopPlayer() {
        if (player1 != null) {
            player1.release();
            player1 = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
        if (player2 != null) {
            player2.release();
            player2 = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
        if (player3 != null) {
            player3.release();
            player3 = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }


}