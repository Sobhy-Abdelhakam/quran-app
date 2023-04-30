package com.sobhy.quran.ui.tafseer;

import android.content.Context;

import com.sobhy.quran.Database.tafaseer.TafaseerDao;
import com.sobhy.quran.Database.tafaseer.TafaseerDatabase;
import com.sobhy.quran.pojo.tafaseer.TafseerName;

import java.util.ArrayList;
import java.util.List;

public class TafaseerViewModel {
    TafaseerDao tafaseerDao;

    public TafaseerViewModel(Context context) {
        this.tafaseerDao = TafaseerDatabase.getInstance(context).tafaseerDao();
    }

    List<TafseerName> getNamesOfAllTafaseer() {
        return tafaseerDao.getNamesOfAllTafseer();
    }

    List<String> getAllTafaseerOfAyah(int ayahId) {
        List<String> tafaseer = new ArrayList<>();

        tafaseer.add(tafaseerDao.getMa3anyOfAyah(ayahId) != null ? tafaseerDao.getMa3anyOfAyah(ayahId) : "لا يوجد معاني");
        tafaseer.add(tafaseerDao.getMuyassarTafseerOfAyah(ayahId) != null ? tafaseerDao.getMuyassarTafseerOfAyah(ayahId) : "لا يوجد تفسير");
        tafaseer.add(tafaseerDao.getTabaryTafseerOfAyah(ayahId) != null ? tafaseerDao.getTabaryTafseerOfAyah(ayahId) : "لا يوجد تفسير");
        tafaseer.add(tafaseerDao.getIbnKatherTafseerOfAyah(ayahId) != null ? tafaseerDao.getIbnKatherTafseerOfAyah(ayahId) : "لا يوجد تفسير");
        tafaseer.add(tafaseerDao.getSa3dyTafseerOfAyah(ayahId) != null ? tafaseerDao.getSa3dyTafseerOfAyah(ayahId) : "لا يوجد تفسير");
        tafaseer.add(tafaseerDao.getQortobyTafseerOfAyah(ayahId) != null ? tafaseerDao.getQortobyTafseerOfAyah(ayahId) : "لا يوجد تفسير");
        tafaseer.add(tafaseerDao.getBaghawyTafseerOfAyah(ayahId) != null ? tafaseerDao.getBaghawyTafseerOfAyah(ayahId) : "لا يوجد تفسير");
        tafaseer.add(tafaseerDao.getTanweerTafseerOfAyah(ayahId) != null ? tafaseerDao.getTanweerTafseerOfAyah(ayahId) : "لا يوجد تفسير");
        tafaseer.add(tafaseerDao.getWaseetTafseerOfAyah(ayahId) != null ? tafaseerDao.getWaseetTafseerOfAyah(ayahId) : "لا يوجد تفسير");
        tafaseer.add(tafaseerDao.getE3rabOfAyah(ayahId) != null ? tafaseerDao.getE3rabOfAyah(ayahId) : "لا يوجد إعراب");

        return tafaseer;
    }
}
