package com.sobhy.quran.pojo.quran;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shamarly_mushaf")
public class ShamarlyAyah {
    @PrimaryKey
    int ayah_id;
    @NonNull
    int jozz, sura, page, ayah_num, x, y;
    @NonNull
    String sura_name, ayah_text, ayah_text_emlaey;

    public int getAyah_id() {
        return ayah_id;
    }

    public void setAyah_id(int ayah_id) {
        this.ayah_id = ayah_id;
    }

    public int getJozz() {
        return jozz;
    }

    public void setJozz(int jozz) {
        this.jozz = jozz;
    }

    public int getSura() {
        return sura;
    }

    public void setSura(int sura) {
        this.sura = sura;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getAyah_num() {
        return ayah_num;
    }

    public void setAyah_num(int ayah_num) {
        this.ayah_num = ayah_num;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getSura_name() {
        return sura_name;
    }

    public void setSura_name(String sura_name) {
        this.sura_name = sura_name;
    }

    public String getAyah_text() {
        return ayah_text;
    }

    public void setAyah_text(String ayah_text) {
        this.ayah_text = ayah_text;
    }

    public String getAyah_text_emlaey() {
        return ayah_text_emlaey;
    }

    public void setAyah_text_emlaey(String ayah_text_emlaey) {
        this.ayah_text_emlaey = ayah_text_emlaey;
    }
}
