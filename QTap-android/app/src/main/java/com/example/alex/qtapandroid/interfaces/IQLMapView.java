package com.example.alex.qtapandroid.interfaces;

/**
 * Created by Carson on 29/07/2017.
 * Interface for fragment or activity that uses a MapView.
 */

public interface IQLMapView {

    /**
     * Method that will initialize and set the MapView/GoogleMap
     * Should be called from onCreateView() after the view in inflated.
     */
    void setMapView();

    /**
     * Method that will request location permissions in order to allow
     * the user to go to their current location in a Google map.
     *
     * Should call Util.requestLocationPermissions().
     */
    void requestLocationPermissions();

    /**
     * Method that will handle logic after the user has responded to a
     * permissions request.
     *
     * Should be called from onRequestPermissionResult() after checking
     * this invocation is from a location permissions request.
     *
     * Should call Util.onLocationPermissionsGiven().
     */
    void onRequestLocationPermissionsResult();
}
