package com.example.alex.qtapandroid.common.database.contacts.emergency;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alex.qtapandroid.common.database.DatabaseAccessor;

import java.util.ArrayList;

/**
 * Created by Carson on 21/06/2017.
 * Manages rows in EmergencyContact table in phone database.
 */

public class EmergencyContactsManager extends DatabaseAccessor {
    public EmergencyContactsManager(Context context) {
        super(context);
    }

    /**
     * Inserts a EmergencyContact into the database.
     *
     * @param contact The EmergencyContact to be inserted. Before calling it must have
     *                the values to be inserted.
     * @return <long> The ID of the course just inserted. Set the id of the
     * the EmergencyContact inserted to be the return value.
     */
    public long insertRow(EmergencyContact contact) {
        ContentValues values = new ContentValues();
        values.put(EmergencyContact._ID, contact.getID());
        values.put(EmergencyContact.COLUMN_NAME, contact.getName());
        values.put(EmergencyContact.COLUMN_PHONE_NUMBER, contact.getPhoneNumber());
        values.put(EmergencyContact.COLUMN_DESCRIPTION, contact.getDescription());

        return getDatabase().insert(EmergencyContact.TABLE_NAME, null, values);
    }

    /**
     * Deletes a EmergencyContact from the database.
     *
     * @param contact The EmergencyContact to be deleted. Identifies which EmergencyContact
     *                using the ID of this parameter.
     */
    public void deleteRow(EmergencyContact contact) {
        String selection = EmergencyContact._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(contact.getID())};
        getDatabase().delete(EmergencyContact.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Gets the entire EmergencyContact table.
     *
     * @return ArrayList of all the rows in the EmergencyContact table.
     */
    public ArrayList<EmergencyContact> getTable() {
        String[] projection = {
                EmergencyContact._ID,
                EmergencyContact.COLUMN_NAME,
                EmergencyContact.COLUMN_PHONE_NUMBER,
                EmergencyContact.COLUMN_DESCRIPTION,
        };
        ArrayList<EmergencyContact> contacts = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = getDatabase().query(EmergencyContact.TABLE_NAME, projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                EmergencyContact contact = getRow(cursor.getInt(EmergencyContact.ID_POS));
                contacts.add(contact);
            }
            cursor.close();
            return contacts; //return only when the cursor has been closed
        }
    }

    /**
     * Gets a single known EmergencyContact from the EmergencyContact table.
     *
     * @param id ID of the EmergencyContact to get from the table.
     * @return EmergencyContact class obtained from the table. Contains all information
     * held in row.
     */
    public EmergencyContact getRow(long id) {
        String[] projection = {
                EmergencyContact._ID,
                EmergencyContact.COLUMN_NAME,
                EmergencyContact.COLUMN_PHONE_NUMBER,
                EmergencyContact.COLUMN_DESCRIPTION,
        };
        EmergencyContact contact;
        String selection = EmergencyContact._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(EmergencyContact.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            cursor.moveToNext();
            contact = new EmergencyContact(cursor.getInt(EmergencyContact.ID_POS), cursor.getString(EmergencyContact.NAME_POS), cursor.getString(EmergencyContact.PHONE_NUMBER_POS),
                    cursor.getString(EmergencyContact.DESCRIPTION_POS));
            cursor.close();
            return contact; //return only when the cursor has been closed.
            //Return statement never missed, try block always finishes this.
        }

    }

    /**
     * Deletes the entire EmergencyContact table.
     */
    public void deleteTable() {
        getDatabase().delete(EmergencyContact.TABLE_NAME, null, null);
    }

    /**
     * Changes information to one pre-existing contact.
     *
     * @param oldContact EmergencyContact class that is being replaced.
     * @param newContact EmergencyContact class that holds the new information.
     * @return EmergencyContact class containing updated information
     */
    public EmergencyContact updateRow(EmergencyContact oldContact, EmergencyContact newContact) {
        ContentValues values = new ContentValues();
        values.put(EmergencyContact._ID, newContact.getID());
        values.put(EmergencyContact.COLUMN_NAME, newContact.getName());
        values.put(EmergencyContact.COLUMN_PHONE_NUMBER, newContact.getPhoneNumber());
        values.put(EmergencyContact.COLUMN_DESCRIPTION, newContact.getDescription());

        String selection = EmergencyContact._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(oldContact.getID())};
        getDatabase().update(EmergencyContact.TABLE_NAME, values, selection, selectionArgs);
        return newContact;
    }
}
