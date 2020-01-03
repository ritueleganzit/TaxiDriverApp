package com.eleganzit.taxidriverapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.eleganzit.taxidriverapp.adapter.HistoryAdapter;
import com.eleganzit.taxidriverapp.adapter.NewRequestAdapter;
import com.google.android.material.snackbar.Snackbar;

public class NewRequestActivity extends Fragment {
    RecyclerView fav;
    FrameLayout containerLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_new_request, container, false);

        fav=root.findViewById(R.id.fav);
        containerLayout=root.findViewById(R.id.containerLayout);
        fav.setAdapter(new NewRequestAdapter(fav,getActivity()));
        Snackbar snackbar = Snackbar.make(containerLayout, "", Snackbar.LENGTH_LONG);
// Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
// Hide the text


// Inflate our custom view
        View snackView = getLayoutInflater().inflate(R.layout.request_info, null);
// Configure the view


//If the view is not covering the whole snackbar layout, add this line
        layout.setPadding(0,0,0,0);

// Add the view to the Snackbar's layout
        layout.addView(snackView, 0);
// Show the Snackbar
        View view = snackbar.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snackbar.show();

        return root;
    }

}
