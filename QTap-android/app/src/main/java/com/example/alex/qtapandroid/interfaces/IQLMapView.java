package com.example.alex.qtapandroid.interfaces;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Carson on 29/07/2017.
 * Interface for fragment or activity that uses a MapView.
 */

public interface IQLMapView {

    /**
     * Method that will initialize and set the MapView/GoogleMap.
     * Called from onCreateView()/onMapReady().
     */
    void setMapView();

    /**
     * Method that will request location permissions in order to allow
     * the user to go to their current location in a Google map.
     */
    void requestLocationPermissions();

    /**
     * Method that will handle logic after the user has responded to a
     * permissions request.
     *
     * @param googleMap GoogleMap that uses the location permissions.
     */
    void onRequestLocationPermissionsResult(GoogleMap googleMap);
}
