package com.eleganzit.taxidriverapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.eleganzit.taxidriverapp.R;


public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder>
{
    Context context;
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;

    RecyclerView recyclerView;

    public VehicleAdapter(RecyclerView fav, Context context) {
        this.context = context;
        recyclerView=fav;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vehicle,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if(position == 0 )
        {
            lastChecked = holder.checkBox;
            lastCheckedPos = 0;
        }
        holder.checkBox.setTag(new Integer(position));

        holder.checkBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckBox cb = (CheckBox)v;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if(cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else
                    lastChecked = null;

            }
        });
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
LinearLayout accept;
CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox);
            accept=itemView.findViewById(R.id.accept);



        }
    }
}
