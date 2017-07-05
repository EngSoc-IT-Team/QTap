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
    public static final String COLUMN_MON_STOP_HOURS = "MonStartHours";
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
    public static final String COLUMN_SUN_STOP_HOURS = "SunStartHours";
    public static final String COLUMN_SUN_START_HOURS = "SunStopHours";

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
    private float monStartHours, monStopHours, tueStartHours, tueStopHours, wedStartHours, wedStopHours, thurStartHours,
            thurStopHours, friStartHours, friStopHours, satStartHours, satStopHours, sunStartHours, sunStopHours;

    public Food(String name, int buildingID, String information, boolean mealPlan, boolean card, float monStartHours, float monStopHours, float tueStartHours,
                float tueStopHours, float wedStartHours, float wedStopHours, float thurStartHours, float thurStopHours, float friStartHours,
                float friStopHours, float satStartHours, float satStopHours, float sunStartHours, float sunStopHours) {
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

    public float getMonStartHours() {
        return monStartHours;
    }

    public void setMonStartHours(float monStartHours) {
        this.monStartHours = monStartHours;
    }

    public float getMonStopHours() {
        return monStopHours;
    }

    public void setMonStopHours(float monStopHours) {
        this.monStopHours = monStopHours;
    }

    public float getTueStartHours() {
        return tueStartHours;
    }

    public void setTueStartHours(float tueStartHours) {
        this.tueStartHours = tueStartHours;
    }

    public float getTueStopHours() {
        return tueStopHours;
    }

    public void setTueStopHours(float tueStopHours) {
        this.tueStopHours = tueStopHours;
    }

    public float getWedStartHours() {
        return wedStartHours;
    }

    public void setWedStartHours(float wedStartHours) {
        this.wedStartHours = wedStartHours;
    }

    public float getWedStopHours() {
        return wedStopHours;
    }

    public void setWedStopHours(float wedStopHours) {
        this.wedStopHours = wedStopHours;
    }

    public float getThurStartHours() {
        return thurStartHours;
    }

    public void setThurStartHours(float thurStartHours) {
        this.thurStartHours = thurStartHours;
    }

    public float getThurStopHours() {
        return thurStopHours;
    }

    public void setThurStopHours(float thurStopHours) {
        this.thurStopHours = thurStopHours;
    }

    public float getFriStartHours() {
        return friStartHours;
    }

    public void setFriStartHours(float friStartHours) {
        this.friStartHours = friStartHours;
    }

    public float getFriStopHours() {
        return friStopHours;
    }

    public void setFriStopHours(float friStopHours) {
        this.friStopHours = friStopHours;
    }

    public float getSatStartHours() {
        return satStartHours;
    }

    public void setSatStartHours(float satStartHours) {
        this.satStartHours = satStartHours;
    }

    public float getSatStopHours() {
        return satStopHours;
    }

    public void setSatStopHours(float satStopHours) {
        this.satStopHours = satStopHours;
    }

    public float getSunStartHours() {
        return sunStartHours;
    }

    public void setSunStartHours(float sunStartHours) {
        this.sunStartHours = sunStartHours;
    }

    public float getSunStopHours() {
        return sunStopHours;
    }

    public void setSunStopHours(float sunStopHours) {
        this.sunStopHours = sunStopHours;
    }
}
