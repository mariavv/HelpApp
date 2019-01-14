package com.example.user.myapplication.model.db.tables;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class ProfileRealmObject implements RealmModel {

    private String photo;
    private String lastName;
    @PrimaryKey
    private String name;
    private Date birthDate;
    private String competenceArea;

    public ProfileRealmObject() {
        //required
    }

    public ProfileRealmObject(String photo, String lastName, String name, Date birthDate, String competenceArea) {
        this.photo = photo;
        this.lastName = lastName;
        this.name = name;
        this.birthDate = birthDate;
        this.competenceArea = competenceArea;
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
}
