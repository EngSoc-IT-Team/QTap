package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.buildings.Building;
import com.example.alex.qtapandroid.common.database.buildings.BuildingManager;
import com.example.alex.qtapandroid.common.database.food.Food;
import com.example.alex.qtapandroid.common.database.food.FoodManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Carson on 26/06/2017.
 * Fragment that displays the buildings in the phone/cloud database.
 */
public class BuildingsFragment extends ListFragment {

    public static final String TAG_FOOD_NAMES = "FOOD_NAMES";

    private BuildingManager mBuildingManager;
    private NavigationView mNavView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mBuildingManager = new BuildingManager(getActivity().getApplicationContext());
        ArrayList<HashMap<String, String>> buildingsList = new ArrayList<>();

        ArrayList<Building> buildings = mBuildingManager.getTable();
        for (Building building : buildings) {
            HashMap<String, String> map = new HashMap<>();
            map.put(Building.COLUMN_NAME, building.getName());
            map.put(Building.COLUMN_PURPOSE, building.getPurpose());
            String food = building.getFood() ? "Yes" : "No";
            map.put(Building.COLUMN_FOOD, food);
            map.put(FoodFragment.TAG_DB_ID, String.valueOf(building.getID()));
            buildingsList.add(map);
        }
        ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), buildingsList,
                R.layout.buildings_list_item, new String[]{Building.COLUMN_NAME, Building.COLUMN_PURPOSE, Building.COLUMN_FOOD, FoodFragment.TAG_DB_ID},
                new int[]{R.id.name, R.id.purpose, R.id.food, R.id.db_id});
        setListAdapter(adapter);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Bundle args = packBuildingInfo(v);

        OneBuildingFragment oneBuildingFragment = new OneBuildingFragment();
        oneBuildingFragment.setArguments(args);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().addToBackStack(null).replace(R.id.content_frame, oneBuildingFragment).commit();
    }

    private Bundle packBuildingInfo(View v) {
        Bundle args = new Bundle();
        FoodManager foodManager = new FoodManager(getActivity().getApplicationContext());
        String sId = ((TextView) v.findViewById(R.id.db_id)).getText().toString();
        Building building = mBuildingManager.getRow(Integer.parseInt(sId));
        ArrayList<Food> food = foodManager.getFoodForBuilding(Integer.parseInt(sId));

        ArrayList<String> foodNames = new ArrayList<>();
        for (Food oneFood : food) {
            foodNames.add(oneFood.getName());
        }

        if (building.getName().equals("Athletics and Recreation Centre (ARC)")) {
            args.putString(Building.COLUMN_NAME, "ARC"); //name too long for activity bar, ARC is common
        } else if (building.getName().equals("John Deutsch Centre (JDUC)")) {
            args.putString(Building.COLUMN_NAME, "JDUC"); //too long again, JDUC common
        } else {
            args.putString(Building.COLUMN_NAME, building.getName());
        }
        args.putString(Building.COLUMN_PURPOSE, building.getPurpose());
        args.putBoolean(Building.COLUMN_BOOK_ROOMS, building.getBookRooms());
        args.putBoolean(Building.COLUMN_ATM, building.getAtm());
        args.putDouble(Building.COLUMN_LAT, building.getLat());
        args.putDouble(Building.COLUMN_LON, building.getLon());
        args.putStringArrayList(TAG_FOOD_NAMES, foodNames);
        return args;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(getString(R.string.fragment_buildings));
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
    }
}
