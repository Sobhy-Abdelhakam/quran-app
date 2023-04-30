package com.sobhy.quran.ui.reading.indexes.listofjozz;

import android.content.Context;

import com.sobhy.quran.Database.QuranDatabase;
import com.sobhy.quran.Database.shamarly.ShamarlyDatabase;
import com.sobhy.quran.pojo.quran.Jozz;

import java.util.ArrayList;

public class JozzListViewModel {

    public static ArrayList<Jozz> getAllJozz(Context context) {
        ArrayList<Jozz> allJozz = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            allJozz.add(QuranDatabase.getINSTANCE(context).quranDao().getJozzByNumber(i));
        }
        return allJozz;
    }

    public static ArrayList<Jozz> getAllShamarlyJozz(Context context) {
        ArrayList<Jozz> allJozz = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            allJozz.add(ShamarlyDatabase.getInstance(context).shamarlyDao().getShamarlyJozzByNumber(i));
        }
        return allJozz;
    }

}
