package com.example.alex.qtapandroid.common.database.buildings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alex.qtapandroid.common.database.DatabaseAccessor;

import java.util.ArrayList;

/**
 * Created by Carson on 22/01/2017.
 * Holds all information for the buildings table.
 * Manages the buildings within the database. Inserts/deletes rows and the entire table.
 */
public class BuildingManager extends DatabaseAccessor {

    public BuildingManager(Context context) {
        super(context);
    }

    /**
     * Inserts a building into the database.
     *
     * @param building The building to be inserted. Before calling it must have
     *                 the values to be inserted.
     * @return <long> The ID of the building just inserted.
     */
    public long insertRow(Building building) {
        ContentValues values = new ContentValues();
        values.put(Building.COLUMN_TITLE, building.getTitle());
        return getDatabase().insert(Building.TABLE_NAME, null, values);
    }

    /**
     * Deletes a building from the database.
     *
     * @param building The building to be deleted. Identifies which Building
     *                 using the ID of this parameter.
     */
    public void deleteRow(Building building) {
        String selection = Building._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(building.getID())};
        getDatabase().delete(Building.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Gets the entire buildings table.
     *
     * @return ArrayList of all the Buildings in the Buildings table.
     */
    public ArrayList<Building> getTable() {
        String[] projection = {
                Building._ID,
                Building.COLUMN_TITLE,
                Building.COLUMN_ROOM_NUM
        };
        ArrayList<Building> buildings = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = getDatabase().query(Building.TABLE_NAME, projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                Building building = getRow(cursor.getInt(Building.ID_POS));
                buildings.add(building);
            }
            cursor.close();
            return buildings; //return only when the cursor has been closed
        }
    }

    /**
     * Gets a single building from the Buildings table.
     *
     * @param id ID of the building to get from the table.
     * @return Building class obtained from the table. Contins all information
     * held in row.
     */
    public Building getRow(long id) {
        String[] projection = {
                Building._ID,
                Building.COLUMN_TITLE,
                Building.COLUMN_ROOM_NUM
        };
        Building building;
        String selection = Building._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(Building.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToNext()) {
                building = new Building(cursor.getString(Building.TITLE_POS),cursor.getString(Building.ROOM_NUM_POS));
                building.setID(cursor.getInt(Building.ID_POS));
                cursor.close();
                return building; //return only when the cursor has been closed.
                //Return statement never missed, try block always finishes this.
            } else {
                return null;
            }
        }
    }

    /**
     * Deletes the entire buildings table.
     */
    public void deleteTable() {
        getDatabase().delete(Building.TABLE_NAME, null, null);
    }

    /**
     * Changes information to one pre-existing building.
     *
     * @param oldBuilding building class that is being replaced.
     * @param newBuilding building class that holds the new information.
     * @return Building class containing updated information
     */
    public Building updateRow(Building oldBuilding, Building newBuilding) {
        ContentValues values = new ContentValues();
        values.put(Building.COLUMN_TITLE, newBuilding.getTitle());
        String selection = Building._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(oldBuilding.getID())};
        getDatabase().update(Building.TABLE_NAME, values, selection, selectionArgs);
        newBuilding.setID(oldBuilding.getID());
        return newBuilding;
    }
}