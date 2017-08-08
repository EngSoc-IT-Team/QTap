package com.example.alex.qtapandroid.interfaces;

/**
 * Created by Carson on 01/08/2017.
 * Interface that enforces a Fragment to change the Action bar title.
 */
public interface IQLActionbarFragment {

    /**
     * This method should get the Activity the implementing Fragment is attached to and
     * call Util.setActionbarTitle().
     * Should be called from onCreateView() after the view has been inflated.
     */
    void setActionbarTitle();
}
