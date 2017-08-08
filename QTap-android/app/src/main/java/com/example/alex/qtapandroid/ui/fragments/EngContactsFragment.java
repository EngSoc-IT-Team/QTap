package com.example.alex.qtapandroid.ui.fragments;


import android.os.Bundle;
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
import com.example.alex.qtapandroid.common.database.local.contacts.engineering.EngineeringContact;
import com.example.alex.qtapandroid.common.database.local.contacts.engineering.EngineeringContactsManager;
import com.example.alex.qtapandroid.interfaces.IQLActionbarFragment;
import com.example.alex.qtapandroid.interfaces.IQLDrawerItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Carson on 12/06/2017.
 * Activity that displays engineering contact information held in cloud database
 */
public class EngContactsFragment extends ListFragment implements IQLActionbarFragment, IQLDrawerItem {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        setActionbarTitle();
        selectDrawer();

        ArrayList<HashMap<String, String>> engContactsList = new ArrayList<>();
        ArrayList<DatabaseRow> contacts = (new EngineeringContactsManager(getActivity().getApplicationContext())).getTable();
        for (DatabaseRow row : contacts) {
            EngineeringContact contact = (EngineeringContact) row;
            HashMap<String, String> map = new HashMap<>();
            map.put(EngineeringContact.COLUMN_NAME, contact.getName());
            map.put(EngineeringContact.COLUMN_EMAIL, contact.getEmail());
            map.put(EngineeringContact.COLUMN_POSITION, contact.getPosition());
            map.put(EngineeringContact.COLUMN_DESCRIPTION, contact.getDescription());
            engContactsList.add(map);
        }
        ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), engContactsList,
                R.layout.eng_contacts_list_item, new String[]{EngineeringContact.COLUMN_NAME, EngineeringContact.COLUMN_EMAIL,
                EngineeringContact.COLUMN_POSITION, EngineeringContact.COLUMN_DESCRIPTION}, new int[]{R.id.name, R.id.email, R.id.position, R.id.description});
        setListAdapter(adapter);
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        deselectDrawer();
    }

    @Override
    public void setActionbarTitle() {
        Util.setActionbarTitle(getString(R.string.fragment_eng_contacts), (AppCompatActivity) getActivity());
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
