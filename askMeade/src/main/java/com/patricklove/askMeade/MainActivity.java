package com.patricklove.askMeade;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.Timer;

public class MainActivity extends ActionBarActivity {

    private AudioManager audMan;
    private MediaPlayer mp = new MediaPlayer();
    private int[] sounds;
    private Timer frameTime = new Timer();
    private MouthUpdate currentLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audMan = (AudioManager) getSystemService(AUDIO_SERVICE);
        sounds = loadSounds(R.raw.class.getFields());

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                currentLoop.closeAndCancel();
            }
        });
    }

    private int[] loadSounds(Field[] fields) {
        int[] ret = new int[fields.length];
        int index = 0;
        for(Field f : fields){
            try {
                ret[index] = f.getInt(new Object());
                index++;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public void askMeade(View view) {
        try {
            if(currentLoop != null){
                currentLoop.cancel();
            }
            currentLoop = new MouthUpdate(this);
            Random chooser = new Random();
            int chosenSound = chooser.nextInt(sounds.length);
            float vol = (float) audMan.getStreamVolume(AudioManager.STREAM_MUSIC)/ (float) audMan.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            mp.reset();
            AssetFileDescriptor afd = getResources().openRawResourceFd(sounds[chosenSound]);
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.seekTo(0);
            mp.setVolume(vol,vol);
            mp.start();
            frameTime.schedule(currentLoop, 0, 5);
            Log.i("Inquiry", "Playing sound at index: " + chosenSound);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
