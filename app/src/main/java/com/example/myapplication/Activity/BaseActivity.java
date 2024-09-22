package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.example.myapplication.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set layout if necessary
        // setContentView(R.layout.activity_base);

        //FirebaseApp.initializeApp(this);  // Initialize Firebase

        firebaseDatabase = FirebaseDatabase.getInstance();

        // Set window flags
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    // Override other lifecycle methods as needed
}
