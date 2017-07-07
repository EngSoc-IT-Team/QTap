package com.example.alex.qtapandroid.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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

        findViewById(R.id.suggestions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBrowser(getString(R.string.suggestions_url));
            }
        });
    }

    private void startBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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
