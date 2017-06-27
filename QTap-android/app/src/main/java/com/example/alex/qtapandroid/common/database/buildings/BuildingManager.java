package com.example.alex.qtapandroid.common.database.buildings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alex.qtapandroid.common.database.DatabaseAccessor;

import java.util.ArrayList;

/**
 * Created by Carson on 26/06/2017.
 * Manages rows in Buildings table in phone database.
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
     * @return <long> The ID of the building just inserted. Set the id of the
     * the building inserted to be the return value.
     */
    public long insertRow(Building building) {
        ContentValues values = new ContentValues();
        values.put(Building.COLUMN_NAME, building.getName());
        values.put(Building.COLUMN_PURPOSE, building.getPurpose());
        values.put(Building.COLUMN_BOOK_ROOMS, building.getBookRooms());
        values.put(Building.COLUMN_FOOD, building.getFood());
        values.put(Building.COLUMN_ATM, building.getAtm());
        values.put(Building.COLUMN_LAT, building.getLat());
        values.put(Building.COLUMN_LON, building.getLon());

        return getDatabase().insert(Building.TABLE_NAME, null, values);
    }

    /**
     * Deletes a building from the database.
     *
     * @param building The building to be deleted. Identifies which building
     *                 using the ID of this parameter.
     */
    public void deleteRow(Building building) {
        String selection = Building._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(building.getID())};
        getDatabase().delete(Building.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Gets the entire building table.
     *
     * @return ArrayList of all the rows in the Building table.
     */
    public ArrayList<Building> getTable() {
        String[] projection = {
                Building._ID,
                Building.COLUMN_NAME,
                Building.COLUMN_PURPOSE,
                Building.COLUMN_BOOK_ROOMS,
                Building.COLUMN_FOOD,
                Building.COLUMN_ATM,
                Building.COLUMN_LAT,
                Building.COLUMN_LON
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
     * Gets a single known Building from the Building table.
     *
     * @param id ID of the Building to get from the table.
     * @return Building class obtained from the table. Contains all information
     * held in row.
     */
    public Building getRow(long id) {
        String[] projection = {
                Building._ID,
                Building.COLUMN_NAME,
                Building.COLUMN_PURPOSE,
                Building.COLUMN_BOOK_ROOMS,
                Building.COLUMN_FOOD,
                Building.COLUMN_ATM,
                Building.COLUMN_LAT,
                Building.COLUMN_LON
        };
        Building building;
        String selection = Building._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(Building.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            cursor.moveToNext();
            //getInt()>0 because SQLite doesn't have boolean types - 1 is true, 0 is false
            building = new Building(cursor.getString(Building.NAME_POS), cursor.getString(Building.PURPOSE_POS),
                    cursor.getInt(Building.BOOK_ROOKS_POS) > 0, cursor.getInt(Building.FOOD_POS) > 0, cursor.getInt(Building.ATM_POS) > 0,
                    cursor.getDouble(Building.LAT_POS), cursor.getDouble(Building.LON_POST));
            building.setID(cursor.getInt(Building.ID_POS));
            cursor.close();
            return building; //return only when the cursor has been closed.
            //Return statement never missed, try block always finishes this.
        }

    }

    /**
     * Deletes the entire building table.
     */
    public void deleteTable() {
        getDatabase().delete(Building.TABLE_NAME, null, null);
    }

    /**
     * Changes information to one pre-existing building.
     *
     * @param oldBuilding building class that is being replaced.
     * @param newBuilding building class that holds the new information.
     * @return building class containing updated information
     */
    public Building updateRow(Building oldBuilding, Building newBuilding) {
        ContentValues values = new ContentValues();
        values.put(Building.COLUMN_NAME, newBuilding.getName());
        values.put(Building.COLUMN_PURPOSE, newBuilding.getPurpose());
        values.put(Building.COLUMN_BOOK_ROOMS, newBuilding.getBookRooms());
        values.put(Building.COLUMN_FOOD, newBuilding.getFood());
        values.put(Building.COLUMN_ATM, newBuilding.getAtm());
        values.put(Building.COLUMN_LAT, newBuilding.getLat());
        values.put(Building.COLUMN_LON, newBuilding.getLon());

        String selection = Building._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(oldBuilding.getID())};
        getDatabase().update(Building.TABLE_NAME, values, selection, selectionArgs);
        newBuilding.setID(oldBuilding.getID());
        return newBuilding;
    }
}
