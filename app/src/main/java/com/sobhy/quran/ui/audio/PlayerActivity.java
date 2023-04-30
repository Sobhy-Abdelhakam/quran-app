package com.sobhy.quran.ui.audio;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.sobhy.quran.R;
import com.sobhy.quran.pojo.quran.AudioSura;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity
        implements ActionPlaying, ServiceConnection {
    TextView suraNameTv, reciterNameTV, moshafNameTV, durationPlayed, durationTotal;
    ImageView previse, next;
    FloatingActionButton play;
    SeekBar seekBar;
    public static ArrayList<AudioSura> suraList= new ArrayList<>();
    int position= -1;
    String suraName, suraUri, reciterName, moshafName;
    private final Handler handler= new Handler();
    QuranAudioService quranAudioService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initView();
        getIntentMethod();
        IntentFilter filter = new IntentFilter();
        filter.addAction("finish_activity");
        registerReceiver(finishActivityReceiver, filter);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(quranAudioService != null && fromUser){
                    quranAudioService.seekTo(progress*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(quranAudioService != null){
                    int currentPosition= quranAudioService.getCurrentPosition()/1000;
                    seekBar.setProgress(currentPosition);
                    durationPlayed.setText(formattedTime(currentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent= new Intent(this, QuranAudioService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
        playThreadBtn();
        prevThreadBtn();
        nextThreadBtn();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(finishActivityReceiver);
    }

    private void nextThreadBtn() {
        Thread nextThread = new Thread() {
            @Override
            public void run() {
                super.run();
                next.setOnClickListener(v -> nextBtnClicked());
            }
        };
        nextThread.start();
    }

    public void nextBtnClicked() {
        quranAudioService.stop();
        quranAudioService.release();
        position= ((position+1) % suraList.size());
        suraUri= suraList.get(position).getSuraUrl();
        quranAudioService.createMediaPlayer(position);
        setText(position);
        seekBar.setMax(quranAudioService.getDuration()/1000);
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (quranAudioService != null){
                    int currentPosition= quranAudioService.getCurrentPosition()/1000;
                    seekBar.setProgress(currentPosition);
                }
                handler.postDelayed(this, 1000);
            }
        });
        quranAudioService.onCompleted();
        quranAudioService.showNotification(R.drawable.ic_pause);
        play.setImageResource(R.drawable.ic_pause);
        quranAudioService.start();
    }

    private void prevThreadBtn() {
        Thread prevThread = new Thread() {
            @Override
            public void run() {
                super.run();
                previse.setOnClickListener(v -> previseBtnClicked());
            }
        };
        prevThread.start();
    }

    public void previseBtnClicked() {
        quranAudioService.stop();
        quranAudioService.release();
        position= ((position-1)<0 ? suraList.size()-1 : (position-1));
        suraUri= suraList.get(position).getSuraUrl();
        quranAudioService.createMediaPlayer(position);
        setText(position);
        seekBar.setMax(quranAudioService.getDuration()/1000);
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (quranAudioService != null){
                    int currentPosition= quranAudioService.getCurrentPosition()/1000;
                    seekBar.setProgress(currentPosition);
                }
                handler.postDelayed(this, 1000);
            }
        });
        quranAudioService.onCompleted();
        quranAudioService.showNotification(R.drawable.ic_pause);
        play.setImageResource(R.drawable.ic_pause);
        quranAudioService.start();
    }

    private void playThreadBtn() {
        Thread playThread = new Thread() {
            @Override
            public void run() {
                super.run();
                play.setOnClickListener(v -> playPauseBtnClicked());
            }
        };
        playThread.start();
    }

    public void playPauseBtnClicked() {
        if(quranAudioService.isPlaying()){
            quranAudioService.showNotification(R.drawable.ic_play_arrow);
            play.setImageResource(R.drawable.ic_play_arrow);
            quranAudioService.pause();
        } else {
            quranAudioService.showNotification(R.drawable.ic_pause);
            play.setImageResource(R.drawable.ic_pause);
            quranAudioService.start();
        }
        seekBar.setMax(quranAudioService.getDuration()/1000);
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(quranAudioService!= null){
                    int currentPosition= quranAudioService.getCurrentPosition()/1000;
                    seekBar.setProgress(currentPosition);
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private String formattedTime(int currentPosition){
        int hours = currentPosition / 3600;
        int minutes = (currentPosition % 3600) / 60;
        int seconds = currentPosition % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    private void getIntentMethod(){
        suraList= (ArrayList<AudioSura>) getIntent().getSerializableExtra("suraList");
        position= getIntent().getIntExtra("position", -1);

        if(suraList != null){
            play.setImageResource(R.drawable.ic_pause);
            suraUri= suraList.get(position).getSuraUrl();
        }

        new Thread(() -> {
            Intent intent= new Intent(getApplicationContext(), QuranAudioService.class);
            intent.putExtra("servicePosition", position);
            startService(intent);
        }).start();


    }
    private void initView() {
        reciterNameTV= findViewById(R.id.player_tv_reciter_name);
        suraNameTv = findViewById(R.id.player_tv_sura_name);
        moshafNameTV= findViewById(R.id.player_tv_moshaf_name);
        durationPlayed= findViewById(R.id.player_duration_played);
        durationTotal= findViewById(R.id.player_duration_total);
        previse= findViewById(R.id.player_previous);
        next= findViewById(R.id.player_next);
        play= findViewById(R.id.player_play_arrow);
        seekBar= findViewById(R.id.player_seekbar);
    }
    private void setText(int position){
        int duration_Total=0;
        if(quranAudioService != null){
            duration_Total= quranAudioService.getDuration()/1000;
        }
        durationTotal.setText(formattedTime(duration_Total));

        suraName= suraList.get(position).getSuraName();
        reciterName= suraList.get(position).getReciterName();
        moshafName= suraList.get(position).getMoshafName();

        suraNameTv.setText("سورة "+suraName);
        reciterNameTV.setText(reciterName);
        moshafNameTV.setText(moshafName);
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        QuranAudioService.MyBinder myBinder= (QuranAudioService.MyBinder) service;
        quranAudioService= myBinder.getService();
        quranAudioService.setCallBack(this);
        seekBar.setMax(quranAudioService.getDuration()/1000);
        setText(position);
        quranAudioService.onCompleted();
        quranAudioService.showNotification(R.drawable.ic_pause);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        quranAudioService= null;
    }

    private BroadcastReceiver finishActivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("finish_activity")) {
                finish();
            }
        }
    };

}