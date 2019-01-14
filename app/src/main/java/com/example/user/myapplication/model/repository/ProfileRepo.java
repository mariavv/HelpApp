package com.example.user.myapplication.model.repository;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.user.myapplication.model.db.DbProvider;
import com.example.user.myapplication.model.entity.Friend;
import com.example.user.myapplication.model.entity.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileRepo extends Repo {
    private Listener listener;
    private DbProvider db;
    private AssetManager assetManager;

    public interface Listener {
        void onLoadProfile(Profile p);
    }

    public ProfileRepo(Context context, Listener listener, AssetManager assetManager) {
        super(context);
        this.listener = listener;
        db = new DbProvider();
        this.assetManager = assetManager;
    }

    public void loadProfile() {
        Profile p = db.getProfile();

        List<Friend> friends = new ArrayList<>();
        friends.add(new Friend("Дмитрий Валерьевич", "http://ipic.su/img/img7/fs/avatar_1.1544608127.png"));
        friends.add(new Friend("Евгений Александров", "http://ipic.su/img/img7/fs/avatar_2.1544608194.png"));
        friends.add(new Friend("Виктор Кузнецов", "http://ipic.su/img/img7/fs/avatar_3.1544608210.png"));
        friends.add(new Friend("Виктор Кузнецов2", "http://ipic.su/img/img7/fs/avatar_1.1544608127.png"));
        friends.add(new Friend("Виктор Кузнецов23", "http://ipic.su/img/img7/fs/icon_kids.1544608244.jpg"));
        p.setFriends(friends);

        p.setGetNotifications(true);

        listener.onLoadProfile(p);
    }
}
