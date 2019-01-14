package com.example.user.myapplication.model.remote;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @SerializedName("data")
    protected T data;

    public T getData() {
        return data;
    }
}
