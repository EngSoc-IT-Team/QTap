package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.courses.OneClass;
import com.example.alex.qtapandroid.common.database.courses.OneClassManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Carson on 02/12/2016.
 * Fragment that shows the classes for one day, and allows the user to cycle through to the next days.
 */
public class OldDayFragment extends Fragment {

    private static final String TAG = OldDayFragment.class.getSimpleName();
    private Calendar cal;
    private TextView mDataInfo;
    private View view; //not mView because that hides an attribute in a parent class (fragment)
    private boolean flag_loading = false;
    private  String[] itemArr;
    private  ArrayAdapter<String> adapter;
    private int positionToSave;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_agenda, container, false);
        final ListView listview = (ListView) view.findViewById(R.id.agendaList);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int day = bundle.getInt("day", 0);
            int month = bundle.getInt("month", 0);
            int year = bundle.getInt("year", calendar.get(Calendar.YEAR));

            calendar.set(year, month, day);

        }

        itemArr = getDayEventData(calendar);


        listview.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                {
//                    Log.d(TAG, "At bottom of list!");

                    if(flag_loading == false)
                    {
                        flag_loading = true;
//                        cal.add(Calendar.DAY_OF_YEAR, 1);
                        positionToSave = listview.getFirstVisiblePosition() + 1;
                        onClickEmailSignInButton();
                    }//        listview.post(new Runnable() {
//
//            @Override
//            public void run() {
//                listview.setSelection(positionToSave);
//            }
//        });

                }
            }
        });

//        listview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//
//            @Override
//            public boolean onPreDraw() {
//                if(listview.getFirstVisiblePosition() == positionToSave) {
//                    listview.getViewTreeObserver().removeOnPreDrawListener(this);
//                    return true;
//                }
//                else {
//                    return false;
//                }
//            }
//        });


        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, itemArr);
        listview.setAdapter(adapter);



        mDataInfo = (TextView) view.findViewById(R.id.textDay);
        CharSequence s = DateFormat.format("yyyy-MM-dd", calendar.getTime());
        mDataInfo.setText("Showing Information For: " + s + " (" + calendar.getDisplayName(
                Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) + ")");

        Button mEmailSignInButton = (Button) view.findViewById(R.id.nextDay);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEmailSignInButton();
            }
        });

        return view;
    }

    /**
     * Defines the on click listener code for the email sign in button = mEmailSignInButton.
     */
    public void onClickEmailSignInButton() {
//        String rTime = mDataInfo.getText().toString();
//        Calendar calendar = cal;
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        rTime = rTime.substring(rTime.indexOf(":") + 2);
//
//        int yr = Integer.parseInt(rTime.substring(0, 4));
//        int mon = Integer.parseInt(rTime.substring(5, 7)) - 1;
//        int day = Integer.parseInt(rTime.substring(8, 10));
//
//        calendar.set(yr, mon, day);

        cal.add(Calendar.DAY_OF_YEAR, 1);
//        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
////            calendar.add(Calendar.DAY_OF_YEAR, 2);
//            cal.add(Calendar.DAY_OF_YEAR, 2);
//        }
//        else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
//            cal.add(Calendar.DAY_OF_YEAR, 1);


        ListView listview = (ListView) view.findViewById(R.id.agendaList);
        String[] items = getDayEventData(cal);
        String[] oldItems = itemArr;

        itemArr = new String[oldItems.length + items.length + 1];

        for (int i = 0; i<oldItems.length; i++)
            itemArr[i] = oldItems[i];
        itemArr[oldItems.length] = "";
        itemArr[oldItems.length + 1] = "";

        for (int i = 0; i<items.length; i++)
            itemArr[i + oldItems.length + 1] = items[i];

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, itemArr);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        CharSequence s = DateFormat.format("yyyy-MM-dd", cal.getTime());
        mDataInfo.setText("Showing Information For: " + s + " (" + cal.getDisplayName(
                Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) + ")");
        flag_loading = false;
        listview.setSelection(positionToSave);
    }

    /**
     * Method that displays the events for the day selected in the calendar.
     *
     * @param calendar The calendar where the user selects a day.
     * @return Array of Strings that hold each event's information.
     */
    public String[] getDayEventData(Calendar calendar) {
        OneClassManager oneClassManager = new OneClassManager(this.getContext());

        List<String> list = new ArrayList<String>();
        ArrayList<OneClass> data = oneClassManager.getTable();

        int day, month, year, sDay, sMon;
        boolean isInfo = false;
        boolean endWeek = false;
         boolean dayInfo = false;
        //calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
        sDay = calendar.get(Calendar.DAY_OF_MONTH);
        sMon = calendar.get(Calendar.MONTH) + 1;

        while (!endWeek) {

            int calDay = calendar.get(Calendar.DAY_OF_MONTH);
            int calMon = calendar.get(Calendar.MONTH) + 1;
            int calYear = calendar.get(Calendar.YEAR);

            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                endWeek = true;
            CharSequence s = DateFormat.format("yyyy-MM-dd", calendar.getTime());
            list.add("Showing Information For: " + s + " (" + calendar.getDisplayName(
                    Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) + ")");

            for (int i = 0; i < data.size(); i++) {             // look for the selected day in the events from the database
                day = Integer.parseInt(data.get(i).getDay());
                month = Integer.parseInt(data.get(i).getMonth());
                year = Integer.parseInt(data.get(i).getYear());


                if (year == calYear && month == calMon && calDay == day) { // if the day matches add the event
                    list.add(s + "/~/" + "Event Name: " + data.get(i).getType() + " Location: " +
                            data.get(i).getRoomNum() + " at: " + data.get(i).getStartTime() +
                            " to " + data.get(i).getEndTime());
                    isInfo = true;
                    dayInfo = true;
                }
            }
            if (!dayInfo) {
                list.add("Nothing is happening today");
                Log.d(TAG, "No events on " + calDay +"/" + calMon + "/" + calYear);
            }
            if (!endWeek)
                calendar.add(Calendar.DAY_OF_YEAR,1);
            cal = calendar;
            dayInfo = false;
        }

        if (!isInfo) {
            list.clear();
            list.add("No events this week (" +  sMon + "/" + sDay + " - " + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + ")" );
            return list.toArray(new String[list.size()]);
        }

        List<String> result = new ArrayList<String>();

        List<Integer> rhours = new ArrayList();
        List<Integer> rmins = new ArrayList();

        int startHour;
        int startMin;
        int posSmall = 0;
        int minHour;
        int minMin;
        boolean isNotFirstDay = false;
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).contains("Showing Information For:")) {
                if (isNotFirstDay)
                    result.add("");
                isNotFirstDay = true;
                result.add(list.get(i));
                list.remove(i);
                i -= 1;
            }else if (list.get(i) == ("Nothing is happening today"))
                {
                    result.add(list.get(i));
                    list.remove(i);
                    i -= 1;

            }else {
                minHour = 25;
                minMin = 61;

                for (int j = 0; j < list.size(); j++) {
                    String s = list.get(j);
                    String s1 = s.substring(0,10);
                    String s2 = list.get(i).substring(0,10);
                    Log.d(TAG, "i.substring" + list.get(i).substring(0,10) + " Event j.substring:" + s1 + " s2.substring: " + s2);

                    if (s1.equals(s2)) {
//                        if (!s.contains("Showing Information For:")) {
                            Log.d(TAG, "Event i: " + list.get(i) + " Event j:" + s);

                            int index = s.indexOf("at: ") + 4;
                            String tp = s.substring(index);
                            String temp1 = tp.substring(0, tp.indexOf(":"));
                            index = temp1.length() + 1;
                            String temp2 = tp.substring(index, tp.indexOf(" "));

                            startHour = Integer.parseInt(temp1);
                            startMin = Integer.parseInt(temp2);
                            if (startHour < minHour) {
                                posSmall = j;
                                minHour = startHour;
                                minMin = startMin;
                            } else if (startHour == minHour) {
                                if (startMin < minMin) {
                                    posSmall = j;
                                    minHour = startHour;
                                    minMin = startMin;
                                }
                            }
//                        }
                    }
                }
                rhours.add(minHour);
                rmins.add(minHour);
                result.add(list.get(posSmall).substring(13));
                list.remove(posSmall);
                i = -1;
            }
        }
        if (list.size() > 0) {
            if (list.get(0).contains("/~/"))
                result.add(list.get(0).substring(13));
            else
            result.add(list.get(0));

            // This stuff is for adding spacing to events
//            int index = list.get((0)).indexOf("at: ") + 4;
//            String tp = list.get((0)).substring(index);
//            String temp1 = tp.substring(0, tp.indexOf(":"));
//            index = temp1.length() + 1;
//            String temp2 = tp.substring(index, list.get((0)).indexOf(" ") - 1);
//
//            rhours.add(Integer.parseInt(temp1));
//            rmins.add(Integer.parseInt(temp2));

        }

        // This stuff is for adding spacing to events

//        for (int i = 0; i < result.size() - 1; i++) {
//
//            int start2 = 0;
//            int start1 = 0;
//
//            if (result.get(i) != "") {
//                start2 = rhours.get(i + 1);  // start time of event 2
//                start1 = rhours.get(i);      // start time of event 1
//
//                while (start2 > (start1 + 1)) {
//                    result.add(i + 1, "");
//                    rhours.add(i+ 1, -1);
//                    i++;
//                    start1++;
//                }
//            }
//        }

        return result.toArray(new String[list.size()]);
    }
}


