package com.geekdroid.sarkarijobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.geekdroid.sarkarijobs.adapter.MyAdapter;
import com.geekdroid.sarkarijobs.model.Data;
import com.geekdroid.sarkarijobs.model.MySingleton;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AdmitCard extends Latest {


    private AdView mAdView;
    private RequestQueue requestQueue;
    private ArrayList<Data> list;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private InterstitialAd mInterstitialAd;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admit_card);
        getSupportActionBar().setTitle("Admit Cards");
        LoadAdd();

        mInterstitialAd=new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8692315641498587/6700853181");
        AdRequest adRequest=new  AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);
        new loadData().execute();
        requestQueue = MySingleton.getInstance(this).getRequestQueue();
        progressBar = (ProgressBar) findViewById(R.id.progressBarAdmit);
        Button btn=(Button) findViewById(R.id.moreButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdmitCard.this,MoreDetails.class);
                Bundle extras=new Bundle();
                extras.putString("title","Admit Cards");
                extras.putString("more","/admitcard.php");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }

    public class loadData extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            requestQueue = MySingleton.getInstance(AdmitCard.this).getRequestQueue();

            final String url =getResources().getString(R.string.links);
            StringRequest mRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Document doc = Jsoup.parse(response);
                    Elements table = doc.select("table");
                    Elements row = table.select("td:eq(1)").select("#box2:eq(0)").select("ul");
                    System.out.println("Size : "+row.size());
                    for (int i = 0; i <row.size(); i++) {
                        Elements pName = row.select("ul").get(i).select("li").select("a:gt(0)");
                        String abs = pName.attr("abs:href");
                        list.add(new Data(pName.text(),abs));
                    }
                    myAdapter = new MyAdapter(AdmitCard.this, list);
                    recyclerView.setAdapter(myAdapter);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            MySingleton.getInstance(AdmitCard.this).addToRequestQueue(mRequest);

            return null;
        }
    }

    private void LoadAdd(){
        mAdView = (AdView) findViewById(R.id.adView);

        MobileAds.initialize(this,
                "ca-app-pub-8692315641498587/4649404916");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {

        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();

            mInterstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                }
            });

        }else {
            super.onBackPressed();
        }

    }
}
