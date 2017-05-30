package com.example.alex.qtapandroid.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.DatabaseAccessor;
import com.example.alex.qtapandroid.common.database.users.User;
import com.example.alex.qtapandroid.common.database.users.UserManager;

import com.example.alex.qtapandroid.ICS.DownloadICSFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    public static final String TAG = DownloadICSFile.class.getSimpleName();
    private static final int REQUEST_READ_CONTACTS = 0;

    //Keep track of the login task to ensure we can cancel it if requested
    private UserLoginTask mAuthTask = null;

    // UI references.
    private View mProgressView;
    private View mLoginFormView;

    public static String mIcsUrl = "";
    public static String mUserEmail = "";

    private boolean isLoggedIn = false;


    //TODO document and remove literals

    public void tryProcessHtml(String html) {
        if (html == null)
            return;

        if (html.contains("Class Schedule")) {
            html = html.replaceAll("\n", "");
            int index = html.indexOf("Class Schedule");
            html = html.substring(index);
            String indexing = "Your URL for the Class Schedule Subscription pilot service is ";
            index = html.indexOf(indexing) + indexing.length();
            String URL = html.substring(index, index + 200);
            URL.trim();
            URL = URL.substring(0, URL.indexOf(".ics") + 4);
            mIcsUrl = URL;
            Log.d("WEB", "URL: " + URL);

            index = URL.indexOf("/FU/") + 4;
            mUserEmail = URL.substring(index, URL.indexOf("-", index + 1));
            mUserEmail += "@queensu.ca";
            setText(mUserEmail);
            attemptLogin();
        }
    }

    private void setText(String userEmail) {
        TextView dataInfo = (TextView) findViewById(R.id.userEmail);
        dataInfo.setText(userEmail);
        Log.d("WEB", "User Email: " + userEmail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        UserManager mUserManager = new UserManager(this.getApplicationContext());
        ArrayList<User> user = mUserManager.getTable();
        if (!user.isEmpty())    // if the user has logged in already
        {
            if (user.get(0).getIcsURL() != "" && user.get(0).getIcsURL().contains(".ics")) {
                Log.d(TAG, "user is logged in");
                isLoggedIn = true;
                attemptLogin();
            }
        }
        if (!isLoggedIn) {
            final WebView browser = (WebView) findViewById(R.id.webView);
            browser.getSettings().setJavaScriptEnabled(true); //TODO check if needed
            browser.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {

                    browser.evaluateJavascript("(function() { return ('<html>'+document." +
                                    "getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String html) {
                                    tryProcessHtml(html);
                                }
                            });
                }
            });
            browser.loadUrl("http://my.queensu.ca/software-centre");
        }
    }

    /**
     * auto complete email used for login if permission for contacts is gained.
     */
//    private void populateAutoComplete() {
//        if (!mayRequestContacts()) {
//            return;
//        }
//
//        getLoaderManager().initLoader(0, null, this);
//    }


    /**
     * Check for contacts permission. Used to autocomplete email.
     * Ask for permission if do not have it.
     * Returns true if have permission, false if permission is requested.
     */
//    private boolean mayRequestContacts() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        if (checkSelfPermission(REQUEST_READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        }
//        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok, new View.OnClickListener() {
//                        @Override
//                        @TargetApi(Build.VERSION_CODES.M)
//                        public void onClick(View v) {
//                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                        }
//                    });
//        } else {
//            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//        }
//        return false;
//    }

    /**
     * Callback received when a permissions request has been completed.
     * If received permission, continue with auto complete of email.
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_READ_CONTACTS) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
//            }
//        }
//    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        if (mAuthTask != null) {
            return;
        }

        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        showProgress(true);
        mAuthTask = new UserLoginTask(mUserEmail, this);
        mAuthTask.execute((Void) null);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

//        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }


//    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
//        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
////        ArrayAdapter<String> adapter =
////                new ArrayAdapter<>(LoginActivity.this,
////                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
////
////        mEmailView.setAdapter(adapter);
//    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseAccessor.getDatabase().close(); //ensure only one database connection is ever open
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String netid;
        private UserManager mUserManager;

        UserLoginTask(String netid, Context context) {
            this.mUserManager = new UserManager(context);
            this.netid = netid;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            //TODO as of now just adding new users into database
            User userInDB = mUserManager.getRow(netid);
            if (userInDB == null && isLoggedIn == false) {
                User newUser = new User(netid, "", "", "", mIcsUrl); //TODO ask for their name
                mUserManager.insertRow(newUser);
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(true);

            // Get the default SharedPreferences context
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if (success) {

                if (!isLoggedIn) {
                    // Allow for editing the preferences
                    SharedPreferences.Editor editor = preferences.edit();
                    // Create a string called "UserEmail" equal to mEmail


                    editor.putString("UserEmail", netid + "@queensu.ca");
                    editor.apply();

                    // DO LOGIC FOR GENERATING ICS FILE HERE....
                    if (!mIcsUrl.equals("") && mIcsUrl.contains(".ics")) {
                        editor.putString("mIcsUrl", mIcsUrl);   // Create a string called "mIcsUrl" to point to the ICS URL on SOLUS
                        editor.apply();
                    } else {
                        editor.putString("mIcsUrl", "Error, failed to download calendar!");   // Create a string called "mIcsUrl" to point to the ICS URL on SOLUS
                        editor.apply();
                    }

                    {
                        final DownloadICSFile downloadICS = new DownloadICSFile(LoginActivity.this);
                        String url = preferences.getString("mIcsUrl", "noURL");
                        if (!url.equals("noURL")) {
                            Log.d(TAG, "PAY ATTENTION _________________________________________________________________________________________________________________________________________________________________________________!");
                            downloadICS.execute(preferences.getString("mIcsUrl", "noURL"));
                            Log.d(TAG, "done!");

                        }
                    }
                    startActivity(new Intent(LoginActivity.this, MainTabActivity.class));

                } else {
                    UserManager mUserManager = new UserManager(getApplicationContext());
                    ArrayList<User> user = mUserManager.getTable();


                    if (!user.get(0).getDateInit().equals("")) { // if the database is up to date
                        Calendar cal = Calendar.getInstance();
                        Calendar lastWeek = Calendar.getInstance();
                        lastWeek.add(Calendar.DAY_OF_YEAR, -7);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

                        try {
                            cal.setTime(sdf.parse(user.get(0).getDateInit()));// all done
                            if (cal.after(lastWeek)) {
                                Log.d(TAG, "data is less than one week old");
                                startActivity(new Intent(LoginActivity.this, MainTabActivity.class));
                            }
                        } catch (ParseException e) {

                        }

                    }
                    final DownloadICSFile downloadICS = new DownloadICSFile(LoginActivity.this);
                    String url = preferences.getString("mIcsUrl", "noURL");
                    if (!url.equals("noURL")) {
                        Log.d(TAG, "PAY ATTENTION _________________________________________________________________________________________________________________________________________________________________________________!");
                        downloadICS.execute(preferences.getString("mIcsUrl", "noURL"));
                        Log.d(TAG, "done!");

                    }
                    startActivity(new Intent(LoginActivity.this, MainTabActivity.class));
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}