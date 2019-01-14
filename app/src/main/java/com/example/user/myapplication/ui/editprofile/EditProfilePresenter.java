package com.example.user.myapplication.ui.editprofile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.user.myapplication.R;
import com.example.user.myapplication.model.DateHelper;
import com.example.user.myapplication.model.entity.Profile;
import com.example.user.myapplication.model.repository.EditProfileRepo;

import java.io.File;
import java.util.Date;

@InjectViewState
public class EditProfilePresenter extends MvpPresenter<EditProfileView> implements EditProfileRepo.Listener {

    private static final int CAMERA_REQUEST = 0;
    private static final int TAKE_PICTURE_REQUEST = 1;

    private EditProfileRepo repo;
    private Date birthDate;
    private Uri outputFileUri;

    void onCreate(Context context, AssetManager assets) {
        getViewState().initCalendarDialog(DateHelper.getCurrentYear(), DateHelper.getCurrentMonth(), DateHelper.getCurrentDay());
        repo = new EditProfileRepo(context, this, assets);
        repo.loadProfile();
    }

    void onSaveMenuItemSelected(String lastName, String name, String birthDate, String profArea) {
        saveProfile(lastName, name, birthDate, profArea);
    }

    void onBirthDateChange(int year, int month, int dayOfMonth) {
        birthDate = repo.calcBirthDate(year, month, dayOfMonth);
        getViewState().showSelectedBirthDate(birthDate);
    }

    void onPassVisibilityClick(int inputType) {
        int inputTypeInvisible = InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT;
        if (inputType == inputTypeInvisible) {
            getViewState().changePassInputType(R.drawable.open, InputType.TYPE_CLASS_TEXT);
        } else {
            getViewState().changePassInputType(R.drawable.close, inputTypeInvisible);
        }
    }

    void onBirthDateChangeClick() {
        getViewState().showDatePickerDialog();
    }

    void onChangePhotoClick() {
        //getViewState().startActivityForResult(MediaStore.ACTION_IMAGE_CAPTURE, CAMERA_REQUEST);
        saveFullImage();
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == CAMERA_REQUEST) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap thumbnailBitmap = (Bitmap) bundle.get("data");
                getViewState().setPhoto(thumbnailBitmap);
            }
        } else if (requestCode == TAKE_PICTURE_REQUEST) {
            // Проверяем, содержит ли результат маленькую картинку
            if (data != null) {
                if (data.hasExtra("data")) {
                    Bitmap thumbnailBitmap = data.getParcelableExtra("data");
                    // Какие-то действия с миниатюрой
                    getViewState().setPhoto(thumbnailBitmap);
                }
            } else {
                // Какие-то действия с полноценным изображением,
                // сохраненным по адресу outputFileUri
                getViewState().setPhoto(outputFileUri);
            }
        }
    }

    @Override
    public void onLoadProfile(Profile p) {
        birthDate = p.getBirthDate();
        getViewState().showProfileInfo(p);
    }

    private void saveProfile(String lastName, String name, String birthDate, String profArea) {
        repo.saveProfile("http://ipic.su/img/img7/fs/image_man.1544603908.png", lastName, name, this.birthDate, profArea);
    }

    private void saveFullImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(),
                "HelpApp_ProfilePhoto.jpg");
        outputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        getViewState().startActivityForResult_(intent, TAKE_PICTURE_REQUEST);
    }
}
