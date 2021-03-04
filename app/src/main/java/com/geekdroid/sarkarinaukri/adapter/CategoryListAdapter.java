package com.geekdroid.sarkarinaukri.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekdroid.sarkarinaukri.R;
import com.geekdroid.sarkarinaukri.activities.DisplayDetailsActivity;
import com.geekdroid.sarkarinaukri.model.CategoryDataModel;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {

    private final Context context;
    private final List<CategoryDataModel.CategoryData> list;


    public CategoryListAdapter(Context context, List<CategoryDataModel.CategoryData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item, viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

//        myViewHolder.linearLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.item_animation_from_bottom));
        // List<String> cl = new ArrayList<String>();
        CategoryDataModel.CategoryData obj = list.get(i);
        myViewHolder.title.setText(obj.getTitle());
        myViewHolder.categoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DisplayDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("links", obj.getUrl());
                intent.putExtra("title", obj.getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout categoryLayout;
        TextView title;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

             title= itemView.findViewById(R.id.category_title);
            categoryLayout = itemView.findViewById(R.id.category_layout);

        }
    }
}
