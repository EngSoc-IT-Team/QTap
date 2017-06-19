package com.example.alex.qtapandroid.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.example.alex.qtapandroid.R;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void requestPermissions() {
        int accessCoarse = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int accessFine = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (accessFine != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (accessCoarse != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
        }

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        createMarkers();
        //move map to ILC area
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(44.228185, -76.492447)).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void createMarkers() {
        MarkerOptions leonard = new MarkerOptions().position(new LatLng(44.2242736, -76.5007331)).title("Leonard Hall");
        MarkerOptions stirling = new MarkerOptions().position(new LatLng(44.224625, -76.497790)).title("Stirling Hall");
        MarkerOptions ilc = new MarkerOptions().position(new LatLng(44.228185, -76.492447)).title("ILC");
        MarkerOptions jduc = new MarkerOptions().position(new LatLng(44.228137,-76.494670)).title("JDUC");
        MarkerOptions wlh = new MarkerOptions().position(new LatLng(44.227914,-76.491708)).title("Walter Light Hall");
        MarkerOptions etherington = new MarkerOptions().position(new LatLng(44.224,-76.493895)).title("Etherington Hall");
        MarkerOptions waldron = new MarkerOptions().position(new LatLng(44.223145,-76.4914)).title("Waldron Tower");
        MarkerOptions stauffer=new MarkerOptions().position(new LatLng(44.228165,-76.496363)).title("Stauffer Library");
        MarkerOptions goodes = new MarkerOptions().position(new LatLng(44.228070,-76.497646)).title("Goodes Hall");
        MarkerOptions douglas = new MarkerOptions().position(new LatLng(44.227509,-76.495111)).title("Douglas Library");
        MarkerOptions grant = new MarkerOptions().position(new LatLng(44.225707,-76.495151)).title("Grant Hall");
        MarkerOptions clark=new MarkerOptions().position(new LatLng(44.226770,-76.493664)).title("Clark Hall Pub");

        mMap.addMarker(ilc); mMap.addMarker(wlh); mMap.addMarker(jduc);
        mMap.addMarker(leonard); mMap.addMarker(stirling); mMap.addMarker(douglas);
        mMap.addMarker(etherington); mMap.addMarker(waldron); mMap.addMarker(grant);
        mMap.addMarker(stauffer); mMap.addMarker(goodes); mMap.addMarker(clark);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {}

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}
}
