package com.example.alex.qtapandroid.common.database.local.courses;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Carson on 19/01/2017.
 * Defines the schema for the Courses table. Currently holds a field for the class title,
 * room number, class time and ID.
 * **Note** Each lecture/lab/studio needs an entry, so 'Course' is a misnomer.
 */
public class Course implements BaseColumns {
    //table schema
    public static final String TABLE_NAME = "courses";
    public static final String COLUMN_TITLE = "title";

    //column number each field ends up in
    public static final int ID_POS = 0;
    public static final int TITLE_POS = 1;
    public static final int ROOM_NUM_POS = 2;
    public static final int STIME_POS = 3;
    public static final int ETIME_POS = 4;
    public static final int DAY_POS = 5;
    public static final int MONTH_POS = 6;
    public static final int YEAR_POS = 7;

    //fields in database
    private long id;
    private String title;

    public Course(String title) {
        this.title = title;
    }

    /**
     * Prints out course information.
     *
     * @param courses ArrayList of courses to print out.
     */
    public static void printCourses(ArrayList<Course> courses) {
        String output = "COURSES:\n";
        for (int i = 0; i < courses.size(); i++) {
            output += System.getProperty("line.separator") + "COURSE id:" +
                    courses.get(i).getID() + " title: " + courses.get(i).getTitle();
        }
        Log.d("SQLITE",output);
    }

    //getters and setters for fields
    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
}
