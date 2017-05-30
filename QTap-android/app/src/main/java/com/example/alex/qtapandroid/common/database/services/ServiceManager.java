package com.example.alex.qtapandroid.common.database.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alex.qtapandroid.common.database.DatabaseAccessor;

import java.util.ArrayList;
/**
 * Created by owner on 2017-02-01.
 */

public class ServiceManager extends DatabaseAccessor{

    public ServiceManager(Context context){super(context);}

    public long insertRow(Service service){
        ContentValues values = new ContentValues();
        values.put(Service.COLUMN_HOURS,service.getHours());
        values.put(Service.COLUMN_BUILDING_ID, service.getBuildingID());
        values.put(Service.COLUMN_WEBSITE, service.getWebsite());
        values.put(Service.COLUMN_PURPOSE, service.getPurpose());
        return getDatabase().insert(Service.TABLE_NAME, null, values);
    }
    public void deleteRow(Service service){
        String selection = Service._ID + "LIKE ?";
        String[] selectionArgs = {String .valueOf(service.getID())};
        getDatabase().delete(Service.TABLE_NAME,selection,selectionArgs);
    }

    public ArrayList<Service> getTable(){
        String[] projection = {
                Service._ID,
                Service.COLUMN_HOURS,
                Service.COLUMN_BUILDING_ID,
                Service.COLUMN_PURPOSE,
                Service.COLUMN_WEBSITE,
        };
                ArrayList<Service> services = new ArrayList<>();
        try (Cursor cursor = getDatabase().query(Service.TABLE_NAME,projection,null,null,null,null,null)) {
            while (cursor.moveToNext()) {
                    Service service = getRow(cursor.getInt(Service.ID_POS));
                    services.add(service);
            }
            cursor.close();
            return services;
        }
    }
    public Service getRow(long id){
        String[] projection = {
                Service._ID,
                Service.COLUMN_HOURS,
                Service.COLUMN_BUILDING_ID,
                Service.COLUMN_PURPOSE,
                Service.COLUMN_WEBSITE,

        };
        Service service;
        String selection = Service._ID + "LIKE ?";
        String[] selectionArgs = {String .valueOf(id)};
        try (Cursor cursor = getDatabase().query(Service.TABLE_NAME, projection, selection,selectionArgs,null,null,null)){
            if (cursor != null && cursor.moveToNext()){
                service = new Service(cursor.getString(Service.HOUSE_POS),cursor.getInt(Service.BUILDING_ID_POS),
                        cursor.getString(Service.WEBSITE_POS),cursor.getString(Service.PURPOSE_POS));
                service.setID(cursor.getInt(Service.ID_POS));
                cursor.close();
                return service;
            }
            else {
                return null;
            }
        }
    }
    public void deleteTable() {getDatabase().delete(Service.TABLE_NAME,null,null);}

    public Service updateRow(Service oldService, Service newService){
        ContentValues values = new ContentValues();
        values.put(Service.COLUMN_HOURS,newService.getHours());
        values.put(Service.COLUMN_BUILDING_ID, newService.getBuildingID());
        values.put(Service.COLUMN_WEBSITE, newService.getWebsite());
        values.put(Service.COLUMN_PURPOSE, newService.getPurpose());
        String selection = Service._ID + "LIKE ?";
        String selectionArgs[] = {String.valueOf(oldService.getID())};
        getDatabase().update(Service.TABLE_NAME,values,selection,selectionArgs);
        newService.setID(oldService.getID());
        return newService;

    }

} //end class
