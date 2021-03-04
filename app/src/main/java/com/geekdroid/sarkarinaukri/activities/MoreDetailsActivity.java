package com.geekdroid.sarkarinaukri.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.geekdroid.sarkarinaukri.R;
import com.geekdroid.sarkarinaukri.Utilis.Utilities;
import com.geekdroid.sarkarinaukri.adapter.MoreListDetailsAdapter;
import com.geekdroid.sarkarinaukri.model.DataModel;
import com.geekdroid.sarkarinaukri.model.MySingleton;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MoreDetailsActivity extends AppCompatActivity {

    private AdView mAdView;
    private RequestQueue requestQueue;
    private ArrayList<DataModel> list;
    private RecyclerView recyclerView;
    private MoreListDetailsAdapter myAdapter;
    ProgressBar progressBar;
    String str = "";
    private Utilities utilities;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);
        initView();

    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar_more_details);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        str = extras.getString("more");
        String title = extras.getString("title");
        setTitle(title);
//        LoadAdd();
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.int_ad_id));
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mInterstitialAd.loadAd(adRequest);

        recyclerView = (RecyclerView) findViewById(R.id.moreDetailsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        new LoadData().execute();
        requestQueue = MySingleton.getInstance(this).getRequestQueue();
        progressBar = (ProgressBar) findViewById(R.id.progressBarDetail);
    }

    public class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            requestQueue = MySingleton.getInstance(MoreDetailsActivity.this).getRequestQueue();
            try {
                final String url = getResources().getString(R.string.links).concat(str);
                StringRequest mRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Document doc = Jsoup.parse(response);
                        Elements table = doc.select("#post");
                        for (int i = 0; i < 100; i++) {
                            Elements pName = table.select("ul").get(i).select("li").select("a");
                            Elements pDate = table.select("ul").get(i).select("li");
                            String lastDate=pDate.text();
                           // String res=pass.substring(pass.indexOf("Last Date"));
                            String abs = pName.attr("abs:href");
                            list.add(new DataModel(pName.text(), abs,lastDate));
                        }
                      //  myAdapter = new MoreListDetailsAdapter(MoreDetailsActivity.this, list,"","");
                        recyclerView.setAdapter(myAdapter);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });

                MySingleton.getInstance(MoreDetailsActivity.this).addToRequestQueue(mRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
                return null;
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        if (id == R.id.action_more) {
            new Utilities().moreAppsUrl(this);
        }
        /*if (id == R.id.action_about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        }*/
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {

//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//
//            mInterstitialAd.setAdListener(new AdListener() {
//                @Override
//                public void onAdClosed() {
//                    super.onAdClosed();
//                    finish();
//                }
//            });
//
//        } else {
//            super.onBackPressed();
//            finish();
//        }


    }
}
