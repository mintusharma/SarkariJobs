package com.geekdroid.sarkarinaukri.Utilis;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;


public class Utilities {
    private Context mContext;
    private static final String TAG = "ADS TAG";
    private InterstitialAd interstitialAd;

    public void moreAppsUrl(Context context) {
        Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=geekdroid");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(likeIng);
        Toast.makeText(context.getApplicationContext(), "More Apps", Toast.LENGTH_SHORT).show();
    }

    public void appSharing(Context context) {
        String link = "https://play.google.com/store/apps/details?id="+context.getPackageName();
        Intent likeIng = new Intent(Intent.ACTION_SEND);
        likeIng.putExtra(Intent.EXTRA_TEXT, link);
        likeIng.setType("text/plain");
        context.startActivity(likeIng);
    }

    public void rateOnPlayStore(Context context) {
        try{
            Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id="+ context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        catch (ActivityNotFoundException e){
            Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id="+context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static final void displayBanner(Context context, AdView adView) {
        AdListener adListener = new AdListener() {


            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.d("TAG", "Banner Error: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };

        // Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
    }

    /*private void loadAds(Context context) {
        AudienceNetworkAds.initialize(context);
        interstitialAd = new InterstitialAd(context, Constants.InterstitialID);

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
    }*/

}
