package com.example.alex.qtapandroid.interfaces;

/**
 * Created by Carson on 01/08/2017.
 * Interface that defines a fragment that displays a list of items.
 */
public interface IQLListFragment {

    /**
     * Method that handles iterating through a table and setting the ListView data.
     * Should be called from onCreateView() after the view is inflated.
     */
    void inflateListView();
}
