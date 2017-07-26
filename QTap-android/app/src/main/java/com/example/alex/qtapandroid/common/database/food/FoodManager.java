package com.example.alex.qtapandroid.common.database.food;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.example.alex.qtapandroid.common.database.DatabaseAccessor;

import java.util.ArrayList;

/**
 * Created by Carson on 04/07/2017.
 * Class that handles rows in phone database for Food table.
 */
public class FoodManager extends DatabaseAccessor {
    public FoodManager(Context context) {
        super(context);
    }

    /**
     * Inserts a Food into the database.
     *
     * @param food The Food to be inserted. Before calling it must have
     *             the values to be inserted.
     * @return <long> The ID of the Food just inserted. Set the id of the
     * the Food inserted to be the return value.
     */
    public long insertRow(Food food) {
        ContentValues values = new ContentValues();
        values.put(Food.COLUMN_NAME, food.getName());
        values.put(Food.COLUMN_MEAL_PLAN, food.isMealPlan());
        values.put(Food.COLUMN_CARD, food.isCard());
        values.put(Food.COLUMN_INFORMATION, food.getInformation());
        values.put(Food.COLUMN_BUILDING_ID, food.getBuildingID());
        values.put(Food.COLUMN_MON_START_HOURS, food.getMonStartHours());
        values.put(Food.COLUMN_MON_STOP_HOURS, food.getMonStopHours());
        values.put(Food.COLUMN_TUE_START_HOURS, food.getTueStartHours());
        values.put(Food.COLUMN_TUE_STOP_HOURS, food.getTueStopHours());
        values.put(Food.COLUMN_WED_START_HOURS, food.getWedStartHours());
        values.put(Food.COLUMN_WED_STOP_HOURS, food.getWedStopHours());
        values.put(Food.COLUMN_THUR_START_HOURS, food.getThurStartHours());
        values.put(Food.COLUMN_THUR_STOP_HOURS, food.getThurStopHours());
        values.put(Food.COLUMN_FRI_START_HOURS, food.getFriStartHours());
        values.put(Food.COLUMN_FRI_STOP_HOURS, food.getFriStopHours());
        values.put(Food.COLUMN_SAT_START_HOURS, food.getSatStartHours());
        values.put(Food.COLUMN_SAT_STOP_HOURS, food.getSatStopHours());
        values.put(Food.COLUMN_SUN_START_HOURS, food.getSunStartHours());
        values.put(Food.COLUMN_SUN_STOP_HOURS, food.getSunStopHours());

        return getDatabase().insert(Food.TABLE_NAME, null, values);
    }

    /**
     * Deletes a Food from the database.
     *
     * @param food The Food to be deleted. Identifies which Food
     *             using the ID of this parameter.
     */
    public void deleteRow(Food food) {
        String selection = Food._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(food.getID())};
        getDatabase().delete(Food.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Gets the entire Food table.
     *
     * @return ArrayList of all the rows in the Food table.
     */
    public ArrayList<Food> getTable() {
        String[] projection = {
                Food._ID,
                Food.COLUMN_NAME,
                Food.COLUMN_MEAL_PLAN,
                Food.COLUMN_CARD,
                Food.COLUMN_INFORMATION,
                Food.COLUMN_BUILDING_ID,
                Food.COLUMN_MON_START_HOURS,
                Food.COLUMN_MON_STOP_HOURS,
                Food.COLUMN_TUE_START_HOURS,
                Food.COLUMN_TUE_STOP_HOURS,
                Food.COLUMN_WED_START_HOURS,
                Food.COLUMN_WED_STOP_HOURS,
                Food.COLUMN_THUR_START_HOURS,
                Food.COLUMN_THUR_STOP_HOURS,
                Food.COLUMN_FRI_START_HOURS,
                Food.COLUMN_FRI_STOP_HOURS,
                Food.COLUMN_SAT_START_HOURS,
                Food.COLUMN_SAT_STOP_HOURS,
                Food.COLUMN_SUN_START_HOURS,
                Food.COLUMN_SUN_STOP_HOURS
        };
        ArrayList<Food> food = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        //order table by name, ascending
        try (Cursor cursor = getDatabase().query(Food.TABLE_NAME, projection, null, null, null, null, Food.COLUMN_NAME + " ASC")) {
            while (cursor.moveToNext()) {
                Food oneFood = getRow(cursor.getInt(Food.POS_ID));
                food.add(oneFood);
            }
            cursor.close();
            return food; //return only when the cursor has been closed
        }
    }

    /**
     * Gets a single known Food from the Food table.
     *
     * @param id ID of the Food to get from the table.
     * @return Food class obtained from the table. Contains all information
     * held in row.
     */
    public Food getRow(long id) {
        String[] projection = {
                Food._ID,
                Food.COLUMN_NAME,
                Food.COLUMN_MEAL_PLAN,
                Food.COLUMN_CARD,
                Food.COLUMN_INFORMATION,
                Food.COLUMN_BUILDING_ID,
                Food.COLUMN_MON_START_HOURS,
                Food.COLUMN_MON_STOP_HOURS,
                Food.COLUMN_TUE_START_HOURS,
                Food.COLUMN_TUE_STOP_HOURS,
                Food.COLUMN_WED_START_HOURS,
                Food.COLUMN_WED_STOP_HOURS,
                Food.COLUMN_THUR_START_HOURS,
                Food.COLUMN_THUR_STOP_HOURS,
                Food.COLUMN_FRI_START_HOURS,
                Food.COLUMN_FRI_STOP_HOURS,
                Food.COLUMN_SAT_START_HOURS,
                Food.COLUMN_SAT_STOP_HOURS,
                Food.COLUMN_SUN_START_HOURS,
                Food.COLUMN_SUN_STOP_HOURS
        };
        Food food;
        String selection = Food._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(Food.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            cursor.moveToNext();
            //getInt()>0 because SQLite doesn't have boolean types - 1 is true, 0 is false
            food = new Food(cursor.getString(Food.POS_NAME), cursor.getInt(Food.POS_BUILDING_ID),
                    cursor.getString(Food.POS_INFORMATION), cursor.getInt(Food.POS_MEAL_PLAN) > 0, cursor.getInt(Food.POS_CARD) > 0,
                    cursor.getDouble(Food.POS_MON_START_HOURS), cursor.getDouble(Food.POS_MON_STOP_HOURS), cursor.getDouble(Food.POS_TUE_START_HOURS),
                    cursor.getDouble(Food.POS_TUE_STOP_HOURS), cursor.getDouble(Food.POS_WED_START_HOURS), cursor.getDouble(Food.POS_WED_STOP_HOURS),
                    cursor.getDouble(Food.POS_THUR_START_HOURS), cursor.getDouble(Food.POS_THUR_STOP_HOURS), cursor.getDouble(Food.POS_FRI_START_HOURS),
                    cursor.getDouble(Food.POS_FRI_STOP_HOURS), cursor.getDouble(Food.POS_SAT_START_HOURS),
                    cursor.getDouble(Food.POS_SAT_STOP_HOURS), cursor.getDouble(Food.POS_SUN_START_HOURS), cursor.getDouble(Food.POS_SUN_STOP_HOURS));
            food.setID(cursor.getInt(Food.POS_ID));
            cursor.close();
            return food; //return only when the cursor has been closed.
            //Return statement never missed, try block always finishes this.
        }
    }

    /**
     * Gets a single known Food from the Food table.
     *
     * @param buildingID building ID of the Food to get from the table. String to differentiate from long ID getRow
     *                    string converted to int inside method.
     * @return Food class obtained from the table. Contains all information
     * held in row.
     */
    public ArrayList<Food> getFoodForBuilding(int buildingID) {
        String[] projection = {
                Food._ID,
                Food.COLUMN_NAME,
                Food.COLUMN_MEAL_PLAN,
                Food.COLUMN_CARD,
                Food.COLUMN_INFORMATION,
                Food.COLUMN_BUILDING_ID,
                Food.COLUMN_MON_START_HOURS,
                Food.COLUMN_MON_STOP_HOURS,
                Food.COLUMN_TUE_START_HOURS,
                Food.COLUMN_TUE_STOP_HOURS,
                Food.COLUMN_WED_START_HOURS,
                Food.COLUMN_WED_STOP_HOURS,
                Food.COLUMN_THUR_START_HOURS,
                Food.COLUMN_THUR_STOP_HOURS,
                Food.COLUMN_FRI_START_HOURS,
                Food.COLUMN_FRI_STOP_HOURS,
                Food.COLUMN_SAT_START_HOURS,
                Food.COLUMN_SAT_STOP_HOURS,
                Food.COLUMN_SUN_START_HOURS,
                Food.COLUMN_SUN_STOP_HOURS
        };
        String selection = Food.COLUMN_BUILDING_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(buildingID)};
        ArrayList<Food> food = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        //order table by name, ascending
        try (Cursor cursor = getDatabase().query(Food.TABLE_NAME, projection, selection, selectionArgs, null, null, Food.COLUMN_NAME + " ASC")) {
            while (cursor.moveToNext()) {
                Food oneFood = getRow(cursor.getInt(Food.POS_ID));
                food.add(oneFood);
            }
            cursor.close();
            return food; //return only when the cursor has been closed
        }
    }

    /**
     * Deletes the entire Food table.
     */
    public void deleteTable() {
        getDatabase().delete(Food.TABLE_NAME, null, null);
    }

    /**
     * Changes information to one pre-existing Food.
     *
     * @param oldFood Food class that is being replaced.
     * @param newFood Food class that holds the new information.
     * @return Food class containing updated information
     */
    public Food updateRow(Food oldFood, Food newFood) {
        ContentValues values = new ContentValues();
        values.put(Food.COLUMN_NAME, oldFood.getName());
        values.put(Food.COLUMN_MEAL_PLAN, oldFood.isMealPlan());
        values.put(Food.COLUMN_CARD, oldFood.isCard());
        values.put(Food.COLUMN_INFORMATION, oldFood.getInformation());
        values.put(Food.COLUMN_BUILDING_ID, oldFood.getBuildingID());
        values.put(Food.COLUMN_MON_START_HOURS, oldFood.getMonStartHours());
        values.put(Food.COLUMN_MON_STOP_HOURS, oldFood.getMonStopHours());
        values.put(Food.COLUMN_TUE_START_HOURS, oldFood.getTueStartHours());
        values.put(Food.COLUMN_TUE_STOP_HOURS, oldFood.getTueStopHours());
        values.put(Food.COLUMN_WED_START_HOURS, oldFood.getWedStartHours());
        values.put(Food.COLUMN_WED_STOP_HOURS, oldFood.getWedStopHours());
        values.put(Food.COLUMN_THUR_START_HOURS, oldFood.getThurStartHours());
        values.put(Food.COLUMN_THUR_STOP_HOURS, oldFood.getThurStopHours());
        values.put(Food.COLUMN_FRI_START_HOURS, oldFood.getFriStartHours());
        values.put(Food.COLUMN_FRI_STOP_HOURS, oldFood.getFriStopHours());
        values.put(Food.COLUMN_SAT_START_HOURS, oldFood.getSatStartHours());
        values.put(Food.COLUMN_SAT_STOP_HOURS, oldFood.getSatStopHours());
        values.put(Food.COLUMN_SUN_START_HOURS, oldFood.getSunStartHours());
        values.put(Food.COLUMN_SUN_STOP_HOURS, oldFood.getSunStopHours());

        String selection = Food._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(oldFood.getID())};
        getDatabase().update(Food.TABLE_NAME, values, selection, selectionArgs);
        newFood.setID(oldFood.getID());
        return newFood;
    }
}
