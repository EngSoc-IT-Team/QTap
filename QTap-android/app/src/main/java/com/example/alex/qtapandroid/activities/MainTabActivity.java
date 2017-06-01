package com.example.alex.qtapandroid.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.DatabaseAccessor;
import com.example.alex.qtapandroid.common.database.users.User;
import com.example.alex.qtapandroid.common.database.users.UserManager;
import com.example.alex.qtapandroid.ui.fragments.AboutFragment;
import com.example.alex.qtapandroid.ui.fragments.AgendaFragment;
import com.example.alex.qtapandroid.ui.fragments.CalendarFragment;
import com.example.alex.qtapandroid.ui.fragments.DayFragment;
import com.example.alex.qtapandroid.ui.fragments.EngSocFragment;
import com.example.alex.qtapandroid.ui.fragments.InformationFragment;
import com.example.alex.qtapandroid.ui.fragments.ItsFragment;
import com.example.alex.qtapandroid.ui.fragments.StudentToolsFragment;

/**
 * activity holding most of the app.
 * contains the drawer that navigates user to fragments with map, schedule, info etc.
 */
public class MainTabActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    public static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displayView(R.id.nav_day); //start at calendar view
        User u=(new UserManager(this)).getRow(1); //only ever one person in database
        String userEmail=u.getNetid().split("ps://mytimetable.queensu.ca/timetable/MC/")[1]; //split into ICS URL stuff and email
        String username=userEmail.split("@")[0]; //username for now is netid, which is found from email

        View header = navigationView.getHeaderView(0);// get the existing headerView
        TextView name = (TextView) header.findViewById(R.id.navHeaderAccountName);
        TextView email = (TextView) header.findViewById(R.id.navHeaderAccountEmail);
        name.setText(username);
        email.setText(userEmail);
    }

    /**
     * does not call super onBackPressed.
     * back closes drawer and sends user to calendar fragment (schedule).
     * If already on calendar, exits app.
     */
    @Override
    public void onBackPressed() {
//        int count = getFragmentManager().getBackStackEntryCount();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() <= 1) { //last item in backstack, so close app
            moveTaskToBack(true);
        } else {
            //set title to be for proper fragment
            String fragTag=fm.getBackStackEntryAt(fm.getBackStackEntryCount()-2).getName(); //at count -2 is the fragment after going back
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(fragTag);
            }
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(MainTabActivity.this, SettingsActivity.class);
            startActivity(settings);
        }

        return false;
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayView(item.getItemId());
        return true;
    }

    /**
     * logic to decide what fragment to show, based on what drawer item user clicked.
     * will attach new fragment.
     * contains logic to know if on the home fragment or not, for back pressed logic.
     * changes title of screen as well.
     *
     * @param viewId the ID of the drawer item user clicked.
     */
    private void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_schedule:
                fragment = new CalendarFragment();
                title = getString(R.string.calendar_fragment);
                flag = false;       // set agendaFragment flag to false as you have returned to the homepage
                break;
            case R.id.nav_agenda:
                fragment = new AgendaFragment();
                title = getString(R.string.agenda_fragment);
                break;
            case R.id.nav_map:
                startActivity(new Intent(MainTabActivity.this, MapsActivity.class));
                break;
            case R.id.nav_information:
                fragment = new InformationFragment();
                title = getString(R.string.information_fragment);
                break;
            case R.id.nav_day:
                fragment = new DayFragment();
                title = getString(R.string.day_fragment);
                break;
            case R.id.nav_tools:
                fragment = new StudentToolsFragment();
                title = getString(R.string.student_tools_fragment);
                break;
            case R.id.nav_its:
                fragment = new ItsFragment();
                title = getString(R.string.its_fragment);
                break;
            case R.id.nav_engsoc:
                fragment = new EngSocFragment();
                title = getString(R.string.engsoc_fragment);
                break;
            case R.id.nav_about:
                fragment = new AboutFragment();
                title = getString(R.string.about_fragment);
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(title); //title is the tag
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        mDrawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseAccessor.getDatabase().close(); //ensures only one database connection is openat atime
    }
}
