package com.sobhy.quran.pojo.tafaseer;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tafseer_name")
public class TafseerName {
    @PrimaryKey
    int id;
    String name;

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
}
