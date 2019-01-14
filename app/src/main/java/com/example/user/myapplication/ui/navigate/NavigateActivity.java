package com.example.user.myapplication.ui.navigate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.example.user.myapplication.R;
import com.example.user.myapplication.ui.UiTools;
import com.example.user.myapplication.ui.categories.CategoriesFragment;
import com.example.user.myapplication.ui.profile.ProfileFragment;
import com.example.user.myapplication.ui.search.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigateActivity extends AppCompatActivity implements IView {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        ButterKnife.bind(this);

        showDefoultFragment();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.categoriesIv, R.id.profileTv, R.id.searchTv})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.categoriesIv:
                showDefoultFragment();
                break;
            case R.id.profileTv:
                showFragment(ProfileFragment.newInstance());
                break;
            case R.id.searchTv:
                showFragment(SearchFragment.newInstance());
                break;
        }
    }

    private void showDefoultFragment() {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.main_menu_tabs_containier, CategoriesFragment.newInstance());
        trans.commit();
        //showFragment(CategoriesFragment.newInstance());
    }

    private void showFragment(Fragment fragment) {
        UiTools.replaceFragment(fragment, getSupportFragmentManager());
    }
}
