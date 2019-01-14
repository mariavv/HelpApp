package com.example.user.myapplication.model.entity;

public class Friend {
    String name;
    String photo;

    public Friend(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }
}
