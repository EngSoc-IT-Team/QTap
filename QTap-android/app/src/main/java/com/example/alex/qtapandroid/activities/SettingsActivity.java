package com.example.alex.qtapandroid.activities;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.alex.qtapandroid.common.database.DatabaseAccessor;
import com.example.alex.qtapandroid.common.database.users.User;
import com.example.alex.qtapandroid.common.database.SqlStringStatements;

import com.example.alex.qtapandroid.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatActivity {

    private void ClearData() {
//        android.webkit.CookieManager cookieManager = CookieManager.getInstance();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();

        WebView web = new WebView(getApplicationContext());
        web.clearFormData();
        web.clearHistory();
        web.clearCache(true);

        // Todo: launch the settings activity with a bundle or flag to clear webView form data so that NetIDs do not show up in the username box
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
//                // a callback which is executed when the cookies have been removed
//                @Override
//                public void onReceiveValue(Boolean aBoolean) {
//                    Log.d("TEST", "Cookie removed: " + aBoolean);
//                }
//            });
//        } else cookieManager.removeAllCookie();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);
        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = v.getContext();
                context.deleteDatabase(SqlStringStatements.PHONE_DATABASE_NAME);

                ClearData();
                new WebView(getApplicationContext()).clearCache(true);

                Intent intent = new Intent(SettingsActivity.this , LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //String userEmail = preferences.getString("UserEmail", "defaultStringIfNothingFound");
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        String userName = preferences.getString("UserName", "USERNAME");
        TextView netID = (TextView) findViewById(R.id.netID);
        TextView date = (TextView) findViewById(R.id.login_date);
        date.setText(currentDateTimeString);
        netID.setText(userName);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseAccessor.getDatabase().close(); //ensure only one database connection is ever open
    }

}