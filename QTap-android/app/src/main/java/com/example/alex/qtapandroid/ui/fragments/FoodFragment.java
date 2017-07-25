package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

    private static final String TAG_DB_ID = "DB_ID";
    private FoodManager mFoodManager;
    private NavigationView mNavView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mFoodManager = new FoodManager(getActivity().getApplicationContext());

        ArrayList<HashMap<String, String>> foodList = new ArrayList<>();
        ArrayList<Food> food = mFoodManager.getTable();
        BuildingManager buildingManager = new BuildingManager(getContext());

        for (Food oneFood : food) {
            HashMap<String, String> map = new HashMap<>();
            map.put(Food.COLUMN_NAME, oneFood.getName());
            //key is buildingID but that's just to avoid hardcoding - actually building name
            //ID+1 because cloud DB starts at ID 0, phone starts at 1
            map.put(Food.COLUMN_BUILDING_ID, buildingManager.getRow(oneFood.getBuildingID()).getName());
            String takesMeal = oneFood.isMealPlan() ? "Yes" : "No";
            map.put(Food.COLUMN_MEAL_PLAN, takesMeal);
            String takesCard = oneFood.isCard() ? "Yes" : "No";
            map.put(Food.COLUMN_CARD, takesCard);
            map.put(TAG_DB_ID, String.valueOf(oneFood.getID()));
            foodList.add(map);
        }

        ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), foodList,
                R.layout.food_list_item, new String[]{Food.COLUMN_NAME, Food.COLUMN_BUILDING_ID, Food.COLUMN_MEAL_PLAN, Food.COLUMN_CARD, TAG_DB_ID},
                new int[]{R.id.name, R.id.building, R.id.meal_plan, R.id.card, R.id.db_id});
        setListAdapter(adapter);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String sId = ((TextView) v.findViewById(R.id.db_id)).getText().toString();
        Food food = mFoodManager.getRow(Integer.parseInt(sId));
        //pack info for detailed fragment
        Bundle args = new Bundle();
        args.putString(Food.COLUMN_NAME, food.getName());

        OneFoodFragment oneFoodFragment = new OneFoodFragment();
        oneFoodFragment.setArguments(args);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().addToBackStack(null).replace(R.id.content_frame, oneFoodFragment).commit();
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
        DatabaseAccessor.getDatabase().close();
    }

    @Override
    public void onResume() {
        super.onResume();
        mNavView = (NavigationView) (getActivity()).findViewById(R.id.drawer_layout).findViewById(R.id.nav_view);
        mNavView.getMenu().findItem(R.id.nav_food).setChecked(true);
    }
}
