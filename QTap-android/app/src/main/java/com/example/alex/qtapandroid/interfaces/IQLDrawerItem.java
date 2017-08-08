package com.example.alex.qtapandroid.interfaces;

/**
 * Created by Carson on 29/07/2017.
 * Interface for fragments that are a part of a drawer layout of an activity.
 */
public interface IQLDrawerItem {

    /**
     * Method that will deselect an item in the drawer layout.
     * Should be called from onPause().
     */
    void deselectDrawer();

    /**
     * Method that will select an item in the drawer layout.
     * Should be called from onCreateView() after the view is inflated.
     */
    void selectDrawer();
}
