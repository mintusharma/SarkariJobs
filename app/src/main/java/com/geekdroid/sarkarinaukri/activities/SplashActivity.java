package com.geekdroid.sarkarinaukri.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.geekdroid.sarkarinaukri.R;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView appVersion = findViewById(R.id.txt_nav_app_version);
        try {
            PackageInfo pInfo = null;
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion.setText(String.format("Version %s", pInfo.versionName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //checkInternet();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                checkInternet();
            }
        }, SPLASH_TIME_OUT);

    }

    private void checkInternet() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert conMgr != null;
        NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                startMainActivities("Connectivity Available");
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                startMainActivities("Internet Available");
            }
        }

    }


    private void startMainActivities(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
