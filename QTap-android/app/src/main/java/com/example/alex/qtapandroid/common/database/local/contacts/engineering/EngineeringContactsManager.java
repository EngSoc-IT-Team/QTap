package com.example.alex.qtapandroid.common.database.local.contacts.engineering;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alex.qtapandroid.common.database.local.DatabaseAccessor;

import java.util.ArrayList;

/**
 * Created by Carson on 21/06/2017.
 * Handles rows in phone database for EngineeringContact table.
 */
public class EngineeringContactsManager extends DatabaseAccessor {
    public EngineeringContactsManager(Context context) {
        super(context);
    }

    /**
     * Inserts a EngineeringContact into the database.
     *
     * @param contacts The EngineeringContact to be inserted. Before calling it must have
     *                 the values to be inserted.
     * @return <long> The ID of the course just inserted. Set the id of the
     * the EngineeringContact inserted to be the return value.
     */
    public long insertRow(EngineeringContact contacts) {
        ContentValues values = new ContentValues();
        values.put(EngineeringContact._ID, contacts.getID());
        values.put(EngineeringContact.COLUMN_NAME, contacts.getName());
        values.put(EngineeringContact.COLUMN_EMAIL, contacts.getEmail());
        values.put(EngineeringContact.COLUMN_POSITION, contacts.getPosition());
        values.put(EngineeringContact.COLUMN_DESCRIPTION, contacts.getDescription());
        return getDatabase().insert(EngineeringContact.TABLE_NAME, null, values);
    }

    /**
     * Deletes a EngineeringContact from the database.
     *
     * @param contact The EngineeringContact to be deleted. Identifies which EngineeringContact
     *                using the ID of this parameter.
     */
    public void deleteRow(EngineeringContact contact) {
        String selection = EngineeringContact._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(contact.getID())};
        getDatabase().delete(EngineeringContact.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Gets the entire EngineeringContact table.
     *
     * @return ArrayList of all the rows in the EngineeringContact table.
     */
    public ArrayList<EngineeringContact> getTable() {
        String[] projection = {
                EngineeringContact._ID,
                EngineeringContact.COLUMN_NAME,
                EngineeringContact.COLUMN_EMAIL,
                EngineeringContact.COLUMN_POSITION,
                EngineeringContact.COLUMN_DESCRIPTION
        };
        ArrayList<EngineeringContact> contacts = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = getDatabase().query(EngineeringContact.TABLE_NAME, projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                EngineeringContact contact = getRow(cursor.getInt(EngineeringContact.ID_POS));
                contacts.add(contact);
            }
            cursor.close();
            return contacts; //return only when the cursor has been closed
        }
    }

    /**
     * Gets a single known EngineeringContact from the EngineeringContact table.
     *
     * @param id ID of the EngineeringContact to get from the table.
     * @return EngineeringContact class obtained from the table. Contains all information
     * held in row.
     */
    public EngineeringContact getRow(long id) {
        String[] projection = {
                EngineeringContact._ID,
                EngineeringContact.COLUMN_NAME,
                EngineeringContact.COLUMN_EMAIL,
                EngineeringContact.COLUMN_POSITION,
                EngineeringContact.COLUMN_DESCRIPTION
        };
        EngineeringContact contact;
        String selection = EngineeringContact._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(EngineeringContact.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            cursor.moveToNext();
            contact = new EngineeringContact(cursor.getInt(EngineeringContact.ID_POS), cursor.getString(EngineeringContact.NAME_POS), cursor.getString(EngineeringContact.EMAIL_POS),
                    cursor.getString(EngineeringContact.POSITION_POS), cursor.getString(EngineeringContact.DESCRIPTION_POS));
            cursor.close();
            return contact; //return only when the cursor has been closed.
            //Return statement never missed, try block always finishes this.
        }

    }

    /**
     * Deletes the entire EngineeringContact table.
     */
    public void deleteTable() {
        getDatabase().delete(EngineeringContact.TABLE_NAME, null, null);
    }

    /**
     * Changes information to one pre-existing EngineeringContact.
     *
     * @param oldContact EngineeringContact class that is being replaced.
     * @param newContact EngineeringContact class that holds the new information.
     * @return EngineeringContact class containing updated information
     */
    public EngineeringContact updateRow(EngineeringContact oldContact, EngineeringContact newContact) {
        ContentValues values = new ContentValues();
        values.put(EngineeringContact._ID, newContact.getID());
        values.put(EngineeringContact.COLUMN_NAME, newContact.getName());
        values.put(EngineeringContact.COLUMN_EMAIL, newContact.getEmail());
        values.put(EngineeringContact.COLUMN_POSITION, newContact.getPosition());
        values.put(EngineeringContact.COLUMN_DESCRIPTION, newContact.getDescription());

        String selection = EngineeringContact._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(oldContact.getID())};
        getDatabase().update(EngineeringContact.TABLE_NAME, values, selection, selectionArgs);
        return newContact;
    }
}
