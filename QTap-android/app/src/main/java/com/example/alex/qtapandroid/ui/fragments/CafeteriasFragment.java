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
        View v = inflater.inflate(R.layout.fragment_cafeterias, container, false);
        ArrayList<HashMap<String, String>> cafList = new ArrayList<>();
        ArrayList<Cafeteria> cafs = (new CafeteriaManager(getActivity().getApplicationContext())).getTable();
        for (Cafeteria caf : cafs) {
            HashMap<String, String> map = new HashMap<>();

            map.put(Cafeteria.COLUMN_NAME, caf.getName());
            //don't put building ID - name makes it obvious
            //use start for key for hours
            map.put(Cafeteria.COLUMN_WEEK_BREAKFAST_START, getHours(caf.getWeekBreakfastStart(), caf.getWeekBreakfastStop()));
            map.put(Cafeteria.COLUMN_FRI_BREAKFAST_START, getHours(caf.getFriBreakfastStart(), caf.getFriBreakfastStop()));
            map.put(Cafeteria.COLUMN_SAT_BREAKFAST_START, getHours(caf.getSatBreakfastStart(), caf.getSatBreakfastStop()));
            map.put(Cafeteria.COLUMN_SUN_BREAKFAST_START, getHours(caf.getSunBreakfastStart(), caf.getSunBreakfastStop()));
            map.put(Cafeteria.COLUMN_WEEK_LUNCH_START, getHours(caf.getWeekLunchStart(), caf.getWeekLunchStop()));
            map.put(Cafeteria.COLUMN_FRI_LUNCH_START, getHours(caf.getFriLunchStart(), caf.getFriLunchStop()));
            map.put(Cafeteria.COLUMN_SAT_LUNCH_START, getHours(caf.getSatLunchStart(), caf.getSatLunchStop()));
            map.put(Cafeteria.COLUMN_SUN_LUNCH_START, getHours(caf.getSunLunchStart(), caf.getSunLunchStop()));
            map.put(Cafeteria.COLUMN_WEEK_DINNER_START, getHours(caf.getWeekDinnerStart(), caf.getWeekDinnerStop()));
            map.put(Cafeteria.COLUMN_FRI_DINNER_START, getHours(caf.getFriDinnerStart(), caf.getFriDinnerStop()));
            map.put(Cafeteria.COLUMN_SAT_DINNER_START, getHours(caf.getSatDinnerStart(), caf.getSatDinnerStop()));
            map.put(Cafeteria.COLUMN_SUN_DINNER_START, getHours(caf.getSunDinnerStart(), caf.getSunDinnerStop()));

            cafList.add(map);
        }
        ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), cafList,
                R.layout.cafeteria_list_item, new String[]{Cafeteria.COLUMN_NAME,
                Cafeteria.COLUMN_WEEK_BREAKFAST_START, Cafeteria.COLUMN_FRI_BREAKFAST_START, Cafeteria.COLUMN_SAT_BREAKFAST_START,
                Cafeteria.COLUMN_SUN_BREAKFAST_START, Cafeteria.COLUMN_WEEK_LUNCH_START, Cafeteria.COLUMN_FRI_LUNCH_START,
                Cafeteria.COLUMN_SAT_LUNCH_START, Cafeteria.COLUMN_SUN_LUNCH_START, Cafeteria.COLUMN_WEEK_DINNER_START,
                Cafeteria.COLUMN_FRI_DINNER_START, Cafeteria.COLUMN_SAT_DINNER_START, Cafeteria.COLUMN_SUN_DINNER_START},
                new int[]{R.id.name, R.id.week_breakfast, R.id.fri_breakfast, R.id.sat_breakfast, R.id.sun_breakfast,
                        R.id.week_lunch, R.id.fri_lunch, R.id.sat_lunch, R.id.sun_lunch, R.id.week_dinner, R.id.fri_dinner, R.id.sat_dinner,
                        R.id.sun_dinner});
        setListAdapter(adapter);
        return v;
    }

    private String getHours(double startHour, double endHour) {
        //check for closed all day flag
        if (startHour < 0) {
            return "Closed";
        }

        String start = getOneTimeBoundary(startHour);
        String end = getOneTimeBoundary(endHour);
        return start + " to " + end;
    }

    private String getOneTimeBoundary(double hour){
        String sHour="";
        if (hour < 1 || hour >= 13) { //24 hour time
            sHour += String.valueOf((int)hour - 12);
        } else {
            sHour += String.valueOf((int)hour);
        }

        double min=(hour-(int)hour)*60; //convert 0.5, 0.75 to 30, 45 min
        sHour+=":"+String.valueOf((int)min);
        if (min==0){
            sHour+="0";
        }

        if (hour < 12) {
            sHour += " am";
        } else {
            sHour += " pm";
        }
        return sHour;
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
