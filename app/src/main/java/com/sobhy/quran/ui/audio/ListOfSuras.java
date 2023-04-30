package com.sobhy.quran.ui.audio;

import static com.sobhy.quran.ui.audio.AudioSurasAdapter.isSoundDownloaded;
import static com.sobhy.quran.ui.audio.RecitersFragment.isNetworkAvailable;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sobhy.quran.Database.QuranDatabase;
import com.sobhy.quran.R;
import com.sobhy.quran.pojo.quran.AudioSura;
import com.sobhy.quran.pojo.quran.MoshafEntity;
import com.sobhy.quran.pojo.quran.ReciterEntity;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListOfSuras extends AppCompatActivity {
    Toolbar listOfSurasToolbar;
    TextView moshafNameTv;
    ImageView moshafDownloadButton;
    RecyclerView suraRecyclerView;
    AudioSurasAdapter adapter;
    ArrayList<AudioSura> sura;

    Spinner moshafSpinner;

    String reciterName;

    SurahDownloadedAdapter surahDownloadedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ofsuras);
        listOfSurasToolbar = findViewById(R.id.list_sura_tool_bar);
        setSupportActionBar(listOfSurasToolbar);
        moshafNameTv = findViewById(R.id.list_sura_moshaf_name);
        moshafDownloadButton = findViewById(R.id.list_sura_image_download);
        moshafSpinner = findViewById(R.id.list_sura_moshaf_spinner);
        suraRecyclerView = findViewById(R.id.audio_list_suras_recyclerview);


        if (getIntent().getStringExtra("reciterNameFromDownload") != null) {
            moshafDownloadButton.setVisibility(View.GONE);
            surahDownloadedAdapter = new SurahDownloadedAdapter();
            suraRecyclerView.setAdapter(surahDownloadedAdapter);
            reciterName = getIntent().getStringExtra("reciterNameFromDownload");
            listOfSurasToolbar.setTitle(reciterName);

            List<String> moshafList = getDownloadedMoshafForReciter();
            ArrayAdapter<String> moshafDownloadedadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, moshafList);
            moshafDownloadedadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            moshafSpinner.setAdapter(moshafDownloadedadapter);

            moshafSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sura = new ArrayList<>();
                    String moshafName = moshafList.get(position);
                    moshafNameTv.setText(moshafName);
                    ArrayList<AudioSura> audioSuras = (ArrayList<AudioSura>) getSurahsFilesForMoshafs(moshafName);
                    surahDownloadedAdapter.setList(audioSuras);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            moshafDownloadButton.setVisibility(View.VISIBLE);
            adapter = new AudioSurasAdapter();
            suraRecyclerView.setAdapter(adapter);
            int reciterId = getIntent().getIntExtra("reciterId", -1);

            if (isNetworkAvailable(this)) {
                ReciterEntity reciter = ReciterViewModel.getReciterFromApi(reciterId);
                listOfSurasToolbar.setTitle(reciter.getName());
                String reciterName = reciter.getName();

                List<MoshafEntity> moshafList = reciter.getMoshaf();
                ArrayAdapter<MoshafEntity> moshafadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, moshafList);
                moshafadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                moshafSpinner.setAdapter(moshafadapter);

                moshafSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sura = new ArrayList<>();
                        String moshafName = moshafList.get(position).getName();
                        moshafNameTv.setText(moshafName);
                        int totalOfSuras = moshafList.get(position).getSurah_total();
                        String listOfSurasAsString = moshafList.get(position).getSurah_list();
                        ArrayList<Integer> listOfSuras = convertStringToArrayList(listOfSurasAsString);
                        String server = moshafList.get(position).getServer();
                        DecimalFormat formatter = new DecimalFormat("000");

                        for (int i = 0; i < totalOfSuras; i++) {
                            int suraNumber = listOfSuras.get(i);
                            String url = server + formatter.format(suraNumber) + ".mp3";
                            String suraName = QuranDatabase.getINSTANCE(getApplicationContext()).quranDao().getSoraByNumber(suraNumber).getArabicName();
                            sura.add(new AudioSura(suraNumber, url, suraName, reciterName, moshafName));
                        }
                        adapter.setList(sura);
                        // Download all moshaf
                        moshafDownloadButton.setOnClickListener(view1 -> {
                            String folderName = reciter.getName() + "/" + moshafName;
                            File folder = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), folderName);
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            if (success) {
                                for (AudioSura eachSura : sura) {
                                    if (!isSoundDownloaded(getApplicationContext(), eachSura)) {
                                        // Start the download
                                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(eachSura.getSuraUrl()));
                                        request.setDescription("Downloading");
                                        request.setMimeType("audio/MP3");
                                        request.setTitle(eachSura.getSuraName());
                                        request.allowScanningByMediaScanner();
                                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                        request.setDestinationInExternalFilesDir(getBaseContext(), Environment.DIRECTORY_DOWNLOADS, folderName + "/" + eachSura.getSuraName() + ".mp3");
                                        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                                        long managerId = manager.enqueue(request);
                                        eachSura.setDownloading(true);

                                        BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
                                            @Override
                                            public void onReceive(Context context, Intent intent) {
                                                long id1 = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                                                if (id1 == managerId) {
                                                    eachSura.setDownloading(false);
                                                }
                                            }
                                        };
                                        registerReceiver(downloadCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                                    }
                                }

                            }

                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } else {
                new Thread(() -> {
                    ReciterEntity reciter = ReciterViewModel.getReciterFromDb(getBaseContext(), reciterId);
                    listOfSurasToolbar.setTitle(reciter.getName());
                    List<MoshafEntity> moshafList = reciter.getMoshaf();
                    String reciterName = reciter.getName();
                    ArrayAdapter<MoshafEntity> moshafadapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, moshafList);
                    moshafadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    moshafSpinner.setAdapter(moshafadapter);

                    moshafSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            sura = new ArrayList<>();
                            String moshafName = moshafList.get(position).getName();
                            int totalOfSuras = moshafList.get(position).getSurah_total();
                            String listOfSurasAsString = moshafList.get(position).getSurah_list();
                            ArrayList<Integer> listOfSuras = convertStringToArrayList(listOfSurasAsString);
                            String server = moshafList.get(position).getServer();
                            DecimalFormat formatter = new DecimalFormat("000");

                            for (int i = 0; i < totalOfSuras; i++) {
                                int suraNumber = listOfSuras.get(i);
                                String url = server + formatter.format(suraNumber) + ".mp3";
                                String suraName = QuranDatabase.getINSTANCE(getApplicationContext()).quranDao().getSoraByNumber(suraNumber).getArabicName();
                                sura.add(new AudioSura(suraNumber, url, suraName, reciterName, moshafName));
                            }
                            adapter.setList(sura);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }).start();

                moshafDownloadButton.setOnClickListener(view -> Toast.makeText(ListOfSuras.this, "لا يوجد اتصال بالإنترنت", Toast.LENGTH_SHORT).show());

            }

        }
    }

    public ArrayList<Integer> convertStringToArrayList(String text) {
        String[] elements = text.split(","); // step two : convert String array to list of String
        List<String> fixedLenghtList = Arrays.asList(elements); // step three : copy fixed list to an ArrayList
        return fixedLenghtList.stream()
                .map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<String> getDownloadedMoshafForReciter() {
        List<String> moshafs = new ArrayList<>();
        File[] moshafsFolder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/" + reciterName).listFiles(File::isDirectory);
        if (moshafsFolder != null) {
            for (File reciterFolder : moshafsFolder) {
                moshafs.add(reciterFolder.getName());
            }
        }
        return moshafs;
    }

    private List<AudioSura> getSurahsFilesForMoshafs(String moshafsName) {
        List<AudioSura> audioSuras = new ArrayList<>();
        File[] surahsFiles = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/" + reciterName + "/" + moshafsName).listFiles(File::isFile);
        if (surahsFiles != null) {
            for (File file : surahsFiles) {
                if (file.getName().endsWith(".mp3")) {
                    String soraName = file.getName().replace(".mp3", "");
                    AudioSura audioSura = new AudioSura(1, file.getPath(), soraName, reciterName, moshafsName);
                    audioSuras.add(audioSura);
                }
            }
        }
        return audioSuras;
    }


}