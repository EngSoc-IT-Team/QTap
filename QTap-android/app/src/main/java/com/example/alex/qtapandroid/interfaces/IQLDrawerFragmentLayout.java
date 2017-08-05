package com.example.alex.qtapandroid.interfaces;

import android.app.Activity;

/**
 * Created by Carson on 29/07/2017.
 * Interface for fragments that are a part of a drawer layout of an activity.
 */
public interface IQLDrawerFragmentLayout {

    /**
     * Method that will deselect an item in the drawer layout.
     * Should be called from onPause().
     *
     * @param activity Activity that the fragment, and drawer layout, is attached to.
     * @param itemId   R.id of the item to set deselected in the drawer layout.
     */
    void deselectDrawer(Activity activity, int itemId);

    /**
     * Method that will select an item in the drawer layout.
     * Should be called from onResume().
     *
     * @param activity Activity that the fragment, and drawer layout, is attached to.
     * @param itemId   R.id of the item to set selected in the drawer layout.
     */
    void selectDrawer(Activity activity, int itemId);
}
