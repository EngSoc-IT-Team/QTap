package com.example.alex.qtapandroid.common.database.local.contacts.engineering;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alex.qtapandroid.common.database.local.DatabaseAccessor;
import com.example.alex.qtapandroid.common.database.local.DatabaseManager;
import com.example.alex.qtapandroid.common.database.local.DatabaseRow;

import java.util.ArrayList;

/**
 * Created by Carson on 21/06/2017.
 * Handles rows in phone database for EngineeringContact table.
 */
public class EngineeringContactsManager extends DatabaseManager {
    public EngineeringContactsManager(Context context) {
        super(context);
    }

    @Override
    public void insertRow(DatabaseRow row) {
        if (row instanceof EngineeringContact) {
            EngineeringContact contact = (EngineeringContact) row;
            ContentValues values = new ContentValues();
            values.put(EngineeringContact.ID, contact.getId());
            values.put(EngineeringContact.COLUMN_NAME, contact.getName());
            values.put(EngineeringContact.COLUMN_EMAIL, contact.getEmail());
            values.put(EngineeringContact.COLUMN_POSITION, contact.getPosition());
            values.put(EngineeringContact.COLUMN_DESCRIPTION, contact.getDescription());
            getDatabase().insert(EngineeringContact.TABLE_NAME, null, values);
        }
    }

    @Override
    public ArrayList<DatabaseRow> getTable() {
        String[] projection = {
                EngineeringContact.ID,
                EngineeringContact.COLUMN_NAME,
                EngineeringContact.COLUMN_EMAIL,
                EngineeringContact.COLUMN_POSITION,
                EngineeringContact.COLUMN_DESCRIPTION
        };
        ArrayList<DatabaseRow> contacts = new ArrayList<>();
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

    @Override
    public EngineeringContact getRow(long id) {
        String[] projection = {
                EngineeringContact.ID,
                EngineeringContact.COLUMN_NAME,
                EngineeringContact.COLUMN_EMAIL,
                EngineeringContact.COLUMN_POSITION,
                EngineeringContact.COLUMN_DESCRIPTION
        };
        EngineeringContact contact;
        String selection = EngineeringContact.ID + " LIKE ?";
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

    @Override
    public void updateRow(DatabaseRow oldRow, DatabaseRow newRow) {
        if (oldRow instanceof EngineeringContact && newRow instanceof EngineeringContact) {
            EngineeringContact oldContact = (EngineeringContact) oldRow;
            EngineeringContact newContact = (EngineeringContact) newRow;

            ContentValues values = new ContentValues();
            values.put(EngineeringContact.ID, newContact.getId());
            values.put(EngineeringContact.COLUMN_NAME, newContact.getName());
            values.put(EngineeringContact.COLUMN_EMAIL, newContact.getEmail());
            values.put(EngineeringContact.COLUMN_POSITION, newContact.getPosition());
            values.put(EngineeringContact.COLUMN_DESCRIPTION, newContact.getDescription());

            String selection = EngineeringContact.ID + " LIKE ?";
            String selectionArgs[] = {String.valueOf(oldContact.getId())};
            getDatabase().update(EngineeringContact.TABLE_NAME, values, selection, selectionArgs);
        }
    }
}
