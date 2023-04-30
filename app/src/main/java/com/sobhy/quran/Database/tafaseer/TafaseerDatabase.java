package com.sobhy.quran.Database.tafaseer;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.sobhy.quran.pojo.tafaseer.Baghawy;
import com.sobhy.quran.pojo.tafaseer.E3rab;
import com.sobhy.quran.pojo.tafaseer.Kather;
import com.sobhy.quran.pojo.tafaseer.Ma3any;
import com.sobhy.quran.pojo.tafaseer.Muyassar;
import com.sobhy.quran.pojo.tafaseer.Qortoby;
import com.sobhy.quran.pojo.tafaseer.Sa3dy;
import com.sobhy.quran.pojo.tafaseer.Tabary;
import com.sobhy.quran.pojo.tafaseer.TafseerName;
import com.sobhy.quran.pojo.tafaseer.Tanweer;
import com.sobhy.quran.pojo.tafaseer.Waseet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipInputStream;

@Database(entities = {Baghawy.class, E3rab.class, Kather.class, Ma3any.class,
        Muyassar.class, Qortoby.class, Sa3dy.class, Tabary.class, TafseerName.class,
        Tanweer.class, Waseet.class}, version = 1)
public abstract class TafaseerDatabase extends RoomDatabase {
    public abstract TafaseerDao tafaseerDao();
    private static TafaseerDatabase INSTANCE;
    public static synchronized TafaseerDatabase getInstance(Context context){
        if(INSTANCE== null){
            INSTANCE= buildDatabase(context);
        }
        return INSTANCE;
    }

    private static TafaseerDatabase buildDatabase(Context context) {
        // Copy compressed database file from assets to local storage
        try {
            InputStream inputStream = context.getAssets().open("databases/tafaseer.zip");
            File dbFile = context.getDatabasePath("tafaseer_database.db");
            OutputStream outputStream = new FileOutputStream(dbFile);
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            zipInputStream.getNextEntry();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = zipInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            zipInputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy database", e);
        }

        // Open uncompressed database file with Room
        return Room.databaseBuilder(context, TafaseerDatabase.class, "tafaseer_database.db")
                .allowMainThreadQueries()
                .build();
    }
}
