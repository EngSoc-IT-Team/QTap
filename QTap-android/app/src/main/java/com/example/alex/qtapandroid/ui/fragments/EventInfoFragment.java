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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.activities.MapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.alex.qtapandroid.common.maps.icsToBuilding;

import java.util.ArrayList;
import java.util.List;

public class EventInfoFragment extends Fragment {

    private String mEventTitle, mEventLoc, mDate;
    private View myView;
    private NavigationView mNavView;
    private MapView mMapView;
    private GoogleMap mGoogleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mEventTitle = bundle.getString(DayFragment.TAG_TITLE);
            mEventLoc = bundle.getString(DayFragment.TAG_LOC);
            mDate = bundle.getString(DayFragment.TAG_DATE);
        }
        myView = inflater.inflate(R.layout.fragment_event_info, container, false);
        mMapView = (MapView) myView.findViewById(R.id.event_map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mGoogleMap = mMap;
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions();
                } else {
                    mGoogleMap.setMyLocationEnabled(true);
                }
                String loc = mEventLoc.substring(mEventLoc.indexOf("at:") + 4, mEventLoc.length());
                double[] address = icsToBuilding.getAddress(loc);
                LatLng building = new LatLng(address[0], address[1]);
                mGoogleMap.addMarker(new MarkerOptions().position(building).title(loc)).showInfoWindow();

                //For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(building).zoom(16).build();
                mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
        return myView;
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView eventDate = (TextView) view.findViewById(R.id.EventDate);
        eventDate.setText(mDate);
        TextView eventLoc = (TextView) view.findViewById(R.id.EventLoc);
        eventLoc.setText(mEventLoc);
        TextView eventName = (TextView) view.findViewById(R.id.EventName);
        eventName.setText(mEventTitle);


        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(getString(R.string.fragment_event_info));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        myView.setFocusableInTouchMode(true);
        myView.requestFocus();
        mNavView = (NavigationView) (getActivity()).findViewById(R.id.drawer_layout).findViewById(R.id.nav_view);
        mNavView.getMenu().findItem(R.id.nav_day).setChecked(true);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        mNavView.getMenu().findItem(R.id.nav_day).setChecked(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MapsActivity.REQUEST_LOCATION_PERMISSIONS &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        }
    }
}
