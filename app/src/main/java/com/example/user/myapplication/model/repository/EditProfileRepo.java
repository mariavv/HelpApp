package com.example.user.myapplication.model.repository;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.user.myapplication.model.db.DbProvider;
import com.example.user.myapplication.model.entity.Profile;

import java.util.Date;
import java.util.GregorianCalendar;

public class EditProfileRepo extends ProfileRepo {

    private DbProvider db;

    public EditProfileRepo(Context context, Listener listener, AssetManager assetManager) {
        super(context, listener, assetManager);
        db = new DbProvider();
    }

    public void saveProfile(String photoPath, String lastName, String name, Date birthDate, String profArea) {
        db.saveProfile(new Profile(photoPath, lastName, name, birthDate, profArea));
    }

    public Date calcBirthDate(int year, int month, int dayOfMonth) {
        GregorianCalendar calendar = new GregorianCalendar(year,
                month, dayOfMonth);
        Date date = new Date(calendar.getTimeInMillis());
        return date;
    }
}
