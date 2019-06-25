package com.example.android.myhealth.ui.onboarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.android.myhealth.R;

public class SliderAdapter extends PagerAdapter {
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
	Context context;
	LayoutInflater layoutInflater;

	public SliderAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return slide_headings.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object o) {
		return view == o;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

		ImageView slideImageView = view.findViewById(R.id.image);
		TextView slideheading = view.findViewById(R.id.healthkeep);
		TextView slideDesc = view.findViewById(R.id.healthkeep2);

		slideImageView.setImageResource(slide_images[position]);
		slideheading.setText(slide_headings[position]);
		slideDesc.setText(slide_desc[position]);

		container.addView(view);

		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((RelativeLayout) object);
	}
}
