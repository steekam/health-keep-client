package com.example.android.myhealth;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.health,
            R.drawable.appointment,
            R.drawable.doctor,
    };

    public String[] slide_headings = {
            "Welcome to Health Keep!",
            "Appointment Tracker",
            "Doctor Chat",
    };

    public String[] slide_desc = {
            "Your personal health reminder",
            "We help you remember",
            "Keep in touch with your Doctors",
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView)view.findViewById(R.id.image);
        TextView slideheading = (TextView)view.findViewById(R.id.healthkeep);
        TextView slideDesc = (TextView)view.findViewById(R.id.healthkeep2);

        slideImageView.setImageResource(slide_images[position]);
        slideheading.setText(slide_headings[position]);
        slideDesc.setText(slide_desc[position]);

        container.addView(view);

        return view;
    };

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
