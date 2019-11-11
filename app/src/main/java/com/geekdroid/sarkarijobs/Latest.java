package com.geekdroid.sarkarijobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class Latest extends AppCompatActivity {

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
        setContentView(R.layout.activity_latest);
        list = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Latest Job");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLatest);
        new loadData().execute();
        LoadAdd();
        mInterstitialAd=new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8692315641498587/6700853181");
        AdRequest adRequest=new  AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);

        Button btn=(Button) findViewById(R.id.moreButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Latest.this,MoreDetails.class);
                Bundle extras=new Bundle();
                extras.putString("title","Latest Jobs");
                extras.putString("more","/latestjob.php");
                intent.putExtras(extras);
                startActivity(intent);

            }
        });

    }



    private class loadData extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            requestQueue = MySingleton.getInstance(Latest.this).getRequestQueue();
            final String url =getResources().getString(R.string.links);
            StringRequest mRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Document doc = Jsoup.parse(response);
                    Elements table = doc.select("table");
                    Elements row = table.select("td:eq(2)").select("#box1").select("ul");
                    System.out.println("Size : "+row.size());
                    for (int i = 0; i <row.size(); i++) {
                        Elements pName = row.select("ul").get(i).select("li").select("a:gt(0)");
                        String abs = pName.attr("abs:href");
                        list.add(new Data(pName.text(),abs));
                    }
                    myAdapter = new MyAdapter(Latest.this, list);
                    recyclerView.setAdapter(myAdapter);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            MySingleton.getInstance(Latest.this).addToRequestQueue(mRequest);

            return null;
        }
    }



    private void LoadAdd(){
        mAdView = (AdView) findViewById(R.id.adView);

        MobileAds.initialize(this,
                "ca-app-pub-8692315641498587/1490148289");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        if (id == R.id.action_more) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=TakshakSH&hl=en");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(likeIng);
            Toast.makeText(this, "More Apps", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.action_about) {
            startActivity(new Intent(getApplicationContext(),About.class));
        }
        if (id == R.id.action_share) {
            String link="https://play.google.com/store/apps/details?id=com.geekdroid.sarkarijobs";
            Intent likeIng = new Intent(Intent.ACTION_SEND);
            likeIng.putExtra(Intent.EXTRA_TEXT,link);
            likeIng.setType("text/plain");
            startActivity(likeIng);
        }
        if (id == R.id.action_refresh) {
            finish();
            startActivity(getIntent());
        }


        return super.onOptionsItemSelected(item);
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
