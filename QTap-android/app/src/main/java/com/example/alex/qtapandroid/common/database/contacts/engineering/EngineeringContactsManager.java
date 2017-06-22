package com.example.alex.qtapandroid.common.database.contacts.engineering;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.example.alex.qtapandroid.common.database.DatabaseAccessor;

import java.util.ArrayList;

/**
 * Created by Carson on 21/06/2017.
 * Handles rows in phone database for EngineeringContacts table.
 */
public class EngineeringContactsManager extends DatabaseAccessor {
    public EngineeringContactsManager(Context context) {
        super(context);
    }

    /**
     * Inserts a EngineeringContacts into the database.
     *
     * @param contacts The EngineeringContacts to be inserted. Before calling it must have
     *                 the values to be inserted.
     * @return <long> The ID of the course just inserted. Set the id of the
     * the EngineeringContacts inserted to be the return value.
     */
    public long insertRow(EngineeringContacts contacts) {
        ContentValues values = new ContentValues();
        values.put(EngineeringContacts.COLUMN_NAME, contacts.getName());
        values.put(EngineeringContacts.COLUMN_EMAIL, contacts.getEmail());
        values.put(EngineeringContacts.COLUMN_POSITION, contacts.getPosition());
        values.put(EngineeringContacts.COLUMN_DESCRIPTION, contacts.getDescription());
        return getDatabase().insert(EngineeringContacts.TABLE_NAME, null, values);
    }

    /**
     * Deletes a EngineeringContacts from the database.
     *
     * @param contact The EngineeringContacts to be deleted. Identifies which EngineeringContacts
     *                using the ID of this parameter.
     */
    public void deleteRow(EngineeringContacts contact) {
        String selection = EngineeringContacts._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(contact.getID())};
        getDatabase().delete(EngineeringContacts.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Gets the entire EngineeringContacts table.
     *
     * @return ArrayList of all the rows in the EngineeringContacts table.
     */
    public ArrayList<EngineeringContacts> getTable() {
        String[] projection = {
                EngineeringContacts._ID,
                EngineeringContacts.COLUMN_NAME,
                EngineeringContacts.COLUMN_EMAIL,
                EngineeringContacts.COLUMN_POSITION,
                EngineeringContacts.COLUMN_DESCRIPTION
        };
        ArrayList<EngineeringContacts> contacts = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = getDatabase().query(EngineeringContacts.TABLE_NAME, projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                EngineeringContacts contact = getRow(cursor.getInt(EngineeringContacts.ID_POS));
                contacts.add(contact);
            }
            cursor.close();
            return contacts; //return only when the cursor has been closed
        }
    }

    /**
     * Gets a single known EngineeringContacts from the EngineeringContacts table.
     *
     * @param id ID of the EngineeringContacts to get from the table.
     * @return EngineeringContacts class obtained from the table. Contains all information
     * held in row.
     */
    public EngineeringContacts getRow(long id) {
        String[] projection = {
                EngineeringContacts._ID,
                EngineeringContacts.COLUMN_NAME,
                EngineeringContacts.COLUMN_EMAIL,
                EngineeringContacts.COLUMN_POSITION,
                EngineeringContacts.COLUMN_DESCRIPTION
        };
        EngineeringContacts contact;
        String selection = EngineeringContacts._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(EngineeringContacts.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            cursor.moveToNext();
            contact = new EngineeringContacts(cursor.getString(EngineeringContacts.NAME_POS), cursor.getString(EngineeringContacts.EMAIL_POS),
                    cursor.getString(EngineeringContacts.POSITION_POS), cursor.getString(EngineeringContacts.DESCRIPTION_POS));
            contact.setID(cursor.getInt(EngineeringContacts.ID_POS));
            cursor.close();
            return contact; //return only when the cursor has been closed.
            //Return statement never missed, try block always finishes this.
        }

    }

    /**
     * Deletes the entire EngineeringContacts table.
     */
    public void deleteTable() {
        getDatabase().delete(EngineeringContacts.TABLE_NAME, null, null);
    }

    /**
     * Changes information to one pre-existing EngineeringContacts.
     *
     * @param oldContact EngineeringContacts class that is being replaced.
     * @param newContact EngineeringContacts class that holds the new information.
     * @return EngineeringContacts class containing updated information
     */
    public EngineeringContacts updateRow(EngineeringContacts oldContact, EngineeringContacts newContact) {
        ContentValues values = new ContentValues();
        values.put(EngineeringContacts.COLUMN_NAME, newContact.getName());
        values.put(EngineeringContacts.COLUMN_EMAIL, newContact.getEmail());
        values.put(EngineeringContacts.COLUMN_POSITION, newContact.getPosition());
        values.put(EngineeringContacts.COLUMN_DESCRIPTION, newContact.getDescription());

        String selection = EngineeringContacts._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(oldContact.getID())};
        getDatabase().update(EngineeringContacts.TABLE_NAME, values, selection, selectionArgs);
        newContact.setID(oldContact.getID());
        return newContact;
    }
}
