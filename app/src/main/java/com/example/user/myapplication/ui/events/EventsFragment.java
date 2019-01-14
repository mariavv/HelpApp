package com.example.user.myapplication.ui.events;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.user.myapplication.R;
import com.example.user.myapplication.model.entity.Event;
import com.example.user.myapplication.model.repository.EventsRepo;
import com.example.user.myapplication.ui.UiTools;
import com.example.user.myapplication.ui.event.EventActivity;
import com.example.user.myapplication.ui.navigate.IView;

import java.util.List;

public class EventsFragment extends Fragment implements FeedAdapter.OnItemClickListener, EventsRepo.Listener {

    private static final String ARG_ITEM_ID = "item_id";
    IView iView;
    private FragmentActivity activity;
    private ProgressBar progressBar;
    private RecyclerView recycler;
    private FeedAdapter adapter;
    private Handler handler;
    private String categoryId;

    private List<Event> events;

    private EventsRepo eventsRepo;

    public static EventsFragment newInstance(String categoryId) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getString(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        recycler = view.findViewById(R.id.recycler);
        configureRecyclerView();
        //progressBar = view.findViewById(R.id.progressBar);
        iView = (IView) getActivity();
        handler = new Handler();

        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle("События");
            initEventsRepo(activity.getAssets());
        } else {
            initEventsRepo(null);
        }

        loadEvents();

        return view;
    }

    @Override
    public void onItemClick(String itemId) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.startActivity(EventActivity.start(getContext(), categoryId, itemId));
        }
    }

    @Override
    public void onError(String message) {
        UiTools.showError(message, getContext());
    }

    @Override
    public void onError(@StringRes int messageRes) {
        UiTools.showError(messageRes, getContext());
    }

    @Override
    public void onLoadEvents(List<Event> events, int[] defPictures) {
        updateFeed(events, defPictures);
    }

    private void configureRecyclerView() {
        adapter = new FeedAdapter(this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initEventsRepo(AssetManager assets) {
        eventsRepo = new EventsRepo(categoryId, getContext(), this, assets);
    }

    private void loadEvents() {
        eventsRepo.loadEvents();
    }

    private void updateFeed(List<Event> events, int[] imagesRes) {
        adapter.updateItems(events, imagesRes);
    }

    //todo
    private void loadFromAssets() {
        activity = getActivity();
        if (activity != null) {
            handler.post(showInfo);

            final Runnable runn1 = () -> iView.showProgressBar();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Thread t = new Thread(() -> {
                progressBar.post(runn1);
                events = eventsRepo.getEvents(activity.getAssets(), categoryId);
            });
            t.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadEvents(List<Event> events) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        updateFeed(events, new int[]{R.drawable.img, R.drawable.img_2});
        //FragmentActivity activity = getActivity();
        //iView = (IView) getActivity();
        hideProgressBar();
    }

    private void hideProgressBar() {
        if (iView != null) {
            //if (activity != null) {
            //((NavigateActivity) activity).hideProgress();
            iView.hideProgressBar();
        }
    }

    private Runnable showInfo = new Runnable() {
        private static final int HANDLER_DELAY = 500;

        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hideProgressBar();
            loadEvents(events);
            handler.postDelayed(showInfo, HANDLER_DELAY);
        }
    };
}


