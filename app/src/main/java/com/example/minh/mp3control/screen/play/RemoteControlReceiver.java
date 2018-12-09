package com.example.minh.mp3control.screen.play;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class RemoteControlReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, ""+intent.getAction(), Toast.LENGTH_SHORT).show();
//        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
//            KeyEvent keyEvent = (KeyEvent) intent.getExtras().get(Intent.EXTRA_KEY_EVENT);
//            Toast.makeText(context, "" + keyEvent, Toast.LENGTH_SHORT).show();
//        }
    }

}
