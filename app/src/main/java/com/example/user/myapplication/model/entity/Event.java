package com.example.user.myapplication.model.entity;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

import io.realm.annotations.PrimaryKey;

public class Event {

    @SerializedName("base_id")
    @PrimaryKey
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("date")
    private String date;
    @SerializedName("full_description")
    private String fullDescription;
    @SerializedName("file")
    private Image picture;
    @SerializedName("category_id")
    private String categoryId;

    public Event(String id, String name, String description, String date, String fullDescription, String picture, String categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.fullDescription = fullDescription;
        this.picture = new Image(picture);
        this.categoryId = categoryId;
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
    public String getDescription() {
        return description;
    }

    @Nullable
    public String getDate() {
        return date;
    }

    @Nullable
    public String getFullDescription() {
        return fullDescription;
    }

    @Nullable
    public String getPicture() {
        return picture != null ? picture.getPath() : null;
    }

    @Nullable
    public String getCategoryId() {
        return categoryId;
    }
}
