package qlife.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import qlife.R;
import qlife.database.local.buildings.Building;
import qlife.database.local.buildings.BuildingManager;
import qlife.database.local.cafeterias.Cafeteria;
import qlife.database.local.cafeterias.CafeteriaManager;
import qlife.database.local.contacts.emergency.EmergencyContact;
import qlife.database.local.contacts.emergency.EmergencyContactsManager;
import qlife.database.local.contacts.engineering.EngineeringContact;
import qlife.database.local.contacts.engineering.EngineeringContactsManager;
import qlife.database.local.food.Food;
import qlife.database.local.food.FoodManager;

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

    private static final String TAG_SUCCESS = "Success";

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

    /**
     * Method that ties together methods that get specific parts of the cloud database JSON.
     * @param json The JSONObject representing the cloud database.
     */
    private void cloudToPhoneDB(JSONObject json) {
        emergencyContacts(json);
        engineeringContacts(json);
        buildings(json);
        food(json);
        cafeterias(json);
    }

    /**
     * Method that retrieves and inserts the cloud emergency contact data into the phone database.
     * @param json The JSONObject representing the cloud database.
     */
    private void emergencyContacts(JSONObject json) {
        try {
            JSONArray contacts = json.getJSONArray(EmergencyContact.TABLE_NAME);
            EmergencyContactsManager tableManager = new EmergencyContactsManager(mContext);
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject contact = contacts.getJSONObject(i);
                tableManager.insertRow(new EmergencyContact(contact.getInt(EmergencyContact.ID), contact.getString(EmergencyContact.COLUMN_NAME),
                        contact.getString(EmergencyContact.COLUMN_PHONE_NUMBER), contact.getString(EmergencyContact.COLUMN_DESCRIPTION)));
            }
        } catch (JSONException e) {
            Log.d("HELLOTHERE", "EMERG: " + e);
        }
    }

    /**
     * Method that retrieves and inserts the cloud engineering contact data into the phone database.
     * @param json The JSONObject representing the cloud database.
     */
    private void engineeringContacts(JSONObject json) {
        try {
            JSONArray contacts = json.getJSONArray(EngineeringContact.TABLE_NAME);
            EngineeringContactsManager tableManager = new EngineeringContactsManager(mContext);
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject contact = contacts.getJSONObject(i);
                tableManager.insertRow(new EngineeringContact(contact.getInt(EngineeringContact.ID), contact.getString(EngineeringContact.COLUMN_NAME), contact.getString(EngineeringContact.COLUMN_EMAIL),
                        contact.getString(EngineeringContact.COLUMN_POSITION), contact.getString(EngineeringContact.COLUMN_DESCRIPTION)));
            }
        } catch (JSONException e) {
            Log.d("HELLOTHERE", "ENG: " + e);
        }
    }

    /**
     * Method that retrieves and inserts the cloud buildings data into the phone database.
     * @param json The JSONObject representing the cloud database.
     */
    private void buildings(JSONObject json) {
        try {
            JSONArray buildings = json.getJSONArray(Building.TABLE_NAME);
            BuildingManager manager = new BuildingManager(mContext);
            for (int i = 0; i < buildings.length(); i++) {
                JSONObject building = buildings.getJSONObject(i);
                //getInt()>0 because SQL has 0/1 there, not real boolean
                manager.insertRow(new Building(building.getInt(Building.ID), building.getString(Building.COLUMN_NAME), building.getString(Building.COLUMN_PURPOSE),
                        building.getInt(Building.COLUMN_BOOK_ROOMS) > 0, building.getInt(Building.COLUMN_FOOD) > 0, building.getInt(Building.COLUMN_ATM) > 0,
                        building.getDouble(Building.COLUMN_LAT), building.getDouble(Building.COLUMN_LON)));
            }
        } catch (JSONException e) {
            Log.d("HELLOTHERE", "BUILDING: " + e);
        }
    }

    /**
     * Method that retrieves and inserts the cloud food data into the phone database.
     * @param json The JSONObject representing the cloud database.
     */
    private void food(JSONObject json) {
        try {
            JSONArray food = json.getJSONArray(Food.TABLE_NAME);
            FoodManager manager = new FoodManager(mContext);
            for (int i = 0; i < food.length(); i++) {
                JSONObject oneFood = food.getJSONObject(i);
                //getInt()>0 because SQL has 0/1 there, not real boolean
                manager.insertRow(new Food(oneFood.getInt(Food.ID), oneFood.getString(Food.COLUMN_NAME), oneFood.getInt(Food.COLUMN_BUILDING_ID),
                        oneFood.getString(Food.COLUMN_INFORMATION), oneFood.getInt(Food.COLUMN_MEAL_PLAN) > 0, oneFood.getInt(Food.COLUMN_CARD) > 0,
                        oneFood.getDouble(Food.COLUMN_MON_START_HOURS), oneFood.getDouble(Food.COLUMN_MON_STOP_HOURS), oneFood.getDouble(Food.COLUMN_TUE_START_HOURS),
                        oneFood.getDouble(Food.COLUMN_TUE_STOP_HOURS), oneFood.getDouble(Food.COLUMN_WED_START_HOURS), oneFood.getDouble(Food.COLUMN_WED_STOP_HOURS),
                        oneFood.getDouble(Food.COLUMN_THUR_START_HOURS), oneFood.getDouble(Food.COLUMN_THUR_STOP_HOURS), oneFood.getDouble(Food.COLUMN_FRI_START_HOURS),
                        oneFood.getDouble(Food.COLUMN_FRI_STOP_HOURS), oneFood.getDouble(Food.COLUMN_SAT_START_HOURS),
                        oneFood.getDouble(Food.COLUMN_SAT_STOP_HOURS), oneFood.getDouble(Food.COLUMN_SUN_START_HOURS), oneFood.getDouble(Food.COLUMN_SUN_STOP_HOURS)));
            }
        } catch (JSONException e) {
            Log.d("HELLOTHERE", "FOOD: " + e);
        }
    }

    /**
     * Method that retrieves and inserts the cloud cafeteria data into the phone database.
     * @param json The JSONObject representing the cloud database.
     */
    private void cafeterias(JSONObject json) {
        try {
            JSONArray cafs = json.getJSONArray(Cafeteria.TABLE_NAME);
            CafeteriaManager manager = new CafeteriaManager(mContext);
            for (int i = 0; i < cafs.length(); i++) {
                JSONObject caf = cafs.getJSONObject(i);
                manager.insertRow(new Cafeteria(caf.getInt(Cafeteria.ID), caf.getString(Cafeteria.COLUMN_NAME), caf.getInt(Cafeteria.COLUMN_BUILDING_ID),
                        caf.getDouble(Cafeteria.COLUMN_WEEK_BREAKFAST_START), caf.getDouble(Cafeteria.COLUMN_WEEK_BREAKFAST_STOP),
                        caf.getDouble(Cafeteria.COLUMN_FRI_BREAKFAST_START), caf.getDouble(Cafeteria.COLUMN_FRI_BREAKFAST_STOP),
                        caf.getDouble(Cafeteria.COLUMN_SAT_BREAKFAST_START), caf.getDouble(Cafeteria.COLUMN_SAT_BREAKFAST_STOP),
                        caf.getDouble(Cafeteria.COLUMN_SUN_BREAKFAST_START), caf.getDouble(Cafeteria.COLUMN_SUN_BREAKFAST_STOP),
                        caf.getDouble(Cafeteria.COLUMN_WEEK_LUNCH_START), caf.getDouble(Cafeteria.COLUMN_WEEK_LUNCH_STOP),
                        caf.getDouble(Cafeteria.COLUMN_FRI_LUNCH_START), caf.getDouble(Cafeteria.COLUMN_FRI_LUNCH_STOP),
                        caf.getDouble(Cafeteria.COLUMN_SAT_LUNCH_START), caf.getDouble(Cafeteria.COLUMN_SAT_LUNCH_STOP),
                        caf.getDouble(Cafeteria.COLUMN_SUN_LUNCH_START), caf.getDouble(Cafeteria.COLUMN_SUN_LUNCH_STOP),
                        caf.getDouble(Cafeteria.COLUMN_WEEK_DINNER_START), caf.getDouble(Cafeteria.COLUMN_WEEK_DINNER_STOP),
                        caf.getDouble(Cafeteria.COLUMN_FRI_DINNER_START), caf.getDouble(Cafeteria.COLUMN_FRI_DINNER_STOP),
                        caf.getDouble(Cafeteria.COLUMN_SAT_DINNER_START), caf.getDouble(Cafeteria.COLUMN_SAT_DINNER_STOP),
                        caf.getDouble(Cafeteria.COLUMN_SUN_DINNER_START), caf.getDouble(Cafeteria.COLUMN_SUN_DINNER_STOP)));
            }
        } catch (JSONException e) {
            Log.d("HELLOTHERE", "CAF: " + e);
        }
    }
}