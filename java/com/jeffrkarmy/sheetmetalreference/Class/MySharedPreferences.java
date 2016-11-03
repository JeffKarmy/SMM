package com.jeffrkarmy.sheetmetalreference.Class;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jrkar on 9/5/2016.
 */
public class MySharedPreferences {

    public float getFloatPreference(Context context, String fileName, String preferenceName) {
        SharedPreferences pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        float value = pref.getFloat(preferenceName, 0);
        return value;
    }

    public void setFloatPreferences(Context context, String fileName, String preferenceName, float value) {
        SharedPreferences pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(preferenceName, value);
        editor.apply();
    }

    public void setBooleanPreference(Context context, String fileName, String preferenceName, boolean value) {
        SharedPreferences pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(preferenceName, value);
        editor.apply();
    }

    public boolean getBooleanPreference(Context context, String fileName, String preferenceName) {
        SharedPreferences pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        boolean value = pref.getBoolean(preferenceName, true);
        return value;
    }


}
