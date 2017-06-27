package com.example.alex.qtapandroid.ui.fragments;



import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.DatabaseAccessor;
import com.example.alex.qtapandroid.common.database.contacts.emergency.EmergencyContact;
import com.example.alex.qtapandroid.common.database.contacts.emergency.EmergencyContactsManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Carson on 12/06/2017.
 * Fragment that displays emergency contact information held in cloud database
 */
public class EmergContactsFragment extends ListFragment {

    private static final String TAG_NAME = "Name";
    private static final String TAG_NUMBER = "PhoneNumber";
    private static final String TAG_DESCRIPTION = "Description";

    private NavigationView mNavView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_emerg_contacts, container, false);
        ArrayList<HashMap<String, String>> emergContactsList = new ArrayList<>();
        ArrayList<EmergencyContact> contacts = (new EmergencyContactsManager(getActivity().getApplicationContext())).getTable();
        for (EmergencyContact contact : contacts) {
            HashMap<String, String> map = new HashMap<>();
            map.put(TAG_NAME, contact.getName());
            map.put(TAG_NUMBER, contact.getPhoneNumber());
            map.put(TAG_DESCRIPTION, contact.getDescription());
            emergContactsList.add(map);
        }
        ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), emergContactsList,
                R.layout.emerg_contacts_list_item, new String[]{TAG_NAME, TAG_NUMBER, TAG_DESCRIPTION}, new int[]{R.id.name, R.id.number, R.id.description});
        setListAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(getString(R.string.title_emergency_contacts));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mNavView.getMenu().findItem(R.id.nav_tools).setChecked(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNavView = (NavigationView) (getActivity()).findViewById(R.id.drawer_layout).findViewById(R.id.nav_view);
        mNavView.getMenu().findItem(R.id.nav_tools).setChecked(true);
        DatabaseAccessor.getDatabase().close();
    }
}
