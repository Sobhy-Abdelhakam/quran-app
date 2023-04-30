package com.sobhy.quran.pojo.tafaseer;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tanweer")
public class Tanweer {
    @PrimaryKey
    int ayah_id;
    String text;

    public int getAyah_id() {
        return ayah_id;
    }

    public void setAyah_id(int ayah_id) {
        this.ayah_id = ayah_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
