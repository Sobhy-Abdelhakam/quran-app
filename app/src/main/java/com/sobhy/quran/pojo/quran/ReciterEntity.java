package com.sobhy.quran.pojo.quran;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import java.util.List;
@Entity(tableName = "reciter")
public class ReciterEntity implements Comparable<ReciterEntity> {
    @PrimaryKey
    int id;
    String name;
    String letter;
    @SerializedName("moshaf")
    List<MoshafEntity> moshaf;

    public ReciterEntity() {
    }

    public ReciterEntity(int id, String name, String letter, List<MoshafEntity> moshaf) {
        this.id = id;
        this.name = name;
        this.letter = letter;
        this.moshaf = moshaf;
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

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public List<MoshafEntity> getMoshaf() {
        return moshaf;
    }

    public void setMoshaf(List<MoshafEntity> moshaf) {
            this.moshaf = moshaf;
        }


    @Override
    public int compareTo(ReciterEntity o) {
        return this.name.compareTo(o.getName());
    }
}
