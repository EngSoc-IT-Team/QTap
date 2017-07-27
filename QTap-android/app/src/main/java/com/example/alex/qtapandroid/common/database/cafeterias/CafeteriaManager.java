package com.example.alex.qtapandroid.common.database.cafeterias;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alex.qtapandroid.common.database.DatabaseAccessor;

import java.util.ArrayList;

/**
 * Created by Carson on 18/07/2017.
 * This class manages the rows in the Cafeteria table in the phone.
 */
public class CafeteriaManager extends DatabaseAccessor {
    public CafeteriaManager(Context context) {
        super(context);
    }

    /**
     * Inserts a Cafeteria into the database.
     *
     * @param caf The Cafeteria to be inserted. Before calling it must have
     *            the values to be inserted.
     * @return <long> The ID of the Cafeteria just inserted. Set the id of the
     * the Cafeteria inserted to be the return value.
     */
    public long insertRow(Cafeteria caf) {
        ContentValues values = new ContentValues();
        values.put(Cafeteria._ID, caf.getID());
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

        return getDatabase().insert(Cafeteria.TABLE_NAME, null, values);
    }

    /**
     * Deletes a Cafeteria from the database.
     *
     * @param caf The Cafeteria to be deleted. Identifies which Cafeteria
     *            using the ID of this parameter.
     */
    public void deleteRow(Cafeteria caf) {
        String selection = Cafeteria._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(caf.getID())};
        getDatabase().delete(Cafeteria.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Gets the entire Cafeteria table.
     *
     * @return ArrayList of all the rows in the Cafeteria table.
     */
    public ArrayList<Cafeteria> getTable() {
        String[] projection = {
                Cafeteria._ID,
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
        ArrayList<Cafeteria> cafs = new ArrayList<>();
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

    /**
     * Gets a single known Cafeteria from the Cafeteria table.
     *
     * @param id ID of the Cafeteria to get from the table.
     * @return Cafeteria class obtained from the table. Contains all information
     * held in row.
     */
    public Cafeteria getRow(long id) {
        String[] projection = {
                Cafeteria._ID,
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
        String selection = Cafeteria._ID + " LIKE ?";
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

    /**
     * Gets a single known Cafeteria from the Cafeteria table.
     *
     * @param sBuildingID building ID of the Cafeteria to get from the table. String to differentiate from long ID getRow
     *                    string converted to int inside method.
     * @return Cafeteria class obtained from the table. Contains all information
     * held in row.
     */
    public Cafeteria getRow(String sBuildingID) {
        int buildingID = Integer.parseInt(sBuildingID);
        String[] projection = {
                Cafeteria._ID,
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
        String selection = Cafeteria.COLUMN_BUILDING_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(buildingID)};
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
            caf.setID(cursor.getInt(Cafeteria.POS_ID));
            cursor.close();
            return caf; //return only when the cursor has been closed.
            //Return statement never missed, try block always finishes this.
        }
    }

    /**
     * Deletes the entire Cafeteria table.
     */
    public void deleteTable() {
        getDatabase().delete(Cafeteria.TABLE_NAME, null, null);
    }

    /**
     * Changes information to one pre-existing Cafeteria.
     *
     * @param oldCaf Cafeteria class that is being replaced.
     * @param newCaf Cafeteria class that holds the new information.
     * @return Cafeteria class containing updated information
     */
    public Cafeteria updateRow(Cafeteria oldCaf, Cafeteria newCaf) {
        ContentValues values = new ContentValues();
        values.put(Cafeteria._ID, oldCaf.getID());
        values.put(Cafeteria.COLUMN_NAME, oldCaf.getName());
        values.put(Cafeteria.COLUMN_BUILDING_ID, oldCaf.getBuildingID());
        values.put(Cafeteria.COLUMN_WEEK_BREAKFAST_START, oldCaf.getWeekBreakfastStart());
        values.put(Cafeteria.COLUMN_WEEK_BREAKFAST_STOP, oldCaf.getWeekBreakfastStop());
        values.put(Cafeteria.COLUMN_FRI_BREAKFAST_START, oldCaf.getFriBreakfastStart());
        values.put(Cafeteria.COLUMN_FRI_BREAKFAST_STOP, oldCaf.getFriBreakfastStop());
        values.put(Cafeteria.COLUMN_SAT_BREAKFAST_START, oldCaf.getSatBreakfastStart());
        values.put(Cafeteria.COLUMN_SAT_BREAKFAST_STOP, oldCaf.getSatBreakfastStop());
        values.put(Cafeteria.COLUMN_SUN_BREAKFAST_START, oldCaf.getSunBreakfastStart());
        values.put(Cafeteria.COLUMN_SUN_BREAKFAST_STOP, oldCaf.getSunBreakfastStop());
        values.put(Cafeteria.COLUMN_WEEK_LUNCH_START, oldCaf.getWeekLunchStart());
        values.put(Cafeteria.COLUMN_WEEK_LUNCH_STOP, oldCaf.getWeekLunchStop());
        values.put(Cafeteria.COLUMN_FRI_LUNCH_START, oldCaf.getFriLunchStart());
        values.put(Cafeteria.COLUMN_FRI_LUNCH_STOP, oldCaf.getFriLunchStop());
        values.put(Cafeteria.COLUMN_SAT_LUNCH_START, oldCaf.getSatLunchStart());
        values.put(Cafeteria.COLUMN_SAT_LUNCH_STOP, oldCaf.getSatLunchStop());
        values.put(Cafeteria.COLUMN_SUN_LUNCH_START, oldCaf.getSunLunchStart());
        values.put(Cafeteria.COLUMN_SUN_LUNCH_STOP, oldCaf.getSunLunchStop());
        values.put(Cafeteria.COLUMN_WEEK_DINNER_START, oldCaf.getWeekDinnerStart());
        values.put(Cafeteria.COLUMN_WEEK_DINNER_STOP, oldCaf.getWeekDinnerStop());
        values.put(Cafeteria.COLUMN_FRI_DINNER_START, oldCaf.getFriDinnerStart());
        values.put(Cafeteria.COLUMN_FRI_DINNER_STOP, oldCaf.getFriDinnerStop());
        values.put(Cafeteria.COLUMN_SAT_DINNER_START, oldCaf.getSatDinnerStart());
        values.put(Cafeteria.COLUMN_SAT_DINNER_STOP, oldCaf.getSatDinnerStop());
        values.put(Cafeteria.COLUMN_SUN_DINNER_START, oldCaf.getSunDinnerStart());
        values.put(Cafeteria.COLUMN_SUN_DINNER_STOP, oldCaf.getSunDinnerStop());

        String selection = Cafeteria._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(oldCaf.getID())};
        getDatabase().update(Cafeteria.TABLE_NAME, values, selection, selectionArgs);
        return newCaf;
    }
}
