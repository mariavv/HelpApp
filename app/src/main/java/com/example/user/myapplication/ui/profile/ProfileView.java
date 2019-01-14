package com.example.user.myapplication.ui.profile;

import com.arellomobile.mvp.MvpView;
import com.example.user.myapplication.model.entity.Profile;

interface ProfileView extends MvpView {
    void showProfileInfo(Profile p);
}
