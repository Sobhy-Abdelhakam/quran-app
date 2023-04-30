package com.sobhy.quran.ui.reading;

import static com.sobhy.quran.ui.reading.QuranContainer.toggleToolbarAndTaskbar;

import android.graphics.drawable.Drawable;
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
import com.sobhy.quran.Database.QuranDatabase;
import com.sobhy.quran.R;
import com.sobhy.quran.pojo.quran.Ayah;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class OnePageFragment extends Fragment {
    private int pageNumber;
    TextView suraName, juzzNumber, pageNumberTv;

    ImageView imageView;
    public OnePageFragment() {
        // Required empty public constructor
    }

    public OnePageFragment(int pageNumber){
        this.pageNumber= pageNumber;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout frameLayout= view.findViewById(R.id.fragment_one_page);
        imageView = view.findViewById(R.id.single_page);
        suraName= view.findViewById(R.id.fragment_surah_name);
        juzzNumber= view.findViewById(R.id.fragment_juzz_number);
        pageNumberTv= view.findViewById(R.id.fragment_page_number);

        imageView.setImageDrawable(getImageFromDrawableByPageNumber(pageNumber));

        List<Ayah> ayahs= QuranDatabase.getINSTANCE(view.getContext()).quranDao().getAyatByPage(pageNumber);

        if(ayahs.size()!= 0){
            suraName.setText("سورة "+ayahs.get(ayahs.size()-1).getSora_name_ar());
            juzzNumber.setText("جزء "+ayahs.get(ayahs.size()-1).getJozz());
            pageNumberTv.setText(""+pageNumber);
        }
        frameLayout.setOnClickListener(v -> toggleToolbarAndTaskbar());
    }

    Drawable getImageFromDrawableByPageNumber(int pageNumber){
        DecimalFormat formatter = new DecimalFormat("###");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        formatter.setDecimalFormatSymbols(symbols);
        String drawableName = "quran_images/" + formatter.format(pageNumber) + ".png";

        InputStream istr = null;
        try {
            istr = requireContext().getAssets().open(drawableName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Drawable.createFromStream(istr, null);
    }

}