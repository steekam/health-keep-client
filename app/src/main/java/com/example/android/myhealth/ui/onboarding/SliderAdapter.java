package com.example.android.myhealth.ui.onboarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.android.myhealth.R;

public class SliderAdapter extends PagerAdapter {
	private int[] slide_images;
	private String[] slide_headings;
	private String[] slide_desc;
	private Context context;

	SliderAdapter(Context context) {
		this.context = context;
		slide_images = new int[]{
				R.drawable.health,
				R.drawable.appointment,
				R.drawable.doctor,
		};
		slide_headings = context.getResources().getStringArray(R.array.slide_headings);
		slide_desc = context.getResources().getStringArray(R.array.slide_descriptions);
	}

	@Override
	public int getCount() {
		return slide_headings.length;
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
		return view == o;
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		assert layoutInflater != null;
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
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		container.removeView((View) object);
	}
}
