package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.food.Food;

/**
 * Created by Carson on 23/07/2017.
 * Fragment that holds information for one food establishment.
 */
public class OneFoodFragment extends Fragment {
    private NavigationView mNavView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one_food, container, false);
        ((TextView) v.findViewById(R.id.name)).setText(getArguments().getString(Food.COLUMN_NAME));
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(getArguments().getString(Food.COLUMN_NAME));
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
