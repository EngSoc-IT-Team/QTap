package com.example.alex.qtapandroid.common.database.courses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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
        return getDatabase().insert(Course.TABLE_NAME, null, values);
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
        getDatabase().delete(Course.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Gets the entire Courses table.
     *
     * @return ArrayList of all the rows in the Courses table.
     */
    public ArrayList<Course> getTable() {
        String[] projection = {
                Course._ID,
                Course.COLUMN_TITLE
        };
        ArrayList<Course> courses = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = getDatabase().query(Course.TABLE_NAME, projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                Course course = new Course(cursor.getString(Course.TITLE_POS));
                course.setID(cursor.getInt(Course.ID_POS));
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
     * @return Course class obtained from the table. Contains all information
     * held in row.
     */
    public Course getRow(long id) {
        String[] projection = {
                Course._ID,
                Course.COLUMN_TITLE
        };
        Course course = null;
        String selection = Course._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(Course.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToNext()) {
                course = new Course(cursor.getString(Course.TITLE_POS));
                course.setID(cursor.getInt(Course.ID_POS));
                cursor.close();
                return course; //return only when the cursor has been closed.
                //Return statement never missed, try block always finishes this.
            } else {
                return null;
            }
        }
    }


    /**
     * Deletes the entire Courses table.
     */
    public void deleteTable() {
        getDatabase().delete(Course.TABLE_NAME, null, null);
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
        String selection = Course._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(oldCourse.getID())};
        getDatabase().update(Course.TABLE_NAME, values, selection, selectionArgs);
        newCourse.setID(oldCourse.getID());
        return newCourse;
    }
}