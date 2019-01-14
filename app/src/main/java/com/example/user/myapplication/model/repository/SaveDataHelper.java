package com.example.user.myapplication.model.repository;

import android.content.Context;
import android.preference.PreferenceManager;

import com.example.user.myapplication.model.Const;

class SaveDataHelper {
    static String getValue(String key, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, Const.EMPTY_STRING);
    }

    static void saveValue(String key, String token, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(key, token)
                .apply();
    }
}
