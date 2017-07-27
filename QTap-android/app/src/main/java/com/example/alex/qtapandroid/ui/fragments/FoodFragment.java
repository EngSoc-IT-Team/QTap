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
import com.example.alex.qtapandroid.common.database.local.buildings.Building;
import com.example.alex.qtapandroid.common.database.local.buildings.BuildingManager;
import com.example.alex.qtapandroid.common.database.local.food.Food;
import com.example.alex.qtapandroid.common.database.local.food.FoodManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Carson on 05/07/2017.
 * Fragment displaying data in phone database regarding food establishments.
 */
public class FoodFragment extends ListFragment {

    public static final String TAG_DB_ID = "DB_ID";
    public static final String TAG_BUILDING_NAME = "BUILDING_NAME";

    private FoodManager mFoodManager;
    private BuildingManager mBuildingManager;
    private NavigationView mNavView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mFoodManager = new FoodManager(getActivity().getApplicationContext());

        ArrayList<HashMap<String, String>> foodList = new ArrayList<>();
        ArrayList<Food> food = mFoodManager.getTable();
        mBuildingManager = new BuildingManager(getContext());

        for (Food oneFood : food) {
            HashMap<String, String> map = new HashMap<>();
            map.put(Food.COLUMN_NAME, oneFood.getName());
            map.put(Food.COLUMN_BUILDING_ID, String.valueOf(oneFood.getBuildingID()));
            map.put(TAG_BUILDING_NAME, mBuildingManager.getRow(oneFood.getBuildingID()).getName()); //Building.COLUMN_NAME is the same as Food's
            String takesMeal = oneFood.isMealPlan() ? "Yes" : "No";
            map.put(Food.COLUMN_MEAL_PLAN, takesMeal);
            String takesCard = oneFood.isCard() ? "Yes" : "No";
            map.put(Food.COLUMN_CARD, takesCard);
            map.put(TAG_DB_ID, String.valueOf(oneFood.getID()));
            foodList.add(map);
        }

        ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), foodList,
                R.layout.food_list_item, new String[]{Food.COLUMN_NAME, TAG_BUILDING_NAME, Food.COLUMN_MEAL_PLAN, Food.COLUMN_CARD, TAG_DB_ID, Food.COLUMN_BUILDING_ID},
                new int[]{R.id.name, R.id.building, R.id.meal_plan, R.id.card, R.id.db_id, R.id.building_db_id});
        setListAdapter(adapter);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Bundle args = packFoodInfo(v);

        OneFoodFragment oneFoodFragment = new OneFoodFragment();
        oneFoodFragment.setArguments(args);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().addToBackStack(null).replace(R.id.content_frame, oneFoodFragment).commit();
    }

    private Bundle packFoodInfo(View v) {
        Bundle args = new Bundle();
        String foodId = ((TextView) v.findViewById(R.id.db_id)).getText().toString();
        Food food = mFoodManager.getRow(Integer.parseInt(foodId));
        String buildingId = ((TextView) v.findViewById(R.id.building_db_id)).getText().toString();
        Building building = mBuildingManager.getRow(Integer.parseInt(buildingId));
        String buildingName = ((TextView) v.findViewById(R.id.building)).getText().toString();

        args.putString(Food.COLUMN_NAME, food.getName());
        args.putBoolean(Food.COLUMN_MEAL_PLAN, food.isMealPlan());
        args.putBoolean(Food.COLUMN_CARD, food.isCard());
        args.putString(Food.COLUMN_INFORMATION, food.getInformation());

        //no building ID so no DB access in details fragment
        args.putString(TAG_BUILDING_NAME, buildingName);
        args.putDouble(Building.COLUMN_LAT, building.getLat());
        args.putDouble(Building.COLUMN_LON, building.getLon());

        args.putDouble(Food.COLUMN_MON_START_HOURS, food.getMonStartHours());
        args.putDouble(Food.COLUMN_MON_STOP_HOURS, food.getMonStopHours());
        args.putDouble(Food.COLUMN_TUE_START_HOURS, food.getTueStartHours());
        args.putDouble(Food.COLUMN_TUE_STOP_HOURS, food.getTueStopHours());
        args.putDouble(Food.COLUMN_WED_START_HOURS, food.getWedStartHours());
        args.putDouble(Food.COLUMN_WED_STOP_HOURS, food.getWedStopHours());
        args.putDouble(Food.COLUMN_THUR_START_HOURS, food.getThurStartHours());
        args.putDouble(Food.COLUMN_THUR_STOP_HOURS, food.getThurStopHours());
        args.putDouble(Food.COLUMN_FRI_START_HOURS, food.getFriStartHours());
        args.putDouble(Food.COLUMN_FRI_STOP_HOURS, food.getFriStopHours());
        args.putDouble(Food.COLUMN_SAT_START_HOURS, food.getSatStartHours());
        args.putDouble(Food.COLUMN_SAT_STOP_HOURS, food.getSatStopHours());
        args.putDouble(Food.COLUMN_SUN_START_HOURS, food.getSunStartHours());
        args.putDouble(Food.COLUMN_SUN_STOP_HOURS, food.getSunStopHours());
        return args;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(getString(R.string.fragment_food));
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
