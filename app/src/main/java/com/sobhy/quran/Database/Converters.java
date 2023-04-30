package com.sobhy.quran.Database;

import androidx.room.TypeConverter;

import com.sobhy.quran.pojo.quran.MoshafEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<MoshafEntity> fromString(String value) {
        Type listType = new TypeToken<List<MoshafEntity>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<MoshafEntity> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}