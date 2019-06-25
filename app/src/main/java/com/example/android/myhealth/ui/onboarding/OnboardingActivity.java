package com.example.android.myhealth.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.android.myhealth.R;
import com.example.android.myhealth.ui.auth.LoginActivity;
import com.example.android.myhealth.ui.auth.SignUpActivity;

public class OnboardingActivity extends FragmentActivity {
	ViewPager pager;
	SliderAdapter sliderAdapter;
	private LinearLayout mDotLayout;
	private TextView[] mdots;
	private Button join;
	private Button login;
	private int mCurrentPage;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pager = findViewById(R.id.pager);

		sliderAdapter = new SliderAdapter(this);

		pager.setAdapter(sliderAdapter);

		mDotLayout = findViewById(R.id.dots);
		addDotsIndicator(0);

		pager.addOnPageChangeListener(viewListener);

		join = findViewById(R.id.join);
		login = findViewById(R.id.login);
	}

	public void addDotsIndicator(int position){
		mdots = new TextView[3];
		mDotLayout.removeAllViews();

		for(int i=0; i<mdots.length; i++){
			mdots[i] = new TextView(this);
			mdots[i].setText(Html.fromHtml("&#8226;"));
			mdots[i].setTextSize(35);
			mdots[i].setTextColor(getResources().getColor(R.color.white));

			mDotLayout.addView(mdots[i]);
		}

		if(mdots.length > 0){
			mdots[position].setTextColor(getResources().getColor(R.color.colorAccent));
		}
	}

	ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int i, float v, int i1) {

		}

		@Override
		public void onPageSelected(int i) {
			addDotsIndicator(i);

			mCurrentPage = i;

			if(i==0){
				join.setVisibility(View.INVISIBLE);
				login.setVisibility(View.INVISIBLE);
			}
			else if(i==mdots.length -1){
				join.setVisibility(View.VISIBLE);
				login.setVisibility(View.VISIBLE);
			}
			else{
				join.setVisibility(View.INVISIBLE);
				login.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void onPageScrollStateChanged(int i) {

		}
	};

	public void launchlogin(View view){
		Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}

	public void launchsignup(View view){
		Intent intent = new Intent(OnboardingActivity.this, SignUpActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}
}
