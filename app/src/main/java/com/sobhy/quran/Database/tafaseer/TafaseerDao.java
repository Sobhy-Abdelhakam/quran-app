package com.sobhy.quran.Database.tafaseer;

import androidx.room.Dao;
import androidx.room.Query;

import com.sobhy.quran.pojo.tafaseer.TafseerName;

import java.util.List;

@Dao
public interface TafaseerDao {

    @Query("select * from tafseer_name")
    List<TafseerName> getNamesOfAllTafseer();

    @Query("select text from baghawy where ayah_id= :ayahId")
    String getBaghawyTafseerOfAyah(int ayahId);

    @Query("select text from e3rab where ayah_id= :ayahId")
    String getE3rabOfAyah(int ayahId);

    @Query("select text from katheer where ayah_id= :ayahId")
    String getIbnKatherTafseerOfAyah(int ayahId);

    @Query("select text from ma3any where ayah_id= :ayahId")
    String getMa3anyOfAyah(int ayahId);

    @Query("select text from muyassar where ayah_id= :ayahId")
    String getMuyassarTafseerOfAyah(int ayahId);

    @Query("select text from qortoby where ayah_id= :ayahId")
    String getQortobyTafseerOfAyah(int ayahId);

    @Query("select text from sa3dy where ayah_id= :ayahId")
    String getSa3dyTafseerOfAyah(int ayahId);

    @Query("select text from tabary where ayah_id= :ayahId")
    String getTabaryTafseerOfAyah(int ayahId);

    @Query("select text from tanweer where ayah_id= :ayahId")
    String getTanweerTafseerOfAyah(int ayahId);

    @Query("select text from waseet where ayah_id= :ayahId")
    String getWaseetTafseerOfAyah(int ayahId);

}
