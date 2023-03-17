package com.example.ebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.se.omapi.Session;

public class SplashscreenActivity extends AppCompatActivity {

    Handler handler;
    Session session;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        Activity activity = SplashscreenActivity.this;
        handler = new Handler();
        GotoActivity();

    }

    private void GotoActivity() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                    Intent intent=new Intent(SplashscreenActivity.this,LoginActivity.class);
                    startActivity(intent);


            }
        },2000);
    }
}