package com.sobhy.quran.Database.reciters;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.sobhy.quran.Database.Converters;
import com.sobhy.quran.pojo.quran.MoshafEntity;
import com.sobhy.quran.pojo.quran.ReciterEntity;

@androidx.room.Database(entities = {MoshafEntity.class, ReciterEntity.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class ReciterDatabase extends RoomDatabase {
    public abstract ReciterDao dao();

    private static ReciterDatabase INSTANCE;
    public static synchronized ReciterDatabase getInstance(Context context){
        if(INSTANCE== null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(), ReciterDatabase.class, "reciter_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}