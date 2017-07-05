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
import com.example.alex.qtapandroid.common.database.food.Food;
import com.example.alex.qtapandroid.common.database.food.FoodManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Carson on 05/07/2017.
 * Fragment displaying data in phone database regarding food establishments.
 */
public class FoodFragment extends ListFragment {

    private NavigationView mNavView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food, container, false);
        ArrayList<HashMap<String, String>> foodList = new ArrayList<>();
        ArrayList<Food> food = (new FoodManager(getActivity().getApplicationContext())).getTable();
        BuildingManager buildingManager = new BuildingManager(getContext());
        for (Food oneFood : food) {
            HashMap<String, String> map = new HashMap<>();
            map.put(Food.COLUMN_NAME, oneFood.getName());
            //key is buildingID but that's just to avoid hardcoding - actually building name
            //ID+1 because cloud DB starts at ID 0, phone starts at 1
            map.put(Food.COLUMN_BUILDING_ID, buildingManager.getRow(oneFood.getBuildingID() + 1).getName());
            String takesMeal = oneFood.isMealPlan() ? "Yes" : "No";
            map.put(Food.COLUMN_MEAL_PLAN, takesMeal);
            String takesCard = oneFood.isCard() ? "Yes" : "No";
            map.put(Food.COLUMN_CARD, takesCard);
            foodList.add(map);
        }
        ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), foodList,
                R.layout.food_list_item, new String[]{Food.COLUMN_NAME, Food.COLUMN_BUILDING_ID, Food.COLUMN_MEAL_PLAN, Food.COLUMN_CARD},
                new int[]{R.id.name, R.id.building, R.id.meal_plan, R.id.card});
        setListAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(getString(R.string.food_fragment));
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
        DatabaseAccessor.getDatabase().close();
    }
}
