package com.example.user.myapplication.ui.editprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.arellomobile.mvp.MvpView;
import com.example.user.myapplication.model.entity.Profile;

import java.util.Date;

public interface EditProfileView extends MvpView {
    void showSelectedBirthDate(Date date);

    void showProfileInfo(Profile p);

    void initCalendarDialog(int year, int month, int day);

    void changePassInputType(int drawableRes, int inputType);

    void showDatePickerDialog();

    void startActivityForResult(String actionImageCapture, int cameraRequest);

    void startActivityForResult_(Intent intent, int cameraRequest);

    void setPhoto(Bitmap photoBitmap);

    void setPhoto(Uri outputFileUri);
}
