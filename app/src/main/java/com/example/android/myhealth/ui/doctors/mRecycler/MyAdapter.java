package com.example.android.myhealth.ui.doctors.mRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerViewHolder> {

    Context context;
    String[] appoint;

    public MyAdapter(Context context, String[] appoint){
        this.context = context;
        this.appoint = appoint;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.doctor_model,parent,false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.nametext.setText(appoint[position]);
    }

    @Override
    public int getItemCount() {
        return appoint.length;
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView nametext;

        public RecyclerViewHolder(View itemview){
            super(itemview);
            nametext = itemview.findViewById(R.id.nametext);

        }
    }
}
