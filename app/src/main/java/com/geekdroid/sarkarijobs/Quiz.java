package com.geekdroid.sarkarijobs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Quiz extends Latest {

    WebView webView;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        String url = "https://www.indiabix.com/aptitude/questions-and-answers/";
        LoadAdd();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8692315641498587/5927607816");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        getSupportActionBar().setTitle("Start Quiz");
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        webView = (WebView) findViewById(R.id.webView);
        String st=getIntent().getStringExtra("links");
        if(st!=null){
            loadUrl(st);
        }else {
            loadUrl(url);
        }

    }
        public void loadUrl(String url) {
            progressBar.setVisibility(View.INVISIBLE);
                webView.setWebViewClient(new WebViewClient());
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setBuiltInZoomControls(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.setInitialScale(1);
                webView.loadUrl(url);

        }

        @Override
        public void onBackPressed() {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                super.onBackPressed();
            }
        }

        private void LoadAdd(){
            mAdView = (AdView) findViewById(R.id.adView);
            MobileAds.initialize(this,
                    "ca-app-pub-8692315641498587/8745342846");

            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

}
