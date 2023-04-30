package com.sobhy.quran.Database.shamarly;

import androidx.room.Dao;
import androidx.room.Query;

import com.sobhy.quran.pojo.quran.Jozz;
import com.sobhy.quran.pojo.quran.ShamarlyAyah;
import com.sobhy.quran.pojo.quran.Sora;

import java.util.List;

@Dao
public interface ShamarlyDao {

    @Query("select * from shamarly_mushaf where page= :page")
    List<ShamarlyAyah> getShamarlyAyahsByPage(int page);

    @Query("select * from shamarly_mushaf where ayah_text_emlaey like '%'|| :text ||'%'")
    List<ShamarlyAyah> getShamarlyAyatByText(String text);


    @Query("select * from shamarly_mushaf where ayah_text_emlaey like :text||'%'")
    List<ShamarlyAyah> getShamarlyAyatStartWith(String text);

    @Query("select * from shamarly_mushaf where ayah_text_emlaey like '%'||:text")
    List<ShamarlyAyah> getShamarlyAyatEndWith(String text);

    @Query("SELECT sura as soraNumber, min(page) as startPage, max(page) as endPage,max(ayah_num) as numbersOfAyat, sura_name as arabicName FROM shamarly_mushaf WHERE sura= :soraNumber")
    Sora getShamarlySoraByNumber(int soraNumber);

    @Query("select jozz as jozzNumber, min(page) as startPage, max(page) as endPage, min(ayah_id) as ayahId, ayah_text as firstAyahInJozz from shamarly_mushaf where jozz= :jozzNumber ")
    Jozz getShamarlyJozzByNumber(int jozzNumber);


}
