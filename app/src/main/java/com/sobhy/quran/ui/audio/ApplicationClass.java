package com.sobhy.quran.ui.audio;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class ApplicationClass extends Application {
    public static final String QURAN_NOTIFICATION_ID= "QuranNotification";

    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PREVIOUS = "action_previous";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_STOP = "action_stop";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel(QURAN_NOTIFICATION_ID, "Quran Recitation", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager= getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
