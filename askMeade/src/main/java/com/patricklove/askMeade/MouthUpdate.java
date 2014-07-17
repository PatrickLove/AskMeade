package com.patricklove.askMeade;

import android.app.Activity;
import android.view.View;

import java.util.TimerTask;

/**
 * Created by Patrick Love on 5/9/14.
 */
public class MouthUpdate extends TimerTask {

    private volatile boolean moving = false;
    private Activity parent;
    private boolean down;

    public MouthUpdate(Activity par){
        parent = par;
    }

    @Override
    public void run() {

        View mouth = parent.findViewById(R.id.imageView2);
        if(mouth.getPaddingTop()==0){
            down = true;
            if(!moving){ return; }
        }
        else if(mouth.getPaddingTop()==35){ down = false; }
        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View mouth = parent.findViewById(R.id.imageView2);
                move(mouth);
            }
        });
    }

    private void move(View mouth) {
        if(!moving){
            mouth.setPadding(0,0,0,0);
        }
        else if(down){
            mouth.setPadding(0,mouth.getPaddingTop()+1,0,0);
        }
        else{
            mouth.setPadding(0,mouth.getPaddingTop()-1,0,0);
        }
        if(mouth.getPaddingTop() > 35){
            mouth.setPadding(0,35,0,0);
        }
        if(mouth.getPaddingTop() < 0){
            mouth.setPadding(0,0,0,0);
        }
        mouth.invalidate();
    }

    public void start(){
        moving = true;
    }

    public void stop(){
        moving = false;
    }
}
