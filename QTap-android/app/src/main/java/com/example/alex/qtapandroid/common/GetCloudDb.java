package com.example.alex.qtapandroid.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.alex.qtapandroid.R;

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
 */
public class GetCloudDb extends AsyncTask<Void, Void, Void> {

    private static final String TAG_CONTACTS = "EmergencyContacts";
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_NAME = "Name";
    private static final String TAG_NUMBER = "PhoneNumber";
    private static final String TAG_DESCRIPTION = "Description";

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
        JSONObject json = null;
        try {
            //call php script on server that gets info from cloud database
            json = new JSONObject(getJSON(mContext.getString(R.string.db_get_database), 5000));
        } catch (JSONException e) {
            Log.d("HELLOTHERE", "BAD: " + e);
        }
        /*try {
            int success = json.getInt(TAG_SUCCESS);
            if (success == 1) {*/
        Log.d("HELLOTHERE", "JSON\n" + json.toString());
                /*JSONArray contacts = json.getJSONArray(TAG_CONTACTS);
                 for (int i = 0; i < contacts.length(); i++) {
                     JSONObject contact = contacts.getJSONObject(i);
                 }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
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
}