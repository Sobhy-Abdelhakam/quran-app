package com.sobhy.quran.Database.shamarly;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.sobhy.quran.pojo.quran.ShamarlyAyah;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipInputStream;

@Database(entities = {ShamarlyAyah.class}, version = 1)
public abstract class ShamarlyDatabase extends RoomDatabase {
    public abstract ShamarlyDao shamarlyDao();
    private static ShamarlyDatabase INSTANCE;


    public static ShamarlyDatabase getInstance(Context context) {
        if (INSTANCE == null){
            INSTANCE= buildDatabase(context);
        }
        return INSTANCE;
    }

    private static ShamarlyDatabase buildDatabase(Context context) {
        // Copy compressed database file from assets to local storage
        try {
            InputStream inputStream = context.getAssets().open("databases/shamarly.zip");
            File dbFile = context.getDatabasePath("shamarly_database.db");
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
        return Room.databaseBuilder(context, ShamarlyDatabase.class, "shamarly_database.db")
                .allowMainThreadQueries()
                .build();
    }

}
