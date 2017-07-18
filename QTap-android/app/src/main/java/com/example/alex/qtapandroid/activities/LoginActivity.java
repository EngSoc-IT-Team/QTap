package com.example.alex.qtapandroid.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.alex.qtapandroid.ICS.ParseICS;
import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.GetCloudDb;
import com.example.alex.qtapandroid.common.database.DatabaseAccessor;
import com.example.alex.qtapandroid.common.database.users.User;
import com.example.alex.qtapandroid.common.database.users.UserManager;

import com.example.alex.qtapandroid.ICS.DownloadICSFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        mAuthTask = new UserLoginTask(mUserEmail, this);
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
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String netid;
        private UserManager mUserManager;

        UserLoginTask(String netid, Context context) {
            this.mUserManager = new UserManager(context);
            //TODO get netid to actually be the netid
            //netid right now is a url with netid inside, parsing for the netid
            String[] strings = netid.split("/");
            this.netid = strings[strings.length - 1].split("@")[0];
            Log.d("NETID", "" + this.netid);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy, hh:mm aa", Locale.CANADA);
            String formattedDate = df.format(Calendar.getInstance().getTime());
            User newUser = new User(netid, "", "", formattedDate, mIcsUrl);
            mUserManager.insertRow(newUser);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(true);

            // Get the default SharedPreferences context
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if (success) {
                (new GetCloudDb(LoginActivity.this)).execute();
                SharedPreferences.Editor editor = preferences.edit();
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
                final DownloadICSFile downloadICS = new DownloadICSFile(LoginActivity.this);
                final ParseICS parser = new ParseICS(LoginActivity.this);
                String url = preferences.getString("mIcsUrl", "noURL");
                if (!url.equals("noURL")) {
                    //ADD BACK downloadICS.execute(preferences.getString("mIcsUrl", "noURL"));
                    url = "http://enterpriseair.tk/temp/testCal.ics"; // Add this line for debugging purposes - TODO: Remove this bypass
                    downloadICS.execute(url);
                    parser.parseICSData();
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
                            startActivity(new Intent(LoginActivity.this, MainTabActivity.class));
                        } else {    // if the user IS logged in, but the database is more than a week old
                            final DownloadICSFile downloadICS = new DownloadICSFile(LoginActivity.this);
                            final ParseICS parser = new ParseICS(LoginActivity.this);
                            String url = preferences.getString("mIcsUrl", "noURL");
                            if (!url.equals("noURL")) {
                                url = "http://enterpriseair.tk/temp/testCal.ics"; // Add this line for debugging purposes - TODO: Remove this bypass
                                downloadICS.execute(url); // Not sure why this was removed, it's kinda important :/
                                parser.parseICSData();
                            }
                        }
                    } catch (ParseException e) {
                        Log.e("LoginActivity", "ERROR: " + e);
                    }
                }
                startActivity(new Intent(LoginActivity.this, MainTabActivity.class));
            }
        }
    }
}