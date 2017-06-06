package com.example.alex.qtapandroid.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
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
    @Nullable

    String data, data2, date;

    private MapView mapView;
    private GoogleMap googleMap;
    private final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        String actionTitle = "";
        Bitmap imageBitmap = null;
        String transText = "";
        String transitionName = "";

        if (bundle != null) {
            transitionName = bundle.getString("TRANS_NAME");
            actionTitle = bundle.getString("ACTION");
            imageBitmap = bundle.getParcelable("IMAGE");
            transText = bundle.getString("TRANS_TEXT");
            data = bundle.getString("data1", "");
            data2 = bundle.getString("data2", "");
            date = bundle.getString("date", "");
        }


        getActivity().setTitle(actionTitle);
        View view = inflater.inflate(R.layout.fragment_event_info, container, false);

        mapView = (MapView) view.findViewById(R.id.event_map);
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
                googleMap = mMap;

                boolean hasLoc = true;

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    hasLoc = checkAndRequestPermissions();
                    Log.i("MAPS", " permission status: hasLoc " + hasLoc);
                }


                if (hasLoc == true) {
                    Log.i("MAPS", " permission was granted");
                    googleMap.setMyLocationEnabled(true); // false to disable
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                }

                String loc = data2.substring(data2.indexOf("at:") + 4, data2.length());
                double [] address = icsToBuilding.getAddress(loc);

                LatLng building = new LatLng(address[0], address[1]);
                googleMap.addMarker(new MarkerOptions().position(building).title(loc).snippet(data)).showInfoWindow();
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(building).zoom(16).build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            view.findViewById(R.id.card_view).setTransitionName(transitionName);
//            view.findViewById(R.id.card_view_event).setTransitionName(transText);
//        }

//        ((CardView) view.findViewById(R.id.card_view_event)).set(actionTitle);


        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    googleMap.setMyLocationEnabled(true); // false to disable
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }

    }

    private boolean checkAndRequestPermissions() {
        int loc = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);

        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);

            loc = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
            loc2 = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
            Log.i("MAPS", " permission status: hasLoc " + loc + " loc2: " + loc2);

            if (loc2 == PackageManager.PERMISSION_GRANTED || loc == PackageManager.PERMISSION_GRANTED)
                return true;

            return false;
        }
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView eventName = (TextView) view.findViewById(R.id.EventName);
        eventName.setText(data);
        TextView eventLoc = (TextView) view.findViewById(R.id.EventLoc);
        eventLoc.setText(data2);
        TextView eventDate = (TextView) view.findViewById(R.id.EventDate);
        eventDate.setText(data);
//        TextView eventTime = (TextView) view.findViewById(R.id.EventTime);
//        eventTime.setText(data2.substring(0,data2.indexOf(" ")));
    }

    @Override
    public void onResume() {

        super.onResume();
        mapView.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    DayFragment nextFrag=  new DayFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(date,"");
                    nextFrag.setArguments(bundle);

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
                    CardView card = (CardView) v.findViewById(R.id.card_view_event);
                    String cardName = card.getTransitionName();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, nextFrag)
                            .addSharedElement(card, cardName)
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
        mapView.onLowMemory();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
