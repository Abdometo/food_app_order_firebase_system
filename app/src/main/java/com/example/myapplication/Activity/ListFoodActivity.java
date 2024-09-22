package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Adapter.FoodListAdapter;
import com.example.myapplication.Domain.Foods;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityListFoodBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFoodActivity extends BaseActivity {
    // Binding
    ActivityListFoodBinding activityListFoodBinding;
    //.....................................................//
    private int categoryId;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListFoodBinding = ActivityListFoodBinding.inflate(getLayoutInflater());
        setContentView(activityListFoodBinding.getRoot());
        
        getIntentExtra();
        initList();
        
    }

    private void initList() {
        DatabaseReference myRef = firebaseDatabase.getReference("Foods");
        activityListFoodBinding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();
        Query query = myRef.orderByChild("CategoryId").equalTo(categoryId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue: snapshot.getChildren()){
                        list.add(issue.getValue(Foods.class));
                    }
                    if(list.size()>0){
                        activityListFoodBinding.foodListView.setLayoutManager(new LinearLayoutManager(ListFoodActivity.this,LinearLayoutManager.VERTICAL,false));
                        activityListFoodBinding.foodListView.setAdapter(new FoodListAdapter(list));
                    }
                    activityListFoodBinding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getIntentExtra() {
        categoryId = getIntent().getIntExtra("CategoryId",0);
        categoryName = getIntent().getStringExtra("CategoryName");

        activityListFoodBinding.titleText.setText(categoryName);
        activityListFoodBinding.backButton.setOnClickListener(v -> finish());

    }
}