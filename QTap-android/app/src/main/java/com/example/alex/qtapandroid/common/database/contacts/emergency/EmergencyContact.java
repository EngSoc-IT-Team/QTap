package com.example.alex.qtapandroid.common.database.contacts.emergency;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Carson on 21/06/2017.
 * Schema for phone database EmergencyContact table.
 */
public class EmergencyContact implements BaseColumns {
    public static final String TABLE_NAME = "EmergencyContacts";
    //columns
    public static final String ID = "ID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_PHONE_NUMBER = "PhoneNumber";
    public static final String COLUMN_DESCRIPTION = "Description";

    //order of columns
    private long id;
    public static final int ID_POS = 0;
    public static final int NAME_POS = 1;
    public static final int PHONE_NUMBER_POS = 2;
    public static final int DESCRIPTION_POS = 3;

    //fields in database
    private String name, phoneNumber, description;

    public EmergencyContact(long id, String name, String phoneNumber, String description) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }

    public static void printEmergencyContacts(ArrayList<EmergencyContact> contacts) {
        String output = "EMERG CONTACTS:\n";
        for (int i = 0; i < contacts.size(); i++) {
            output += "ID: " + contacts.get(i).getID() + " NAME: " + contacts.get(i).getName() + " PHONENUMBER: "
                    + contacts.get(i).getPhoneNumber() + " DESCRIPTION: " + contacts.get(i).getDescription();
        }
        Log.d("SQLITEEMERG", output);
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
