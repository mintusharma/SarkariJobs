package com.geekdroid.sarkarinaukri.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.geekdroid.sarkarinaukri.R;
import com.geekdroid.sarkarinaukri.Utilis.Constants;
import com.geekdroid.sarkarinaukri.Utilis.Utilities;
import com.geekdroid.sarkarinaukri.adapter.MoreListDetailsAdapter;
import com.geekdroid.sarkarinaukri.model.DataModel;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class AboutSarkariJob extends Fragment {

    private RequestQueue requestQueue;
    private ArrayList<DataModel> list;
    private RecyclerView recyclerView;
    private MoreListDetailsAdapter myAdapter;
    ProgressBar progressBar;
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
        return inflater.inflate(R.layout.fragment_about_sarkari_job, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext=view.getContext();
        Toolbar toolbar=getActivity().findViewById(R.id.toolbarHome);
        toolbar.setTitle("Check Exams Syllabus");

        AudienceNetworkAds.initialize(mContext);
        adView = new AdView(mContext, Constants.BannerID, AdSize.BANNER_HEIGHT_50);
        LinearLayout bannerContainer=view.findViewById(R.id.banner_container);
        bannerContainer.addView(adView);
        // Request an ad
        adView.loadAd();
        Utilities.displayBanner(mContext,adView);

        TextView appVersionTxt=view.findViewById(R.id.appVersion_txt);
        CardView share=view.findViewById(R.id.share);
        CardView moreApps=view.findViewById(R.id.moreApps);
        CardView rateUs=view.findViewById(R.id.rate_us);
        CardView privacyPolicy=view.findViewById(R.id.policy);

        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            appVersionTxt.setText(String.format("Version %s", pInfo.versionName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        moreApps.setOnClickListener(view1 -> new Utilities().moreAppsUrl(view.getContext()));
        share.setOnClickListener(view1 -> new Utilities().appSharing(view.getContext()));
        rateUs.setOnClickListener(view1 -> new Utilities().rateOnPlayStore(view.getContext()));
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://geekdroid74.blogspot.com/2020/06/sarkari-naukri.html";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }


    private void showToastMessage(String msg) {
        Toast.makeText(mContext, msg, LENGTH_SHORT).show();
    }

}