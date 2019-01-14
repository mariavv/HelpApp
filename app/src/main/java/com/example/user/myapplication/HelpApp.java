package com.example.user.myapplication;

import android.app.Application;

import com.example.user.myapplication.model.repository.AuthUtil;

import io.realm.Realm;

public class HelpApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        new AuthUtil(this).loadToken(this);
    }
}
