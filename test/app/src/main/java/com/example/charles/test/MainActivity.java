package com.example.charles.test;

import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.*;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Queue;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    MediaPlayer bass_player;
    MediaPlayer high_player;
    MediaPlayer mid_player;

    //mock data functions
    double getHeartRate() { return 160; }
    double getStepPerMin() { return 160; }

    //VARIABLES
    private static double step_objective;
    { step_objective = 160; }
    private static double HR_objective;
    { HR_objective = 160; }
    private static double integration_time;
    { integration_time = 5; }

    private static float bass_volume = (float)(1 + Math.log(HR_objective/220));
    private static float high_volume = (float)(Math.log(220/step_objective));


    private double HR_mean = HR_objective;
    private double step_mean = step_objective;

    Queue<Double> HRs = new LinkedList<>();
    Queue<Double> steps = new LinkedList<>();

    private void initialize_queues() {
        for (int i = 0; i < (int)integration_time ; i++ ) {
            steps.add(step_objective);
            HRs.add(HR_objective);
        }
    }

    Handler handler = new Handler();
    private Runnable PeriodicUpdate = new Runnable () {
        @Override
        public void run() {
            // scheduled another events to be in 10 seconds later
            handler.postDelayed(PeriodicUpdate, 1000);
            // below is whatever you want to do

            double HR_now = getHeartRate();
            HRs.add(HR_now);

            double step_now = getStepPerMin();
            steps.add(step_now);
            double HR_old = HRs.remove();
            double step_old = steps.remove();

            //Volume Computation
            HR_mean += (HR_now - HR_old)/integration_time;
            bass_volume = (float)(1 + Math.log(HR_mean));

            step_mean += (step_now - step_old)/integration_time;
            high_volume = (float)(Math.log(220/step_mean));


            //Music Pace
            PlaybackParams plbParam = new PlaybackParams();
            plbParam.setSpeed((float)step_objective/160);

            //Music Transformation :D
            if (bass_player != null) {
                bass_player.setPlaybackParams(plbParam);
                bass_player.setVolume(bass_volume, bass_volume);

            }
            if (high_player != null) {
                high_player.setPlaybackParams(plbParam);
                high_player.setVolume(high_volume, high_volume);
            }
            if (mid_player != null){
                mid_player.setPlaybackParams(plbParam);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize_queues();
    }


    public void play1(View v) {
        //play the first track after having checked if it needed initialization
        if (bass_player == null) {
            bass_player = MediaPlayer.create(this, R.raw.bass_freak);
            bass_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
            bass_player.setVolume(bass_volume, bass_volume);

        }
    }

    public void play2(View v) {
        if (high_player == null) {
            high_player = MediaPlayer.create(this, R.raw.high_freak);
            high_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
            high_player.setVolume(high_volume, high_volume);

        }
    }

    public void play3(View v) {
        if (mid_player == null) {
            mid_player = MediaPlayer.create(this, R.raw.mid_freak);
            mid_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
            float mid_volume = (high_volume+bass_volume)/2;
            mid_player.setVolume(mid_volume, mid_volume);

        }
    }

    public void play_all(View v) {
        play1(v);
        play2(v);
        play3(v);

        bass_player.start();
        high_player.start();
        mid_player.start();
    }

    public void start_music(View v){
        //start the music in the three tracks
        play_all(v);

        PeriodicUpdate.run();
    }

    public void pause(View v) {
        //pause the music
        if (bass_player != null) {
            bass_player.pause();
        }
        if (high_player != null) {
            high_player.pause();
        }
        if (mid_player != null){
            mid_player.pause();
        }
    }

    public void stop(View v) {
        stopPlayer();
    }

    private void stopPlayer() {
        if (bass_player != null) {
            bass_player.release();
            bass_player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
        if (high_player != null) {
            high_player.release();
            high_player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
        if (mid_player != null) {
            mid_player.release();
            mid_player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }


}
