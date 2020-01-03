package com.eleganzit.taxidriverapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.eleganzit.taxidriverapp.NewRequestActivity;
import com.eleganzit.taxidriverapp.R;

public class CardsDataAdapter extends ArrayAdapter<String> {
    Context context;
    Activity activity;

    public CardsDataAdapter(Context context) {
        super(context, R.layout.card_content);
        this.context=context;
        activity = (Activity) context;

    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){
        TextView v = (TextView)(contentView.findViewById(R.id.rider_name));
        TextView tap= (TextView)(contentView.findViewById(R.id.tap));
        v.setText(getItem(position));

        tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewRequestActivity newRequestActivity=new NewRequestActivity();
                FragmentTransaction transaction=((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

                transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                transaction.addToBackStack(null);
                transaction.replace(R.id.nav_host_fragment, newRequestActivity);
                transaction.commit();
            }
        });
        return contentView;
    }
}
