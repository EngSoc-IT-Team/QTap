package com.example.alex.qtapandroid.interfaces;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Carson on 01/08/2017.
 * Interface that enforces a method to change the Action bar title.
 */
public interface IQLActionbarFragment {

    /**
     * Method that will set the action bar title.
     * Called from onViewCreated(), only needed in fragments.
     *
     * @param activity Activity that the fragment is attached to.
     */
    void setActionbarTitle(AppCompatActivity activity);
}
