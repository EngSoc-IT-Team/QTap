package com.example.alex.qtapandroid.common.database;

import com.example.alex.qtapandroid.common.database.contacts.emergency.EmergencyContact;
import com.example.alex.qtapandroid.common.database.contacts.engineering.EngineeringContact;
import com.example.alex.qtapandroid.common.database.courses.Course;
import com.example.alex.qtapandroid.common.database.courses.OneClass;
import com.example.alex.qtapandroid.common.database.users.User;

/**
 * Created by Carson on 18/02/2017.
 * Class to hold SQL statements. All are public static final.
 * Referenced from DbHelper only, this is just to limit the size of DbHelper.
 */
public class SqlStringStatements {

    public static final String PHONE_DATABASE_NAME = "QTAP_PHONE.db";

    //create table statements
    protected static final String CREATE_COURSES = "CREATE TABLE " + Course.TABLE_NAME + "(" +
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

    //Delete table statements
    public static final String DELETE_COURSES = "DROP TABLE IF EXISTS " + Course.TABLE_NAME;
    public static final String DELETE_USERS = "DROP TABLE IF EXISTS " + User.TABLE_NAME;
    public static final String DELETE_CLASSES = "DROP TABLE IF EXISTS " + OneClass.TABLE_NAME;
    public static final String DELETE_ENGINEERING_CONTACTS = "DROP TABLE IF EXISTS " + EngineeringContact.TABLE_NAME;
    public static final String DELETE_EMERGENCY_CONTACTS = "DROP TABLE IF EXISTS " + EmergencyContact.TABLE_NAME;
}
