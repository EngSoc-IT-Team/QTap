package com.example.alex.qtapandroid.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.transition.Visibility;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.activities.MainTabActivity;
import com.example.alex.qtapandroid.common.card.elements.DataObject;
import com.example.alex.qtapandroid.common.card.elements.RecyclerViewAdapter;
import com.example.alex.qtapandroid.common.database.dibs.ILCRoomObj;
import com.example.alex.qtapandroid.common.database.dibs.ILCRoomObjManager;
import com.example.alex.qtapandroid.common.database.dibs.getDibsApiInfo;
import com.example.alex.qtapandroid.common.database.local.DatabaseRow;
import com.example.alex.qtapandroid.common.database.local.courses.OneClass;
import com.example.alex.qtapandroid.common.database.local.courses.OneClassManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ILCRoomInfoFragment extends Fragment {

    public static final String TAG_TITLE = "event_title";
    public static final String TAG_PIC = "pic";
    public static final String TAG_MAP = "map";
    public static final String TAG_LOC = "event_locat";

    private static int mInstances = 0;
    private static SparseIntArray mArray = new SparseIntArray();
    private int mTotalDaysChange = 0;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private NavigationView mNavView;
    private View mView;
    private TextView mDateText;
    private JSONArray json;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_day, container, false);
        mDateText = (TextView) mView.findViewById(R.id.date);

        Bundle bundle = getArguments();

        getDibsApiInfo dibs = new getDibsApiInfo(this.getContext());
        try {
            dibs.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        mRecyclerView = (RecyclerView) mView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewAdapter(getDayEventData());
        mRecyclerView.setAdapter(mAdapter);

        Button nextButton = (Button) mView.findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDate(1);
                mTotalDaysChange += 1;
            }
        });
        Button prevButton = (Button) mView.findViewById(R.id.prev);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDate(-1);
                mTotalDaysChange += -1;
            }
        });

        nextButton.setVisibility(View.GONE);
        prevButton.setVisibility(View.GONE);

        ((RecyclerViewAdapter) mAdapter).setOnItemClickListener(new RecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                DataObject data = ((RecyclerViewAdapter) mAdapter).getItem(position);

                CardView card = (CardView) mView.findViewById(R.id.card_view);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    card.setTransitionName("transistion_event_info" + position);
                }
                ILCRoomObjManager roomInf = new ILCRoomObjManager(getContext());
                ArrayList<DatabaseRow> info = roomInf.getTable();

                String map = "";
                String pic = "";

                if (data != null && info.size() > 0) {
                    for (DatabaseRow row : info) {
                        ILCRoomObj room = (ILCRoomObj) row;
                        if (room.getId() == position) {
                            map = room.getMapUrl();
                            pic = room.getPicUrl();
                        break;
                        }
                    }
                }

                String cardName = card.getTransitionName();
                EventInfoFragment nextFrag = new EventInfoFragment();

                Bundle bundle = new Bundle();
                bundle.putString(TAG_TITLE, data.getmText1());
                bundle.putString(TAG_LOC, data.getmText2()  + "\n" + map + "\n" + pic);
//                bundle.putString(TAG_MAP, map);
//                bundle.putString(TAG_PIC, pic);

                nextFrag.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().addToBackStack(null)
                        .replace(R.id.content_frame, nextFrag)
                        .addSharedElement(card, cardName)
                        .commit();
            }
        });
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(getString(R.string.fragment_ilc_rooms));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstances++;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!((MainTabActivity) getActivity()).isToActivity()) {
            mArray.put(mInstances, mTotalDaysChange); //save number of days to move day view
        } else {
            mArray.put(mInstances, 0);
        }
        mNavView.getMenu().findItem(R.id.nav_rooms).setChecked(false);
    }

    @Override
    public void onDestroy() {
        mArray.delete(mInstances); //instance gone, don't need entry
        mInstances--;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mNavView = (NavigationView) (getActivity()).findViewById(R.id.drawer_layout).findViewById(R.id.nav_view);
        changeDate(mArray.get(mInstances, 0)); //account for day changed before moved fragments
        mNavView.getMenu().findItem(R.id.nav_rooms).setChecked(true);
    }

    public void changeDate(int numChange) {
//        mCalendar.add(Calendar.DAY_OF_YEAR, numChange);
//        mAdapter = new RecyclerViewAdapter(getDayEventData(mCalendar));
//        mRecyclerView.setAdapter(mAdapter);
    }

    public ArrayList<DataObject> getDayEventData() {
        TextView noClassMessage = (TextView) mView.findViewById(R.id.no_class_message);
        noClassMessage.setVisibility(View.GONE); //updates day view when go to new day - may have class
        ILCRoomObjManager roomInf = new ILCRoomObjManager(this.getContext());

        ArrayList<DataObject> result = new ArrayList<DataObject>();

        ArrayList<DatabaseRow> data = roomInf.getTable();

        if (data != null && data.size() > 0) {
            for (DatabaseRow row : data) {
                ILCRoomObj room = (ILCRoomObj) row;
                String hasTV = room.getDescription().contains("TV") || room.getDescription().contains("Projector") ? "Yes" : "No";
                result.add(new DataObject(room.getName(), room.getDescription() + "\nHas TV: " + hasTV));
            }
            return result;
        }
        return null;
    }
}