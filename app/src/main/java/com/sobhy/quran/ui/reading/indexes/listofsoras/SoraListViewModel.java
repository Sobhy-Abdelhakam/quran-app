package com.sobhy.quran.ui.reading.indexes.listofsoras;

import android.content.Context;

import com.sobhy.quran.Database.QuranDatabase;
import com.sobhy.quran.Database.shamarly.ShamarlyDatabase;
import com.sobhy.quran.pojo.quran.Sora;

import java.util.ArrayList;

public class SoraListViewModel {

    public static ArrayList<Sora> getAllSoras(Context context) {
        ArrayList<Sora> allSoras = new ArrayList<>();
        for (int i = 1; i <= 114; i++) {
            allSoras.add(QuranDatabase.getINSTANCE(context).quranDao().getSoraByNumber(i));
        }
        return allSoras;
    }

    public static ArrayList<Sora> getAllShamarlySuras(Context context) {
        ArrayList<Sora> allSoras = new ArrayList<>();
        for (int i = 1; i <= 114; i++) {
            allSoras.add(ShamarlyDatabase.getInstance(context).shamarlyDao().getShamarlySoraByNumber(i));
        }
        return allSoras;
    }
}
