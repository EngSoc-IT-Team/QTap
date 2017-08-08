package com.example.alex.qtapandroid.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.Util;
import com.example.alex.qtapandroid.common.database.local.DatabaseRow;
import com.example.alex.qtapandroid.common.database.local.contacts.emergency.EmergencyContact;
import com.example.alex.qtapandroid.common.database.local.contacts.emergency.EmergencyContactsManager;
import com.example.alex.qtapandroid.interfaces.IQLActionbarFragment;
import com.example.alex.qtapandroid.interfaces.IQLDrawerItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Carson on 12/06/2017.
 * Fragment that displays emergency contact information held in cloud database
 */
public class EmergContactsFragment extends ListFragment implements IQLActionbarFragment, IQLDrawerItem {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        setActionbarTitle();
        selectDrawer();

        ArrayList<HashMap<String, String>> emergContactsList = new ArrayList<>();
        ArrayList<DatabaseRow> contacts = (new EmergencyContactsManager(getActivity().getApplicationContext())).getTable();
        for (DatabaseRow row : contacts) {
            EmergencyContact contact = (EmergencyContact) row;
            HashMap<String, String> map = new HashMap<>();
            map.put(EmergencyContact.COLUMN_NAME, contact.getName());
            map.put(EmergencyContact.COLUMN_PHONE_NUMBER, contact.getPhoneNumber());
            map.put(EmergencyContact.COLUMN_DESCRIPTION, contact.getDescription());
            emergContactsList.add(map);
        }
        ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), emergContactsList,
                R.layout.emerg_contacts_list_item, new String[]{EmergencyContact.COLUMN_NAME, EmergencyContact.COLUMN_PHONE_NUMBER,
                EmergencyContact.COLUMN_DESCRIPTION}, new int[]{R.id.name, R.id.number, R.id.description});
        setListAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setActionbarTitle();
    }

    @Override
    public void onPause() {
        super.onPause();
        deselectDrawer();
    }

    @Override
    public void setActionbarTitle() {
        Util.setActionbarTitle(getString(R.string.fragment_emerg_contacts), (AppCompatActivity) getActivity());
    }

    @Override
    public void deselectDrawer() {
        Util.setDrawerItemSelected(getActivity(), R.id.nav_tools, false);
    }

    @Override
    public void selectDrawer() {
        Util.setDrawerItemSelected(getActivity(), R.id.nav_tools, true);
    }
}
