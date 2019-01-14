package com.example.user.myapplication.ui.search;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.myapplication.R;
import com.example.user.myapplication.ui.search.eventsearch.EventResultsFragment;
import com.example.user.myapplication.ui.search.nkosearch.NkoResultsFragment;

class PagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 2;
    private static final int PAGE_EVENTS = 0;
    private static final int PAGE_NKO = 1;

    private Context context;

    PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PAGE_EVENTS:
                return EventResultsFragment.newInstance();
            case PAGE_NKO:
                return NkoResultsFragment.newInstance();
        }
        return EventResultsFragment.newInstance();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle = "";
        switch (position) {
            case PAGE_EVENTS:
                pageTitle = context.getString(R.string.by_events);
                break;
            case PAGE_NKO:
                pageTitle = context.getString(R.string.by_nko);
                break;
        }
        return pageTitle;
    }
}
