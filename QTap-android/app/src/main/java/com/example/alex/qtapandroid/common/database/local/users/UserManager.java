package com.example.alex.qtapandroid.common.database.local.users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alex.qtapandroid.common.database.local.DatabaseAccessor;

import java.util.ArrayList;

/**
 * Created by Carson on 29/01/2017.
 * Holds all information for the courses table.
 * Manages the courses within the database. Inserts/deletes rows and the entire table.
 */
public class UserManager extends DatabaseAccessor {
    public UserManager(Context context) {
        super(context);
    }

    /**
     * Inserts a user into the database.
     *
     * @param user The user to be inserted. Before calling it must have
     *             the values to be inserted.
     * @return <long> The ID of the course just inserted. Set the id of the
     * the user inserted to be the return value.
     */
    public long insertRow(User user) {
        ContentValues values = new ContentValues();
        values.put(User.COLUMN_NETID, user.getNetid());
        values.put(User.COLUMN_FIRST_NAME, user.getFirstName());
        values.put(User.COLUMN_LAST_NAME, user.getLastName());
        values.put(User.COLUMN_DATE_INIT, user.getDateInit());
        values.put(User.COLUMN_ICS_URL, user.getIcsURL());

        return getDatabase().insert(User.TABLE_NAME, null, values);
    }

    /**
     * Deletes a user from the database.
     *
     * @param user The user to be deleted. Identifies which user
     *             using the ID of this parameter.
     */
    public void deleteRow(User user) {
        String selection = User._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(user.getID())};
        getDatabase().delete(User.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Gets the entire Users table.
     *
     * @return ArrayList of all the rows in the Users table.
     */
    public ArrayList<User> getTable() {
        String[] projection = {
                User._ID,
                User.COLUMN_NETID,
                User.COLUMN_FIRST_NAME,
                User.COLUMN_LAST_NAME,
                User.COLUMN_DATE_INIT,
                User.COLUMN_ICS_URL
        };
        ArrayList<User> users = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = getDatabase().query(User.TABLE_NAME, projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                User user = getRow(cursor.getInt(User.ID_POS));
                users.add(user);
            }
            cursor.close();
            return users; //return only when the cursor has been closed
        }
    }

    /**
     * Queries the Users table for a user with a given netID. Since netIDs are unique they
     * can be used to identify users.
     *
     * @param netid The netid searched for.
     * @return User class obtained from the table. Contains all information held in that row.
     */
    public User getRow(String netid) {
        String[] projection = {
                User._ID,
                User.COLUMN_NETID,
                User.COLUMN_FIRST_NAME,
                User.COLUMN_LAST_NAME,
                User.COLUMN_DATE_INIT,
                User.COLUMN_ICS_URL
        };
        User user;
        String selection = User.COLUMN_NETID + " LIKE ?";
        String[] selectionArgs = {netid};
        try (Cursor cursor = getDatabase().query(User.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToNext()) {
                user = new User(cursor.getString(User.NETID_POS), cursor.getString(User.FIRST_NAME_POS),
                        cursor.getString(User.LAST_NAME_POS), cursor.getString(User.DATE_INIT_POS), cursor.getString(User.ICS_URL_POS));
                user.setID(cursor.getInt(User.ID_POS));
                cursor.close();
                return user; //return only when the cursor has been closed.
                //Return statement never missed, try block always finishes this.
            } else {
                return null;
            }
        }
    }

    /**
     * Gets a single known user from the Users table.
     *
     * @param id ID of the user to get from the table.
     * @return User class obtained from the table. Contains all information
     * held in row.
     */
    public User getRow(long id) {
        String[] projection = {
                User._ID,
                User.COLUMN_NETID,
                User.COLUMN_FIRST_NAME,
                User.COLUMN_LAST_NAME,
                User.COLUMN_DATE_INIT,
                User.COLUMN_ICS_URL
        };
        User user;
        String selection = User._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(User.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            cursor.moveToNext();
            user = new User(cursor.getString(User.NETID_POS), cursor.getString(User.FIRST_NAME_POS),
                    cursor.getString(User.LAST_NAME_POS), cursor.getString(User.DATE_INIT_POS), cursor.getString(User.ICS_URL_POS));
            user.setID(cursor.getInt(User.ID_POS));
            cursor.close();
            return user; //return only when the cursor has been closed.
            //Return statement never missed, try block always finishes this.
        }

    }

    /**
     * Deletes the entire Users table.
     */
    public void deleteTable() {
        getDatabase().delete(User.TABLE_NAME, null, null);
    }

    /**
     * Changes information to one pre-existing user.
     *
     * @param oldUser User class that is being replaced.
     * @param newUser User class that holds the new information.
     * @return User class containing updated information
     */
    public User updateRow(User oldUser, User newUser) {
        ContentValues values = new ContentValues();
        values.put(User.COLUMN_NETID, newUser.getNetid());
        values.put(User.COLUMN_FIRST_NAME, newUser.getFirstName());
        values.put(User.COLUMN_LAST_NAME, newUser.getLastName());
        values.put(User.COLUMN_DATE_INIT, newUser.getDateInit());
        values.put(User.COLUMN_ICS_URL, newUser.getIcsURL());

        String selection = User._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(oldUser.getID())};
        getDatabase().update(User.TABLE_NAME, values, selection, selectionArgs);
        newUser.setID(oldUser.getID());
        return newUser;
    }
}
