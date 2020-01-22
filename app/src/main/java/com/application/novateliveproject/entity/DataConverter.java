package com.application.novateliveproject.entity;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.application.novateliveproject.model.FrameData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataConverter {
    @TypeConverter
    public static List<FrameData> fromString(String value) {
        Type listType = new TypeToken<List<FrameData>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayLisr(List<FrameData> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}
