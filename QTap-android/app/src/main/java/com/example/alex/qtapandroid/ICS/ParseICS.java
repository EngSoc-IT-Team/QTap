package com.example.alex.qtapandroid.ICS;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.DatePicker;

import com.example.alex.qtapandroid.common.database.local.courses.Course;
import com.example.alex.qtapandroid.common.database.local.courses.CourseManager;
import com.example.alex.qtapandroid.common.database.local.courses.OneClass;
import com.example.alex.qtapandroid.common.database.local.courses.OneClassManager;
import com.example.alex.qtapandroid.common.database.local.users.User;
import com.example.alex.qtapandroid.common.database.local.users.UserManager;
import com.example.alex.qtapandroid.ui.fragments.StudentToolsFragment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by Alex on 1/18/2017.
 * Class to parse the ICS file.
 */
public class ParseICS {

    private String TAG = StudentToolsFragment.class.getSimpleName();
    private OneClassManager mOneClassManager;
    private CourseManager mCourseManager;
    private DatePicker mDateSelection;

    private Context mContext;

    public ParseICS(Context context) {
        this.mContext = context;
    }

    public List<String> readLine(String path) {
        List<String> mLines = new ArrayList<>();
        AssetManager am = mContext.getAssets();

        try {
            InputStream is = am.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null)
                mLines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mLines;
    }

    public List<String> readDownloadFile(String path) {
        List<String> mLines = new ArrayList<>();

        String ret = "";

        try {
            InputStream inputStream = mContext.openFileInput(path);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder(); //TODO what is stringbuilder used for

                while ((receiveString = bufferedReader.readLine()) != null) {
                    mLines.add(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        }
        Log.e(TAG, "Done: " + ret);
        return mLines;
    }

    public void parseICSData() {
        UserManager mUserManager = new UserManager(this.mContext);
        ArrayList<User> user = mUserManager.getTable();
        mOneClassManager = new OneClassManager(mContext);
        mCourseManager = new CourseManager(mContext);

        boolean isInit = true;

        // see if user has initialized database yet, and if the database is up to date
        /*for (int i = 0; i < user.size(); i++) {

            if (!user.get(i).getDateInit().isEmpty()) {
                String rTime = user.get(i).getDateInit();
                int yr = Integer.parseInt(rTime.substring(0, 4));
                int mon = Integer.parseInt(rTime.substring(5, 7));
                int day = Integer.parseInt(rTime.substring(8, 10));

                Calendar c = Calendar.getInstance();
                c.set(yr, mon, day);
                c.add(Calendar.DATE, 7);
                Calendar endDate = Calendar.getInstance();

                if (c.after(endDate)) {
                    isInit = true;
                }
            }
        }*/

        if (mOneClassManager.getTable().isEmpty() || !isInit) {
            mOneClassManager.deleteTable();

            boolean isEvent = false;
            String sTime = "", eTime = "", loc = "", name = "", rTime = "";
            int hour = 0, minute = 0, year = 0;
            int shour = 0, sminute = 0, sday = 0, smonth = 0;
            boolean repeatWeekly = false;
            String rDayStr = "", rMonStr = "", rYrStr = "";

            List<String> lines = readDownloadFile("cal.ics");

            int test = 1;

            for (String string : lines) {

                if (string.contains("BEGIN:VEVENT")) {
                    isEvent = true;
                } else if (string.contains("END:VEVENT")) {
                    isEvent = false;

                    String tempTime = Integer.toString(shour) + ":" + Integer.toString(sminute);
                    String tempEndTime = Integer.toString(hour) + ":" + Integer.toString(minute);

                    Course course = new Course(name);
                    course.setID(mCourseManager.insertRow(course));
//                    Course.printCourses(mCourseManager.getTable());

                    OneClass one = new OneClass(name, loc, tempTime, tempEndTime, Integer.toString(sday), Integer.toString(smonth), Integer.toString(year));
                    one.setBuildingID(15);       // TODO delete later, this is temporary
                    one.setCourseID(test);
                    one.setID(mOneClassManager.insertRow(one));

                    //TODO set course ID as well: query Course table for entry with the same title, that is the ID to use

                    if (repeatWeekly) {
                        // get the supported ids for GMT-08:00 (Pacific Standard Time)
                        String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
                        // if no ids were returned, something is wrong. get out.
                        if (ids.length == 0)
                            System.exit(0);

                        // create a Pacific Standard Time time zone
                        SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);

                        // set up rules for Daylight Saving Time
                        pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
                        pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

                        // create a GregorianCalendar with the Pacific Daylight time zone
                        // and the current date and time
                        Calendar cal2 = new GregorianCalendar(pdt);
                        Calendar cal = new GregorianCalendar(pdt);

                        cal.set(year, smonth - 1, sday);

                        cal2.set(Integer.parseInt(rYrStr), Integer.parseInt(rMonStr) - 1, Integer.parseInt(rDayStr) + 1);

                        Date endDate = cal2.getTime();
                        Date date1;

                        cal.add(Calendar.DATE, 7);
                        date1 = cal.getTime();

                        while (date1.before(endDate)) {
                            sday = cal.get(Calendar.DAY_OF_MONTH);
                            smonth = cal.get(Calendar.MONTH);
                            year = cal.get(Calendar.YEAR);

                            one = new OneClass(name, loc, tempTime, tempEndTime, Integer.toString(sday), Integer.toString(smonth + 1), Integer.toString(year));
                            one.setBuildingID(15);       // delete later, this is temporary
                            one.setCourseID(test);
                            one.setID(mOneClassManager.insertRow(one));
                            cal.add(Calendar.DATE, 7);
                            date1 = cal.getTime();
                        }
                    }
                    repeatWeekly = false;
                    test += 1;

                } else if (string.contains(("RRULE:FREQ=WEEKLY;"))) {
                    repeatWeekly = true;

                    if (string.contains("UNTIL=")) {
                        rTime = string.replaceAll("[^0-9]", "");

                        rDayStr = rTime.substring(6, 8);
                        rMonStr = rTime.substring(4, 6);
                        rYrStr = rTime.substring(0, 4);
                    }

                } else if (isEvent) {
                    if (string.contains("LOCATION"))
                        loc = string.substring(9);
                    else if (string.contains("DTSTART")) {
                        sTime = string.replaceAll("[^0-9]", "");
                        shour = Integer.parseInt(sTime.substring(8, 10));
                        sminute = Integer.parseInt(sTime.substring(10, 12));
                        sday = Integer.parseInt(sTime.substring(6, 8));
                        smonth = Integer.parseInt(sTime.substring(4, 6));
                        year = Integer.parseInt(sTime.substring(0, 4));
                    } else if (string.contains("DTEND")) {
                        eTime = string.replaceAll("[^0-9]", "");
                        hour = Integer.parseInt(eTime.substring(8, 10));
                        minute = Integer.parseInt(eTime.substring(10, 12));

                    } else if (string.contains("SUMMARY")) {
                        //take part of string that is the course code
                        name = (string.substring(string.lastIndexOf(":") + 1, string.indexOf(" ", string.indexOf(" ") + 1)));
                    }
                }
            }
            SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy, hh:mm aa", Locale.CANADA);
            String formattedDate = df.format(Calendar.getInstance().getTime());

            String uName = user.get(0).getFirstName();
            String uLastName = user.get(0).getLastName();
            String uNetID = user.get(0).getNetid();
            String uURL = user.get(0).getIcsURL();

            User nUser = new User(uNetID, uName, uLastName, formattedDate, uURL);
            mUserManager.updateRow(user.get(0), nUser);
        }
    }
}
