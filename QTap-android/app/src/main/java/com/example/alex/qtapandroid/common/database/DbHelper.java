package com.example.alex.qtapandroid.common.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alex.qtapandroid.common.database.building.Building;
import com.example.alex.qtapandroid.common.database.course.Course;

/**
 * Created by Carson on 19/01/2017.
 * Manages creating/upgrading/downgrading the database.
 * Also holds static SQL query strings to create/delete the database.
 */
public class DBHelper extends SQLiteOpenHelper {

    //sql query strings to create/delete each table with its fields **HARD CODED**
    private static final String SQL_CREATE_CLASSES = "CREATE TABLE " + Course.TABLE_NAME + "(" +
            Course._ID + " INTEGER PRIMARY KEY," +
            Course.COLUMN_TITLE + " TEXT," +
            Course.COLUMN_ROOM_NUM + " TEXT," + Course.COLUMN_TIME + " TEXT, " +
            Course.COLUMN_BUILDING_ID + " INT, " + "FOREIGN KEY (" +
            Course.COLUMN_BUILDING_ID + ") REFERENCES " +
            Building.TABLE_NAME + "(id) );";

    private static final String SQL_CREATE_BUILDINGS = "CREATE TABLE " + Building.TABLE_NAME + "(" +
            Building._ID + " INTEGER PRIMARY KEY," +
            Building.COLUMN_NAME + " TEXT);";

    private static final String SQL_DELETE_CLASSES = "DROP TABLE IF EXISTS " + Course.TABLE_NAME;
    private static final String SQL_DELETE_BUILDINGS = "DROP TABLE IF EXISTS " + Building.TABLE_NAME;

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "QTap.db";

    private static DBHelper mInstance = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates an instance of the DBHelper. This method ensures that only one instance
     * of DBHelper can be created at once.
     *
     * @param context Context to create the DBHelper for.
     * @return returns the instance of DBHelper.
     */
    public static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            //use application context so as to not accidentally leak application context in database.
            mInstance = new DBHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CLASSES);
        db.execSQL(SQL_CREATE_BUILDINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //deletes the database and then re-creates the new version
        db.execSQL(SQL_DELETE_CLASSES);
        db.execSQL(SQL_DELETE_BUILDINGS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}