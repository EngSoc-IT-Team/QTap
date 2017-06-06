package com.example.alex.qtapandroid.common.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Carson on 21/01/2017.
 * Manages opening and closing the database, as well as managing
 * the instances of the database helper class. Database managers extends this
 * class to access the database easily.
 */
public class DatabaseAccessor {
    private static SQLiteDatabase mDatabase;
    private DbHelper mDBHelper;
    private Context mContext;

    public DatabaseAccessor(Context context) {
        this.mContext = context;
        mDBHelper = com.example.alex.qtapandroid.common.database.DbHelper.getInstance(mContext);
        open();
    }

    /**
     * Creates an instance of the database helper and opens
     * a readable/writable instance of the database.
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        if (mDBHelper == null) {
            mDBHelper = com.example.alex.qtapandroid.common.database.DbHelper.getInstance(mContext);
        }
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public static SQLiteDatabase getDatabase(){
        return mDatabase;
    }
}

