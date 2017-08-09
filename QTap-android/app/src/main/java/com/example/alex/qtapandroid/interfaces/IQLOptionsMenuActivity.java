package com.example.alex.qtapandroid.interfaces;

import android.view.Menu;

/**
 * Created by Carson on 08/08/2017.
 * Interface that defines an Activity accessed from the options menu.
 */
public interface IQLOptionsMenuActivity {
    /**
     * Method that sets the back button in the action bar.
     *
     * Should be called from onCreate() after the view in set.
     *
     * Should call Util.setBackButton().
     */
    void setBackButton();

    /**
     * Method that handles logic for when an item in the options menu is clicked.
     * @param itemId The R.id of the item clicked.
     *
     * Should be called from onOptionsItemClick().
     */
    void handleOptionsClick(int itemId);

    /**
     * Method that handles adding items to the options menu.
     * @param menu Menu to be inflated.
     *
     * Should be called from onCreateOptionsMenu().
     *
     * Should call Util.inflateOptionsMenu().
     */
    void inflateOptionsMenu(Menu menu);
}
