package com.sobhy.quran.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sobhy.quran.pojo.quran.Ayah;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipInputStream;

@Database(entities = {Ayah.class}, version = 1)
public abstract class QuranDatabase extends RoomDatabase {
    public abstract QuranDao quranDao();

    private static QuranDatabase INSTANCE;

    public static synchronized QuranDatabase getINSTANCE(Context context) {
        if(INSTANCE == null){
            INSTANCE= buildDatabase(context);
        }
        return INSTANCE;
    }

    private static QuranDatabase buildDatabase(Context context) {
        // Copy compressed database file from assets to local storage
        try {
            InputStream inputStream = context.getAssets().open("databases/quran.zip");
            File dbFile = context.getDatabasePath("quran_database.db");
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
        return Room.databaseBuilder(context, QuranDatabase.class, "quran_database.db")
                .allowMainThreadQueries()
                .build();
    }
}
