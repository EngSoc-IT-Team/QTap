package qlife.database.local.buildings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import qlife.database.local.DatabaseManager;
import qlife.database.local.DatabaseRow;

import java.util.ArrayList;

/**
 * Created by Carson on 26/06/2017.
 * Manages rows in Buildings table in phone database.
 */
public class BuildingManager extends DatabaseManager {
    public BuildingManager(Context context) {
        super(context);
    }

    @Override
    public void insertRow(DatabaseRow row) {
        if (row instanceof Building) {
            Building building = (Building) row;
            ContentValues values = new ContentValues();
            values.put(Building.ID, building.getId());
            values.put(Building.COLUMN_NAME, building.getName());
            values.put(Building.COLUMN_PURPOSE, building.getPurpose());
            values.put(Building.COLUMN_BOOK_ROOMS, building.getBookRooms());
            values.put(Building.COLUMN_FOOD, building.getFood());
            values.put(Building.COLUMN_ATM, building.getAtm());
            values.put(Building.COLUMN_LAT, building.getLat());
            values.put(Building.COLUMN_LON, building.getLon());
            getDatabase().insert(Building.TABLE_NAME, null, values);
        }
    }

    @Override
    public ArrayList<DatabaseRow> getTable() {
        String[] projection = {
                Building.ID,
                Building.COLUMN_NAME,
                Building.COLUMN_PURPOSE,
                Building.COLUMN_BOOK_ROOMS,
                Building.COLUMN_FOOD,
                Building.COLUMN_ATM,
                Building.COLUMN_LAT,
                Building.COLUMN_LON
        };
        ArrayList<DatabaseRow> buildings = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        //order by building name
        try (Cursor cursor = getDatabase().query(Building.TABLE_NAME, projection, null, null, null, null, Building.COLUMN_NAME + " ASC")) {
            while (cursor.moveToNext()) {
                Building building = getRow(cursor.getInt(Building.ID_POS));
                buildings.add(building);
            }
            cursor.close();
            return buildings; //return only when the cursor has been closed
        }
    }

    @Override
    public Building getRow(long id) {
        String[] projection = {
                Building.ID,
                Building.COLUMN_NAME,
                Building.COLUMN_PURPOSE,
                Building.COLUMN_BOOK_ROOMS,
                Building.COLUMN_FOOD,
                Building.COLUMN_ATM,
                Building.COLUMN_LAT,
                Building.COLUMN_LON
        };
        String selection = Building.ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(Building.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            cursor.moveToNext();
            //getInt()>0 because SQLite doesn't have boolean types - 1 is true, 0 is false
            Building building = new Building(cursor.getInt(Building.ID_POS), cursor.getString(Building.NAME_POS), cursor.getString(Building.PURPOSE_POS),
                    cursor.getInt(Building.BOOK_ROOKS_POS) > 0, cursor.getInt(Building.FOOD_POS) > 0, cursor.getInt(Building.ATM_POS) > 0,
                    cursor.getDouble(Building.LAT_POS), cursor.getDouble(Building.LON_POST));
            cursor.close();
            return building; //return only when the cursor has been closed.
            //Return statement never missed, try block always finishes this.
        }

    }

    @Override
    public void updateRow(DatabaseRow oldRow, DatabaseRow newRow) {
        if (oldRow instanceof Building && newRow instanceof Building) {
            Building oldBuilding = (Building) oldRow;
            Building newBuilding = (Building) newRow;

            ContentValues values = new ContentValues();
            values.put(Building.ID, newBuilding.getId());
            values.put(Building.COLUMN_NAME, newBuilding.getName());
            values.put(Building.COLUMN_PURPOSE, newBuilding.getPurpose());
            values.put(Building.COLUMN_BOOK_ROOMS, newBuilding.getBookRooms());
            values.put(Building.COLUMN_FOOD, newBuilding.getFood());
            values.put(Building.COLUMN_ATM, newBuilding.getAtm());
            values.put(Building.COLUMN_LAT, newBuilding.getLat());
            values.put(Building.COLUMN_LON, newBuilding.getLon());

            String selection = Building.ID + " LIKE ?";
            String selectionArgs[] = {String.valueOf(oldBuilding.getId())};
            getDatabase().update(Building.TABLE_NAME, values, selection, selectionArgs);
        }
    }
}
