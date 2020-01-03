package com.eleganzit.taxidriverapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


setContentView(R.layout.activity_splash_screen);


new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            startActivity(new Intent(SplashScreen.this,MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}).start();

    }
}
