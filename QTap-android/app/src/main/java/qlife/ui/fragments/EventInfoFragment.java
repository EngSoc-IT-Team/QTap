package qlife.ui.fragments;

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

import qlife.R;
import qlife.activities.MapsActivity;
import qlife.Util;
import qlife.ICS.icsToBuilding;
import qlife.interfaces.IQLActionbarFragment;
import qlife.interfaces.IQLDrawerItem;
import qlife.interfaces.IQLListItemDetailsFragment;
import qlife.interfaces.IQLMapView;

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

public class EventInfoFragment extends Fragment implements IQLActionbarFragment, IQLDrawerItem, IQLMapView, IQLListItemDetailsFragment {

    private String mEventTitle, mEventLoc, mDate;
    private Bundle mSavedInstanceState;
    private View myView;
    private MapView mMapView;
    private GoogleMap mGoogleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_event_info, container, false);
        setActionbarTitle();
        addDataToViews();
        mSavedInstanceState = savedInstanceState;
        setMapView();
        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        myView.setFocusableInTouchMode(true);
        myView.requestFocus();
        selectDrawer();
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
        deselectDrawer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MapsActivity.REQUEST_LOCATION_PERMISSIONS) {
            onRequestLocationPermissionsResult();
        }
    }

    @Override
    public void setActionbarTitle() {
        Util.setActionbarTitle(getString(R.string.fragment_event_info), (AppCompatActivity) getActivity());
    }

    @Override
    public void deselectDrawer() {
        Util.setDrawerItemSelected(getActivity(), R.id.nav_day, false);
    }

    @Override
    public void selectDrawer() {
        Util.setDrawerItemSelected(getActivity(), R.id.nav_day, true);
    }

    @Override
    public void setMapView() {
        mMapView = (MapView) myView.findViewById(R.id.event_map);
        mMapView.onCreate(mSavedInstanceState);
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
                    requestLocationPermissions();
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
    }

    @Override
    public void requestLocationPermissions() {
        Util.requestLocationPermissions(getActivity());
    }

    @Override
    public void onRequestLocationPermissionsResult() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void addDataToViews() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mEventTitle = bundle.getString(DayFragment.TAG_TITLE);
            mEventLoc = bundle.getString(DayFragment.TAG_LOC);
            mDate = bundle.getString(DayFragment.TAG_DATE);
        }

        TextView eventDate = (TextView) myView.findViewById(R.id.EventDate);
        eventDate.setText(mDate);
        TextView eventLoc = (TextView) myView.findViewById(R.id.EventLoc);
        eventLoc.setText(mEventLoc);
        TextView eventName = (TextView) myView.findViewById(R.id.EventName);
        eventName.setText(mEventTitle);
    }
}
