package com.eleganzit.taxidriverapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;

public class MyPaymentActivity extends AppCompatActivity   {
    private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;
    private int mMaxScrollSize;
    private boolean mIsImageHidden;
    CardView mFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payment);
        Toolbar maintoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(maintoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


}
