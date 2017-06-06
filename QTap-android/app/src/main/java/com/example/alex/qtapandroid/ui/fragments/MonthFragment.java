package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.courses.CourseManager;
import com.example.alex.qtapandroid.common.database.courses.OneClass;
import com.example.alex.qtapandroid.common.database.courses.OneClassManager;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Carson on 02/12/2016.
 * Fragment used to control UI of the calendar.
 * Other scheduling information could be added to other parts of the screen.
 * This is the first screen user sees upon logging in (unless first time login).
 * Attached to MainTabActivity only.
 */
public class MonthFragment extends Fragment {
    //TODO replace literal strings with values from a Literals class
    private static final String TAG = StudentToolsFragment.class.getSimpleName();
    private static final String TAG_FRAGMENT = "AgendaFrag";

    private OneClassManager mOneClassManager;
    private CourseManager mCourseManager;
    private DatePicker mDateSelection;

    private TextView mDataInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_month, container, false);
//        mDataInfo = (TextView) v.findViewById(R.id.calendarEvents);
        mDateSelection = (DatePicker) v.findViewById(R.id.datePicker);
        mOneClassManager = new OneClassManager(this.getContext());
        mCourseManager = new CourseManager(this.getContext());
//        setup();
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mDateSelection.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        getCalData();
                    }
                }
        );

//        mDataInfo.setMovementMethod(new ScrollingMovementMethod());
//        mDataInfo.setText(getString(R.string.init_database));
        getData();
    }

    public void getCalData(){
        DatePicker dateSel = (DatePicker) getView().findViewById(R.id.datePicker);

        OldDayFragment nextFrag= new OldDayFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("day", dateSel.getDayOfMonth());
        bundle.putInt("month", dateSel.getMonth());
        bundle.putInt("year", dateSel.getYear());
        nextFrag.setArguments(bundle);

        this.getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, nextFrag)
                .addToBackStack("AgendaFragmentDateClick")
                .commit();
    }

    public void getData() {                     // this function displays the data for the selected day in the green text vie
        ArrayList<OneClass> data = mOneClassManager.getTable();

//        mDataInfo.setText("Event Information for " + (mDateSelection.getMonth() + 1) + "/" + mDateSelection.getDayOfMonth());        // get the selected day

        int day, month, year;
        boolean isInfo = false;
        // look for the selected day in the events from the database
        for (int i = 0; i < data.size(); i++) {
            day = Integer.parseInt(data.get(i).getDay());
            month = Integer.parseInt(data.get(i).getMonth());
            year = Integer.parseInt(data.get(i).getYear());
            String courseTitle = mCourseManager.getRow(data.get(i).getCourseID()).getTitle();
            if (year == mDateSelection.getYear() && month == (mDateSelection.getMonth() + 1)
                    && mDateSelection.getDayOfMonth() == day) {     // if the day matches add its info to mDataInfo

//                mDataInfo.append(System.getProperty("line.separator") + "Event Name: " + courseTitle +
//                        " Location: " + data.get(i).getRoomNum() + " at: " + data.get(i).getStartTime() + " to " +
//                        data.get(i).getEndTime());
                isInfo = true;
            }
        }
        if (!isInfo) {
//            mDataInfo.setText("");
        }
    }

//    public void setup() {
//        UserManager mUserManager = new UserManager(this.getContext());
//        ArrayList<User> user = mUserManager.getTable();
//
//        boolean isInit = false;
//
//        // see if user has initialized database yet, and if the database is up to date
//        for (int i = 0; i < user.size(); i++) {
//
//            if (!user.get(i).getDateInit().isEmpty()) {
//                String rTime = user.get(i).getDateInit();
//                int yr = Integer.parseInt(rTime.substring(0, 4));
//                int mon = Integer.parseInt(rTime.substring(5, 7));
//                int day = Integer.parseInt(rTime.substring(8, 10));
//
//                Calendar c = Calendar.getInstance();
//                c.set(yr, mon, day);
//                c.add(Calendar.DATE, 7);
//                Calendar endDate = Calendar.getInstance();
//
//                if (c.after(endDate)) {
//                    isInit = true;
//                }
//            }
//        }
//        if (mOneClassManager.getTable().isEmpty() || !isInit) {
//            mOneClassManager.deleteTable();
//
//            boolean isEvent = false;
//            String sTime = "", eTime = "", loc = "", name = "", rTime = "";
//            int hour = 0, minute = 0, year = 0;
//            int shour = 0, sminute = 0, sday = 0, smonth = 0;
//            boolean repeatWeekly = false;
//            String rDayStr = "", rMonStr = "", rYrStr = "";
//
//            ParseICS icsParser = new ParseICS(this.getContext());
//            List<String> lines = icsParser.readDownloadFile("cal.ics");
//
//            int test = 1;
//
//            for (String string : lines) {
//
//                if (string.contains("BEGIN:VEVENT")) {
//                    isEvent = true;
//                } else if (string.contains("END:VEVENT")) {
//                    isEvent = false;
//
//                    String tempTime = Integer.toString(shour) + ":" + Integer.toString(sminute);
//                    String tempEndTime = Integer.toString(hour) + ":" + Integer.toString(minute);
//
//                    Course course = new Course(name);
//                    course.setID(mCourseManager.insertRow(course));
////                    Course.printCourses(mCourseManager.getTable());
//
//                    OneClass one = new OneClass(name, loc, tempTime, tempEndTime, Integer.toString(sday), Integer.toString(smonth), Integer.toString(year));
//                    one.setBuildingID(15);       // TODO delete later, this is temporary
//                    one.setCourseID(test);
//                    one.setID(mOneClassManager.insertRow(one));
//
//                    //TODO set course ID as well: query Course table for entry with the same title, that is the ID to use
//
//                    if (repeatWeekly) {
//                        // get the supported ids for GMT-08:00 (Pacific Standard Time)
//                        String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
//                        // if no ids were returned, something is wrong. get out.
//                        if (ids.length == 0)
//                            System.exit(0);
//
//                        // create a Pacific Standard Time time zone
//                        SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);
//
//                        // set up rules for Daylight Saving Time
//                        pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
//                        pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
//
//                        // create a GregorianCalendar with the Pacific Daylight time zone
//                        // and the current date and time
//                        Calendar cal2 = new GregorianCalendar(pdt);
//                        Calendar cal = new GregorianCalendar(pdt);
//
//                        cal.set(year, smonth - 1, sday);
//
//                        cal2.set(Integer.parseInt(rYrStr), Integer.parseInt(rMonStr) - 1, Integer.parseInt(rDayStr) + 1);
//
//                        Date endDate = cal2.getTime();
//                        Date date1;
//
//                        cal.add(Calendar.DATE, 7);
//                        date1 = cal.getTime();
//
//                        while (date1.before(endDate)) {
//                            sday = cal.get(Calendar.DAY_OF_MONTH);
//                            smonth = cal.get(Calendar.MONTH);
//                            year = cal.get(Calendar.YEAR);
//
//                            one = new OneClass(name, loc, tempTime, tempEndTime, Integer.toString(sday), Integer.toString(smonth + 1), Integer.toString(year));
//                            one.setBuildingID(15);       // delete later, this is temporary
//                            one.setCourseID(test);
//                            one.setID(mOneClassManager.insertRow(one));
//                            cal.add(Calendar.DATE, 7);
//                            date1 = cal.getTime();
//                        }
//                    }
//                    repeatWeekly = false;
//                    test += 1;
//
//                } else if (string.contains(("RRULE:FREQ=WEEKLY;"))) {
//                    repeatWeekly = true;
//
//                    if (string.contains("UNTIL=")) {
//                        rTime = string.replaceAll("[^0-9]", "");
//
//                        rDayStr = rTime.substring(6, 8);
//                        rMonStr = rTime.substring(4, 6);
//                        rYrStr = rTime.substring(0, 4);
//                    }
//
//                } else if (isEvent) {
//                    if (string.contains("LOCATION"))
//                        loc = string.substring(9);
//                    else if (string.contains("DTSTART")) {
//                        sTime = string.replaceAll("[^0-9]", "");
//                        shour = Integer.parseInt(sTime.substring(8, 10));
//                        sminute = Integer.parseInt(sTime.substring(10, 12));
//                        sday = Integer.parseInt(sTime.substring(6, 8));
//                        smonth = Integer.parseInt(sTime.substring(4, 6));
//                        year = Integer.parseInt(sTime.substring(0, 4));
//                    } else if (string.contains("DTEND")) {
//                        eTime = string.replaceAll("[^0-9]", "");
////                        Log.d(TAG, "time that's crashing: " + eTime);
//                        hour = Integer.parseInt(eTime.substring(8, 10));
//                        minute = Integer.parseInt(eTime.substring(10, 12));
//
//                    } else if (string.contains("SUMMARY")) {
//                        name = (string.substring(string.lastIndexOf(":") + 1));
//                    }
//                }
//            }
//            Date d = new Date();
//            CharSequence s = DateFormat.format("yyyy-MM-dd hh:mm:ss", d.getTime());
//
//            String uName = user.get(0).getFirstName();
//            String uLastName = user.get(0).getLastName();
//            String uNetID = user.get(0).getNetid();
//            String uURL = user.get(0).getIcsURL();
//
//            User nUser = new User(uNetID, uName, uLastName, s.toString(), uURL);
//            mUserManager.updateRow(user.get(0), nUser);
//        }
//    }
}
