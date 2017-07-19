package com.example.alex.qtapandroid.common.database.cafeterias;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Carson on 18/07/2017.
 * Class that defines the SQLite database table for Cafeterias
 */
public class Cafeteria implements BaseColumns {
    public static final String TABLE_NAME = "Cafeterias";

    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_BUILDING_ID = "BuildingID";
    public static final String COLUMN_WEEK_BREAKFAST_START = "WeekBreakfastStart";
    public static final String COLUMN_WEEK_BREAKFAST_STOP = "WeekBreakfastStop";
    public static final String COLUMN_FRI_BREAKFAST_START = "FriBreakfastStart";
    public static final String COLUMN_FRI_BREAKFAST_STOP = "FriBreakfastStop";
    public static final String COLUMN_SAT_BREAKFAST_START = "SatBreakfastStart";
    public static final String COLUMN_SAT_BREAKFAST_STOP = "SatBreakfastStop";
    public static final String COLUMN_SUN_BREAKFAST_START = "SunBreakfastStart";
    public static final String COLUMN_SUN_BREAKFAST_STOP = "SunBreakfastStop";
    public static final String COLUMN_WEEK_LUNCH_START = "WeekLunchStart";
    public static final String COLUMN_WEEK_LUNCH_STOP = "WeekLunchStop";
    public static final String COLUMN_FRI_LUNCH_START = "FriLunchStart";
    public static final String COLUMN_FRI_LUNCH_STOP = "FriLunchStop";
    public static final String COLUMN_SAT_LUNCH_START = "SatLunchStart";
    public static final String COLUMN_SAT_LUNCH_STOP = "SatLunchStop";
    public static final String COLUMN_SUN_LUNCH_START = "SunLunchStart";
    public static final String COLUMN_SUN_LUNCH_STOP = "SunLunchStop";
    public static final String COLUMN_WEEK_DINNER_START = "WeekDinnerStart";
    public static final String COLUMN_WEEK_DINNER_STOP = "WeekDinnerStop";
    public static final String COLUMN_FRI_DINNER_START = "FriDinnerStart";
    public static final String COLUMN_FRI_DINNER_STOP = "FriDinnerStop";
    public static final String COLUMN_SAT_DINNER_START = "SatDinnerStart";
    public static final String COLUMN_SAT_DINNER_STOP = "SatDinnerStop";
    public static final String COLUMN_SUN_DINNER_START = "SunDinnerStart";
    public static final String COLUMN_SUN_DINNER_STOP = "SunDinnerStop";

    public static final int POS_ID = 0;
    public static final int POS_NAME = 1;
    public static final int POS_BUILDING_ID = 2;
    public static final int POS_WEEK_BREAKFAST_START = 3;
    public static final int POS_WEEK_BREAKFAST_STOP = 4;
    public static final int POS_FRI_BREAKFAST_START = 5;
    public static final int POS_FRI_BREAKFAST_STOP = 6;
    public static final int POS_SAT_BREAKFAST_START = 7;
    public static final int POS_SAT_BREAKFAST_STOP = 8;
    public static final int POS_SUN_BREAKFAST_START = 9;
    public static final int POS_SUN_BREAKFAST_STOP = 10;
    public static final int POS_WEEK_LUNCH_START = 11;
    public static final int POS_WEEK_LUNCH_STOP = 12;
    public static final int POS_FRI_LUNCH_START = 13;
    public static final int POS_FRI_LUNCH_STOP = 14;
    public static final int POS_SAT_LUNCH_START = 15;
    public static final int POS_SAT_LUNCH_STOP = 16;
    public static final int POS_SUN_LUNCH_START = 17;
    public static final int POS_SUN_LUNCH_STOP = 18;
    public static final int POS_WEEK_DINNER_START = 19;
    public static final int POS_WEEK_DINNER_STOP = 20;
    public static final int POS_FRI_DINNER_START = 21;
    public static final int POS_FRI_DINNER_STOP = 22;
    public static final int POS_SAT_DINNER_START = 23;
    public static final int POS_SAT_DINNER_STOP = 24;
    public static final int POS_SUN_DINNER_START = 25;
    public static final int POS_SUN_DINNER_STOP = 26;

    private long id;
    private int buildingID;
    private String name;
    private double weekBreakfastStart, weekBreakfastStop, friBreakfastStart, friBreakfastStop, satBreakfastStart, satBreakfastStop,
            sunBreakfastStart, sunBreakfastStop, weekLunchStart, weekLunchStop, friLunchStart, friLunchStop,
            satLunchStart, satLunchStop, sunLunchStart, sunLunchStop, weekDinnerStart, weekDinnerStop,
            friDinnerStart, friDinnerStop, satDinnerStart, satDinnerStop, sunDinnerStart, sunDinnerStop;

    public Cafeteria(String name, int buildingID, double weekBreakfastStart, double weekBreakfastStop, double friBreakfastStart, double friBreakfastStop,
                     double satBreakfastStart, double satBreakfastStop, double sunBreakfastStart, double sunBreakfastStop, double weekLunchStart,
                     double weekLunchStop, double friLunchStart, double friLunchStop, double satLunchStart, double satLunchStop, double sunLunchStart, double sunLunchStop,
                     double weekDinnerStart, double weekDinnerStop, double friDinnerStart, double friDinnerStop,
                     double satDinnerStart, double satDinnerStop, double sunDinnerStart, double sunDinnerStop) {
        this.buildingID = buildingID;
        this.name = name;
        this.weekBreakfastStart = weekBreakfastStart;
        this.weekBreakfastStop = weekBreakfastStop;
        this.friBreakfastStart = friBreakfastStart;
        this.friBreakfastStop = friBreakfastStop;
        this.satBreakfastStart = satBreakfastStart;
        this.satBreakfastStop = satBreakfastStop;
        this.sunBreakfastStart = sunBreakfastStart;
        this.sunBreakfastStop = sunBreakfastStop;
        this.weekLunchStart = weekLunchStart;
        this.weekLunchStop = weekLunchStop;
        this.friLunchStart = friLunchStart;
        this.friLunchStop = friLunchStop;
        this.satLunchStart = satLunchStart;
        this.satLunchStop = satLunchStop;
        this.sunLunchStart = sunLunchStart;
        this.sunLunchStop = sunLunchStop;
        this.weekDinnerStart = weekDinnerStart;
        this.weekDinnerStop = weekDinnerStop;
        this.friDinnerStart = friDinnerStart;
        this.friDinnerStop = friDinnerStop;
        this.satDinnerStart = satDinnerStart;
        this.satDinnerStop = satDinnerStop;
        this.sunDinnerStart = sunDinnerStart;
        this.sunDinnerStop = sunDinnerStop;
    }

    public static void printCafeterias(ArrayList<Cafeteria> cafs) {
        String output = "CAFETERIAS:\n";
        for (Cafeteria caf : cafs) {
            output += "NAME: " + caf.getName() + " BUILDINGID: " + caf.getBuildingID() + " THATS IT FOR NOW\n";
        }
        Log.d("SQLITECAFETERIAS", output);
    }


    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeekBreakfastStart() {
        return weekBreakfastStart;
    }

    public void setWeekBreakfastStart(double weekBreakfastStart) {
        this.weekBreakfastStart = weekBreakfastStart;
    }

    public double getWeekBreakfastStop() {
        return weekBreakfastStop;
    }

    public void setWeekBreakfastStop(double weekBreakfastStop) {
        this.weekBreakfastStop = weekBreakfastStop;
    }

    public double getFriBreakfastStart() {
        return friBreakfastStart;
    }

    public void setFriBreakfastStart(double friBreakfastStart) {
        this.friBreakfastStart = friBreakfastStart;
    }

    public double getFriBreakfastStop() {
        return friBreakfastStop;
    }

    public void setFriBreakfastStop(double friBreakfastStop) {
        this.friBreakfastStop = friBreakfastStop;
    }

    public double getSatBreakfastStart() {
        return satBreakfastStart;
    }

    public void setSatBreakfastStart(double satBreakfastStart) {
        this.satBreakfastStart = satBreakfastStart;
    }

    public double getSatBreakfastStop() {
        return satBreakfastStop;
    }

    public void setSatBreakfastStop(double satBreakfastStop) {
        this.satBreakfastStop = satBreakfastStop;
    }

    public double getSunBreakfastStart() {
        return sunBreakfastStart;
    }

    public void setSunBreakfastStart(double sunBreakfastStart) {
        this.sunBreakfastStart = sunBreakfastStart;
    }

    public double getSunBreakfastStop() {
        return sunBreakfastStop;
    }

    public void setSunBreakfastStop(double sunBreakfastStop) {
        this.sunBreakfastStop = sunBreakfastStop;
    }

    public double getWeekLunchStart() {
        return weekLunchStart;
    }

    public void setWeekLunchStart(double weekLunchStart) {
        this.weekLunchStart = weekLunchStart;
    }

    public double getWeekLunchStop() {
        return weekLunchStop;
    }

    public void setWeekLunchStop(double weekLunchStop) {
        this.weekLunchStop = weekLunchStop;
    }

    public double getFriLunchStart() {
        return friLunchStart;
    }

    public void setFriLunchStart(double friLunchStart) {
        this.friLunchStart = friLunchStart;
    }

    public double getFriLunchStop() {
        return friLunchStop;
    }

    public void setFriLunchStop(double friLunchStop) {
        this.friLunchStop = friLunchStop;
    }

    public double getSatLunchStart() {
        return satLunchStart;
    }

    public void setSatLunchStart(double satLunchStart) {
        this.satLunchStart = satLunchStart;
    }

    public double getSatLunchStop() {
        return satLunchStop;
    }

    public void setSatLunchStop(double satLunchStop) {
        this.satLunchStop = satLunchStop;
    }

    public double getSunLunchStart() {
        return sunLunchStart;
    }

    public void setSunLunchStart(double sunLunchStart) {
        this.sunLunchStart = sunLunchStart;
    }

    public double getSunLunchStop() {
        return sunLunchStop;
    }

    public void setSunLunchStop(double sunLunchStop) {
        this.sunLunchStop = sunLunchStop;
    }

    public double getWeekDinnerStart() {
        return weekDinnerStart;
    }

    public void setWeekDinnerStart(double weekDinnerStart) {
        this.weekDinnerStart = weekDinnerStart;
    }

    public double getWeekDinnerStop() {
        return weekDinnerStop;
    }

    public void setWeekDinnerStop(double weekDinnerStop) {
        this.weekDinnerStop = weekDinnerStop;
    }

    public double getFriDinnerStart() {
        return friDinnerStart;
    }

    public void setFriDinnerStart(double friDinnerStart) {
        this.friDinnerStart = friDinnerStart;
    }

    public double getFriDinnerStop() {
        return friDinnerStop;
    }

    public void setFriDinnerStop(double friDinnerStop) {
        this.friDinnerStop = friDinnerStop;
    }

    public double getSatDinnerStart() {
        return satDinnerStart;
    }

    public void setSatDinnerStart(double satDinnerStart) {
        this.satDinnerStart = satDinnerStart;
    }

    public double getSatDinnerStop() {
        return satDinnerStop;
    }

    public void setSatDinnerStop(double satDinnerStop) {
        this.satDinnerStop = satDinnerStop;
    }

    public double getSunDinnerStart() {
        return sunDinnerStart;
    }

    public void setSunDinnerStart(double sunDinnerStart) {
        this.sunDinnerStart = sunDinnerStart;
    }

    public double getSunDinnerStop() {
        return sunDinnerStop;
    }

    public void setSunDinnerStop(double sunDinnerStop) {
        this.sunDinnerStop = sunDinnerStop;
    }
}
