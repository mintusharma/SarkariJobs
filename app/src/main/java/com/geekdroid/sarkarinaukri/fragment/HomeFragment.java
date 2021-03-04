package com.geekdroid.sarkarinaukri.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
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
import com.geekdroid.sarkarinaukri.R;
import com.geekdroid.sarkarinaukri.Utilis.Constants;
import com.geekdroid.sarkarinaukri.Utilis.Utilities;
import com.geekdroid.sarkarinaukri.activities.AdmitCardActivity;
import com.geekdroid.sarkarinaukri.activities.AnswerKeyActivity;
import com.geekdroid.sarkarinaukri.activities.LatestActivity;
import com.geekdroid.sarkarinaukri.activities.ResultActivity;
import com.geekdroid.sarkarinaukri.activities.SyllabusActivity;
import com.geekdroid.sarkarinaukri.adapter.CategoryListAdapter;
import com.geekdroid.sarkarinaukri.adapter.ViewPagerAdapter;
import com.geekdroid.sarkarinaukri.model.CategoryDataModel;
import com.geekdroid.sarkarinaukri.model.DataModel;
import com.geekdroid.sarkarinaukri.model.MySingleton;
import com.geekdroid.sarkarinaukri.retrofit.RetrofitServiceBase;
import com.geekdroid.sarkarinaukri.retrofit.RetrofitServiceInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment {

    ViewPager2 myViewPager2;
    ViewPagerAdapter adapter;
    private List<DataModel> arrayList = new ArrayList<>();
    private List<CategoryDataModel.CategoryData> categoryDataModelList;
    private Context mContext;
    int currentPage = 0;
    ProgressBar progressBar;
    private RequestQueue requestQueue;
    private RecyclerView jobCategoryRV;
    CategoryListAdapter dataAdapter;
    private Document doc;
    private AdView adView;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = view.getContext();
        Toolbar toolbar=getActivity().findViewById(R.id.toolbarHome);
        toolbar.setTitle("Sarkari Naukri");
        myViewPager2 = view.findViewById(R.id.viewPager);
        progressBar = view.findViewById(R.id.progressBar);
        jobCategoryRV=view.findViewById(R.id.jobCategoryRV);
        CardView answerKey=view.findViewById(R.id.answerKey);
        CardView admitCard=view.findViewById(R.id.admitCard);
        CardView results=view.findViewById(R.id.results);
        CardView latest=view.findViewById(R.id.latest);
        CardView syllabusCard=view.findViewById(R.id.syllabus);
        new LoadTopData().execute();
        setTrendingData();

        AudienceNetworkAds.initialize(mContext);
        adView = new AdView(mContext, Constants.BannerID, AdSize.BANNER_HEIGHT_50);
        LinearLayout bannerContainer=view.findViewById(R.id.banner_container);
        bannerContainer.addView(adView);
        // Request an ad
        adView.loadAd();
        Utilities.displayBanner(mContext,adView);

        latest.setOnClickListener(v -> {goToAnswerKey(LatestActivity.class);});
        admitCard.setOnClickListener(v -> {goToAnswerKey(AdmitCardActivity.class);});
        results.setOnClickListener(v -> {goToAnswerKey(ResultActivity.class);});
        answerKey.setOnClickListener(v -> {goToAnswerKey(AnswerKeyActivity.class);});
        syllabusCard.setOnClickListener(v -> {goToAnswerKey(SyllabusActivity.class);});
    }

    private void setTrendingData() {
        categoryDataModelList=new ArrayList<>();
        jobCategoryRV.setHasFixedSize(true);
        RetrofitServiceInterface retrofitServiceInterface = RetrofitServiceBase.changedBaseUrl(getString(R.string.url)).create(RetrofitServiceInterface.class);
        Call<CategoryDataModel> categoryCall = retrofitServiceInterface.getCategory();
        categoryCall.enqueue(new Callback<CategoryDataModel>() {
            @Override
            public void onResponse(Call<CategoryDataModel> call, retrofit2.Response<CategoryDataModel> response) {
                if(response.isSuccessful()){
                    CategoryDataModel obj =response.body();
                    Log.d("TAG", "onResponse Called: ");
                    Log.d("TAG", "Adapter Called: "+response.body());
                    categoryDataModelList.addAll(obj.getCategoryData());
                    Log.d("TAG", "List Size: "+categoryDataModelList.size());
                    dataAdapter = new CategoryListAdapter(mContext, categoryDataModelList);
                    Log.d("TAG", "Adapter Set : ");
                    jobCategoryRV.setAdapter(dataAdapter);
                    dataAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CategoryDataModel> call, Throwable t) {

            }
        });

        //jobCategory.setAdapter(myAdapter);
    }

    private void setData(List<DataModel> dataModelList) {
        adapter = new ViewPagerAdapter(mContext, arrayList);
        myViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        myViewPager2.setAdapter(adapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == arrayList.size() - 1) {
                    currentPage = 0;
                }
                myViewPager2.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    @SuppressLint("StaticFieldLeak")
    public class LoadTopData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            requestQueue = MySingleton.getInstance(mContext).getRequestQueue();

            final String url = getResources().getString(R.string.links);
            StringRequest mRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    doc = Jsoup.parse(response);
                    Element tab = doc.select("table").get(0);
                    Elements row = tab.select("a");
                    for (int i = 0; i < 8; i++) {
                        Element pName = row.select("a").get(i);
                        final String abs = pName.attr("abs:href");
                        arrayList.add(new DataModel(pName.text(), abs,""));
                    }
                    setData(arrayList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(mContext, "Unable to Connect With Internet", Toast.LENGTH_SHORT).show();
                }
            });

            MySingleton.getInstance(mContext).addToRequestQueue(mRequest);

            return null;
        }
    }

    public void goToAnswerKey(Class mCLass){
        mContext.startActivity(new Intent(mContext, mCLass));
    }
}