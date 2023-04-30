package com.sobhy.quran.Database;

import androidx.room.Dao;
import androidx.room.Query;

import com.sobhy.quran.pojo.quran.Ayah;
import com.sobhy.quran.pojo.quran.Jozz;
import com.sobhy.quran.pojo.quran.Sora;

import java.util.List;

@Dao
public interface QuranDao {

    @Query("select * from quran where page= :page")
    List<Ayah> getAyatByPage(int page);

    @Query("select * from quran where aya_text_emlaey like '%'||:text||'%'")
    List<Ayah> getAyatByText(String text);


    @Query("select * from quran where aya_text_emlaey like :text||'%'")
    List<Ayah> getAyatStartWith(String text);


    @Query("select * from quran where aya_text_emlaey like '%'||:text")
    List<Ayah> getAyatEndWith(String text);


    @Query("SELECT sora as soraNumber, min(page) as startPage, max(page) as endPage,max(aya_no) as numbersOfAyat, sora_name_ar as arabicName FROM quran WHERE sora= :soraNumber")
    Sora getSoraByNumber(int soraNumber);

    @Query("select jozz as jozzNumber, min(page) as startPage, max(page) as endPage, min(id) as ayahId, aya_text as firstAyahInJozz from quran where jozz= :jozzNumber ")
    Jozz getJozzByNumber(int jozzNumber);

    @Query("select * from quran where id= :ayahId")
    Ayah getAyahByItsId(int ayahId);

}
