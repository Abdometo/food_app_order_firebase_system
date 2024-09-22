package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.myapplication.Domain.Foods;
import com.example.myapplication.Helper.ManagmentCart;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityDetailsBinding;
import com.example.myapplication.databinding.ActivityIntroBinding;

public class DetailsActivity extends BaseActivity {

    // Binding
    ActivityDetailsBinding activityDetailsBinding;
    // Class Attributes
    private Foods object;
    private int num = 1;
    private ManagmentCart managmentCart;
    //..............................................//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailsBinding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityDetailsBinding.getRoot());
        
        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        managmentCart = new ManagmentCart(this);

        // Back Button
        activityDetailsBinding.backButton.setOnClickListener(v -> finish());

        // Glide
        Glide
                .with(this)
                .load(object.getImagePath())
                .transform(new CenterCrop(),new RoundedCorners(60))
                .into(activityDetailsBinding.detailsPic);

        activityDetailsBinding.priceText.setText("$"+object.getPrice());
        activityDetailsBinding.titleText.setText(object.getTitle());
        activityDetailsBinding.descriptionText.setText(object.getDescription());
        activityDetailsBinding.ratignText.setText(object.getStar()+" Rating");
        activityDetailsBinding.ratingBar.setRating(object.getStar().floatValue());
        activityDetailsBinding.totalText.setText(num*object.getPrice()+"$");


        // Plus Button
        activityDetailsBinding.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=num+1;
                activityDetailsBinding.numText.setText(num +"");
                activityDetailsBinding.totalText.setText("$"+(num*object.getPrice()));
            }
        });

        // minus Button
        activityDetailsBinding.minusButton.setOnClickListener(v -> {
            if(num<1){
                num=num-1;
                activityDetailsBinding.numText.setText(num +"");
                activityDetailsBinding.totalText.setText("$"+(num*object.getPrice()));
            }
        });


        // Add Cart Button
        activityDetailsBinding.addcartButton.setOnClickListener(v -> {
            object.setNumberInCart(num);
            managmentCart.insertFood(object);
        });
    }

    private void getIntentExtra() {

        object = (Foods) getIntent().getSerializableExtra("object");
    }
}