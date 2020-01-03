package com.eleganzit.taxidriverapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.eleganzit.taxidriverapp.R;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder>
{
    Context context;
    RecyclerView recyclerView;

    public HistoryAdapter(RecyclerView fav, Context context) {
        this.context = context;
        recyclerView=fav;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history_,parent,false);
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
        return 7;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
LinearLayout accept;

        public MyViewHolder(View itemView) {
            super(itemView);
            accept=itemView.findViewById(R.id.accept);



        }
    }
}
