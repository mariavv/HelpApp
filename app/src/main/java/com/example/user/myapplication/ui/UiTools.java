package com.example.user.myapplication.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.user.myapplication.R;

public class UiTools {
    public static void replaceFragment(Fragment fragment, FragmentManager supportFragmentManager) {
        FragmentTransaction trans = supportFragmentManager.beginTransaction();
        trans.replace(R.id.main_menu_tabs_containier, fragment);
        trans.commit();
    }

    public static void showError(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showError(int messageRes, Context context) {
        showError(context.getString(messageRes), context);
    }
}
