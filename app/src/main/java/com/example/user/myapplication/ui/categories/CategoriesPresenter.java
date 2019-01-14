package com.example.user.myapplication.ui.categories;

import android.content.Context;
import android.content.res.AssetManager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.user.myapplication.model.entity.Category;
import com.example.user.myapplication.model.repository.CategoriesRepo;

import java.util.List;

@InjectViewState
public class CategoriesPresenter extends MvpPresenter<CategoriesView> implements CategoriesRepo.Listener {

    private Context context;

    private CategoriesRepo categoriesRepo;

    void onCreateView(Context context, AssetManager assets) {
        categoriesRepo = new CategoriesRepo(context, this, assets);
        this.context = context;
        loadCategories();
    }

    void onItemClick(String itemId) {
        getViewState().showItemsByCategory(itemId);
    }

    @Override
    public void onLoadCategories(List<Category> categories, int[] defPictures) {
        getViewState().updateFeed(categories, defPictures);
    }

    @Override
    public void onError(String message) {
        getViewState().showError(message);
    }

    @Override
    public void onError(int messageRes) {
        getViewState().showError(context.getString(messageRes));
    }

    private void loadCategories() {
        categoriesRepo.loadCategories();
    }
}
