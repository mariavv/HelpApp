package com.example.user.myapplication.model.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.Nullable;

import com.example.user.myapplication.R;
import com.example.user.myapplication.model.db.DbProvider;
import com.example.user.myapplication.model.entity.Event;
import com.example.user.myapplication.model.remote.BaseResponse;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EventsRepo extends Repo implements AuthUtil.Listener {

    private static final String EVENTS_FILE_NAME = "events.json";

    private Listener listener;
    private DbProvider db;
    private AssetManager assetManager;
    private String categoryId;

    public interface Listener {
        void onError(String message);

        void onLoadEvents(List<Event> events, int[] defPictures);

        void onError(int messageRes);
    }

    public EventsRepo(String categoryId, Context context, @Nullable AssetManager assetManager) {
        super(context);
        this.categoryId = categoryId;
        db = new DbProvider();
        this.assetManager = assetManager;
    }

    public EventsRepo(String categoryId, Context context, Listener listener, @Nullable AssetManager assetManager) {
        super(context);
        this.categoryId = categoryId;
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

    public void loadEvents() {
        List<Event> events = db.getEvents(categoryId);
        if (!events.isEmpty()) {
            listener.onLoadEvents(events, new int[]{});
        } else {
            if (false) {
                // TODO потоки
                loadFromAssets();
            } else {
                loadFromRemote();
            }
        }
    }

    public Event getEvent(String eventId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Event> callable = new EventCallable(assetManager, categoryId, eventId);
        Future<Event> future = executor.submit(callable);

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            executor.shutdown();
            return null;
        }
    }

    @SuppressLint("CheckResult")
    private void loadFromRemote() {
        getEvents()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadEvents, throwable -> checkAuth(throwable, this));
    }

    private Observable<List<Event>> getEvents() {
        return dataRestService().getEvents().map(BaseResponse::getData);
    }

    private void onLoadEvents(List<Event> events) {
        db.saveEvents(events);
        listener.onLoadEvents(db.getEvents(categoryId), new int[]{});
    }

    private void loadFromAssets() {
        if (assetManager == null) {
            listener.onError(R.string.resourses_unavailable);
        }
        List<Event> events = getEvents(assetManager, categoryId);
        listener.onLoadEvents(events,
                new int[]{R.drawable.img, R.drawable.img_2});
    }

    public List<Event> getEvents(final AssetManager assetManager, final String categoryId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Event>> callable = new EventListCallable(assetManager, categoryId);
        Future<List<Event>> future = executor.submit(callable);

        try {
            List<Event> events = future.get();
            db.saveEvents(events);
            return db.getEvents(categoryId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            executor.shutdown();
            return null;
        }

        /*Callable task = new Callable() {
            @Override
            public List<Event> call() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                InputStream inputStream = openAssert(assetManager, Repo.EVENTS_FILE_NAME);
                if (inputStream != null) {
                    return gson().fromJson(loadJSONFromAsset(inputStream), new TypeToken<List<Event>>() {
                    }.getType());
                }
                return null;
            }
        };

        try {
            return (List<Event>) task.call();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/
    }

    private List<Event> getEventsCollection(AssetManager assetManager) {
        InputStream inputStream = openAssert(assetManager, EVENTS_FILE_NAME);
        if (inputStream != null) {
            return gson().fromJson(loadJSONFromAsset(inputStream), new TypeToken<List<Event>>() {
            }.getType());
        }
        return null;
    }

    private class EventListCallable implements Callable<List<Event>> {
        private AssetManager assetManager;
        private String categoryId;

        EventListCallable(AssetManager assetManager, String categoryId) {
            this.assetManager = assetManager;
            this.categoryId = categoryId;
        }

        @Override
        public List<Event> call() {
            /*try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            /*try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            List<Event> eventsByCategory = new ArrayList<>();
            List<Event> eventsAll = getEventsCollection(assetManager);
            if (eventsAll != null) {
                for (Event e : eventsAll) {
                    if (Objects.equals(e.getCategoryId(), categoryId)) {
                        eventsByCategory.add(e);
                    }
                }
            }

            /*InputStream inputStream = openAssert(assetManager, EVENTS_FILE_NAME);
            if (inputStream != null) {
                List<Event> eventsAll = gson().fromJson(loadJSONFromAsset(inputStream), new TypeToken<List<Event>>() {
                }.getType());

                if (eventsAll != null) {
                    for (Event e : eventsAll) {
                        if (Objects.equals(e.getCategoryId(), categoryId)) {
                            eventsByCategory.add(e);
                        }
                    }
                }

                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/

            return eventsByCategory;
        }
    }

    private class EventCallable implements Callable<Event> {
        private AssetManager assetManager;
        private String categoryId;
        private String eventId;

        EventCallable(AssetManager assetManager, String categoryId, String eventId) {
            this.assetManager = assetManager;
            this.categoryId = categoryId;
            this.eventId = eventId;
        }

        @Override
        public Event call() {
            /*try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            List<Event> events = getEventsCollection(assetManager);
            if (events != null) {
                for (Event e : events) {
                    if (Objects.equals(e.getCategoryId(), categoryId) && Objects.equals(e.getId(), eventId)) {
                        return e;
                    }
                }
            }
            return null;
        }
    }
}
