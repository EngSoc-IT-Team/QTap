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
import com.example.alex.qtapandroid.common.database.buildings.BuildingManager;
import com.example.alex.qtapandroid.common.database.cafeterias.Cafeteria;
import com.example.alex.qtapandroid.common.database.cafeterias.CafeteriaManager;
import com.example.alex.qtapandroid.common.database.food.Food;
import com.example.alex.qtapandroid.common.database.food.FoodManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Carson on 18/07/2017.
 * Fragment that displays the cafeterias in the phone database.
 */
public class CafeteriasFragment extends ListFragment {
    private NavigationView mNavView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food, container, false);
        ArrayList<HashMap<String, String>> cafList = new ArrayList<>();
        ArrayList<Cafeteria> cafs = (new CafeteriaManager(getActivity().getApplicationContext())).getTable();
        BuildingManager buildingManager = new BuildingManager(getContext());
        for (Cafeteria caf : cafs) {
            HashMap<String, String> map = new HashMap<>();
            map.put(Cafeteria.COLUMN_NAME, caf.getName());
            //key is buildingID but that's just to avoid hardcoding - actually building name
            //ID+1 because cloud DB starts at ID 0, phone starts at 1
            map.put(Cafeteria.COLUMN_BUILDING_ID, buildingManager.getRow(caf.getBuildingID()).getName());
            cafList.add(map);
        }
        ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), cafList,
                R.layout.cafeteria_list_item, new String[]{Cafeteria.COLUMN_NAME, Cafeteria.COLUMN_BUILDING_ID},
                new int[]{R.id.name, R.id.building});
        setListAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(getString(R.string.fragment_cafeterias));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mNavView.getMenu().findItem(R.id.nav_cafeterias).setChecked(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNavView = (NavigationView) (getActivity()).findViewById(R.id.drawer_layout).findViewById(R.id.nav_view);
        mNavView.getMenu().findItem(R.id.nav_cafeterias).setChecked(true);
        DatabaseAccessor.getDatabase().close();
    }
}
