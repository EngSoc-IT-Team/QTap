package com.example.alex.qtapandroid.common.database.contacts.engineering;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Carson on 21/06/2017.
 * Defines schema for phone database table EngineeringContact
 */
public class EngineeringContact implements BaseColumns {
    public static final String TABLE_NAME = "EngineeringContacts";
    //columns
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_POSITION = "Position";
    public static final String COLUMN_DESCRIPTION = "Description";

    //order of columns
    private long id;
    public static final int ID_POS = 0;
    public static final int NAME_POS = 1;
    public static final int EMAIL_POS = 2;
    public static final int POSITION_POS = 3;
    public static final int DESCRIPTION_POS = 4;

    //fields in database
    private String name, email, position, description;

    public EngineeringContact(String name, String email, String position, String description) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.description = description;
    }

    public static void printEngineeringContacts(ArrayList<EngineeringContact> contacts) {
        String output = "ENG CONTACTS:\n";
        for (int i = 0; i < contacts.size(); i++) {
            output += "ID: " + contacts.get(i).getID() + " NAME: " + contacts.get(i).getName() + " EMAIL: "
                    + contacts.get(i).getEmail() + " POSITION: " + contacts.get(i).getPosition() + " DESCRIPTION: " + contacts.get(i).getDescription();
        }
        Log.d("SQLITE", output);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
