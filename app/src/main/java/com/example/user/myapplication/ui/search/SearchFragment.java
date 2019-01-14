package com.example.user.myapplication.ui.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.myapplication.R;

public class SearchFragment extends Fragment {

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setTitle("Поиск");
            ViewPager viewPager = view.findViewById(R.id.viewPager);
            final android.support.v4.view.PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), activity);
            viewPager.setAdapter(pagerAdapter);
        }

        return view;
    }
}
