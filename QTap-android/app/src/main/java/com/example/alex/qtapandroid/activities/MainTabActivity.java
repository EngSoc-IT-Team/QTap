package com.example.alex.qtapandroid.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.alex.qtapandroid.ui.fragments.MonthFragment;
import com.example.alex.qtapandroid.ui.fragments.DayFragment;
import com.example.alex.qtapandroid.ui.fragments.StudentToolsFragment;


/**
 * activity holding most of the app.
 * contains the drawer that navigates user to fragments with map, schedule, info etc.
 */
public class MainTabActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private NavigationView mNavView;
    private FragmentManager mFragManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavView = (NavigationView) mDrawer.findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        mFragManager=getSupportFragmentManager();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displayView(R.id.nav_day); //start at calendar view
        User u = (new UserManager(this)).getRow(1); //only ever one person in database
        View header = navigationView.getHeaderView(0);// get the existing headerView
        TextView name = (TextView) header.findViewById(R.id.navHeaderAccountName);
        name.setText(u.getNetid());
    }

    /**
     * does not call super onBackPressed.
     * back closes drawer and sends user to calendar fragment (schedule).
     * If already on calendar, exits app.
     */
    @Override
    public void onBackPressed() {
        mDrawer.closeDrawer(GravityCompat.START);
        if (mFragManager.getBackStackEntryCount() <= 1) { //last item in backstack, so close app
            moveTaskToBack(true);
        } else {
            //set title to be for proper fragment
            String fragTag = mFragManager.getBackStackEntryAt(mFragManager.getBackStackEntryCount() - 2).getName(); //at count -2 is the fragment after going back
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(fragTag);
            }
            clearSelectedDrawerItem();
            int itemID = getSelectedDrawerItem(fragTag);
            mNavView.getMenu().findItem(itemID).setChecked(true);
            super.onBackPressed();
        }
    }

    /**
     * finds the drawer menu ID that corresponds to a fragment
     *
     * @param fragTag Tag given to a fragment
     * @return ID of the item on the drawer corresponding to the fragment, defaults to nav_day
     */
    private int getSelectedDrawerItem(String fragTag) {
        if (fragTag.equals(mNavView.getMenu().findItem(R.id.nav_month).getTitle().toString())) {
            return R.id.nav_month;
        } else if (fragTag.equals(mNavView.getMenu().findItem(R.id.nav_map).getTitle().toString())) {
            return R.id.nav_map;
        } else if (fragTag.equals(mNavView.getMenu().findItem(R.id.nav_tools).getTitle().toString())) {
            return R.id.nav_tools;
        }
        return R.id.nav_day;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent settings = new Intent(MainTabActivity.this, SettingsActivity.class);
                startActivity(settings);
                break;
            case R.id.about:
                Intent about = new Intent(MainTabActivity.this, AboutActivity.class);
                startActivity(about);
                break;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayView(item.getItemId());
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String tag = mFragManager.getBackStackEntryAt(mFragManager.getBackStackEntryCount()-1).getName(); //get current fragment
        getSupportActionBar().setTitle(tag);
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
        boolean activity = false;

        switch (viewId) {
            case R.id.nav_month:
                fragment = new MonthFragment();
                title = getString(R.string.month_fragment);
                break;
            case R.id.nav_map:
                startActivity(new Intent(MainTabActivity.this, MapsActivity.class));
                activity = true;
                break;
            case R.id.nav_day:
                fragment = new DayFragment();
                title = getString(R.string.day_fragment);
                break;
            case R.id.nav_tools:
                fragment = new StudentToolsFragment();
                title = getString(R.string.student_tools_fragment);
                break;
        }

        if (!activity) {
            //set item chosen in drawer, unless going to activity
            clearSelectedDrawerItem();
            mNavView.getMenu().findItem(viewId).setChecked(true);
            //if chose a fragment, add to backstack
            FragmentTransaction ft = mFragManager.beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(title); //title is the tag
            ft.commit();
        }
        getSupportActionBar().setTitle(title);
        mDrawer.closeDrawer(GravityCompat.START);
    }

    /**
     * Un-selects every item in the drawer because user selected another one
     */
    private void clearSelectedDrawerItem() {
        mNavView.getMenu().findItem(R.id.nav_month).setChecked(false);
        mNavView.getMenu().findItem(R.id.nav_map).setChecked(false);
        mNavView.getMenu().findItem(R.id.nav_day).setChecked(false);
        mNavView.getMenu().findItem(R.id.nav_tools).setChecked(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseAccessor.getDatabase().close(); //ensures only one database connection is open at a time
    }
}
