package com.example.user.myapplication.model.entity;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

import io.realm.annotations.PrimaryKey;

public class Category {

    @SerializedName("base_id")
    @PrimaryKey
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("file")
    private Image picture;

    public Category(String id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = new Image(picture);
    }

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getPicture() {
        return picture != null ? picture.getPath() : null;
    }
}
