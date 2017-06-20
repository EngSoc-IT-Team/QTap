package com.example.alex.qtapandroid.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
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
    private final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    String mEventTitle, data2, mDate;

    private View myView;
    private MapView mMapView;
    private GoogleMap mGoogleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        String actionTitle = "";

        if (bundle != null) {
            actionTitle = bundle.getString("ACTION");
            mEventTitle = bundle.getString(DayFragment.TAG_TITLE);
            data2 = bundle.getString("data2");
            mDate = bundle.getString(DayFragment.TAG_DATE);
        }
        getActivity().setTitle(actionTitle);
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
                }
                mGoogleMap.setMyLocationEnabled(true);
                String loc = data2.substring(data2.indexOf("at:") + 4, data2.length());
                double[] address = icsToBuilding.getAddress(loc);
                LatLng building = new LatLng(address[0], address[1]);
                mGoogleMap.addMarker(new MarkerOptions().position(building).title(loc).snippet(mEventTitle)).showInfoWindow();

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
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView eventName = (TextView) view.findViewById(R.id.EventName);
        if (!mEventTitle.equals("No events today")) {
            //have information to show
            eventName.setText(mEventTitle);
            TextView eventLoc = (TextView) view.findViewById(R.id.EventLoc);
            eventLoc.setText(data2);
            TextView eventDate = (TextView) view.findViewById(R.id.EventDate);
            eventDate.setText(mEventTitle);
        } else {
            mMapView.setVisibility(View.GONE);
            CardView c = (CardView) myView.findViewById(R.id.card_view_event);
            c.setVisibility(View.GONE);
            TextView t = (TextView) myView.findViewById(R.id.no_events_message);
            t.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        myView.setFocusableInTouchMode(true);
        myView.requestFocus();
        myView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    DayFragment nextFrag = new DayFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        setSharedElementReturnTransition(TransitionInflater.from(
                                getActivity()).inflateTransition(R.transition.card_transistion));
                        setExitTransition(TransitionInflater.from(
                                getActivity()).inflateTransition(android.R.transition.explode));

                        nextFrag.setSharedElementEnterTransition(TransitionInflater.from(
                                getActivity()).inflateTransition(R.transition.card_transistion));
                        nextFrag.setEnterTransition(TransitionInflater.from(
                                getActivity()).inflateTransition(android.R.transition.explode));
                    }
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, nextFrag)
                            .commit();
                    return true;
                }
                return false;
            }
        });
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}
