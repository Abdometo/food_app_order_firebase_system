package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.myapplication.Domain.Foods;
import com.example.myapplication.Helper.ChangeNumberItemsListener;
import com.example.myapplication.Helper.ManagmentCart;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    // Class Attributes
    ArrayList<Foods> list;
    private ManagmentCart managmentCart;
    ChangeNumberItemsListener changeNumberItemsListener;


    // Constructor
    public CartAdapter(ArrayList<Foods> list, ManagmentCart managmentCart, ChangeNumberItemsListener changeNumberItemsListener) {
        this.list = list;
        this.managmentCart = managmentCart;
        this.changeNumberItemsListener = changeNumberItemsListener;
    }
    //......................................................................//

    // ViewHolder Class
    public class CartViewHolder extends RecyclerView.ViewHolder{
        TextView titleText,feeEachItem,plusItem,minusItem;
        ImageView pic;
        TextView num;
        ConstraintLayout trashButton;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleCartText);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            plusItem = itemView.findViewById(R.id.plusCartButton);
            minusItem = itemView.findViewById(R.id.minusCartButton);
            pic = itemView.findViewById(R.id.cartPic);
            num = itemView.findViewById(R.id.numberItemText);
            trashButton = itemView.findViewById(R.id.trashButton);


        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titleText.setText(list.get(position).getTitle());
        holder.feeEachItem.setText("$"+(list.get(position).getNumberInCart()*list.get(position).getPrice()));
        holder.num.setText(list.get(position).getNumberInCart()+"");

        Glide
                .with(holder.itemView.getContext())
                .load(list.get(position).getImagePath())
                .transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.pic);


        // Plus Button
        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.plusNumberItem(list, position, () -> {
                    notifyDataSetChanged();
                    changeNumberItemsListener.change();
                });
            }
        });


        // Minus Button
        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.minusNumberItem(list, position, () -> {
                    notifyDataSetChanged();
                    changeNumberItemsListener.change();
                });
            }
        });

        // Trash Button
        holder.trashButton.setOnClickListener(v -> managmentCart.removeItem(list, position, new ChangeNumberItemsListener() {
            @Override
            public void change() {
                notifyDataSetChanged();
                changeNumberItemsListener.change();
            }
        }));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
