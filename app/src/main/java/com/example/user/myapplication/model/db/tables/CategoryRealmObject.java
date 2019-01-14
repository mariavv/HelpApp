package com.example.user.myapplication.model.db.tables;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class CategoryRealmObject implements RealmModel {

    @PrimaryKey
    private String id;

    private String name;

    private String picture;

    public CategoryRealmObject() {
        //required
    }

    public CategoryRealmObject(String id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }
}
