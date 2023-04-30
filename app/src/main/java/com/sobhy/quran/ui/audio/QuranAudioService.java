package com.sobhy.quran.ui.audio;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.sobhy.quran.R;
import com.sobhy.quran.pojo.quran.AudioSura;
import java.util.ArrayList;

public class QuranAudioService extends Service implements MediaPlayer.OnCompletionListener {
    public class MyBinder extends Binder{
        QuranAudioService getService(){
            return QuranAudioService.this;
        }
    }
    IBinder mBinder= new MyBinder();
    private MediaPlayer mediaPlayer;
    ArrayList<AudioSura> audioList= new ArrayList<>();
    Uri uri;
    int position=-1;
    ActionPlaying actionPlaying;

    MediaSessionCompat mediaSessionCompat;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaSessionCompat= new MediaSessionCompat(getBaseContext(), "MyAudio");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        audioList= PlayerActivity.suraList;
        int myPosition= intent.getIntExtra("servicePosition", -1);
        String actionName= intent.getStringExtra("ActionName");
        if(myPosition != -1)
            playAudio(myPosition);

        if(actionName != null){
            switch (actionName){
                case "playPause":
                    if(actionPlaying != null)
                        actionPlaying.playPauseBtnClicked();
                    break;
                case "next":
                    if(actionPlaying != null)
                        actionPlaying.nextBtnClicked();
                    break;
                case "previous":
                    if(actionPlaying != null)
                        actionPlaying.previseBtnClicked();
                    break;
                case "stop":
                    onDestroy();


            }
        }

        return START_STICKY;
    }

    private void playAudio(int startPosition) {
        position= startPosition;
        if (mediaPlayer != null){
            stop();
            release();
        }
        createMediaPlayer(position);
        start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
        Intent intent = new Intent("finish_activity");
        sendBroadcast(intent);
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Bind", "method");
        return mBinder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }


    void start(){
        mediaPlayer.start();
    }
    void stop(){
        mediaPlayer.stop();
    }
    void pause() {
        mediaPlayer.pause();
    }
    void release(){
        mediaPlayer.release();
    }
    int getDuration() {
        return mediaPlayer.getDuration();
    }
    int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }
    boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }
    void seekTo(int position){
        mediaPlayer.seekTo(position);
    }

    void createMediaPlayer(int positionInner){
        position= positionInner;
        uri= Uri.parse(audioList.get(position).getSuraUrl());
        mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
    }
    void onCompleted(){
        mediaPlayer.setOnCompletionListener(this);
    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        if(actionPlaying != null){
            actionPlaying.nextBtnClicked();
        }
    }
    void setCallBack(ActionPlaying actionPlaying){
        this.actionPlaying= actionPlaying;
    }

    void showNotification(int playPauseBtn){
        RemoteViews contentView= new RemoteViews(getPackageName(), R.layout.custom_notification_background);
        Intent intent= new Intent(this, PlayerActivity.class);
        PendingIntent contentIntent= PendingIntent.getActivity(this, 0, intent, 0);

        Intent prevIntent= new Intent(this, NotificationReceiver.class)
                .setAction(ApplicationClass.ACTION_PREVIOUS);
        PendingIntent prevPendingIntent= PendingIntent
                .getBroadcast(this, 0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent playIntent= new Intent(this, NotificationReceiver.class)
                .setAction(ApplicationClass.ACTION_PLAY);
        PendingIntent playPendingIntent= PendingIntent
                .getBroadcast(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent= new Intent(this, NotificationReceiver.class)
                .setAction(ApplicationClass.ACTION_NEXT);
        PendingIntent nextPendingIntent= PendingIntent
                .getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent stopIntent= new Intent(this, NotificationReceiver.class)
                .setAction(ApplicationClass.ACTION_STOP);
        PendingIntent stopPendingIntent= PendingIntent
                .getBroadcast(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification= new NotificationCompat.Builder(this, ApplicationClass.QURAN_NOTIFICATION_ID)
                .setSmallIcon(R.drawable.quran_audio)
                .setContentTitle("سورة "+audioList.get(position).getSuraName())
                .setContentText(audioList.get(position).getReciterName())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_skip_previous, "Previous", prevPendingIntent)
                .addAction(playPauseBtn, "Pause", playPendingIntent)
                .addAction(R.drawable.ic_skip_next, "Next", nextPendingIntent)
                .addAction(R.drawable.ic_stop, "stop", stopPendingIntent)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSessionCompat.getSessionToken()))
                .setOnlyAlertOnce(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCustomContentView(contentView)
//                .setContentIntent(contentIntent)
                .build();

        startForeground(1, notification);

    }

}