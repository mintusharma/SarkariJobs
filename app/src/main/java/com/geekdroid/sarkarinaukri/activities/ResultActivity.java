package com.geekdroid.sarkarinaukri.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.geekdroid.sarkarinaukri.R;
import com.geekdroid.sarkarinaukri.Utilis.Constants;
import com.geekdroid.sarkarinaukri.Utilis.Utilities;
import com.geekdroid.sarkarinaukri.adapter.MoreListDetailsAdapter;
import com.geekdroid.sarkarinaukri.model.DataModel;
import com.geekdroid.sarkarinaukri.model.MySingleton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private AdView mAdView;
    private RequestQueue requestQueue;
    private ArrayList<DataModel> list;
    private RecyclerView recyclerView;
    private MoreListDetailsAdapter myAdapter;
    ProgressBar progressBar;
    private static final String TAG = "Results";
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initView();
    }

    private void initView() {
        setTitle("Check Result");
        Toolbar toolbar = findViewById(R.id.toolbar_results);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.resultRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        requestQueue = MySingleton.getInstance(this).getRequestQueue();
        progressBar = (ProgressBar) findViewById(R.id.progressBarResult);
        loadAds();
        if (interstitialAd.isAdLoaded()) {
            interstitialAd.show();
            new LoadData().execute();
        } else {
            new LoadData().execute();
            Log.d("TAG", "The interstitial wasn't loaded yet. ");
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

    public class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            requestQueue = MySingleton.getInstance(getApplicationContext()).getRequestQueue();
            try {
                final String url = getResources().getString(R.string.links).concat("/result.php");
                StringRequest mRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Document doc = Jsoup.parse(response);
                        Elements table = doc.select("#post");
                        for (int i = 0; i < 40; i++) {
                            Elements pName = table.select("ul").get(i).select("a");
                            String abs = pName.attr("abs:href");
                            list.add(new DataModel(pName.text(), abs, ""));
                            // String res=pass.substring(pass.indexOf("Last Date"));
                        }
                        myAdapter = new MoreListDetailsAdapter(getApplicationContext(), list, "Check result", "",R.drawable.ic_baseline_analytics_24);
                        recyclerView.setAdapter(myAdapter);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });

                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(mRequest);
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

        MenuItem menuItem = menu.findItem(R.id.search_View);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        //SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener((androidx.appcompat.widget.SearchView.OnQueryTextListener) this);


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
/*        if (id == R.id.action_about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        }*/
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String userInput = s.toLowerCase();
        List<DataModel> dataList = new ArrayList<>();
        for (DataModel data : list) {
            if (data.getJobList().toLowerCase().contains(userInput)){
                dataList.add(data);
            }
        }
        myAdapter.updateList(dataList);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();finish();
    }

}
