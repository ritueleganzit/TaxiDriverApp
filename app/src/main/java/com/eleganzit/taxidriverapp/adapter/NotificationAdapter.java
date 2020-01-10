package com.eleganzit.taxidriverapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.eleganzit.taxidriverapp.R;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>
{
    Context context;


    RecyclerView recyclerView;

    public NotificationAdapter(RecyclerView fav, Context context) {
        this.context = context;
        recyclerView=fav;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_noti,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {



holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});

    }

    @Override
    public int getItemCount() {
        return 3;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);



        }
    }
}
