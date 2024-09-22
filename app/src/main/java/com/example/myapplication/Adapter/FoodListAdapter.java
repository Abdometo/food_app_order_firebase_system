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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.myapplication.Activity.DetailsActivity;
import com.example.myapplication.Domain.Foods;
import com.example.myapplication.R;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    // Class Attributes
    ArrayList<Foods> items;
    Context context;
    //................................//

    // Constructor
    public FoodListAdapter(ArrayList<Foods> items) {
        this.items = items;
    }
    //...........................................................//

    public static class FoodViewHolder extends RecyclerView.ViewHolder{
        // Attributes
        TextView titleText,priceText,reteText,timeText;
        ImageView pic;
        //.........................................................//

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            priceText = itemView.findViewById(R.id.priceText);
            reteText = itemView.findViewById(R.id.ratingTxt);
            timeText = itemView.findViewById(R.id.timeText);
            pic = itemView.findViewById(R.id.img);

        }
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new FoodViewHolder(LayoutInflater.from(context).inflate(R.layout.viewholder_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.titleText.setText(items.get(position).getTitle());
        holder.timeText.setText(items.get(position).getTimeValue()+" min");
        holder.priceText.setText("$"+items.get(position).getPrice());
        holder.reteText.setText(""+items.get(position).getStar());

        Glide
                .with(context)
                .load(items.get(position).getImagePath())
                .transform(new CenterCrop(),new RoundedCorners(50))
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("object",items.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
