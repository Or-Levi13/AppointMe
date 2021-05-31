package com.example.appointme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appointme.model.Model;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Model.instance.setActivity(this);
    }

   /* @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        if(Model.instance.isUserLoggedIn()){
            // Navigate to Main Page
        }
    }*/
}