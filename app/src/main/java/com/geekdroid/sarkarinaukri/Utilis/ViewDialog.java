package com.geekdroid.sarkarinaukri.Utilis;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.geekdroid.sarkarinaukri.R;


public class ViewDialog {
    private AdView adView;

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.exit_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button rateUs = dialog.findViewById(R.id.rate_us);
        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Utilities().rateOnPlayStore(activity);
                dialog.dismiss();
            }
        });
        Button exitApp = dialog.findViewById(R.id.exitApp);
        exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
                dialog.cancel();
            }
        });
        FrameLayout mDialogOk = dialog.findViewById(R.id.frmClose);
        mDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

        AudienceNetworkAds.initialize(activity);
        adView = new AdView(activity, Constants.BannerID, AdSize.RECTANGLE_HEIGHT_250);
        LinearLayout bannerContainer=dialog.findViewById(R.id.exit_banner_container);
        bannerContainer.addView(adView);
        // Request an ad
        adView.loadAd();
        Utilities.displayBanner(activity,adView);
    }





}
