package com.example.android.myhealth.ui.patients.mRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.RecyclerViewHolder> {

    private final Context context;
    private final String[] appoint;

    public DashboardAdapter(Context context, String[] appoint){
        this.context = context;
        this.appoint = appoint;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_dashboard_model,parent,false);
        return new DashboardAdapter.RecyclerViewHolder(v);
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
        final TextView nametext;

        RecyclerViewHolder(View itemview) {
            super(itemview);
            nametext = itemview.findViewById(R.id.nametext);

        }
    }
}
