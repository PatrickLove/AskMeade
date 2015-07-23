package com.patricklove.askMeade;

import android.app.Activity;
import android.view.View;

import java.util.TimerTask;

/**
 * Created by Patrick Love on 5/9/14.
 */
public class MouthUpdate extends TimerTask {

    private enum MovementState {
        TALKING,
        CLOSING;
    }
    private MovementState state = MovementState.TALKING;
    private Activity parent;
    private static boolean down;

    public MouthUpdate(Activity par){
        parent = par;
    }

    @Override
    public void run() {
        View mouth = parent.findViewById(R.id.imageView2);
        if(mouth.getPaddingTop()==0){
            down = true;
            if(state == MovementState.CLOSING) cancel();
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

    public void closeAndCancel(){
        down = false;
        state = MovementState.CLOSING;
    }

    private void move(View mouth) {
        if (down) {
            mouth.setPadding(0, mouth.getPaddingTop() + 1, 0, 0);
        } else {
            mouth.setPadding(0, mouth.getPaddingTop() - 1, 0, 0);
        }
        if (mouth.getPaddingTop() > 35) {
            mouth.setPadding(0, 35, 0, 0);
        }
        if (mouth.getPaddingTop() < 0) {
            mouth.setPadding(0, 0, 0, 0);
        }
        mouth.invalidate();
    }
}
