package com.example.user.myapplication.ui.categories;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.user.myapplication.R;
import com.example.user.myapplication.model.entity.Category;
import com.example.user.myapplication.ui.UiTools;
import com.example.user.myapplication.ui.events.EventsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoriesFragment extends MvpAppCompatFragment
        implements CategoriesView, FeedAdapter.OnItemClickListener {

    private Unbinder unbinder;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @InjectPresenter
    CategoriesPresenter presenter;

    private FeedAdapter adapter;

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        unbinder = ButterKnife.bind(this, view);

        configureRecyclerView();

        Activity activity = getActivity();
        if (activity != null) {
            activity.setTitle(getContext().getString(R.string.categories_fragment_label));
            presenter.onCreateView(getContext(), activity.getAssets());
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter = null;
    }

    @Override
    public void onItemClick(String itemId) {
        presenter.onItemClick(itemId);
    }

    @Override
    public void showError(String message) {
        UiTools.showError(message, getContext());
    }

    @Override
    public void showItemsByCategory(String itemId) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            UiTools.replaceFragment(EventsFragment.newInstance(itemId), activity.getSupportFragmentManager());
        }
    }

    @Override
    public void updateFeed(List<Category> categories, int[] imagesRes) {
        adapter.updateItems(categories, imagesRes);
    }

    private void configureRecyclerView() {
        adapter = new FeedAdapter(this);
        recycler.setAdapter(adapter);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
    }
}
