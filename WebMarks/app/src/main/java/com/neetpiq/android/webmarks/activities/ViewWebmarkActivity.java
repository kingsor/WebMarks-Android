package com.neetpiq.android.webmarks.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.neetpiq.android.webmarks.R;
import com.neetpiq.android.webmarks.models.Webmark;
import com.neetpiq.android.webmarks.utils.NetworkUtils;

public class ViewWebmarkActivity extends AppCompatActivity {

    public static final String TAG = "ViewWebmarkActivity";

    public static final String EXTRA_WEBMARK = "com.neetpiq.android.webmarks.EXTRA_WEBMARK";


    private WebView mWebView;
    private LinearLayout mOfflineLayout;
    private Toolbar mToolbar;
    private LinearLayout mProgressContainer;

    private Webmark mWebmark;

    public static Intent getStartIntent(Context context, Webmark item) {
        Intent intent = new Intent(context, ViewWebmarkActivity.class);
        intent.putExtra(EXTRA_WEBMARK, item);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_webmark);

        mWebView = (WebView) findViewById(R.id.web_view);
        mOfflineLayout = (LinearLayout) findViewById(R.id.layout_offline);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgressContainer = (LinearLayout) findViewById(R.id.progress_indicator);

        Bundle bundle = getIntent().getExtras();
        mWebmark = bundle.getParcelable(EXTRA_WEBMARK);
        if (mWebmark == null) throw new IllegalArgumentException("ViewWebmarkActivity requires a Webmark object!");

        setupToolbar();
        setupWebView();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setTitle(mWebmark.getTitle());
        }
    }

    private void setupWebView() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) mProgressContainer.setVisibility(ProgressBar.GONE);
            }
        });
        mWebView.setWebViewClient(new ProgressWebViewClient());
        mWebView.setInitialScale(1);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        if (NetworkUtils.isNetworkAvailable(this)) {
            showHideOfflineLayout(false);
            mWebView.loadUrl(mWebmark.getUrl());
        } else {
            showHideOfflineLayout(true);
        }
    }

    private void showHideOfflineLayout(boolean isOffline) {
        mOfflineLayout.setVisibility(isOffline ? View.VISIBLE : View.GONE);
        mWebView.setVisibility(isOffline ? View.GONE : View.VISIBLE);
        mProgressContainer.setVisibility(isOffline ? View.GONE : View.VISIBLE);
    }

    private class ProgressWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String page) {
            mProgressContainer.setVisibility(ProgressBar.GONE);
        }
    }
}
