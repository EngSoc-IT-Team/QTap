package qlife.database.local;

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

    /**
     * Method that deletes a table in the phone database.
     *
     * @param tableName The String name of the table to be deleted.
     */
    public void deleteTable(String tableName) {
        getDatabase().delete(tableName, null, null);
    }

    /**
     * Method that iserts a row into a table in the phone database. Which table is defined
     * by the child database manager
     *
     * @param row The data to be inserted.
     */
    public abstract void insertRow(DatabaseRow row);

    /**
     * Method that retrieves an entire table. Which table is defined by the child manager.
     *
     * @return ArrayList of the rows in the table.
     */
    public abstract ArrayList<DatabaseRow> getTable();

    /**
     * Method that retrieves a row from a table. Which table is defined by the child manager.
     *
     * @param id The ID of the row to get.
     * @return The row data.
     */
    public abstract DatabaseRow getRow(long id);

    /**
     * Method that changes information in an existing row. Which table is defined by
     * the child manager.
     *
     * @param oldRow The data of the row to be changed.
     * @param newRow The data to use for the change.
     */
    public abstract void updateRow(DatabaseRow oldRow, DatabaseRow newRow);
}
