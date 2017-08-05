package com.example.alex.qtapandroid.interfaces;

import android.view.View;

/**
 * Created by Carson on 28/07/2017.
 * Interface for fragments that show the details
 * of an item after one was clicked in a fragment list.
 */
public interface IQLListItemDetailsFragment {
    /**
     * Method that will put phone database table information into the views of a list fragment.
     * Called from onCreateView().
     *
     * @param view The view that holds the TextViews (or whatever else) that hold the data.
     */
    void addDataToViews(View view);
}
