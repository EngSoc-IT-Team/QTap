package com.example.alex.qtapandroid.common.database;

import com.example.alex.qtapandroid.common.database.buildings.Building;
import com.example.alex.qtapandroid.common.database.contacts.emergency.EmergencyContact;
import com.example.alex.qtapandroid.common.database.contacts.engineering.EngineeringContact;
import com.example.alex.qtapandroid.common.database.courses.Course;
import com.example.alex.qtapandroid.common.database.courses.OneClass;
import com.example.alex.qtapandroid.common.database.food.Food;
import com.example.alex.qtapandroid.common.database.users.User;

/**
 * Created by Carson on 18/02/2017.
 * Class to hold SQL statements. All are public static final.
 * Referenced from DbHelper only, this is just to limit the size of DbHelper.
 */
public class SqlStringStatements {

    public static final String PHONE_DATABASE_NAME = "QTAP_PHONE.db";

    //create table statements
    public static final String CREATE_COURSES = "CREATE TABLE " + Course.TABLE_NAME + "(" +
            Course._ID + " INTEGER PRIMARY KEY," + Course.COLUMN_TITLE + " TEXT);";

    public static final String CREATE_USERS = "CREATE TABLE " + User.TABLE_NAME + "(" +
            User._ID + " INTEGER PRIMARY KEY," + User.COLUMN_NETID + " TEXT," +
            User.COLUMN_FIRST_NAME + " TEXT," + User.COLUMN_LAST_NAME + " TEXT," +
            User.COLUMN_DATE_INIT + " TEXT," + User.COLUMN_ICS_URL + " TEXT);";

    public static final String CREATE_CLASSES = "CREATE TABLE " + OneClass.TABLE_NAME + "(" +
            OneClass._ID + " INTEGER PRIMARY KEY," + OneClass.COLUMN_CLASS_TYPE + " TEXT," +
            OneClass.COLUMN_BUILDING_ID + " INT," + OneClass.COLUMN_ROOM_NUM + " TEXT," +
            OneClass.COLUMN_START_TIME + " TEXT," + OneClass.COLUMN_END_TIME + " TEXT," +
            OneClass.COLUMN_DAY + " TEXT," + OneClass.COLUMN_MONTH + " TEXT," + OneClass.COLUMN_YEAR +
            " TEXT," + OneClass.COLUMN_COURSE_ID + " INT );";

    public static final String CREATE_ENGINEERING_CONTACTS = "CREATE TABLE " + EngineeringContact.TABLE_NAME + "(" + EngineeringContact._ID +
            " INTEGER PRIMARY KEY," + EngineeringContact.COLUMN_NAME + " TEXT," + EngineeringContact.COLUMN_EMAIL + " TEXT,"
            + EngineeringContact.COLUMN_POSITION + " TEXT," + EngineeringContact.COLUMN_DESCRIPTION + " TEXT);";

    public static final String CREATE_EMERGENCY_CONTACTS = "CREATE TABLE " + EmergencyContact.TABLE_NAME + "(" + EmergencyContact._ID +
            " INTEGER PRIMARY KEY," + EngineeringContact.COLUMN_NAME + " TEXT," + EmergencyContact.COLUMN_PHONE_NUMBER + " TEXT,"
            + EmergencyContact.COLUMN_DESCRIPTION + " TEXT);";

    public static final String CREATE_BUILDINGS = "CREATE TABLE " + Building.TABLE_NAME + "(" + Building._ID + " INTEGER PRIMARY KEY," +
            Building.COLUMN_NAME + " TEXT," + Building.COLUMN_PURPOSE + " TEXT," + Building.COLUMN_BOOK_ROOMS + " INTEGER," + Building.COLUMN_FOOD
            + " INTEGER," + Building.COLUMN_ATM + " INTEGER," + Building.COLUMN_LAT + " REAL," + Building.COLUMN_LON + " REAL);";

    public static final String CREATE_FOOD = "CREATE TABLE " + Food.TABLE_NAME + "(" + Food._ID + " INTEGER PRIMARY KEY," + Food.COLUMN_NAME + " TEXT," + Food.COLUMN_MEAL_PLAN +
            " INTEGER," + Food.COLUMN_CARD + " INTEGER," + Food.COLUMN_INFORMATION + " TEXT," + Food.COLUMN_BUILDING_ID + " INTEGER," + Food.COLUMN_MON_START_HOURS + " REAL," +
            Food.COLUMN_MON_STOP_HOURS + " REAL," + Food.COLUMN_TUE_START_HOURS + " REAL," + Food.COLUMN_TUE_STOP_HOURS + " REAL," + Food.COLUMN_WED_START_HOURS + " REAL," +
            Food.COLUMN_WED_STOP_HOURS + " REAL," + Food.COLUMN_THUR_START_HOURS + " REAL," + Food.COLUMN_THUR_STOP_HOURS + " REAL," + Food.COLUMN_FRI_START_HOURS + " REAL," +
            Food.COLUMN_FRI_STOP_HOURS + " REAL," + Food.COLUMN_SAT_START_HOURS + " REAL," + Food.COLUMN_SAT_STOP_HOURS + " REAL," + Food.COLUMN_SUN_START_HOURS + " REAL," +
            Food.COLUMN_SUN_STOP_HOURS + " REAL);";

    //Delete table statements
    public static final String DELETE_COURSES = "DROP TABLE IF EXISTS " + Course.TABLE_NAME;
    public static final String DELETE_USERS = "DROP TABLE IF EXISTS " + User.TABLE_NAME;
    public static final String DELETE_CLASSES = "DROP TABLE IF EXISTS " + OneClass.TABLE_NAME;
    public static final String DELETE_ENGINEERING_CONTACTS = "DROP TABLE IF EXISTS " + EngineeringContact.TABLE_NAME;
    public static final String DELETE_EMERGENCY_CONTACTS = "DROP TABLE IF EXISTS " + EmergencyContact.TABLE_NAME;
    public static final String DELETE_BUILDINGS = "DROP TABLE IF EXISTS " + Building.TABLE_NAME;
    public static final String DELETE_FOOD = "DROP TABLE IF EXISTS " + Food.TABLE_NAME;
}
