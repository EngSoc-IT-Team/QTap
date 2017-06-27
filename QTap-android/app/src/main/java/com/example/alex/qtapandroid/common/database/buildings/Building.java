package com.example.alex.qtapandroid.common.database.buildings;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Carson on 26/06/2017.
 * Class that defines schema for Buildings table in phone database.
 */
public class Building implements BaseColumns {
    public static final String TABLE_NAME = "Buildings";
    //columns
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_PURPOSE = "Purpose";
    public static final String COLUMN_BOOK_ROOMS = "BookRooms";
    public static final String COLUMN_FOOD = "Food";
    public static final String COLUMN_ATM = "ATM";
    public static final String COLUMN_LAT = "Lat";
    public static final String COLUMN_LON = "Lon";

    //order of columns
    public static final int ID_POS = 0;
    public static final int NAME_POS = 1;
    public static final int PURPOSE_POS = 2;
    public static final int BOOK_ROOKS_POS = 3;
    public static final int FOOD_POS = 4;
    public static final int ATM_POS = 5;
    public static final int LAT_POS = 6;
    public static final int LON_POST = 7;

    //fields
    private String name, purpose;
    private boolean bookRooms, food, atm;
    private double lat, lon;
    private long id;

    public Building(String name, String purpose, boolean bookRooms, boolean food, boolean atm, double lat, double lon) {
        this.name = name;
        this.purpose = purpose;
        this.bookRooms = bookRooms;
        this.food = food;
        this.atm = atm;
        this.lat = lat;
        this.lon = lon;
    }

    public static void printBuildings(ArrayList<Building> buildings) {
        String output = "BUILDINGS:\n";
        for (int i = 0; i < buildings.size(); i++) {
            output += "ID: " + buildings.get(i).getID() + " NAME: " + buildings.get(i).getName() + " PURPOSE: "
                    + buildings.get(i).getPurpose() + " ROOMS: " + buildings.get(i).getBookRooms() + " FOOD: " + buildings.get(i).getFood()
                    + " ATM: " + buildings.get(i).getAtm() + " LAT: " + buildings.get(i).getLat() + " LON: " + buildings.get(i).getLon()+" ";
        }
        Log.d("SQLITEBUILDING", output);
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public boolean getBookRooms() {
        return bookRooms;
    }

    public void setBookRooms(boolean bookRooms) {
        this.bookRooms = bookRooms;
    }

    public boolean getFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean getAtm() {
        return atm;
    }

    public void setAtm(boolean atm) {
        this.atm = atm;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
