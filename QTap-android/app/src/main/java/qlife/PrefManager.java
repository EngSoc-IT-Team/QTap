package qlife;

/**
 * Created by Alex on 11/3/2016.
 */


import android.content.SharedPreferences;
import android.content.Context;

/**
 *Controls the preferences of user.
 * Currently only used for showing introduction on first launch
 */
//TODO remove all uses of this, add info to database instead
public class PrefManager {

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    // shared mPref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this.mContext = context;
        mPref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mPref.edit(); //TODO why is edit called here?
    }

    /**
     * Controls if user sees the introduction.
     * isFirstTime used as flag in preferences for showing the introduction.
     */
    public void setFirstTimeLaunch(boolean isFirstTime) {
        mEditor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        mEditor.commit();
    }

    /**
     * Accessor to see if the introduction needs to be shown
     */
    public boolean isFirstTimeLaunch() {
        return mPref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
