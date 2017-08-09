package qlife.interfaces;

/**
 * Created by Carson on 08/08/2017.
 * Interface that defines an Activity accessed from the options menu.
 */
public interface IQLOptionsMenuActivity extends IQLActivityHasOptionsMenu{
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
}
