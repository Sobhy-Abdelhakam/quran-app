package com.sobhy.quran.ui.audio;

import static com.sobhy.quran.ui.audio.ApplicationClass.ACTION_NEXT;
import static com.sobhy.quran.ui.audio.ApplicationClass.ACTION_PLAY;
import static com.sobhy.quran.ui.audio.ApplicationClass.ACTION_PREVIOUS;
import static com.sobhy.quran.ui.audio.ApplicationClass.ACTION_STOP;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String actionName= intent.getAction();
        Intent serviceIntent= new Intent(context, QuranAudioService.class);
        if(actionName != null){
            switch (actionName){
                case ACTION_PLAY:
                    serviceIntent.putExtra("ActionName", "playPause");
                    context.startService(serviceIntent);
                    break;
                case ACTION_NEXT:
                    serviceIntent.putExtra("ActionName", "next");
                    context.startService(serviceIntent);
                    break;
                case ACTION_PREVIOUS:
                    serviceIntent.putExtra("ActionName", "previous");
                    context.startService(serviceIntent);
                    break;
                case ACTION_STOP:
                    serviceIntent.putExtra("ActionName", "stop");
                    context.startService(serviceIntent);
            }

        }
    }
}
