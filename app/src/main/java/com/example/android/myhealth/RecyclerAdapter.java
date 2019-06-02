package com.example.android.myhealth;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;
    private RecyclerAdapter.ViewHolder viewHolder;
    private int i;

    public RecyclerAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.link_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder viewHolder, int i) {
        this.viewHolder = viewHolder;
        this.i = i;
        ListItem listItem = listItems.get(i);
        viewHolder.header.setText(listItem.getHeader());
        viewHolder.desc.setText(listItem.getDesc());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView header;
        public TextView desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            header = (TextView)itemView.findViewById(R.id.header);
            desc = (TextView)itemView.findViewById(R.id.desc);
        }
    }
}
