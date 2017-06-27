package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.DatabaseAccessor;
import com.example.alex.qtapandroid.common.database.buildings.Building;
import com.example.alex.qtapandroid.common.database.buildings.BuildingManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Carson on 26/06/2017.
 * Fragment that displays the buildings in the phone/cloud database.
 */
public class BuildingsFragment extends ListFragment {

    private static final String TAG_NAME = "Name";
    private static final String TAG_PURPOSE = "Purpose";
    private static final String TAG_BOOK_ROOMS = "BookRooms";
    private static final String TAG_FOOD = "Food";
    private static final String TAG_ATM = "ATM";
    private static final String TAG_LAT = "Lat";
    private static final String TAG_LON = "Lon";

    private NavigationView mNavView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buildings, container, false);
        ArrayList<HashMap<String, String>> buildingsList = new ArrayList<>();
        ArrayList<Building> buildings = (new BuildingManager(getActivity().getApplicationContext())).getTable();
        for (Building building : buildings) {
            HashMap<String, String> map = new HashMap<>();
            map.put(TAG_NAME, building.getName());
            String food = building.getFood() ? "Yes" : "No";
            map.put(TAG_PURPOSE, building.getPurpose());
            map.put(TAG_FOOD, food);
            buildingsList.add(map);
        }
        ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), buildingsList,
                R.layout.buildings_list_item, new String[]{TAG_NAME, TAG_PURPOSE, TAG_FOOD}, new int[]{R.id.name, R.id.purpose, R.id.food});
        setListAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(getString(R.string.buildings_fragment));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mNavView.getMenu().findItem(R.id.nav_buildings).setChecked(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNavView = (NavigationView) (getActivity()).findViewById(R.id.drawer_layout).findViewById(R.id.nav_view);
        mNavView.getMenu().findItem(R.id.nav_buildings).setChecked(true);
        DatabaseAccessor.getDatabase().close();
    }
}
