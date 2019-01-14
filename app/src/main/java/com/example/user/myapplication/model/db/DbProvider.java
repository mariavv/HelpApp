package com.example.user.myapplication.model.db;

import android.support.annotation.NonNull;

import com.example.user.myapplication.model.db.tables.CategoryRealmObject;
import com.example.user.myapplication.model.db.tables.EventRealmObject;
import com.example.user.myapplication.model.db.tables.ProfileRealmObject;
import com.example.user.myapplication.model.entity.Category;
import com.example.user.myapplication.model.entity.Event;
import com.example.user.myapplication.model.entity.Profile;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DbProvider {
    private static final String CATEGORY_ID_PARAM = "categoryId";

    private Realm db;

    public DbProvider() {
        config();
    }

    public void saveCategories(List<Category> categories) {
        db.executeTransaction(realm -> {
            db.delete(CategoryRealmObject.class);
            for (Category c : categories) {
                db.copyToRealmOrUpdate(new CategoryRealmObject(c.getId(), c.getName(), c.getPicture()));
            }
        });
        saveEvents(new ArrayList<>());
    }

    @NonNull
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        RealmResults<CategoryRealmObject> results = db.where(CategoryRealmObject.class).findAll();
        for (CategoryRealmObject ct : results) {
            categories.add(new Category(ct.getId(), ct.getName(), ct.getPicture()));
        }
        return categories;
    }

    @NonNull
    public List<Event> getEvents(String categoryId) {
        List<Event> events = new ArrayList<>();
        RealmResults<EventRealmObject> results = db.where(EventRealmObject.class)
                .equalTo(CATEGORY_ID_PARAM, categoryId)
                .findAll();
        for (EventRealmObject o : results) {
            events.add(new Event(o.getId(), o.getName(), o.getDescription(),
                    o.getDate(), o.getFullDescription(), o.getPicture(), o.getCategoryId()));
        }
        return events;
    }

    public void saveEvents(List<Event> events) {
        db.executeTransaction(realm -> {
            db.delete(EventRealmObject.class);

            for (Event event : events) {
                db.copyToRealmOrUpdate(new EventRealmObject(event.getId(), event.getName(),
                        event.getDescription(), event.getDate(), event.getFullDescription(),
                        event.getPicture(), event.getCategoryId()));
            }
        });
    }

    public void saveProfile(Profile profile) {
        db.executeTransaction(realm -> {
            db.delete(ProfileRealmObject.class);
            db.copyToRealmOrUpdate(new ProfileRealmObject(profile.getPhoto(), profile.getLastName(),
                    profile.getName(), profile.getBirthDate(), profile.getCompetenceArea()));
        });
    }

    public Profile getProfile() {
        ProfileRealmObject result = db.where(ProfileRealmObject.class).findFirst();
        if (result != null) {
            return new Profile(result.getPhoto(), result.getLastName(), result.getName(), result.getBirthDate(), result.getCompetenceArea());
        } else {
            return new Profile();
        }
    }

    protected void finalize() {
        db.close();
    }

    private void config() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        db = Realm.getInstance(config);
    }
}
