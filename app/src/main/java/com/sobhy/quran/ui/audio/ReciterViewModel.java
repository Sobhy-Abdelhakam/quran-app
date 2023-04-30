package com.sobhy.quran.ui.audio;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sobhy.quran.Database.ReciterRepository;
import com.sobhy.quran.pojo.quran.ReciterEntity;

import java.util.List;

public class ReciterViewModel extends ViewModel {
    public LiveData<List<ReciterEntity>> getRecitersFromApi(Context context) {
        return new ReciterRepository(context).getReciterFromApi();
    }

    public static ReciterEntity getReciterFromApi(int reciterId) {
        return ReciterRepository.getReciterByIdFromApi(reciterId);
    }

    public List<ReciterEntity> getRecitersFromDb(Context context) {
        return new ReciterRepository(context).getRecitersFromDb();
    }

    public static ReciterEntity getReciterFromDb(Context context, int reciterId) {
        return new ReciterRepository(context).getReciterByIdFromDb(reciterId);
    }
}
