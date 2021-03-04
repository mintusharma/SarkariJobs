package com.geekdroid.sarkarinaukri.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekdroid.sarkarinaukri.R;
import com.geekdroid.sarkarinaukri.activities.DisplayDetailsActivity;
import com.geekdroid.sarkarinaukri.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class MoreListDetailsAdapter extends RecyclerView.Adapter<MoreListDetailsAdapter.MyViewHolder> {

    private final Context mContext;
    private ArrayList<DataModel> list;
    private String title, url;
    private int imageId;

    public MoreListDetailsAdapter(Context mContext, ArrayList<DataModel> list, String title, String url, int imageId) {
        this.mContext = mContext;
        this.list = list;
        this.title = title;
        this.url = url;
        this.imageId = imageId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.job_list, viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

//        myViewHolder.linearLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.item_animation_from_bottom));
        // List<String> cl = new ArrayList<String>();
        DataModel obj = list.get(i);
        String lastDate = obj.getLastDate();
        if (!lastDate.isEmpty() && lastDate != null) {
            int endIndex = lastDate.indexOf("Last Date");
            if (endIndex == -1) {
                endIndex = lastDate.length();
            }
            String lDate = lastDate.substring(endIndex);
            myViewHolder.dateImage.setVisibility(View.VISIBLE);
            myViewHolder.lastDate.setVisibility(View.VISIBLE);
            myViewHolder.lastDate.setText(lDate);
        } else {
            myViewHolder.dateImage.setVisibility(View.GONE);
        }
        myViewHolder.imageView.setBackgroundResource(imageId);

        myViewHolder.jobName.setText(obj.getJobList());
        myViewHolder.title.setText(title);
        final String links = obj.getLinkList();
        final String postName = obj.getJobList();
        myViewHolder.jobList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), DisplayDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("links", links);
                intent.putExtra("title", postName);
                mContext.startActivity(intent);
            }
        });
    }
    public void updateList(List<DataModel> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout, dateLayout;
        LinearLayout jobList;
        TextView lastDate, jobName, title;
        Button viewMore;
        ImageView imageView,dateImage;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            jobList = itemView.findViewById(R.id.jobList);
            title = itemView.findViewById(R.id.applyNow);
            jobName = itemView.findViewById(R.id.jobName);
            lastDate = itemView.findViewById(R.id.lastDate);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            viewMore = itemView.findViewById(R.id.viewMoreButton);
            imageView = itemView.findViewById(R.id.imageView);
            dateImage = itemView.findViewById(R.id.dateImage);

        }
    }
}
