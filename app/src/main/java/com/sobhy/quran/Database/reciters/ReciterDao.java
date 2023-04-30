package com.sobhy.quran.Database.reciters;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sobhy.quran.pojo.quran.MoshafEntity;
import com.sobhy.quran.pojo.quran.ReciterEntity;

import java.util.List;

@Dao
public interface ReciterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMoshaf(MoshafEntity moshaf);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMoshafs(List<MoshafEntity> moshafEntityList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReciter(ReciterEntity... reciter);


//    @Query("select moshaf from reciter where id= :reciterId")
//    List<MoshafEntity> getAllMoshaf(int reciterId);

    @Query("select * from reciter")
    List<ReciterEntity> getAllReciter();

    @Query("select * from reciter where id= :reciterId")
    ReciterEntity getReciterById(int reciterId);
}
