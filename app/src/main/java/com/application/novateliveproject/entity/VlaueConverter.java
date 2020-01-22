package com.application.novateliveproject.entity;

import androidx.room.TypeConverter;

import com.application.novateliveproject.model.FrameValue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class VlaueConverter {
    @TypeConverter
    public static ArrayList<FrameValue> fromString(String value) {
        Type listType = new TypeToken<ArrayList<FrameValue>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayLisr(ArrayList<FrameValue> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}
