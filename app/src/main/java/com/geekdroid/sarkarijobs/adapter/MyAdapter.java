package com.geekdroid.sarkarijobs.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.geekdroid.sarkarijobs.Display;
import com.geekdroid.sarkarijobs.R;
import com.geekdroid.sarkarijobs.model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Data> list;


    public MyAdapter(Context context, ArrayList<Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.job_list,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        List<String> cl=new ArrayList<String>();
        cl.add("#ef6c00");
        cl.add("#01579b");
        cl.add("#1b5e20");
        cl.add("#ffab00");
        cl.add("#ff5722");

        Random r = new Random();
        int i1 = r.nextInt(5- 0) + 0;
        GradientDrawable draw = new GradientDrawable();
        draw.setColor(Color.parseColor(cl.get(i1)));

        myViewHolder.joblist.setBackground(draw);

        Data obj=list.get(i);
       // myViewHolder.linkName.setText(obj.getLinkList());

        myViewHolder.jobName.setText(obj.getJobList());


         final String links=obj.getLinkList();
        final String postName=obj.getJobList();
        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Display.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("links",links);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        RelativeLayout joblist;
        CardView mks;
        TextView linkName,jobName;
       public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            joblist=(RelativeLayout)itemView.findViewById(R.id.jobList);
            jobName=(TextView) itemView.findViewById(R.id.jobName);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);
            mks=(CardView)itemView.findViewById(R.id.mks);

        }
    }
}
