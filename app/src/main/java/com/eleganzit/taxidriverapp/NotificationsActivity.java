package com.eleganzit.taxidriverapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eleganzit.taxidriverapp.adapter.NotificationAdapter;
import com.eleganzit.taxidriverapp.adapter.VehicleAdapter;

public class NotificationsActivity extends AppCompatActivity {
    RecyclerView fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fav=findViewById(R.id.fav);

        fav.setAdapter(new NotificationAdapter(fav,NotificationsActivity.this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
