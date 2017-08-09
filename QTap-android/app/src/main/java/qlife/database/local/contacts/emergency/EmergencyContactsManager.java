package qlife.database.local.contacts.emergency;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import qlife.database.local.DatabaseManager;
import qlife.database.local.DatabaseRow;

import java.util.ArrayList;

/**
 * Created by Carson on 21/06/2017.
 * Manages rows in EmergencyContact table in phone database.
 */

public class EmergencyContactsManager extends DatabaseManager {
    public EmergencyContactsManager(Context context) {
        super(context);
    }

    @Override
    public void insertRow(DatabaseRow row) {
        if (row instanceof EmergencyContact) {
            EmergencyContact contact = (EmergencyContact) row;
            ContentValues values = new ContentValues();
            values.put(EmergencyContact.ID, contact.getId());
            values.put(EmergencyContact.COLUMN_NAME, contact.getName());
            values.put(EmergencyContact.COLUMN_PHONE_NUMBER, contact.getPhoneNumber());
            values.put(EmergencyContact.COLUMN_DESCRIPTION, contact.getDescription());
            getDatabase().insert(EmergencyContact.TABLE_NAME, null, values);
        }
    }

    @Override
    public ArrayList<DatabaseRow> getTable() {
        String[] projection = {
                EmergencyContact.ID,
                EmergencyContact.COLUMN_NAME,
                EmergencyContact.COLUMN_PHONE_NUMBER,
                EmergencyContact.COLUMN_DESCRIPTION,
        };
        ArrayList<DatabaseRow> contacts = new ArrayList<>();
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

    @Override
    public EmergencyContact getRow(long id) {
        String[] projection = {
                EmergencyContact.ID,
                EmergencyContact.COLUMN_NAME,
                EmergencyContact.COLUMN_PHONE_NUMBER,
                EmergencyContact.COLUMN_DESCRIPTION,
        };
        EmergencyContact contact;
        String selection = EmergencyContact.ID + " LIKE ?";
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

    @Override
    public void updateRow(DatabaseRow oldRow, DatabaseRow newRow) {
        if (oldRow instanceof EmergencyContact && newRow instanceof EmergencyContact) {
            EmergencyContact oldContact = (EmergencyContact) oldRow;
            EmergencyContact newContact = (EmergencyContact) newRow;

            ContentValues values = new ContentValues();
            values.put(EmergencyContact.ID, newContact.getId());
            values.put(EmergencyContact.COLUMN_NAME, newContact.getName());
            values.put(EmergencyContact.COLUMN_PHONE_NUMBER, newContact.getPhoneNumber());
            values.put(EmergencyContact.COLUMN_DESCRIPTION, newContact.getDescription());

            String selection = EmergencyContact.ID + " LIKE ?";
            String selectionArgs[] = {String.valueOf(oldContact.getId())};
            getDatabase().update(EmergencyContact.TABLE_NAME, values, selection, selectionArgs);
        }
    }
}
