package com.sobhy.quran.ui.audio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sobhy.quran.R;

public class AudioActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        bottomNavigationView = findViewById(R.id.audio_bottom_nav_bar);
        replaceFragment(new RecitersFragment());
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navbar_reciter:
                    replaceFragment(new RecitersFragment());
                    break;
                case R.id.navbar_download:
                    replaceFragment(new RecitersDownloadedFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.audio_frame_layout, fragment);
        fragmentTransaction.commit();
    }
}