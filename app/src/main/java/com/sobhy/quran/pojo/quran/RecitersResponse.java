package com.sobhy.quran.pojo.quran;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class RecitersResponse {
    @SerializedName("reciters")
    private List<ReciterEntity> reciters;

    public List<ReciterEntity> getReciters() {
        Collections.sort(reciters);
        return reciters;
    }
}
