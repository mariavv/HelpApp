package com.example.user.myapplication.model.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.StringRes;

import com.example.user.myapplication.R;
import com.example.user.myapplication.model.db.DbProvider;
import com.example.user.myapplication.model.entity.Category;
import com.example.user.myapplication.model.remote.BaseResponse;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoriesRepo extends Repo implements AuthUtil.Listener {

    private static final String CATEGORIES_FILE_NAME = "categories.json";

    private Listener listener;
    private DbProvider db;
    private AssetManager assetManager;

    public interface Listener {
        void onError(String message);

        void onLoadCategories(List<Category> categories, int[] defPictures);

        void onError(@StringRes int messageRes);
    }

    public CategoriesRepo(Context context, Listener listener, AssetManager assetManager) {
        super(context);
        this.listener = listener;
        db = new DbProvider();
        this.assetManager = assetManager;
    }

    @Override
    public void onSetToken() {
        loadFromRemote();
    }

    @Override
    public void onAuthError(String message) {
        listener.onError(message);
    }

    @Override
    public void onAuthError(int messageRes) {
        listener.onError(messageRes);
    }

    public void loadCategories() {
        List<Category> categories = db.getCategories();
        if (!categories.isEmpty()) {
            listener.onLoadCategories(categories, new int[]{});
        } else {
            if (false) {
                // TODO потоки
                loadFromAssets();
            } else {
                loadFromRemote();
            }
        }
    }

    @SuppressLint("CheckResult")
    private void loadFromRemote() {
        getCategories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadCategories, throwable -> checkAuth(throwable, this));
    }

    private Observable<List<Category>> getCategories() {
        return dataRestService().getCategories().map(BaseResponse::getData);
    }

    private void onLoadCategories(List<Category> categories) {
        db.saveCategories(categories);
        listener.onLoadCategories(db.getCategories(), new int[]{});
    }

    private void loadFromAssets() {
        if (assetManager == null) {
            listener.onError(R.string.resourses_unavailable);
        }
        List<Category> categories = getCategories(assetManager);
        listener.onLoadCategories(categories,
                new int[]{R.drawable.icon_kids, R.drawable.icon_adult, R.drawable.icon_elderly,
                        R.drawable.icon_animals, R.drawable.icon_event});
    }

    private List<Category> getCategories(final AssetManager assetManager) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Category>> callable = new CategoryListCallable(assetManager);
        Future<List<Category>> future = executor.submit(callable);

        try {
            List<Category> categories = future.get();
            db.saveCategories(categories);
            return db.getCategories();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            executor.shutdown();
            return null;
        }

        /*Callable task = new Callable() {
            @Override
            public List<Category> call() {
                InputStream inputStream = openAssert(assetManager, Repo.CATEGORIES_FILE_NAME);
                if (inputStream != null) {
                    return gson().fromJson(loadJSONFromAsset(inputStream), new TypeToken<List<Category>>() {
                    }.getType());
                    inputStream.close();
                }
                return null;
            }
        };

        try {
            return (List<Category>) task.call();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/
    }

    private class CategoryListCallable implements Callable<List<Category>> {
        private AssetManager assetManager;

        CategoryListCallable(AssetManager assetManager) {
            this.assetManager = assetManager;
        }

        @Override
        public List<Category> call() {
            /*try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            InputStream inputStream = openAssert(assetManager, CATEGORIES_FILE_NAME);
            if (inputStream != null) {
                return gson().fromJson(loadJSONFromAsset(inputStream), new TypeToken<List<Category>>() {
                }.getType());
            }
            return null;
        }
    }
}
