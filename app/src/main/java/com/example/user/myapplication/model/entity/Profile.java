package com.example.user.myapplication.model.entity;

import java.util.Date;
import java.util.List;

public class Profile {
    private String photo;
    private String lastName;
    private String name;
    private Date birthDate;
    private String competenceArea;
    private boolean getNotifications;
    private List<Friend> friends;

    public Profile(String photo, String lastName, String name, Date birthDate, String competenceArea, boolean getNotifications, List<Friend> friends) {
        this.photo = photo;
        this.lastName = lastName;
        this.name = name;
        this.birthDate = birthDate;
        this.competenceArea = competenceArea;
        this.getNotifications = getNotifications;
        this.friends = friends;
    }

    public Profile(String photo, String lastName, String name, Date birthDate, String competenceArea) {
        this.photo = photo;
        this.lastName = lastName;
        this.name = name;
        this.birthDate = birthDate;
        this.competenceArea = competenceArea;
    }

    public Profile() {
        this.photo = "";
        this.lastName = "";
        this.name = "";
        this.birthDate = new Date();
        this.competenceArea = "";
    }

    public void setGetNotifications(boolean getNotifications) {
        this.getNotifications = getNotifications;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public String getPhoto() {
        return photo;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getCompetenceArea() {
        return competenceArea;
    }

    public boolean isGetNotifications() {
        return getNotifications;
    }

    public List<Friend> getFriends() {
        return friends;
    }
}
