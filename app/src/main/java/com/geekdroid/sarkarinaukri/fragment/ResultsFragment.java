package com.geekdroid.sarkarinaukri.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
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

public class ResultsFragment extends Fragment {
    private AdView mAdView;
    private RequestQueue requestQueue;
    private ArrayList<DataModel> list;
    private RecyclerView recyclerView;
    private MoreListDetailsAdapter myAdapter;
    ProgressBar progressBar;
    private InterstitialAd mInterstitialAd;
    private Context mContext;
    private AdView adView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = view.getContext();

        Toolbar toolbar=getActivity().findViewById(R.id.toolbarHome);
        toolbar.setTitle("Check Exams Results");


        AudienceNetworkAds.initialize(mContext);
        adView = new AdView(mContext, Constants.BannerID, AdSize.BANNER_HEIGHT_50);
        LinearLayout bannerContainer=view.findViewById(R.id.banner_container);
        bannerContainer.addView(adView);
        // Request an ad
        adView.loadAd();
        Utilities.displayBanner(mContext,adView);

        recyclerView = view.findViewById(R.id.resultFragmentRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        new LoadData().execute();
        requestQueue = MySingleton.getInstance(mContext).getRequestQueue();
        progressBar = view.findViewById(R.id.progressBarDetail);

    }

    public class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            requestQueue = MySingleton.getInstance(mContext).getRequestQueue();
            try {
                final String url = getResources().getString(R.string.links).concat("/result.php");
                StringRequest mRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Document doc = Jsoup.parse(response);
                        Elements table = doc.select("#post");
                        for (int i = 0; i < 100; i++) {
                            Elements pName = table.select("ul").get(i).select("li").select("a");
                            Elements pDate = table.select("ul").get(i).select("li");
                            //String lastDate = pDate.text();
                            // String res=pass.substring(pass.indexOf("Last Date"));
                            String abs = pName.attr("abs:href");
                            list.add(new DataModel(pName.text(), abs, ""));
                        }
                        myAdapter = new MoreListDetailsAdapter(mContext, list, "", "",R.drawable.ic_baseline_analytics_24);
                        recyclerView.setAdapter(myAdapter);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(mContext, "Something went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });

                MySingleton.getInstance(mContext).addToRequestQueue(mRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}