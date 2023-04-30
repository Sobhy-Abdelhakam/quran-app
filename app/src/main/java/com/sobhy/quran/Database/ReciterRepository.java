package com.sobhy.quran.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sobhy.quran.Database.reciters.ReciterClint;
import com.sobhy.quran.Database.reciters.ReciterDatabase;
import com.sobhy.quran.pojo.quran.MoshafEntity;
import com.sobhy.quran.pojo.quran.ReciterEntity;
import com.sobhy.quran.pojo.quran.RecitersResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReciterRepository {
    static MutableLiveData<List<ReciterEntity>> reciters= new MutableLiveData<>();
    Context context;
    public ReciterRepository(Context context){
        this.context= context;
    }
    public LiveData<List<ReciterEntity>> getReciterFromApi(){
        fetchReciters();
        return reciters;
    }
    public void fetchReciters(){
        Call<RecitersResponse> call= ReciterClint.getInstance().getReciters();
        call.enqueue(new Callback<RecitersResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecitersResponse> call, @NonNull Response<RecitersResponse> response) {

                reciters.setValue(response.body().getReciters());
                new SaveDataTask().execute(response.body().getReciters());
            }
            @Override
            public void onFailure(@NonNull Call<RecitersResponse> call, @NonNull Throwable t) {

            }
        });

    }

    public static ReciterEntity getReciterByIdFromApi(int reciterId) {
        List<ReciterEntity> recitersList = reciters.getValue();
        if (recitersList != null) {
            for (ReciterEntity reciter : recitersList) {
                if (reciter.getId() == reciterId) {
                    return reciter;
                }
            }
        }
        return null;
    }

    public List<ReciterEntity> getRecitersFromDb(){
        return ReciterDatabase.getInstance(context).dao().getAllReciter();
    }

    public ReciterEntity getReciterByIdFromDb(int reciterId){
        return ReciterDatabase.getInstance(context).dao().getReciterById(reciterId);
    }

    private class SaveDataTask extends AsyncTask<List<ReciterEntity>, Void, Void> {
        @Override
        protected Void doInBackground(List<ReciterEntity>... reciters) {

            // convert API models to Room Entities
            List<ReciterEntity> roomReciters= new ArrayList<>();
            for(ReciterEntity reciter: reciters[0]) {
                List<MoshafEntity> apiMoshaf = reciter.getMoshaf();
                ReciterDatabase.getInstance(context).dao().insertMoshafs(apiMoshaf);
                ReciterDatabase.getInstance(context).dao().insertReciter(reciter);
            }
            return null;
        }
    }
}
