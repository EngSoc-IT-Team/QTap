package com.example.alex.qtapandroid.activities;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.DatabaseAccessor;
import com.example.alex.qtapandroid.common.database.contacts.emergency.EmergencyContact;
import com.example.alex.qtapandroid.common.database.contacts.emergency.EmergencyContactsManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Carson on 12/06/2017.
 * Activity that displays emergency contact information held in cloud database
 */
public class EmergContactsActivity extends ListActivity {

    private static final String TAG_NAME = "Name";
    private static final String TAG_NUMBER = "PhoneNumber";
    private static final String TAG_DESCRIPTION = "Description";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emerg_contacts);
        ArrayList<HashMap<String, String>> emergContactsList = new ArrayList<>();
        ArrayList<EmergencyContact> contacts = (new EmergencyContactsManager(this)).getTable();
        Log.d("SQLITE","DISPLAY");
        EmergencyContact.printEmergencyContacts(contacts);
        for (EmergencyContact contact : contacts) {
            HashMap<String, String> map = new HashMap<>();
            map.put(TAG_NAME, contact.getName());
            map.put(TAG_NUMBER, contact.getPhoneNumber());
            map.put(TAG_DESCRIPTION, contact.getDescription());
            emergContactsList.add(map);
        }
        ListAdapter adapter = new SimpleAdapter(EmergContactsActivity.this, emergContactsList,
                R.layout.emerg_contacts_list_item, new String[]{TAG_NAME, TAG_NUMBER, TAG_DESCRIPTION}, new int[]{R.id.name, R.id.number, R.id.description});
        setListAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseAccessor.getDatabase().close();
    }
}
