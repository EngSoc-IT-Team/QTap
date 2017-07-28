package com.example.alex.qtapandroid.common.database.local.cafeterias;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alex.qtapandroid.common.database.local.DatabaseAccessor;
import com.example.alex.qtapandroid.common.database.local.DatabaseManager;
import com.example.alex.qtapandroid.common.database.local.DatabaseRow;
import com.example.alex.qtapandroid.common.database.local.buildings.Building;

import java.util.ArrayList;

/**
 * Created by Carson on 18/07/2017.
 * This class manages the rows in the Cafeteria table in the phone.
 */
public class CafeteriaManager extends DatabaseManager {
    public CafeteriaManager(Context context) {
        super(context);
    }

    @Override
    public void insertRow(DatabaseRow row) {
        if (row instanceof Cafeteria) {
            Cafeteria caf = (Cafeteria) row;
            ContentValues values = new ContentValues();
            values.put(Cafeteria.ID, caf.getId());
            values.put(Cafeteria.COLUMN_NAME, caf.getName());
            values.put(Cafeteria.COLUMN_BUILDING_ID, caf.getBuildingID());
            values.put(Cafeteria.COLUMN_WEEK_BREAKFAST_START, caf.getWeekBreakfastStart());
            values.put(Cafeteria.COLUMN_WEEK_BREAKFAST_STOP, caf.getWeekBreakfastStop());
            values.put(Cafeteria.COLUMN_FRI_BREAKFAST_START, caf.getFriBreakfastStart());
            values.put(Cafeteria.COLUMN_FRI_BREAKFAST_STOP, caf.getFriBreakfastStop());
            values.put(Cafeteria.COLUMN_SAT_BREAKFAST_START, caf.getSatBreakfastStart());
            values.put(Cafeteria.COLUMN_SAT_BREAKFAST_STOP, caf.getSatBreakfastStop());
            values.put(Cafeteria.COLUMN_SUN_BREAKFAST_START, caf.getSunBreakfastStart());
            values.put(Cafeteria.COLUMN_SUN_BREAKFAST_STOP, caf.getSunBreakfastStop());
            values.put(Cafeteria.COLUMN_WEEK_LUNCH_START, caf.getWeekLunchStart());
            values.put(Cafeteria.COLUMN_WEEK_LUNCH_STOP, caf.getWeekLunchStop());
            values.put(Cafeteria.COLUMN_FRI_LUNCH_START, caf.getFriLunchStart());
            values.put(Cafeteria.COLUMN_FRI_LUNCH_STOP, caf.getFriLunchStop());
            values.put(Cafeteria.COLUMN_SAT_LUNCH_START, caf.getSatLunchStart());
            values.put(Cafeteria.COLUMN_SAT_LUNCH_STOP, caf.getSatLunchStop());
            values.put(Cafeteria.COLUMN_SUN_LUNCH_START, caf.getSunLunchStart());
            values.put(Cafeteria.COLUMN_SUN_LUNCH_STOP, caf.getSunLunchStop());
            values.put(Cafeteria.COLUMN_WEEK_DINNER_START, caf.getWeekDinnerStart());
            values.put(Cafeteria.COLUMN_WEEK_DINNER_STOP, caf.getWeekDinnerStop());
            values.put(Cafeteria.COLUMN_FRI_DINNER_START, caf.getFriDinnerStart());
            values.put(Cafeteria.COLUMN_FRI_DINNER_STOP, caf.getFriDinnerStop());
            values.put(Cafeteria.COLUMN_SAT_DINNER_START, caf.getSatDinnerStart());
            values.put(Cafeteria.COLUMN_SAT_DINNER_STOP, caf.getSatDinnerStop());
            values.put(Cafeteria.COLUMN_SUN_DINNER_START, caf.getSunDinnerStart());
            values.put(Cafeteria.COLUMN_SUN_DINNER_STOP, caf.getSunDinnerStop());
            getDatabase().insert(Cafeteria.TABLE_NAME, null, values);
        }
    }

    @Override
    public ArrayList<DatabaseRow> getTable() {
        String[] projection = {
                Cafeteria.ID,
                Cafeteria.COLUMN_NAME,
                Cafeteria.COLUMN_BUILDING_ID,
                Cafeteria.COLUMN_WEEK_BREAKFAST_START,
                Cafeteria.COLUMN_WEEK_BREAKFAST_STOP,
                Cafeteria.COLUMN_FRI_BREAKFAST_START,
                Cafeteria.COLUMN_FRI_BREAKFAST_STOP,
                Cafeteria.COLUMN_SAT_BREAKFAST_START,
                Cafeteria.COLUMN_SAT_BREAKFAST_STOP,
                Cafeteria.COLUMN_SUN_BREAKFAST_START,
                Cafeteria.COLUMN_SUN_BREAKFAST_STOP,
                Cafeteria.COLUMN_WEEK_LUNCH_START,
                Cafeteria.COLUMN_WEEK_LUNCH_STOP,
                Cafeteria.COLUMN_FRI_LUNCH_START,
                Cafeteria.COLUMN_FRI_LUNCH_STOP,
                Cafeteria.COLUMN_SAT_LUNCH_START,
                Cafeteria.COLUMN_SAT_LUNCH_STOP,
                Cafeteria.COLUMN_SUN_LUNCH_START,
                Cafeteria.COLUMN_SUN_LUNCH_STOP,
                Cafeteria.COLUMN_WEEK_DINNER_START,
                Cafeteria.COLUMN_WEEK_DINNER_STOP,
                Cafeteria.COLUMN_FRI_DINNER_START,
                Cafeteria.COLUMN_FRI_DINNER_STOP,
                Cafeteria.COLUMN_SAT_DINNER_START,
                Cafeteria.COLUMN_SAT_DINNER_STOP,
                Cafeteria.COLUMN_SUN_DINNER_START,
                Cafeteria.COLUMN_SUN_DINNER_STOP
        };
        ArrayList<DatabaseRow> cafs = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        //order table by name, ascending
        try (Cursor cursor = getDatabase().query(Cafeteria.TABLE_NAME, projection, null, null, null, null, Cafeteria.COLUMN_NAME + " ASC")) {
            while (cursor.moveToNext()) {
                Cafeteria caf = getRow(cursor.getInt(Cafeteria.POS_ID));
                cafs.add(caf);
            }
            cursor.close();
            return cafs; //return only when the cursor has been closed
        }
    }

    @Override
    public Cafeteria getRow(long id) {
        String[] projection = {
                Cafeteria.ID,
                Cafeteria.COLUMN_NAME,
                Cafeteria.COLUMN_BUILDING_ID,
                Cafeteria.COLUMN_WEEK_BREAKFAST_START,
                Cafeteria.COLUMN_WEEK_BREAKFAST_STOP,
                Cafeteria.COLUMN_FRI_BREAKFAST_START,
                Cafeteria.COLUMN_FRI_BREAKFAST_STOP,
                Cafeteria.COLUMN_SAT_BREAKFAST_START,
                Cafeteria.COLUMN_SAT_BREAKFAST_STOP,
                Cafeteria.COLUMN_SUN_BREAKFAST_START,
                Cafeteria.COLUMN_SUN_BREAKFAST_STOP,
                Cafeteria.COLUMN_WEEK_LUNCH_START,
                Cafeteria.COLUMN_WEEK_LUNCH_STOP,
                Cafeteria.COLUMN_FRI_LUNCH_START,
                Cafeteria.COLUMN_FRI_LUNCH_STOP,
                Cafeteria.COLUMN_SAT_LUNCH_START,
                Cafeteria.COLUMN_SAT_LUNCH_STOP,
                Cafeteria.COLUMN_SUN_LUNCH_START,
                Cafeteria.COLUMN_SUN_LUNCH_STOP,
                Cafeteria.COLUMN_WEEK_DINNER_START,
                Cafeteria.COLUMN_WEEK_DINNER_STOP,
                Cafeteria.COLUMN_FRI_DINNER_START,
                Cafeteria.COLUMN_FRI_DINNER_STOP,
                Cafeteria.COLUMN_SAT_DINNER_START,
                Cafeteria.COLUMN_SAT_DINNER_STOP,
                Cafeteria.COLUMN_SUN_DINNER_START,
                Cafeteria.COLUMN_SUN_DINNER_STOP
        };
        Cafeteria caf;
        String selection = Cafeteria.ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(Cafeteria.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            cursor.moveToNext();
            caf = new Cafeteria(cursor.getInt(Cafeteria.POS_ID), cursor.getString(Cafeteria.POS_NAME), cursor.getInt(Cafeteria.POS_BUILDING_ID),
                    cursor.getDouble(Cafeteria.POS_WEEK_BREAKFAST_START), cursor.getDouble(Cafeteria.POS_WEEK_BREAKFAST_STOP), cursor.getDouble(Cafeteria.POS_FRI_BREAKFAST_START),
                    cursor.getDouble(Cafeteria.POS_FRI_BREAKFAST_STOP), cursor.getDouble(Cafeteria.POS_SAT_BREAKFAST_START), cursor.getDouble(Cafeteria.POS_SAT_BREAKFAST_STOP),
                    cursor.getDouble(Cafeteria.POS_SUN_BREAKFAST_START), cursor.getDouble(Cafeteria.POS_SUN_BREAKFAST_STOP), cursor.getDouble(Cafeteria.POS_WEEK_LUNCH_START),
                    cursor.getDouble(Cafeteria.POS_WEEK_LUNCH_STOP), cursor.getDouble(Cafeteria.POS_FRI_LUNCH_START), cursor.getDouble(Cafeteria.POS_FRI_LUNCH_STOP),
                    cursor.getDouble(Cafeteria.POS_SAT_LUNCH_START), cursor.getDouble(Cafeteria.POS_SAT_LUNCH_STOP), cursor.getDouble(Cafeteria.POS_SUN_LUNCH_START),
                    cursor.getDouble(Cafeteria.POS_SUN_LUNCH_STOP), cursor.getDouble(Cafeteria.POS_WEEK_DINNER_START), cursor.getDouble(Cafeteria.POS_WEEK_DINNER_STOP),
                    cursor.getDouble(Cafeteria.POS_FRI_DINNER_START), cursor.getDouble(Cafeteria.POS_FRI_DINNER_STOP), cursor.getDouble(Cafeteria.POS_SAT_DINNER_START),
                    cursor.getDouble(Cafeteria.POS_SAT_DINNER_STOP), cursor.getDouble(Cafeteria.POS_SUN_DINNER_START), cursor.getDouble(Cafeteria.POS_SUN_DINNER_STOP));
            cursor.close();
            return caf; //return only when the cursor has been closed.
            //Return statement never missed, try block always finishes this.
        }
    }

    @Override
    public void updateRow(DatabaseRow oldRow, DatabaseRow newRow) {
        if (oldRow instanceof Cafeteria && newRow instanceof Cafeteria) {
            Cafeteria oldCaf = (Cafeteria) oldRow;
            Cafeteria newCaf = (Cafeteria) newRow;

            ContentValues values = new ContentValues();
            values.put(Cafeteria.ID, newCaf.getId());
            values.put(Cafeteria.COLUMN_NAME, newCaf.getName());
            values.put(Cafeteria.COLUMN_BUILDING_ID, newCaf.getBuildingID());
            values.put(Cafeteria.COLUMN_WEEK_BREAKFAST_START, newCaf.getWeekBreakfastStart());
            values.put(Cafeteria.COLUMN_WEEK_BREAKFAST_STOP, newCaf.getWeekBreakfastStop());
            values.put(Cafeteria.COLUMN_FRI_BREAKFAST_START, newCaf.getFriBreakfastStart());
            values.put(Cafeteria.COLUMN_FRI_BREAKFAST_STOP, newCaf.getFriBreakfastStop());
            values.put(Cafeteria.COLUMN_SAT_BREAKFAST_START, newCaf.getSatBreakfastStart());
            values.put(Cafeteria.COLUMN_SAT_BREAKFAST_STOP, newCaf.getSatBreakfastStop());
            values.put(Cafeteria.COLUMN_SUN_BREAKFAST_START, newCaf.getSunBreakfastStart());
            values.put(Cafeteria.COLUMN_SUN_BREAKFAST_STOP, newCaf.getSunBreakfastStop());
            values.put(Cafeteria.COLUMN_WEEK_LUNCH_START, newCaf.getWeekLunchStart());
            values.put(Cafeteria.COLUMN_WEEK_LUNCH_STOP, newCaf.getWeekLunchStop());
            values.put(Cafeteria.COLUMN_FRI_LUNCH_START, newCaf.getFriLunchStart());
            values.put(Cafeteria.COLUMN_FRI_LUNCH_STOP, newCaf.getFriLunchStop());
            values.put(Cafeteria.COLUMN_SAT_LUNCH_START, newCaf.getSatLunchStart());
            values.put(Cafeteria.COLUMN_SAT_LUNCH_STOP, newCaf.getSatLunchStop());
            values.put(Cafeteria.COLUMN_SUN_LUNCH_START, newCaf.getSunLunchStart());
            values.put(Cafeteria.COLUMN_SUN_LUNCH_STOP, newCaf.getSunLunchStop());
            values.put(Cafeteria.COLUMN_WEEK_DINNER_START, newCaf.getWeekDinnerStart());
            values.put(Cafeteria.COLUMN_WEEK_DINNER_STOP, newCaf.getWeekDinnerStop());
            values.put(Cafeteria.COLUMN_FRI_DINNER_START, newCaf.getFriDinnerStart());
            values.put(Cafeteria.COLUMN_FRI_DINNER_STOP, newCaf.getFriDinnerStop());
            values.put(Cafeteria.COLUMN_SAT_DINNER_START, newCaf.getSatDinnerStart());
            values.put(Cafeteria.COLUMN_SAT_DINNER_STOP, newCaf.getSatDinnerStop());
            values.put(Cafeteria.COLUMN_SUN_DINNER_START, newCaf.getSunDinnerStart());
            values.put(Cafeteria.COLUMN_SUN_DINNER_STOP, newCaf.getSunDinnerStop());

            String selection = Cafeteria.ID + " LIKE ?";
            String selectionArgs[] = {String.valueOf(oldCaf.getId())};
            getDatabase().update(Cafeteria.TABLE_NAME, values, selection, selectionArgs);
        }
    }
}
