package com.example.alex.qtapandroid.common.database.food;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Carson on 04/07/2017.
 * Class that defines schema for Food table in phone database.
 */
public class Food implements BaseColumns {
    public static final String TABLE_NAME = "Food";

    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_MEAL_PLAN = "MealPlan";
    public static final String COLUMN_CARD = "Card";
    public static final String COLUMN_INFORMATION = "Information";
    public static final String COLUMN_BUILDING_ID = "BuildingID";
    public static final String COLUMN_MON_START_HOURS = "MonStartHours";
    public static final String COLUMN_MON_STOP_HOURS = "MonStopHours";
    public static final String COLUMN_TUE_START_HOURS = "TueStartHours";
    public static final String COLUMN_TUE_STOP_HOURS = "TueStopHours";
    public static final String COLUMN_WED_START_HOURS = "WedStartHours";
    public static final String COLUMN_WED_STOP_HOURS = "WedStopHours";
    public static final String COLUMN_THUR_START_HOURS = "ThurStartHours";
    public static final String COLUMN_THUR_STOP_HOURS = "ThurStopHours";
    public static final String COLUMN_FRI_START_HOURS = "FriStartHours";
    public static final String COLUMN_FRI_STOP_HOURS = "FriStopHours";
    public static final String COLUMN_SAT_START_HOURS = "SatStartHours";
    public static final String COLUMN_SAT_STOP_HOURS = "SatStopHours";
    public static final String COLUMN_SUN_START_HOURS = "SunStartHours";
    public static final String COLUMN_SUN_STOP_HOURS = "SunStopHours";

    public static final int POS_ID = 0;
    public static final int POS_NAME = 1;
    public static final int POS_MEAL_PLAN = 2;
    public static final int POS_CARD = 3;
    public static final int POS_INFORMATION = 4;
    public static final int POS_BUILDING_ID = 5;
    public static final int POS_MON_START_HOURS = 6;
    public static final int POS_MON_STOP_HOURS = 7;
    public static final int POS_TUE_START_HOURS = 8;
    public static final int POS_TUE_STOP_HOURS = 9;
    public static final int POS_WED_START_HOURS = 10;
    public static final int POS_WED_STOP_HOURS = 11;
    public static final int POS_THUR_START_HOURS = 12;
    public static final int POS_THUR_STOP_HOURS = 13;
    public static final int POS_FRI_START_HOURS = 14;
    public static final int POS_FRI_STOP_HOURS = 15;
    public static final int POS_SAT_START_HOURS = 16;
    public static final int POS_SAT_STOP_HOURS = 17;
    public static final int POS_SUN_START_HOURS = 18;
    public static final int POS_SUN_STOP_HOURS = 19;

    private long id;
    private int buildingID;
    private String name, information;
    private boolean mealPlan, card;
    private double monStartHours, monStopHours, tueStartHours, tueStopHours, wedStartHours, wedStopHours, thurStartHours,
            thurStopHours, friStartHours, friStopHours, satStartHours, satStopHours, sunStartHours, sunStopHours;

    public Food(String name, int buildingID, String information, boolean mealPlan, boolean card, double monStartHours, double monStopHours, double tueStartHours,
                double tueStopHours, double wedStartHours, double wedStopHours, double thurStartHours, double thurStopHours, double friStartHours,
                double friStopHours, double satStartHours, double satStopHours, double sunStartHours, double sunStopHours) {
        this.name = name;
        this.buildingID = buildingID;
        this.information = information;
        this.mealPlan = mealPlan;
        this.card = card;
        this.monStartHours = monStartHours;
        this.monStopHours = monStopHours;
        this.tueStartHours = tueStartHours;
        this.tueStopHours = tueStopHours;
        this.wedStartHours = wedStartHours;
        this.wedStopHours = wedStopHours;
        this.thurStartHours = thurStartHours;
        this.thurStopHours = thurStopHours;
        this.friStartHours = friStartHours;
        this.friStopHours = friStopHours;
        this.satStartHours = satStartHours;
        this.satStopHours = satStopHours;
        this.sunStartHours = sunStartHours;
        this.sunStopHours = sunStopHours;
    }

    public static void printFood(ArrayList<Food> food) {
        String output = "FOOD:\n";
        for (Food oneFood : food) {
            output += "ID: " + oneFood.getID() + " NAME: " + oneFood.getName() + " MEAL PLAN: " + oneFood.isMealPlan() + " CARD: " + oneFood.isCard() +
                    " INFORMATION: " + oneFood.getInformation() + " BUILDING ID: " + oneFood.getBuildingID() + " NOT PRINTING HOURS CURRENTLY\n";
        }
        Log.d("SQLITEFOOD", output);
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

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public boolean isMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(boolean mealPlan) {
        this.mealPlan = mealPlan;
    }

    public boolean isCard() {
        return card;
    }

    public void setCard(boolean card) {
        this.card = card;
    }

    public double getMonStartHours() {
        return monStartHours;
    }

    public void setMonStartHours(double monStartHours) {
        this.monStartHours = monStartHours;
    }

    public double getMonStopHours() {
        return monStopHours;
    }

    public void setMonStopHours(double monStopHours) {
        this.monStopHours = monStopHours;
    }

    public double getTueStartHours() {
        return tueStartHours;
    }

    public void setTueStartHours(double tueStartHours) {
        this.tueStartHours = tueStartHours;
    }

    public double getTueStopHours() {
        return tueStopHours;
    }

    public void setTueStopHours(double tueStopHours) {
        this.tueStopHours = tueStopHours;
    }

    public double getWedStartHours() {
        return wedStartHours;
    }

    public void setWedStartHours(double wedStartHours) {
        this.wedStartHours = wedStartHours;
    }

    public double getWedStopHours() {
        return wedStopHours;
    }

    public void setWedStopHours(double wedStopHours) {
        this.wedStopHours = wedStopHours;
    }

    public double getThurStartHours() {
        return thurStartHours;
    }

    public void setThurStartHours(double thurStartHours) {
        this.thurStartHours = thurStartHours;
    }

    public double getThurStopHours() {
        return thurStopHours;
    }

    public void setThurStopHours(double thurStopHours) {
        this.thurStopHours = thurStopHours;
    }

    public double getFriStartHours() {
        return friStartHours;
    }

    public void setFriStartHours(double friStartHours) {
        this.friStartHours = friStartHours;
    }

    public double getFriStopHours() {
        return friStopHours;
    }

    public void setFriStopHours(double friStopHours) {
        this.friStopHours = friStopHours;
    }

    public double getSatStartHours() {
        return satStartHours;
    }

    public void setSatStartHours(double satStartHours) {
        this.satStartHours = satStartHours;
    }

    public double getSatStopHours() {
        return satStopHours;
    }

    public void setSatStopHours(double satStopHours) {
        this.satStopHours = satStopHours;
    }

    public double getSunStartHours() {
        return sunStartHours;
    }

    public void setSunStartHours(double sunStartHours) {
        this.sunStartHours = sunStartHours;
    }

    public double getSunStopHours() {
        return sunStopHours;
    }

    public void setSunStopHours(double sunStopHours) {
        this.sunStopHours = sunStopHours;
    }
}
