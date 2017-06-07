package com.example.alex.qtapandroid.ui.fragments;

import android.gesture.Gesture;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.card.elements.DataObject;
import com.example.alex.qtapandroid.common.card.elements.RecyclerViewAdapter;
import com.example.alex.qtapandroid.common.database.courses.OneClass;
import com.example.alex.qtapandroid.common.database.courses.OneClassManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DayFragment extends Fragment {

    private static final String TAG ="DayFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private View view; //not mView because that hides an attribute in a parent class (fragment)
    private TextView dateText;
    private String dateString;
    private int changeAmount;
    private boolean isChanged;
    private Calendar cal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_day, container, false);

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        Log.i("GESTURE", "OnDown");
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        final int SWIPE_MIN_DISTANCE = 100;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;

                        try {
                            Log.i("GESTURE", "onFling has been called! Length: " + (e1.getX() - e2.getX() + " MinDistance: " +  SWIPE_MIN_DISTANCE + " || Velocity: " + Math.abs(velocityX) + " ThresholdVelocity: " + SWIPE_THRESHOLD_VELOCITY));

                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.i("GESTURE", "Right to Left");
                                changeAmount = 1;
                                isChanged = true;
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.i("GESTURE", "Left to Right");
                                changeAmount = -1;
                                isChanged = true;
                            }
                        } catch (Exception e) {
                            // nothing
                            Log.i("GESTURE", "onFling called, Error: " + e.getMessage());
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("GESTURE", "Touch detected!");
                boolean worked = gesture.onTouchEvent(event);
                if (isChanged)
                {
                    changeDate();
                }
                return worked;

            }
        });


        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        };

        mRecyclerView.setOnTouchListener(gestureListener);


        dateText = (TextView) view.findViewById(R.id.DateTextDisplay);

        cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

//        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
//            cal.add(Calendar.DAY_OF_YEAR, 1);

//        String[] s = getDayEventData(cal);
        mAdapter = new RecyclerViewAdapter(getDayEventData(cal));
        mRecyclerView.setAdapter(mAdapter);



        // Code to Add an item with default animation
//        ((RecyclerViewAdapter) mAdapter).addItem(new DataObject("TEST!", "EXAMPLE TEST TEXT..."), 0);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    public void changeDate() {
        cal.add(Calendar.DAY_OF_YEAR,changeAmount);
        changeAmount = 0;
        isChanged = false;
        mAdapter = new RecyclerViewAdapter(getDayEventData(cal));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((RecyclerViewAdapter) mAdapter).setOnItemClickListener(new RecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);

                DataObject data = ((RecyclerViewAdapter) mAdapter).getItem(position);

                CardView card = (CardView) view.findViewById(R.id.card_view);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    card.setTransitionName("transistion_event_info" + position);
                }

                String cardName = card.getTransitionName();


                EventInfoFragment nextFrag=  new EventInfoFragment();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setSharedElementReturnTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(R.transition.card_transistion));
                    setExitTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(android.R.transition.explode));

                    nextFrag.setSharedElementEnterTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(R.transition.card_transistion));
                    nextFrag.setEnterTransition(TransitionInflater.from(
                            getActivity()).inflateTransition(android.R.transition.explode));
                }


                Bundle bundle = new Bundle();
                bundle.putString("data1", data.getmText1());
                bundle.putString("data2", data.getmText2());
                bundle.putString("date", dateString);
                bundle.putString("TRANS_TEXT", cardName);

                nextFrag.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, nextFrag)
                        .addSharedElement(card, cardName)
                        .commit();
            }
        });
    }

    public ArrayList<DataObject> getDayEventData(Calendar calendar) {
        OneClassManager oneClassManager = new OneClassManager(this.getContext());

        List<String> list = new ArrayList<String>();
        List<String> loc = new ArrayList<>();
        List<String> time = new ArrayList<>();
        List<String> des = new ArrayList<>();
        ArrayList<DataObject> result = new ArrayList<DataObject>();

        ArrayList<OneClass> data = oneClassManager.getTable();

        int day, month, year;
        boolean isInfo = false;

        int calDay = calendar.get(Calendar.DAY_OF_MONTH);
        int calMon = calendar.get(Calendar.MONTH) + 1;
        int calYear = calendar.get(Calendar.YEAR);

        CharSequence f = DateFormat.format("yyyy-MM-dd", calendar.getTime());
        CharSequence date = DateFormat.format("EEE, d MMM, yyyy", cal.getTime());
        dateString = date.toString();

        dateText.setText(date);
//        list.add("Showing Information For: " + date);

        for (int i = 0; i < data.size(); i++) {             // look for the selected
            // day in the events from the database
            day = Integer.parseInt(data.get(i).getDay());
            month = Integer.parseInt(data.get(i).getMonth());
            year = Integer.parseInt(data.get(i).getYear());


            if (year == calYear && month == calMon && calDay == day) { // if the day matches add the event
                list.add(data.get(i).getType());
                loc.add(data.get(i).getRoomNum());
                time.add(data.get(i).getStartTime() + "-" + data.get(i).getEndTime());
                des.add(data.get(i).getType());

                isInfo = true;
            }
        }

        if (!isInfo) {
            result.add(new DataObject("No events today", date.toString()));
            return result;
        }


        int startHour;
        int startMin;
        int posSmall = 0;
        int minHour;
        int minMin;
        int endHour =0;
        int endMin =0;
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i) == ("Nothing is happening today")) {
                result.add(new DataObject(list.get(i), f.toString()));
                list.remove(i);
                i -= 1;

            } else {
                minHour = 25;
                minMin = 61;

                for (int j = 0; j < list.size(); j++) {
                    String s = time.get(j);
                    String s1 = s.substring(0, s.indexOf("-"));
                    int div = s1.indexOf(":");
                    String shour = s1.substring(0,div);
                    String smin = s1.substring(div + 1, s1.length());

                    int index = s.indexOf("-") + 1;
                    String s2 = s.substring(index,s.length());
                    Log.d(TAG, "Event: " + list.get(i) + " sTime:" + s1 + " eTime: " + s2);
                    div = s2.indexOf(":");

                    startHour = Integer.parseInt(shour);
                    startMin = Integer.parseInt(smin);
                    if (startHour < minHour) {
                        posSmall = j;
                        minHour = startHour;
                        minMin = startMin;
                        endHour = Integer.parseInt(s2.substring(0,div));
                        endMin = Integer.parseInt(s2.substring(div + 1,s2.length()));

                    } else if (startHour == minHour) {
                        if (startMin < minMin) {
                            posSmall = j;
                            minHour = startHour;
                            minMin = startMin;
                            endHour = Integer.parseInt(s2.substring(0,div));
                            endMin = Integer.parseInt(s2.substring(div + 1,s2.length()));

                        }
                    }
                }
                String amPMTime;
                if (minHour > 12)
                    amPMTime = (minHour - 12) + ":" +  minMin + "-" + (endHour - 12) + ":" + endMin + " PM";
                else if (endHour > 12)
                    amPMTime = (minHour) + ":" +  minMin + "-" + (endHour - 12) + ":" + endMin + " PM";
                else amPMTime = time.get(posSmall) + " AM";

                result.add(new DataObject(list.get(posSmall), amPMTime + " at: " + loc.get(posSmall)));
                list.remove(posSmall);
                time.remove(posSmall);
                loc.remove(posSmall);
                des.remove(posSmall);
                i = -1;
            }
        }
        if (list.size() > 0) {
            result.add(new DataObject(list.get(0), time.get(0) + " at: " + loc.get(0) + " description: " + des.get(0)));
        }


        return result;
    }
}