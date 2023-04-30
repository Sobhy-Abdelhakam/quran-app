package com.sobhy.quran.ui.tafseer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.sobhy.quran.Database.QuranDatabase;
import com.sobhy.quran.R;
import com.sobhy.quran.pojo.quran.Ayah;
import com.sobhy.quran.pojo.tafaseer.TafseerName;


import java.util.ArrayList;

public class TafseerOfAyah extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView tafseerRecyclerView;
    ProgressBar tafseerProgress;
    TafseerAdapter tafseerAdapter;
    TextView ayahText;
    int ayahId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tafseer_of_ayah);
        toolbar= findViewById(R.id.tafseer_tool_bar);
        setSupportActionBar(toolbar);
        ayahText= findViewById(R.id.tafseer_ayah_text);
        tafseerProgress= findViewById(R.id.tafseer_progress);
        tafseerRecyclerView= findViewById(R.id.tafseer_recycler_view);
        tafseerAdapter= new TafseerAdapter();
        tafseerRecyclerView.setAdapter(tafseerAdapter);

        ayahId= getIntent().getIntExtra("Ayah_Id", -1);
        Ayah ayah= QuranDatabase.getINSTANCE(this).quranDao().getAyahByItsId(ayahId);
        toolbar.setTitle("سورة "+ayah.getSora_name_ar()+" - آية "+ ayah.getAya_no());
        ayahText.setText(ayah.getAya_text());

        new TafseerTask(getApplicationContext(), ayahId).execute();
    }

    private class TafseerTask extends AsyncTask<Void, Void, Void>{
        private final Context context;
        private ArrayList<TafseerName> tafseerNames;
        private ArrayList<String> tafaseer;
        private final int ayahId;

        public TafseerTask(Context context, int ayahId) {
            this.context = context;
            this.ayahId = ayahId;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tafseerRecyclerView.setVisibility(View.GONE);
            tafseerProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            TafaseerViewModel tafaseerViewModel = new TafaseerViewModel(context);
            tafseerNames= (ArrayList<TafseerName>) tafaseerViewModel.getNamesOfAllTafaseer();
            tafaseer= (ArrayList<String>) tafaseerViewModel.getAllTafaseerOfAyah(ayahId);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            tafseerAdapter.setList(tafaseer, tafseerNames);
            tafseerProgress.setVisibility(View.GONE);
            tafseerRecyclerView.setVisibility(View.VISIBLE);
        }
    }


}