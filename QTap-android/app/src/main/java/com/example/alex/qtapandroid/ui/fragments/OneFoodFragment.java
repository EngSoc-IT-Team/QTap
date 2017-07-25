package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.ConvertHourSpan;
import com.example.alex.qtapandroid.common.database.food.Food;

/**
 * Created by Carson on 23/07/2017.
 * Fragment that holds information for one food establishment.
 */
public class OneFoodFragment extends Fragment {

    private Bundle mArgs;
    private NavigationView mNavView;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_one_food, container, false);
        mArgs = getArguments();
        populateViews();
        return mView;
    }

    private void populateViews() {
        ((TextView) mView.findViewById(R.id.name)).setText(mArgs.getString(Food.COLUMN_NAME));
        ((TextView) mView.findViewById(R.id.mon_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_MON_START_HOURS), mArgs.getDouble(Food.COLUMN_MON_STOP_HOURS)));
        ((TextView) mView.findViewById(R.id.tue_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_TUE_START_HOURS), mArgs.getDouble(Food.COLUMN_TUE_STOP_HOURS)));
        ((TextView) mView.findViewById(R.id.wed_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_WED_START_HOURS), mArgs.getDouble(Food.COLUMN_WED_STOP_HOURS)));
        ((TextView) mView.findViewById(R.id.thur_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_THUR_START_HOURS), mArgs.getDouble(Food.COLUMN_THUR_STOP_HOURS)));
        ((TextView) mView.findViewById(R.id.fri_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_FRI_START_HOURS), mArgs.getDouble(Food.COLUMN_FRI_STOP_HOURS)));
        ((TextView) mView.findViewById(R.id.sat_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_SAT_START_HOURS), mArgs.getDouble(Food.COLUMN_SAT_STOP_HOURS)));
        ((TextView) mView.findViewById(R.id.sun_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_SUN_START_HOURS), mArgs.getDouble(Food.COLUMN_SUN_STOP_HOURS)));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(mArgs.getString(Food.COLUMN_NAME));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mNavView.getMenu().findItem(R.id.nav_food).setChecked(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNavView = (NavigationView) (getActivity()).findViewById(R.id.drawer_layout).findViewById(R.id.nav_view);
        mNavView.getMenu().findItem(R.id.nav_food).setChecked(true);
    }
}
