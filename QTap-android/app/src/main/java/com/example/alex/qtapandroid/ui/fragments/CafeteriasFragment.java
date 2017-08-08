package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.ConvertHourSpan;
import com.example.alex.qtapandroid.common.Util;
import com.example.alex.qtapandroid.common.database.local.DatabaseRow;
import com.example.alex.qtapandroid.common.database.local.cafeterias.Cafeteria;
import com.example.alex.qtapandroid.common.database.local.cafeterias.CafeteriaManager;
import com.example.alex.qtapandroid.interfaces.IQLActionbarFragment;
import com.example.alex.qtapandroid.interfaces.IQLDrawerItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Carson on 18/07/2017.
 * Fragment that displays the cafeterias in the phone database.
 */
public class CafeteriasFragment extends ListFragment implements IQLActionbarFragment, IQLDrawerItem {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        setActionbarTitle();
        selectDrawer();

        ArrayList<HashMap<String, String>> cafList = new ArrayList<>();
        ArrayList<DatabaseRow> cafs = (new CafeteriaManager(getActivity().getApplicationContext())).getTable();
        for (DatabaseRow row : cafs) {
            Cafeteria caf = (Cafeteria) row;
            HashMap<String, String> map = new HashMap<>();

            map.put(Cafeteria.COLUMN_NAME, caf.getName());
            //don't put building ID - name makes it obvious
            //use start for key for hours
            map.put(Cafeteria.COLUMN_WEEK_BREAKFAST_START, ConvertHourSpan.getHours(caf.getWeekBreakfastStart(), caf.getWeekBreakfastStop()));
            map.put(Cafeteria.COLUMN_FRI_BREAKFAST_START, ConvertHourSpan.getHours(caf.getFriBreakfastStart(), caf.getFriBreakfastStop()));
            map.put(Cafeteria.COLUMN_SAT_BREAKFAST_START, ConvertHourSpan.getHours(caf.getSatBreakfastStart(), caf.getSatBreakfastStop()));
            map.put(Cafeteria.COLUMN_SUN_BREAKFAST_START, ConvertHourSpan.getHours(caf.getSunBreakfastStart(), caf.getSunBreakfastStop()));
            map.put(Cafeteria.COLUMN_WEEK_LUNCH_START, ConvertHourSpan.getHours(caf.getWeekLunchStart(), caf.getWeekLunchStop()));
            map.put(Cafeteria.COLUMN_FRI_LUNCH_START, ConvertHourSpan.getHours(caf.getFriLunchStart(), caf.getFriLunchStop()));
            map.put(Cafeteria.COLUMN_SAT_LUNCH_START, ConvertHourSpan.getHours(caf.getSatLunchStart(), caf.getSatLunchStop()));
            map.put(Cafeteria.COLUMN_SUN_LUNCH_START, ConvertHourSpan.getHours(caf.getSunLunchStart(), caf.getSunLunchStop()));
            map.put(Cafeteria.COLUMN_WEEK_DINNER_START, ConvertHourSpan.getHours(caf.getWeekDinnerStart(), caf.getWeekDinnerStop()));
            map.put(Cafeteria.COLUMN_FRI_DINNER_START, ConvertHourSpan.getHours(caf.getFriDinnerStart(), caf.getFriDinnerStop()));
            map.put(Cafeteria.COLUMN_SAT_DINNER_START, ConvertHourSpan.getHours(caf.getSatDinnerStart(), caf.getSatDinnerStop()));
            map.put(Cafeteria.COLUMN_SUN_DINNER_START, ConvertHourSpan.getHours(caf.getSunDinnerStart(), caf.getSunDinnerStop()));

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

    @Override
    public void onPause() {
        super.onPause();
        deselectDrawer();
    }

    @Override
    public void setActionbarTitle() {
        Util.setActionbarTitle(getString(R.string.fragment_cafeterias), (AppCompatActivity) getActivity());
    }

    @Override
    public void deselectDrawer() {
        Util.setDrawerItemSelected(getActivity(), R.id.nav_cafeterias, false);
    }

    @Override
    public void selectDrawer() {
        Util.setDrawerItemSelected(getActivity(), R.id.nav_cafeterias, true);
    }
}
