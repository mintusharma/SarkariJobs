package com.geekdroid.sarkarijobs.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekdroid.sarkarijobs.R;
import com.geekdroid.sarkarijobs.model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoriesFilter extends RecyclerView.Adapter<CategoriesFilter.MyViewHolder2>  {

    private Context context;
    private ArrayList<Data> list;


    public CategoriesFilter(Context context, ArrayList<Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.job_list,viewGroup,false);
        MyViewHolder2 myViewHolder=new MyViewHolder2(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 myViewHolder, int i) {

        List<String> cl=new ArrayList<String>();

        cl.add("#5E97F6");
        cl.add("#9CCC65");
        cl.add("#FF8A65");
        cl.add("#9E9E9E");
        cl.add("#9FA8DA");
        cl.add("#90A4AE");
        cl.add("#AED581");
        cl.add("#F6BF26");
        cl.add("#FFA726");
        cl.add("#4DD0E1");
        cl.add("#BA68C8");
        cl.add("#A1887F");

        Random r = new Random();
        int i1 = r.nextInt(11- 0) + 0;
        GradientDrawable draw = new GradientDrawable();
        draw.setColor(Color.parseColor(cl.get(i1)));

        myViewHolder.mks.setBackground(draw);

        Data obj=list.get(i);
        // myViewHolder.linkName.setText(obj.getLinkList());

        myViewHolder.jobName.setText(obj.getJobList());
        final String links=obj.getLinkList();
        final String postName=obj.getJobList();
        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent=new Intent(context, Display.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("links",links);
                intent.putExtra("post",postName);
                context.startActivity(intent);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder2 extends RecyclerView.ViewHolder {

        LinearLayout linearLayout,mks;
        TextView linkName,jobName;
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);

            //linkName=(TextView)itemView.findViewById(R.id.jobLink);
            jobName=(TextView)itemView.findViewById(R.id.jobName);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);
            mks=(LinearLayout)itemView.findViewById(R.id.mks);

        }
    }
}
