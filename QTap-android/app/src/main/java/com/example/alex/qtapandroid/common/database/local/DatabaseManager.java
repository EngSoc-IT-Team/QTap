package com.example.alex.qtapandroid.common.database.local;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Carson on 27/07/2017.
 * Abstract class that defines what methods a phone database manager must have, and provides some method bodies.
 */
public abstract class DatabaseManager extends DatabaseAccessor {

    public DatabaseManager(Context context) {
        super(context);
    }

    public void deleteRow(DatabaseRow row) {
        String selection = DatabaseRow.ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(row.getId())};
        getDatabase().delete(DatabaseRow.TABLE_NAME, selection, selectionArgs);
    }

    public void deleteTable(String tableName) {
        getDatabase().delete(tableName, null, null);
    }

    public abstract void insertRow(DatabaseRow row);

    public abstract ArrayList<DatabaseRow> getTable();

    public abstract DatabaseRow getRow(long id);

    public abstract void updateRow(DatabaseRow oldRow, DatabaseRow newRow);
}
