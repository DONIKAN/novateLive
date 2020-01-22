package com.application.novateliveproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CommonMethods {
    private static SharedPreferences data;

    public static String getPrefData(String ke, Context ct) {
        data = PreferenceManager.getDefaultSharedPreferences(ct);
        return data.getString(ke, "");
    }

    public static void setPrefData(String key, String vallue, Context context) {
        data = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = data.edit();
        editor.putString(key, vallue);
        editor.commit();
    }

    public static void clearPrefData(Context context) {
        data = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = data.edit();
        editor.clear();
        editor.apply();
    }
    public static void setPrefData(String key2, ArrayList<String> list, Context context) {
        data = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = data.edit();
        Gson gson = new Gson();
        String listData = gson.toJson(list);
        editor.putString(key2, listData);
        editor.commit();
    }

}
