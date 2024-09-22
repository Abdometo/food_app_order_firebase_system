package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activity.ListFoodActivity;
import com.example.myapplication.Domain.Category;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final ArrayList<Category> items;
    private Context context;

    // Constructor
    public CategoryAdapter(ArrayList<Category> items) {
        this.items = items;
    }
    //...............................................................//

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView pic;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.catNameText);
            pic = itemView.findViewById(R.id.imgCat);
        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
         holder.title.setText(items.get(position).getName());
         //int drawableResources = context.getResources().getIdentifier(items.get(position).getImagePath(),"drawable",holder.itemView.getContext().getPackageName());

        // Load the image using Glide
        Glide.with(context)
                .load(items.get(position).getImagePath())
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ListFoodActivity.class);
            intent.putExtra("CategoryId",items.get(position).getId());
            intent.putExtra("CategoryName",items.get(position).getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
