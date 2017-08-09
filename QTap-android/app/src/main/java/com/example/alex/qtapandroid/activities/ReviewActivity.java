package com.example.alex.qtapandroid.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.Util;
import com.example.alex.qtapandroid.interfaces.IQLOptionsMenuActivity;

/**
 * Created by Carson on 06/07/2017.
 * Activity that sends user to review app on Play store and suggest improvements with web form
 */
public class ReviewActivity extends AppCompatActivity implements IQLOptionsMenuActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        setBackButton();

        findViewById(R.id.suggestions).setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("SetJavaScriptEnabled")
            public void onClick(View v) {
                WebView browser = (WebView) findViewById(R.id.reviewBrowser);
                browser.setWebViewClient(getWebViewClient());
                browser.getSettings().setSaveFormData(false); //disable autocomplete - more secure, keyboard popup blocks fields
                browser.getSettings().setJavaScriptEnabled(true); // needed to properly display page / scroll to chosen location
                browser.loadUrl(getString(R.string.suggestions_url));
                browser.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private WebViewClient getWebViewClient() {
        return new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals(getString(R.string.suggestions_accepted_url))) {
                    finish();
                }
            }
        };
    }

    @Override
    public void handleOptionsClick(int itemId) {
        switch (itemId) {
            case R.id.settings:
                startActivity(new Intent(ReviewActivity.this, SettingsActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(ReviewActivity.this, AboutActivity.class));
                break;
            case android.R.id.home:
                finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        handleOptionsClick(item.getItemId());
        return true;
    }

    @Override
    public void setBackButton() {
        Util.setBackButton(getSupportActionBar());
    }

    @Override
    public void inflateOptionsMenu(Menu menu) {
        Util.inflateOptionsMenu(R.menu.review_menu, menu, getMenuInflater());
    }
}
