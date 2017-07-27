package com.example.alex.qtapandroid.common.database.local.courses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alex.qtapandroid.common.database.local.DatabaseAccessor;

import java.util.ArrayList;

/**
 * Created by Carson on 07/02/2017.
 */
public class OneClassManager extends DatabaseAccessor {
    public OneClassManager(Context context) {
        super(context);
    }

    /**
     * Inserts a class into the database.
     *
     * @param oneClass The class to be inserted. Before calling it must have
     *                 the values to be inserted.
     * @return <long> The ID of the class just inserted. Set the id of the
     * the class inserted to be the return value.
     */
    public long insertRow(OneClass oneClass) {
        ContentValues values = new ContentValues();
        values.put(OneClass.COLUMN_CLASS_TYPE, oneClass.getType());
        values.put(OneClass.COLUMN_BUILDING_ID, oneClass.getBuildingID());
        values.put(OneClass.COLUMN_ROOM_NUM, oneClass.getRoomNum());
        values.put(OneClass.COLUMN_START_TIME, oneClass.getStartTime());
        values.put(OneClass.COLUMN_END_TIME, oneClass.getEndTime());
        values.put(OneClass.COLUMN_DAY, oneClass.getDay());
        values.put(OneClass.COLUMN_MONTH, oneClass.getMonth());
        values.put(OneClass.COLUMN_YEAR, oneClass.getYear());
        values.put(OneClass.COLUMN_COURSE_ID, oneClass.getCourseID());
        return getDatabase().insert(OneClass.TABLE_NAME, null, values);
    }

    /**
     * Deletes a class from the database.
     *
     * @param oneClass The class to be deleted. Identifies which class
     *                 using the ID of this parameter.
     */
    public void deleteRow(OneClass oneClass) {
        String selection = OneClass._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(oneClass.getID())};
        getDatabase().delete(OneClass.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Gets the entire OneClasses table.
     *
     * @return ArrayList of all the rows in the oneClasses table.
     */
    public ArrayList<OneClass> getTable() {
        String[] projection = {
                OneClass._ID,
                OneClass.COLUMN_CLASS_TYPE,
                OneClass.COLUMN_BUILDING_ID,
                OneClass.COLUMN_ROOM_NUM,
                OneClass.COLUMN_START_TIME,
                OneClass.COLUMN_END_TIME,
                OneClass.COLUMN_DAY,
                OneClass.COLUMN_MONTH,
                OneClass.COLUMN_YEAR,
                OneClass.COLUMN_COURSE_ID
        };

        ArrayList<OneClass> classes = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = getDatabase().query(OneClass.TABLE_NAME, projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                OneClass oneClass = new OneClass(cursor.getString(OneClass.CLASS_TYPE_POS),
                        cursor.getString(OneClass.ROOM_NUM_POS), cursor.getString(OneClass.STIME_POS),
                        cursor.getString(OneClass.ETIME_POS),
                        cursor.getString(OneClass.DAY_POS), cursor.getString(OneClass.MONTH_POS),
                        cursor.getString(OneClass.YEAR_POS));
                oneClass.setID(cursor.getInt(OneClass.ID_POS));
                oneClass.setBuildingID(cursor.getInt(OneClass.BUILDING_ID_POS));
                oneClass.setCourseID(cursor.getInt(OneClass.COURSE_ID_POS));
                classes.add(oneClass);
            }
            cursor.close();
            return classes; //return only when the cursor has been closed
        }
    }

    /**
     * Gets a single class from the OneClass table.
     *
     * @param id ID of the class to get from the table.
     * @return OneClass class obtained from the table. Contains all information
     * held in row.
     */
    public OneClass getRow(long id) {
        String[] projection = {
                OneClass._ID,
                OneClass.COLUMN_CLASS_TYPE,
                OneClass.COLUMN_BUILDING_ID,
                OneClass.COLUMN_ROOM_NUM,
                OneClass.COLUMN_START_TIME,
                OneClass.COLUMN_END_TIME,
                OneClass.COLUMN_DAY,
                OneClass.COLUMN_MONTH,
                OneClass.COLUMN_YEAR,
                OneClass.COLUMN_COURSE_ID
        };
        OneClass oneClass;
        String selection = OneClass._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(OneClass.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToNext()) {
                oneClass = new OneClass(cursor.getString(OneClass.CLASS_TYPE_POS),
                        cursor.getString(OneClass.ROOM_NUM_POS), cursor.getString(OneClass.STIME_POS),
                        cursor.getString(OneClass.ETIME_POS),
                        cursor.getString(OneClass.DAY_POS), cursor.getString(OneClass.MONTH_POS),
                        cursor.getString(OneClass.YEAR_POS));
                oneClass.setID(cursor.getInt(OneClass.ID_POS));
                oneClass.setBuildingID(cursor.getInt(OneClass.BUILDING_ID_POS));
                oneClass.setCourseID(cursor.getInt(OneClass.COURSE_ID_POS));
                cursor.close();
                return oneClass; //return only when the cursor has been closed.
                //Return statement never missed, try block always finishes this.
            } else {
                return null;
            }
        }
    }

    /**
     * Deletes the entire OneClass table.
     */
    public void deleteTable() {
        getDatabase().delete(OneClass.TABLE_NAME, null, null);
    }

    /**
     * Changes information to one pre-existing class.
     *
     * @param oldClass OneClass class that is being replaced.
     * @param newClass OneClass class that holds the new information.
     * @return OneClass class containing updated information
     */
    public OneClass updateRow(OneClass oldClass, OneClass newClass) {
        ContentValues values = new ContentValues();
        values.put(OneClass.COLUMN_CLASS_TYPE, newClass.getType());
        values.put(OneClass.COLUMN_ROOM_NUM, newClass.getRoomNum());
        values.put(OneClass.COLUMN_START_TIME, newClass.getStartTime());
        values.put(OneClass.COLUMN_END_TIME, newClass.getEndTime());
        values.put(OneClass.COLUMN_DAY, newClass.getDay());
        values.put(OneClass.COLUMN_MONTH, newClass.getMonth());
        values.put(OneClass.COLUMN_YEAR, newClass.getYear());
        values.put(OneClass.COLUMN_COURSE_ID, newClass.getCourseID());
        String selection = OneClass._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(oldClass.getID())};
        getDatabase().update(OneClass.TABLE_NAME, values, selection, selectionArgs);
        newClass.setID(oldClass.getID());
        return newClass;
    }
}
