package com.example.alex.qtapandroid.common;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.alex.qtapandroid.R;

/**
 * Created by Carson on 01/08/2017.
 * Class for common methods. All are short and static.
 */
public class Util {
    public static void setActionbarTitle(int titleId, AppCompatActivity activity) {
        ActionBar actionbar = activity.getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(activity.getString(titleId));
        }
    }

    public static void setDrawerItemSelected(Activity activity, int itemId, boolean isChecked) {
        NavigationView navView = (NavigationView) activity.findViewById(R.id.drawer_layout).findViewById(R.id.nav_view);
        navView.getMenu().findItem(itemId).setChecked(isChecked);
    }
}
