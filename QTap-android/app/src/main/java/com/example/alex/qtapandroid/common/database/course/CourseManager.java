package com.example.alex.qtapandroid.common.database.course;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alex.qtapandroid.common.database.DatabaseAccessor;

import java.util.ArrayList;

/**
 * Created by Carson on 21/01/2017.
 * Holds all information for the courses table.
 * Manages the courses within the database. Inserts/deletes rows and the entire table.
 */
public class CourseManager extends DatabaseAccessor {

    public CourseManager(Context context) {
        super(context);
    }

    /**
     * Inserts a course into the database.
     *
     * @param course The course to be inserted. Before calling it must have
     *               the values to be inserted.
     * @return <long> The ID of the course just inserted. Set the id of the
     * the class inserted to be the return value.
     */
    public long insertRow(Course course) {
        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_TITLE, course.getTitle());
        values.put(Course.COLUMN_BUILDING_ID, course.getBuildingID());
        values.put(Course.COLUMN_ROOM_NUM, course.getRoomNum());
        values.put(Course.COLUMN_STARTTIME, course.getStartTime());
        values.put(Course.COLUMN_ENDTIME, course.getEndTime());
        values.put(Course.COLUMN_DAY, course.getDay());
        values.put(Course.COLUMN_MONTH, course.getMonth());
        values.put(Course.COLUMN_YEAR, course.getYear());


        return mDatabase.insert(Course.TABLE_NAME, null, values);
    }

    /**
     * Deletes a course from the database.
     *
     * @param course The course to be deleted. Identifies which course
     *               using the ID of this parameter.
     */
    public void deleteRow(Course course) {
        String selection = Course._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(course.getID())};
        mDatabase.delete(Course.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Gets the entire Courses table.
     *
     * @return ArrayList of all the courses in the Courses table.
     */
    public ArrayList<Course> getTable() {
        String[] projection = {
                Course._ID,
                Course.COLUMN_TITLE,
                Course.COLUMN_ROOM_NUM,
                Course.COLUMN_STARTTIME,
                Course.COLUMN_ENDTIME,
                Course.COLUMN_DAY,
                Course.COLUMN_MONTH,
                Course.COLUMN_YEAR
        };
        ArrayList<Course> courses = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = mDatabase.query(Course.TABLE_NAME, projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
<<<<<<< HEAD
                Course course = new Course(cursor.getString(Course.TITLE_POS), cursor.getString(Course.ROOM_NUM_POS), cursor.getString(Course.STIME_POS), cursor.getString(Course.ETIME_POS),
                        cursor.getString(Course.DAY_POS), cursor.getString(Course.MONTH_POS), cursor.getString(Course.YEAR_POS));
                course.setID(cursor.getInt(Course.ID_POS));
=======
                Course course = getRow(cursor.getInt(Course.ID_POS));
>>>>>>> sqlitedatabase
                courses.add(course);
            }
            cursor.close();
            return courses; //return only when the cursor has been closed
        }
    }

    /**
     * Gets a single course from the Courses table.
     *
     * @param id ID of the course to get from the table.
     * @return Course class obtained from the table. Contins all information
     * held in row.
     */
    public Course getRow(long id) {
        String[] projection = {
                Course._ID,
                Course.COLUMN_TITLE,
                Course.COLUMN_BUILDING_ID,
                Course.COLUMN_ROOM_NUM,
                Course.COLUMN_STARTTIME,
                Course.COLUMN_ENDTIME,
                Course.COLUMN_DAY,
                Course.COLUMN_MONTH,
                Course.COLUMN_YEAR
        };
        Course course;
        String selection = Course._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = mDatabase.query(Course.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            cursor.moveToNext();
<<<<<<< HEAD
            course = new Course(cursor.getString(Course.TITLE_POS), cursor.getString(Course.ROOM_NUM_POS), cursor.getString(Course.STIME_POS), cursor.getString(Course.ETIME_POS),
                    cursor.getString(Course.DAY_POS), cursor.getString(Course.MONTH_POS), cursor.getString(Course.YEAR_POS));
=======
            course = new Course(cursor.getString(Course.TITLE_POS), cursor.getInt(Course.BUILDING_ID_POS),
                    cursor.getString(Course.ROOM_NUM_POS), cursor.getString(Course.TIME_POS));
>>>>>>> sqlitedatabase
            course.setID(cursor.getInt(Course.ID_POS));
            cursor.close();
            return course; //return only when the cursor has been closed.
            //Return statement never missed, try block always finishes this.
        }
    }

    /**
     * Deletes the entire Courses table.
     */
    public void deleteTable() {
        mDatabase.delete(Course.TABLE_NAME, null, null);
    }

    /**
     * Changes information to one pre-existing course.
     *
     * @param oldCourse Course class that is being replaced.
     * @param newCourse Course class that holds the new information.
     * @return Course class containing updated information
     */
    public Course updateRow(Course oldCourse, Course newCourse) {
        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_TITLE, newCourse.getTitle());
        values.put(Course.COLUMN_ROOM_NUM, newCourse.getRoomNum());
        values.put(Course.COLUMN_STARTTIME, newCourse.getStartTime());
        values.put(Course.COLUMN_ENDTIME, newCourse.getEndTime());
        values.put(Course.COLUMN_DAY, newCourse.getDay());
        values.put(Course.COLUMN_MONTH, newCourse.getMonth());
        values.put(Course.COLUMN_YEAR, newCourse.getYear());
        String selection = Course._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(oldCourse.getID())};
        mDatabase.update(Course.TABLE_NAME, values, selection, selectionArgs);
        newCourse.setID(oldCourse.getID());
        return newCourse;
    }
}