package com.example.user.myapplication.model.db.tables;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class EventRealmObject implements RealmModel {
    @PrimaryKey
    private String id;

    private String name;

    private String description;

    private String date;

    private String fullDescription;

    private String picture;

    private String categoryId;

    public EventRealmObject() {
        //required
    }

    public EventRealmObject(String id, String name, String description, String date, String fullDescription, String picture, String categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.fullDescription = fullDescription;
        this.picture = picture;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public String getPicture() {
        return picture;
    }

    public String getCategoryId() {
        return categoryId;
    }
}
