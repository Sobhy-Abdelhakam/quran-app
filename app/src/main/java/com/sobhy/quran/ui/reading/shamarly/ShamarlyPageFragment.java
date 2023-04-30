package com.sobhy.quran.ui.reading.shamarly;


import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sobhy.quran.Database.shamarly.ShamarlyDatabase;
import com.sobhy.quran.R;
import com.sobhy.quran.pojo.quran.ShamarlyAyah;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

public class ShamarlyPageFragment extends Fragment {

    TextView shamarlySuraName, shamarlyJuzzNumber, shamarlyPageNumberTv;

    ImageView shamarlyImageView;
    int pageNumber;

    public ShamarlyPageFragment() {
        // Required empty public constructor
    }

    public ShamarlyPageFragment(int pageNumber){
        this.pageNumber= pageNumber;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shamarly_page, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout frameLayout= view.findViewById(R.id.shamarly_fragment_one_page);
        shamarlyImageView = view.findViewById(R.id.shamarly_single_page);
        shamarlySuraName= view.findViewById(R.id.fragment_shamarly_surah_name);
        shamarlyJuzzNumber= view.findViewById(R.id.fragment_shamarly_juzz_number);
        shamarlyPageNumberTv= view.findViewById(R.id.fragment_shamarly_page_number);

        getShamarlyImageFromAssets(pageNumber);
//        if(pageNumber>=4){
            List<ShamarlyAyah> shamarlyAyahs= ShamarlyDatabase.getInstance(view.getContext()).shamarlyDao().getShamarlyAyahsByPage(pageNumber);
            if(shamarlyAyahs.size()!= 0){
                shamarlySuraName.setText("سورة "+shamarlyAyahs.get(shamarlyAyahs.size()-1).getSura_name());
                shamarlyJuzzNumber.setText("جزء "+shamarlyAyahs.get(shamarlyAyahs.size()-1).getJozz());
                shamarlyPageNumberTv.setText(""+pageNumber);
            }
//        }
        frameLayout.setOnClickListener(v -> ShamarlyQuranContainer.shamrlyToggleToolbarAndTaskbar());

    }

    void getShamarlyImageFromAssets(int pageNumber){
        DecimalFormat formatter = new DecimalFormat("000");
        String pageName =formatter.format(pageNumber) + ".png";

        try {
            // Get the AssetManager instance
            AssetManager assetManager = requireContext().getAssets();

            // List all files in the "quran_images" folder within the assets folder
            String[] files = assetManager.list("shamraly-images-master");
            // Loop through the files and find the one that matches the page number
            for (String filename : files) {
                if (filename.endsWith(pageName)) {
                    // Load the image as a Bitmap
                    InputStream inputStream = assetManager.open("shamraly-images-master/" + filename);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    shamarlyImageView.setImageBitmap(bitmap);
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}