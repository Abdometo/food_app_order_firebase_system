package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Adapter.CartAdapter;
import com.example.myapplication.Helper.ChangeNumberItemsListener;
import com.example.myapplication.Helper.ManagmentCart;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {

    // Binding
    ActivityCartBinding activityCartBinding;

    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCartBinding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(activityCartBinding.getRoot());


        managmentCart = new ManagmentCart(this);
        
        setVariable();
        calculateCart();
        initCartList();
    }

    private void initCartList() {
        if(managmentCart.getListCart().isEmpty()){
            activityCartBinding.emptyText.setVisibility(View.VISIBLE);
            activityCartBinding.scrollViewCart.setVisibility(View.GONE);
        }else{
            activityCartBinding.emptyText.setVisibility(View.GONE);
            activityCartBinding.scrollViewCart.setVisibility(View.VISIBLE);

        }

        activityCartBinding.cartView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        activityCartBinding.cartView.setAdapter(new CartAdapter(managmentCart.getListCart(), managmentCart, () -> calculateCart()));
    }

    private void calculateCart() {

        double percentTax = 0.02;
        double delivery = 10;

        double tax = (double) Math.round(managmentCart.getTotalFee() * percentTax * 100.0) /100;
        double total = (double) Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) /100;
        double itemTotal = (double) Math.round(managmentCart.getTotalFee() * 100) /100;


        activityCartBinding.totalFeeText.setText("$"+itemTotal);
        activityCartBinding.taxText.setText("$"+tax);
        activityCartBinding.deliveryText.setText("$"+delivery);
        activityCartBinding.totalText.setText("$"+total);

    }

    private void setVariable() {

        // Back Button
        activityCartBinding.backButton.setOnClickListener(v -> startActivity(new Intent(CartActivity.this,MainActivity.class)));
    }
}