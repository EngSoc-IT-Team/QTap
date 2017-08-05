package com.example.alex.qtapandroid.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.activities.MapsActivity;
import com.example.alex.qtapandroid.common.Util;
import com.example.alex.qtapandroid.common.database.local.buildings.Building;
import com.example.alex.qtapandroid.interfaces.IQLActionbarFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carson on 25/07/2017.
 * Fragment that shows details of one building from the list view
 */
public class OneBuildingFragment extends Fragment implements IQLActionbarFragment {

    private Bundle mArgs;
    private NavigationView mNavView;
    private View mView;
    private GoogleMap mGoogleMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_one_building, container, false);
        mArgs = getArguments();

        setMapView(savedInstanceState);
        populateData();
        return mView;
    }

    private void populateData() {
        ArrayList<String> foodNames = mArgs.getStringArrayList(BuildingsFragment.TAG_FOOD_NAMES);
        String foods = "";
        if (foodNames != null && !foodNames.isEmpty()) {
            for (String oneFood : foodNames) {
                foods += oneFood + "\n";
            }
            foods = foods.trim();//remove last \n
        } else {
            mView.findViewById(R.id.food_title).setVisibility(View.GONE);
            mView.findViewById(R.id.food).setVisibility(View.GONE);

        }
        ((TextView) mView.findViewById(R.id.food)).setText(foods);

        ((TextView) mView.findViewById(R.id.purpose)).setText(mArgs.getString(Building.COLUMN_PURPOSE));
        ((TextView) mView.findViewById(R.id.atm)).setText(mArgs.getBoolean(Building.COLUMN_ATM) ? "Yes" : "No");
        ((TextView) mView.findViewById(R.id.book_rooms)).setText(mArgs.getBoolean(Building.COLUMN_BOOK_ROOMS) ? "Yes" : "No");
    }

    private void setMapView(Bundle savedInstanceState) {
        MapView mapView = (MapView) mView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mGoogleMap = mMap;
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions();
                } else {
                    mGoogleMap.setMyLocationEnabled(true);
                }
                LatLng buildingInfo = new LatLng(mArgs.getDouble(Building.COLUMN_LAT), mArgs.getDouble(Building.COLUMN_LON));
                mGoogleMap.addMarker(new MarkerOptions().position(buildingInfo).title(mArgs.getString(Building.COLUMN_NAME))).showInfoWindow();

                //For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(buildingInfo).zoom(15).build();
                mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setActionbarTitle((AppCompatActivity) getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        mNavView.getMenu().findItem(R.id.nav_buildings).setChecked(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNavView = (NavigationView) (getActivity()).findViewById(R.id.drawer_layout).findViewById(R.id.nav_view);
        mNavView.getMenu().findItem(R.id.nav_buildings).setChecked(true);
    }

    private void requestPermissions() {
        int coarsePermission = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int finePermission = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (finePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (coarsePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(),
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MapsActivity.REQUEST_LOCATION_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MapsActivity.REQUEST_LOCATION_PERMISSIONS &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void setActionbarTitle(AppCompatActivity activity) {
        Util.setActionbarTitle(R.string.fragment_buildings, activity);
    }
}
