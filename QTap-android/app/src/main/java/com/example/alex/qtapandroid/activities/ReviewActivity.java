package com.example.alex.qtapandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alex.qtapandroid.R;

/**
 * Created by Carson on 06/07/2017.
 * Activity that sends user to review app on Play store and suggest improvements with web form
 */
public class ReviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.review_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(ReviewActivity.this, SettingsActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(ReviewActivity.this, AboutActivity.class));
                break;
        }
        return false;
    }
}
