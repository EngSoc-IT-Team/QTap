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
 * Fragment that displays emergency contact information held in cloud database
 */
public class EmergContactsActivity extends ListActivity {

    private static final String TAG_CONTACTS = "EmergencyContacts";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NAME = "Name";
    private static final String TAG_NUMBER = "PhoneNumber";
    private static final String TAG_DESCRIPTION = "Description";

    private ProgressDialog mProgressDialog;
    private ArrayList<HashMap<String, String>> productsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emerg_contacts);
        productsList = new ArrayList<>();
        new GetEmergContacts().execute();
    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     */
    private class GetEmergContacts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(EmergContactsActivity.this);
            mProgressDialog.setMessage("Loading products. Please wait...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
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

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            JSONObject json = null;
            try {
                //call php script on server that gets info from cloud database
                json = new JSONObject(getJSON(getString(R.string.db_all_emerg_contacts), 10000));
            } catch (JSONException e) {
                Log.d("HELLOTHERE", "BAD: " + e);
            }
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray products = json.getJSONArray(TAG_CONTACTS);
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject product = products.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("name", product.getString(TAG_NAME));
                        map.put(TAG_NUMBER, product.getString(TAG_NUMBER));
                        map.put(TAG_DESCRIPTION, product.getString(TAG_DESCRIPTION));
                        productsList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            mProgressDialog.dismiss();

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    //Updating parsed JSON data into ListView
                    ListAdapter adapter = new SimpleAdapter(EmergContactsActivity.this, productsList,
                            R.layout.emerg_contacts_list_item, new String[]{"name",TAG_NUMBER,TAG_DESCRIPTION}, new int[]{R.id.name,R.id.number,R.id.description});
                    setListAdapter(adapter);
                }
            });
        }
    }
}