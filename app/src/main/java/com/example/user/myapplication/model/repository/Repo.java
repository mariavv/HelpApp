package com.example.user.myapplication.model.repository;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.user.myapplication.model.remote.RestService;
import com.example.user.myapplication.model.remote.RestServiceProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

import static com.example.user.myapplication.model.Const.ENCODING;

class Repo {

    private AuthUtil auth;

    Repo(Context context) {
        auth = new AuthUtil(context);
    }

    RestService dataRestService() {
        return RestServiceProvider.getInstance(RestServiceProvider.Target.TABLES).getRestService();
    }

    Gson gson() {
        return new GsonBuilder().create();
    }

    InputStream openAssert(AssetManager assetManager, String fileName) {
        try {
            return assetManager.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    String loadJSONFromAsset(InputStream inputStream) {
        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, ENCODING);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    void checkAuth(Throwable throwable, AuthUtil.Listener listener) {
        auth.checkAuth(throwable, listener);
    }
}
