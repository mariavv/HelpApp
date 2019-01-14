package com.example.user.myapplication.ui.categories;

import android.support.annotation.DrawableRes;

import com.arellomobile.mvp.MvpView;
import com.example.user.myapplication.model.entity.Category;

import java.util.List;

interface CategoriesView extends MvpView {
    void updateFeed(List<Category> categories, @DrawableRes int[] defPictures);

    void showError(String message);

    void showItemsByCategory(String itemId);
}
