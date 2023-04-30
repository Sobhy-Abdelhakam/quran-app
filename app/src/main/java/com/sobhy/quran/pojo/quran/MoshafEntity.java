package com.sobhy.quran.pojo.quran;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "moshaf")
public class MoshafEntity {
    @PrimaryKey
    int id;
    String name;
    String server;
    int surah_total;
    String surah_list;

//    int reciterId;

    public MoshafEntity() {
    }

    public MoshafEntity(int id, String name, String server, int surah_total, String surah_list) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.surah_total = surah_total;
        this.surah_list = surah_list;
//        this.reciterId = reciterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getSurah_total() {
        return surah_total;
    }

    public void setSurah_total(int surah_total) {
        this.surah_total = surah_total;
    }

    public String getSurah_list() {
        return surah_list;
    }

    public void setSurah_list(String surah_list) {
        this.surah_list = surah_list;
    }

    @Override
    public String toString() {
        return name;
    }

//    public int getReciterId() {
//        return reciterId;
//    }
//
//    public void setReciterId(int reciterId) {
//        this.reciterId = reciterId;
//    }
}
