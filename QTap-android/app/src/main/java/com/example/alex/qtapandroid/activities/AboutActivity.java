package com.example.alex.qtapandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alex.qtapandroid.R;

/**
 * Created by Carson on 06/06/2017.
 * In options menu, contains information about app
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setBackButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent settings = new Intent(AboutActivity.this, SettingsActivity.class);
                startActivity(settings);
                break;
            case R.id.review:
                startActivity(new Intent(AboutActivity.this, ReviewActivity.class));
                break;
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    private void setBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
