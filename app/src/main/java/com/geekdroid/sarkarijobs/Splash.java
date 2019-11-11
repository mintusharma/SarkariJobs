package com.geekdroid.sarkarijobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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

    private void checkInternet(){
        ConnectivityManager conMgr=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
       /* if(conMgr.getActiveNetworkInfo()!=null && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()){
            return true;
        }
        return false;*/
        NetworkInfo activeNetwork=conMgr.getActiveNetworkInfo();

       if(null!=activeNetwork){
           if(activeNetwork.getType()==ConnectivityManager.TYPE_WIFI){
               Toast.makeText(this,"wifi Enabled",Toast.LENGTH_SHORT).show();
               Intent i = new Intent(Splash.this, MainActivity.class);
               startActivity(i);

               // close this activity
               finish();
           }
           if(activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE){
               Toast.makeText(this,"Data Network Enabled",Toast.LENGTH_SHORT).show();
               Intent i = new Intent(Splash.this, MainActivity.class);
               startActivity(i);

               // close this activity
               finish();
           }
       }
       else {
           Button btn=(Button)findViewById(R.id.tryagain);
           btn.setVisibility(View.VISIBLE);
           Toast.makeText(this,"No Internet Connection ",Toast.LENGTH_SHORT).show();
           btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent t= new Intent(Splash.this,Splash.class);
                   startActivity(t);
                   finish();
               }
           });
       }

    }
}
