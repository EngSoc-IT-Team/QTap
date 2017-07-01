package com.example.alex.qtapandroid.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.buildings.Building;
import com.example.alex.qtapandroid.common.database.buildings.BuildingManager;
import com.example.alex.qtapandroid.common.database.contacts.emergency.EmergencyContact;
import com.example.alex.qtapandroid.common.database.contacts.emergency.EmergencyContactsManager;
import com.example.alex.qtapandroid.common.database.contacts.engineering.EngineeringContact;
import com.example.alex.qtapandroid.common.database.contacts.engineering.EngineeringContactsManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Carson on 21/06/2017.
 * Async task that downloads and parses the cloud database into the phone database.
 */
public class GetCloudDb extends AsyncTask<Void, Void, Void> {

    //tables
    private static final String TAG_EMERGENCY_CONTACTS = "EmergencyContacts";
    private static final String TAG_ENGINEERING_CONTACTS = "EngineeringContacts";
    private static final String TAG_BUILDINGS = "Buildings";

    //fields for tables
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_NAME = "Name";
    private static final String TAG_EMAIL = "Email";
    private static final String TAG_POSITION = "Position";
    private static final String TAG_NUMBER = "PhoneNumber";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_PURPOSE = "Purpose";
    private static final String TAG_BOOK_ROMMS = "BookRooms";
    private static final String TAG_FOOD = "Food";
    private static final String TAG_ATM = "ATM";
    private static final String TAG_LAT = "Lat";
    private static final String TAG_LON = "Lon";

    private ProgressDialog mProgressDialog;
    private Context mContext;

    public GetCloudDb(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Downloading cloud database. Please wait...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        JSONObject json;
        try {
            //call php script on server that gets info from cloud database
            json = new JSONObject(getJSON(mContext.getString(R.string.db_get_database), 5000));
            int success = json.getInt(TAG_SUCCESS);
            if (success == 1) {
                cloudToPhoneDB(json);
            }
        } catch (JSONException e) {
            Log.d("HELLOTHERE", "BAD: " + e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mProgressDialog.dismiss();
    }

    private String getJSON(String url, int timeout) {
        HttpURLConnection con = null;
        try {
            URL u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-length", "0");
            con.setRequestProperty("Connection", "close");
            con.setUseCaches(false);
            con.setAllowUserInteraction(false);
            con.setConnectTimeout(timeout);
            con.setReadTimeout(timeout);
            con.connect();
            int status = con.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            Log.d("HELLOTHERE", "bad " + e);
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    private void cloudToPhoneDB(JSONObject json) {
        emergencyContacts(json);
        engineeringContacts(json);
        buildings(json);
    }

    private void emergencyContacts(JSONObject json) {
        try {
            JSONArray contacts = json.getJSONArray(TAG_EMERGENCY_CONTACTS);
            EmergencyContactsManager tableManager = new EmergencyContactsManager(mContext);
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject contact = contacts.getJSONObject(i);
                tableManager.insertRow(new EmergencyContact(contact.getString(TAG_NAME), contact.getString(TAG_NUMBER), contact.getString(TAG_DESCRIPTION)));
            }
        } catch (JSONException e) {
            Log.d("HELLOTHERE", "BAD: " + e);
        }
    }

    private void engineeringContacts(JSONObject json) {
        try {
            JSONArray contacts = json.getJSONArray(TAG_ENGINEERING_CONTACTS);
            EngineeringContactsManager tableManager = new EngineeringContactsManager(mContext);
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject contact = contacts.getJSONObject(i);
                tableManager.insertRow(new EngineeringContact(contact.getString(TAG_NAME), contact.getString(TAG_EMAIL),
                        contact.getString(TAG_POSITION), contact.getString(TAG_DESCRIPTION)));
            }
        } catch (JSONException e) {
            Log.d("HELLOTHERE", "BAD: " + e);
        }
    }

    private void buildings(JSONObject json) {
        try {
            JSONArray buildings = json.getJSONArray(TAG_BUILDINGS);
            BuildingManager manager = new BuildingManager(mContext);
            for (int i = 0; i < buildings.length(); i++) {
                JSONObject building = buildings.getJSONObject(i);
                //getInt()>0 because SQL has 0/1 there, not real boolean
                manager.insertRow(new Building(building.getString(TAG_NAME), building.getString(TAG_PURPOSE),
                        building.getInt(TAG_BOOK_ROMMS) > 0, building.getInt(TAG_FOOD) > 0, building.getInt(TAG_ATM) > 0,
                        building.getDouble(TAG_LAT), building.getDouble(TAG_LON)));
            }
        } catch (JSONException e) {
            Log.d("HELLOTHERE", "BAD: " + e);
        }
    }
}