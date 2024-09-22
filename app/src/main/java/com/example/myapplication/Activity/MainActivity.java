package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.myapplication.Adapter.CategoryAdapter;
import com.example.myapplication.Adapter.SliderAdapter;
import com.example.myapplication.Domain.Category;
import com.example.myapplication.Domain.SliderItems;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    // Binding
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        
        initCategory();
        initBanners();
        setVariable();
    }

    private void initBanners() {
        DatabaseReference refs = firebaseDatabase.getReference("Banners");
        activityMainBinding.progressBarBanner.setVisibility(View.VISIBLE);
        ArrayList<SliderItems> items = new ArrayList<>();

        refs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(SliderItems.class));
                    }
                    banners(items);
                    activityMainBinding.progressBarBanner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void banners(ArrayList<SliderItems> items){
        activityMainBinding.viewPager2.setAdapter(new SliderAdapter(items,activityMainBinding.viewPager2));
        activityMainBinding.viewPager2.setClipChildren(false);
        activityMainBinding.viewPager2.setClipToPadding(false);
        activityMainBinding.viewPager2.setOffscreenPageLimit(3);
        activityMainBinding.viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        activityMainBinding.viewPager2.setPageTransformer(compositePageTransformer);


    }

    private void setVariable() {
        activityMainBinding.bottomMenu.setItemSelected(R.id.home,true);
        activityMainBinding.bottomMenu.setOnItemSelectedListener(i -> {
            if(i==R.id.cart){
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }

    private void initCategory() {
        DatabaseReference myRef = firebaseDatabase.getReference("Category");
        activityMainBinding.progressBarCategory.setVisibility(View.VISIBLE);
        ArrayList<Category> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot issue :snapshot.getChildren()){
                        list.add(issue.getValue(Category.class));
                    }
                    if(list.size()>0){
                        activityMainBinding.categoryRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
                        activityMainBinding.categoryRecyclerView.setAdapter(new CategoryAdapter(list));
                    }
                    activityMainBinding.progressBarCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}