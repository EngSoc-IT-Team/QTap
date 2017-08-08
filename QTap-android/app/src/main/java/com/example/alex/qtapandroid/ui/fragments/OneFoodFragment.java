package com.example.alex.qtapandroid.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.alex.qtapandroid.common.ConvertHourSpan;
import com.example.alex.qtapandroid.common.Util;
import com.example.alex.qtapandroid.common.database.local.buildings.Building;
import com.example.alex.qtapandroid.common.database.local.food.Food;
import com.example.alex.qtapandroid.interfaces.IQLActionbarFragment;
import com.example.alex.qtapandroid.interfaces.IQLDrawerItem;
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
 * Created by Carson on 23/07/2017.
 * Fragment that holds information for one food establishment.
 */
public class OneFoodFragment extends Fragment implements IQLActionbarFragment, IQLDrawerItem {

    private Bundle mArgs;
    private View mView;
    private GoogleMap mGoogleMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_one_food, container, false);
        mArgs = getArguments();
        setActionbarTitle();
        selectDrawer();

        setMapView(savedInstanceState);
        populateViews();
        return mView;
    }

    private void populateViews() {
        ((TextView) mView.findViewById(R.id.mon_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_MON_START_HOURS), mArgs.getDouble(Food.COLUMN_MON_STOP_HOURS)));
        ((TextView) mView.findViewById(R.id.tue_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_TUE_START_HOURS), mArgs.getDouble(Food.COLUMN_TUE_STOP_HOURS)));
        ((TextView) mView.findViewById(R.id.wed_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_WED_START_HOURS), mArgs.getDouble(Food.COLUMN_WED_STOP_HOURS)));
        ((TextView) mView.findViewById(R.id.thur_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_THUR_START_HOURS), mArgs.getDouble(Food.COLUMN_THUR_STOP_HOURS)));
        ((TextView) mView.findViewById(R.id.fri_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_FRI_START_HOURS), mArgs.getDouble(Food.COLUMN_FRI_STOP_HOURS)));
        ((TextView) mView.findViewById(R.id.sat_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_SAT_START_HOURS), mArgs.getDouble(Food.COLUMN_SAT_STOP_HOURS)));
        ((TextView) mView.findViewById(R.id.sun_hours)).setText(
                ConvertHourSpan.getHours(mArgs.getDouble(Food.COLUMN_SUN_START_HOURS), mArgs.getDouble(Food.COLUMN_SUN_STOP_HOURS)));

        ((TextView) mView.findViewById(R.id.takes_card_short)).setText(mArgs.getBoolean(Food.COLUMN_CARD) ? "Yes" : "No");
        ((TextView) mView.findViewById(R.id.takes_meal_short)).setText(mArgs.getBoolean(Food.COLUMN_MEAL_PLAN) ? "Yes" : "No");

        if (mArgs.getString(Food.COLUMN_INFORMATION) != null && !mArgs.getString(Food.COLUMN_INFORMATION).equals("")) {
            mView.findViewById(R.id.info).setVisibility(View.VISIBLE);
            ((TextView) mView.findViewById(R.id.info)).setText(mArgs.getString(Food.COLUMN_INFORMATION));
        }
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
                String title = mArgs.getString(Food.COLUMN_NAME);
                String buildingName = mArgs.getString(FoodFragment.TAG_BUILDING_NAME);
                mGoogleMap.addMarker(new MarkerOptions().position(buildingInfo).title(title).snippet(buildingName)).showInfoWindow();

                //For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(buildingInfo).zoom(15).build();
                mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        deselectDrawer();
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
    public void setActionbarTitle() {
        Util.setActionbarTitle(mArgs.getString(Food.COLUMN_NAME), (AppCompatActivity) getActivity());
    }

    @Override
    public void deselectDrawer() {
        Util.setDrawerItemSelected(getActivity(), R.id.nav_food, false);
    }

    @Override
    public void selectDrawer() {
        Util.setDrawerItemSelected(getActivity(), R.id.nav_food, true);
    }
}
