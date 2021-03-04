package com.geekdroid.sarkarinaukri.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.geekdroid.sarkarinaukri.R;
import com.geekdroid.sarkarinaukri.activities.DisplayDetailsActivity;
import com.geekdroid.sarkarinaukri.model.DataModel;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder2>  {

    private final Context context;
    private final List<DataModel> list;


    public ViewPagerAdapter(Context context, List<DataModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.pager_item,viewGroup,false);
        return new MyViewHolder2(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 myViewHolder, int i) {

        DataModel obj = list.get(i);
        // myViewHolder.linkName.setText(obj.getLinkList());

        myViewHolder.jobName.setText(obj.getJobList());
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DisplayDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("links",obj.getLinkList());
                intent.putExtra("post",obj.getJobList());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class MyViewHolder2 extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView linkName,jobName;
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);

            //linkName=(TextView)itemView.findViewById(R.id.jobLink);
            jobName=itemView.findViewById(R.id.jobName);
            cardView=itemView.findViewById(R.id.card_view);
         //   mks=(LinearLayout)itemView.findViewById(R.id.mks);

        }
    }
}
