package com.example.user.myapplication.ui.profile;

import android.content.Context;
import android.content.res.AssetManager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.user.myapplication.model.entity.Profile;
import com.example.user.myapplication.model.repository.ProfileRepo;

@InjectViewState
public class ProfilePresenter extends MvpPresenter<ProfileView> implements ProfileRepo.Listener {

    private ProfileRepo profileRepo;

    void onCreateView(Context context, AssetManager assets) {
        profileRepo = new ProfileRepo(context, this, assets);
    }

    @Override
    public void onLoadProfile(Profile p) {
        getViewState().showProfileInfo(p);
    }

    void onResume() {
        profileRepo.loadProfile();
    }
}
