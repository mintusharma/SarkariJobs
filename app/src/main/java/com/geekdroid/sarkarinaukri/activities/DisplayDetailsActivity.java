package com.geekdroid.sarkarinaukri.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.geekdroid.sarkarinaukri.R;
import com.geekdroid.sarkarinaukri.Utilis.Constants;
import com.geekdroid.sarkarinaukri.Utilis.Utilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class DisplayDetailsActivity extends AppCompatActivity  {

    @SuppressLint("SetJavaScriptEnabled")
    WebView webView;
    private AdView adView , adView2;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    private static final String TAG = "AdmitCard";
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_display);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        LoadAdd();
//        mInterstitialAd=new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.int_ad_id));
//        AdRequest adRequest=new  AdRequest.Builder().build();
//        mInterstitialAd.loadAd(adRequest);
        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, Constants.BannerID, AdSize.BANNER_HEIGHT_50);
        LinearLayout bannerContainer=findViewById(R.id.webAdview);
        bannerContainer.addView(adView);
        adView.loadAd();
        Utilities.displayBanner(this,adView);
        // Request an ad
        loadAds();
        ScheduledExecutorService scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (interstitialAd.isAdLoaded()) {
                            interstitialAd.show();
                        } else {
                            Log.d("TAG", "Interstital Not Loaded");
                        }

                    }
                });

            }
        },2,2, TimeUnit.MINUTES);

        setTitle("Job Detail.....");
        webView = (WebView) findViewById(R.id.webView);
        String url = getIntent().getStringExtra("links");
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        relativeLayout=(RelativeLayout)findViewById(R.id.relative_layout);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                progressBar.setVisibility(View.VISIBLE);
                return true;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        if (id == R.id.action_more) {
            try {
                Uri uri = Uri.parse(getResources().getString(R.string.more_apps));
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(likeIng);
            }catch (ActivityNotFoundException e){
                Toast.makeText(getApplicationContext(),"No Such Activity Found",Toast.LENGTH_SHORT).show();
            }
        }
/*        if (id == R.id.action_about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        }*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void loadUrl(String url) {
        relativeLayout.setVisibility(View.VISIBLE);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webView.setScrollbarFadingEnabled(true);
        webView.setInitialScale(100);
        webView.loadUrl(url);

    }



    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();

        } else {
            super.onBackPressed();
            finish();
        }
    }


    private void loadAds() {
        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, Constants.InterstitialID);

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }




}
