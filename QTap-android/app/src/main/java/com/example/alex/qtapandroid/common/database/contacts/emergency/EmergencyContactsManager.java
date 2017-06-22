package com.example.alex.qtapandroid.common.database.contacts.emergency;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alex.qtapandroid.common.database.DatabaseAccessor;
import com.example.alex.qtapandroid.common.database.users.User;

import java.util.ArrayList;

/**
 * Created by Carson on 21/06/2017.
 * Manages rows in EmergencyContacts table in phone database.
 */

public class EmergencyContactsManager extends DatabaseAccessor {
    public EmergencyContactsManager(Context context) {
        super(context);
    }

    /**
     * Inserts a EmergencyContacts into the database.
     *
     * @param contact The EmergencyContacts to be inserted. Before calling it must have
     *                the values to be inserted.
     * @return <long> The ID of the course just inserted. Set the id of the
     * the EmergencyContacts inserted to be the return value.
     */
    public long insertRow(EmergencyContacts contact) {
        ContentValues values = new ContentValues();
        values.put(EmergencyContacts.COLUMN_NAME, contact.getName());
        values.put(EmergencyContacts.COLUMN_PHONE_NUMBER, contact.getPhoneNumber());
        values.put(EmergencyContacts.COLUMN_DESCRIPTION, contact.getDescription());

        return getDatabase().insert(EmergencyContacts.TABLE_NAME, null, values);
    }

    /**
     * Deletes a EmergencyContacts from the database.
     *
     * @param contact The EmergencyContacts to be deleted. Identifies which EmergencyContacts
     *                using the ID of this parameter.
     */
    public void deleteRow(EmergencyContacts contact) {
        String selection = EmergencyContacts._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(contact.getID())};
        getDatabase().delete(EmergencyContacts.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Gets the entire EmergencyContacts table.
     *
     * @return ArrayList of all the rows in the EmergencyContacts table.
     */
    public ArrayList<EmergencyContacts> getTable() {
        String[] projection = {
                EmergencyContacts._ID,
                EmergencyContacts.COLUMN_NAME,
                EmergencyContacts.COLUMN_PHONE_NUMBER,
                EmergencyContacts.COLUMN_DESCRIPTION,
        };
        ArrayList<EmergencyContacts> contacts = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = getDatabase().query(EmergencyContacts.TABLE_NAME, projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                EmergencyContacts contact = getRow(cursor.getInt(EmergencyContacts.ID_POS));
                contacts.add(contact);
            }
            cursor.close();
            return contacts; //return only when the cursor has been closed
        }
    }

    /**
     * Gets a single known EmergencyContacts from the EmergencyContacts table.
     *
     * @param id ID of the EmergencyContacts to get from the table.
     * @return EmergencyContacts class obtained from the table. Contains all information
     * held in row.
     */
    public EmergencyContacts getRow(long id) {
        String[] projection = {
                EmergencyContacts._ID,
                EmergencyContacts.COLUMN_NAME,
                EmergencyContacts.COLUMN_PHONE_NUMBER,
                EmergencyContacts.COLUMN_DESCRIPTION,
        };
        EmergencyContacts contact;
        String selection = EmergencyContacts._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(EmergencyContacts.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            cursor.moveToNext();
            contact = new EmergencyContacts(cursor.getString(EmergencyContacts.NAME_POS), cursor.getString(EmergencyContacts.PHONE_NUMBER_POS),
                    cursor.getString(EmergencyContacts.DESCRIPTION_POS));
            contact.setID(cursor.getInt(EmergencyContacts.ID_POS));
            cursor.close();
            return contact; //return only when the cursor has been closed.
            //Return statement never missed, try block always finishes this.
        }

    }

    /**
     * Deletes the entire EmergencyContacts table.
     */
    public void deleteTable() {
        getDatabase().delete(EmergencyContacts.TABLE_NAME, null, null);
    }

    /**
     * Changes information to one pre-existing contact.
     *
     * @param oldContact EmergencyContacts class that is being replaced.
     * @param newContact EmergencyContacts class that holds the new information.
     * @return EmergencyContacts class containing updated information
     */
    public EmergencyContacts updateRow(EmergencyContacts oldContact, EmergencyContacts newContact) {
        ContentValues values = new ContentValues();
        values.put(EmergencyContacts.COLUMN_NAME, newContact.getName());
        values.put(EmergencyContacts.COLUMN_PHONE_NUMBER, newContact.getPhoneNumber());
        values.put(EmergencyContacts.COLUMN_DESCRIPTION, newContact.getDescription());

        String selection = EmergencyContacts._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(oldContact.getID())};
        getDatabase().update(EmergencyContacts.TABLE_NAME, values, selection, selectionArgs);
        newContact.setID(oldContact.getID());
        return newContact;
    }
}
