package com.example.user.myapplication.model.entity;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("access_token")
    private String accessToken;

    @Nullable
    public String getTokenType() {
        return tokenType;
    }

    @Nullable
    public String getAccessToken() {
        return accessToken;
    }
}


