package com.example.user.myapplication.ui.search.eventsearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;


import com.example.user.myapplication.R;

public class EventResultsFragment extends Fragment {

    View view;

    public static EventResultsFragment newInstance() {
        return new EventResultsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_results, container, false);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (view != null && isVisibleToUser) {
            TextView resultTv = view.findViewById(R.id.resultTv);
            Random rnd = new Random(System.currentTimeMillis());
            resultTv.setText(String.valueOf(rnd.nextInt(9)));
        }
    }
}
