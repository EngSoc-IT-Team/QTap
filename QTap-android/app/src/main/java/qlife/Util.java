package qlife;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import qlife.activities.MapsActivity;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carson on 01/08/2017.
 * Class for common methods. All are short and static.
 */
public class Util {
    public static void setActionbarTitle(String title, AppCompatActivity activity) {
        ActionBar actionbar = activity.getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(title);
        }
    }

    public static void setDrawerItemSelected(Activity activity, int itemId, boolean isChecked) {
        NavigationView navView = (NavigationView) activity.findViewById(R.id.drawer_layout).findViewById(R.id.nav_view);
        navView.getMenu().findItem(itemId).setChecked(isChecked);
    }

    public static void requestLocationPermissions(Activity activity) {
        int coarsePermission = ContextCompat.checkSelfPermission(activity.getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int finePermission = ContextCompat.checkSelfPermission(activity.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (finePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (coarsePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MapsActivity.REQUEST_LOCATION_PERMISSIONS);
        }
    }

    public static void onLocationPermissionsGiven(Context context, GoogleMap map) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
    }

    public static void setBackButton(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static void inflateOptionsMenu(int menuId, Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(menuId, menu);
    }

    public static String getHours(double startHour, double endHour) {
        //check for closed all day flag
        if (startHour < 0) {
            return "Closed";
        }

        String start = getOneTimeBoundary(startHour);
        String end = getOneTimeBoundary(endHour);
        return start + " to " + end;
    }

    private static String getOneTimeBoundary(double hour) {
        String sHour = "";
        if (hour < 1 || hour >= 13) { //24 hour time
            sHour += String.valueOf((int) hour - 12);
        } else {
            sHour += String.valueOf((int) hour);
        }

        double min = (hour - (int) hour) * 60; //convert 0.5, 0.75 to 30, 45 min
        sHour += ":" + String.valueOf((int) min);
        if (min == 0) {
            sHour += "0";
        }

        if (hour < 12) {
            sHour += " am";
        } else {
            sHour += " pm";
        }
        return sHour;
    }
}
