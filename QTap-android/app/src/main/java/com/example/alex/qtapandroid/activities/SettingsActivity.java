package com.example.alex.qtapandroid.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.qtapandroid.common.PrefManager;
import com.example.alex.qtapandroid.common.database.DatabaseAccessor;
import com.example.alex.qtapandroid.common.database.users.User;
import com.example.alex.qtapandroid.common.database.SqlStringStatements;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.users.UserManager;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Activity for the settings. Can see NetID, time since calendar was last synced and can logout here
 */
public class SettingsActivity extends AppCompatActivity {

    private void clearData(View v) {
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        WebView web = new WebView(getApplicationContext());
        web.clearFormData();
        web.clearHistory();
        web.clearCache(true);

        v.getContext().deleteDatabase(SqlStringStatements.PHONE_DATABASE_NAME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearData(v);
                PrefManager mPrefManager = new PrefManager(getApplicationContext());
                mPrefManager.setFirstTimeLaunch(true);
                Toast.makeText(SettingsActivity.this, getString(R.string.logged_out), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingsActivity.this, StartupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setBackButton();

        setTextViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(SettingsActivity.this, AboutActivity.class));
                break;
            case R.id.review:
                startActivity(new Intent(SettingsActivity.this, ReviewActivity.class));
                break;
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseAccessor.getDatabase().close(); //ensure only one database connection is ever open
    }

    private void setTextViews() {
        UserManager mUserManager = new UserManager(this.getApplicationContext());
        ArrayList<User> users = mUserManager.getTable();
        User user = users.get(0); //only ever one user in database
        TextView netID = (TextView) findViewById(R.id.netID);
        TextView date = (TextView) findViewById(R.id.login_date);
        date.setText(user.getDateInit());
        netID.setText(user.getNetid());
    }

    private void setBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}