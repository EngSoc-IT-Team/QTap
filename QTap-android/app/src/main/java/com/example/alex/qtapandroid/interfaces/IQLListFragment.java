package com.example.alex.qtapandroid.interfaces;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.alex.qtapandroid.common.database.local.DatabaseRow;

import java.util.ArrayList;

/**
 * Created by Carson on 01/08/2017.
 * Interface that defines a fragment that displays a list of items.
 */
public interface IQLListFragment {

    /**
     * Method that handles logic for when an item within the ListView is created.
     *
     * @param view     View that holds the children Views holding the data.
     * @param activity Activity that the fragment is attached to.
     */
    void onListItemChosen(View view, Activity activity);

    /**
     * Method that handles packing a Bundle with the data for the details of the item clicked on.
     *
     * @param view View that holds the children Views holding the data to pack.
     * @return Bundle holding all the data for the new fragment that will display this data.
     */
    Bundle setDataForOneItem(View view);

    /**
     * Method that handles iterating through a table and setting the ListView data.
     *
     * @param table   Table that holds the information to be used.
     * @param context Context of the ListView used to hold the data,
     */
    void inflateListView(ArrayList<DatabaseRow> table, Context context);
}
