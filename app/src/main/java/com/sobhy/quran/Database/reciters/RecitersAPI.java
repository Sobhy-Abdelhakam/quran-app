package com.sobhy.quran.Database.reciters;

import com.sobhy.quran.pojo.quran.RecitersResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecitersAPI {
    @GET("reciters?language=ar")
    Call<RecitersResponse> getReciters();
}
