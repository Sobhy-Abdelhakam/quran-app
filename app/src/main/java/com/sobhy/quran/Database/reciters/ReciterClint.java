package com.sobhy.quran.Database.reciters;

import com.sobhy.quran.pojo.quran.RecitersResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReciterClint {

    private static final String BASE_API= "https://www.mp3quran.net/api/v3/";
    RecitersAPI recitersAPI;
    private static ReciterClint INSTANCE;

    private ReciterClint() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BASE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        recitersAPI = retrofit.create(RecitersAPI.class);
    }

    public static synchronized ReciterClint getInstance(){
        if(INSTANCE==null)
            INSTANCE= new ReciterClint();
        return INSTANCE;
    }

    public Call<RecitersResponse> getReciters(){
        return recitersAPI.getReciters();
    }

}
