package com.geekdroid.sarkarinaukri.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.geekdroid.sarkarinaukri.activities.DisplayDetailsActivity;
import com.geekdroid.sarkarinaukri.R;
import com.geekdroid.sarkarinaukri.activities.MoreDetailsActivity;
import com.geekdroid.sarkarinaukri.model.DataModel;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.MyViewHolder> {

    private final int VIEW_TYPE_CELL = 1;
    private final int VIEW_TYPE_FOOTER = 2;
    private final Context context;
    private final ArrayList<DataModel> list;
    private String title, url;


    public JobAdapter(Context context, ArrayList<DataModel> list, String title, String url) {
        this.context = context;
        this.list = list;
        this.title=title;
        this.url=url;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;

        if (viewType==R.layout.job_list) {

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.job_list, viewGroup, false);

        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_btn, viewGroup, false);
        }
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

//        myViewHolder.linearLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.item_animation_from_bottom));

        if(i==list.size()){
            myViewHolder.viewMore.setOnClickListener(v -> {
                Intent intent = new Intent(context, MoreDetailsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("title", title);
                extras.putString("more", url);
                intent.putExtras(extras);
                context.startActivity(intent);
            });
        }else {
            // List<String> cl = new ArrayList<String>();
            DataModel obj = list.get(i);
            if (obj != null) {
                // myViewHolder.linkName.setText(obj.getLinkList());
                myViewHolder.jobName.setText(obj.getJobList());
//                myViewHolder.counting.setText(String.valueOf(i + 1));
                final String links = obj.getLinkList();
                final String postName = obj.getJobList();
                myViewHolder.mks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DisplayDetailsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("links", links);
                        intent.putExtra("title", postName);
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size()) ? R.layout.footer_btn : R.layout.job_list;
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        LinearLayout jobList;
        CardView mks;
        TextView linkName, jobName, counting;
        Button viewMore;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            jobList =  itemView.findViewById(R.id.jobList);
            jobName =  itemView.findViewById(R.id.jobName);
         //   counting = (TextView) itemView.findViewById(R.id.counting);
            linearLayout =itemView.findViewById(R.id.linearLayout);
            viewMore =  itemView.findViewById(R.id.viewMoreButton);
            mks =  itemView.findViewById(R.id.mks);

        }
    }
}
