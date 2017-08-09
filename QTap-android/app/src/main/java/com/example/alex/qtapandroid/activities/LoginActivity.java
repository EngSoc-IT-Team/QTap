package com.example.alex.qtapandroid.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.alex.qtapandroid.ICS.ParseICS;
import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.GetCloudDb;
import com.example.alex.qtapandroid.common.database.local.DatabaseAccessor;
import com.example.alex.qtapandroid.common.database.local.users.User;
import com.example.alex.qtapandroid.common.database.local.users.UserManager;

import com.example.alex.qtapandroid.ICS.DownloadICSFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A login screen that offers login to my.queensu.ca via netid/password SSO.
 */
public class LoginActivity extends AppCompatActivity {

    //Keep track of the login task to ensure we can cancel it if requested
    private UserLoginTask mAuthTask = null;
    private View mProgressView;
    private View mLoginFormView;

    public static String mIcsUrl = "";
    public static String mUserEmail = "";

    /**
     * Parses the html code to look for the ics file. Needed because
     * there are multiple web pages sent through this activity when logging in.
     * @param html Sring representation of the html code of a webpage.
     */
    public void tryProcessHtml(String html) {
        if (html != null && html.contains("Class Schedule")) {
            html = html.replaceAll("\n", "");
            int index = html.indexOf("Class Schedule");
            html = html.substring(index);
            String indexing = "Your URL for the Class Schedule Subscription pilot service is ";
            index = html.indexOf(indexing) + indexing.length();
            String URL = html.substring(index, index + 200);
            mIcsUrl = URL.substring(0, URL.indexOf(".ics") + 4);
            index = URL.indexOf("/FU/") + 4;
            mUserEmail = URL.substring(index, URL.indexOf("-", index + 1));
            mUserEmail += "@queensu.ca";
            attemptLogin();
        }
    }

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        UserManager mUserManager = new UserManager(getBaseContext());
        if (mUserManager.getTable().isEmpty()) {
            //not logged in, show software centre login screen
            final WebView browser = (WebView) findViewById(R.id.webView);
            browser.getSettings().setSaveFormData(false); //disable autocomplete - more secure, keyboard popup blocks fields
            browser.getSettings().setJavaScriptEnabled(true); // needed to properly display page / scroll to chosen location

            browser.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    if (browser.getUrl().contains("login.queensu.ca"))
                        browser.loadUrl("javascript:document.getElementById('queensbody').scrollIntoView();");

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
        } else {
            attemptLogin();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Show a progress spinner, and kick off a background task to perform the user login attempt.
        showProgress(true);
        mAuthTask = new UserLoginTask(mUserEmail);
        mAuthTask.execute((Void) null);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseAccessor.getDatabase().close(); //ensure only one database connection is ever open
    }

    /**
     * Class used to log the user in asynchronously. This login process is not actually asynchronous. This
     * need to be refactored; however, early attempts have caused a strange crash.
     */
    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mNetid;
        private UserManager mUserManager;

        UserLoginTask(String email) {
            //email right now is a url with netid inside, parsing for the netid
            String[] strings = email.split("/");
            this.mNetid = strings[strings.length - 1].split("@")[0];
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(true);

            mUserManager = new UserManager(getApplicationContext());
            if (mUserManager.getTable().isEmpty()) {
                addUserSession();
                (new GetCloudDb(LoginActivity.this)).execute(); //get cloud db into phone db
                getIcsFile();
            }
            startActivity(new Intent(LoginActivity.this, MainTabActivity.class));
        }

        /**
         * Method that adds the user and their information to the User table in the phone, logging them in.
         */
        private void addUserSession() {
            SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy, hh:mm aa", Locale.CANADA);
            String formattedDate = df.format(Calendar.getInstance().getTime());
            User newUser = new User(mNetid, "", "", formattedDate, mIcsUrl);
            mUserManager = new UserManager(LoginActivity.this);
            mUserManager.insertRow(newUser);
        }

        /**
         * Method that starts an asynchronous task that downloads and parses the ICS file.
         */
        private void getIcsFile() {
            if (mIcsUrl != null && mIcsUrl.contains(".ics")) {
                new DownloadICSFile(LoginActivity.this).execute(mIcsUrl);
            }
        }
    }
}