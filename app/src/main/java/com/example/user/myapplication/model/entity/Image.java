package com.example.user.myapplication.model.entity;

import android.support.annotation.Nullable;

import static com.example.user.myapplication.model.StringUtil.nonNull;

class Image {

    private String path;

    Image(String path) {
        this.path = nonNull(path);
    }

    @Nullable
    String getPath() {
        return path;
    }
}
