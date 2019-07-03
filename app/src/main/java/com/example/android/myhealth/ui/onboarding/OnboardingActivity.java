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
import com.example.android.myhealth.ui.doctors.DoctorNav;
import com.example.android.myhealth.ui.patients.PatientNav;
import com.steekam.authentication.Authentication;
import com.steekam.repository.ClientRepository;

import io.reactivex.disposables.CompositeDisposable;

public class OnboardingActivity extends FragmentActivity {
	private final CompositeDisposable disposables = new CompositeDisposable();

	private LinearLayout mDotLayout;
	private TextView[] mdots;
	private Button join;
	private Button login;


	private final ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int i, float v, int i1) {

		}

		@Override
		public void onPageSelected(int i) {
			addDotsIndicator(i);
			int showButtons = (i == mdots.length - 1) ? View.VISIBLE : View.INVISIBLE;
			join.setVisibility(showButtons);
			login.setVisibility(showButtons);
		}

		@Override
		public void onPageScrollStateChanged(int i) {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		loggedInCheck();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewPager pager = findViewById(R.id.pager);

		SliderAdapter sliderAdapter = new SliderAdapter(this);

		pager.setAdapter(sliderAdapter);

		mDotLayout = findViewById(R.id.dots);
		addDotsIndicator(0);

		pager.addOnPageChangeListener(viewListener);

		join = findViewById(R.id.join);
		login = findViewById(R.id.login);
	}

	private void addDotsIndicator(int position) {
		mdots = new TextView[3];
		mDotLayout.removeAllViews();

		for (int i = 0; i < mdots.length; i++) {
			mdots[i] = new TextView(this);
			mdots[i].setText(Html.fromHtml("&#8226;"));
			mdots[i].setTextSize(35);
			mdots[i].setTextColor(getResources().getColor(R.color.white));

			mDotLayout.addView(mdots[i]);
		}

		if (mdots.length > 0) {
			mdots[position].setTextColor(getResources().getColor(R.color.colorAccent));
		}
	}

	public void launchLogin(View view) {
		Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}

	public void launchSignUp(View view) {
		Intent intent = new Intent(OnboardingActivity.this, SignUpActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}

	private void loggedInCheck() {
		ClientRepository clientRepository = new ClientRepository(getApplication(), this);
		disposables.add(
				clientRepository.isClientLoggedIn()
						.subscribe(role -> {
							if (!role.equals("")) {
								Authentication authentication = new Authentication(this);
								authentication.redirectCient(role.equals("patient") ? PatientNav.class : DoctorNav.class);
							}
						})
		);
	}

	@Override
	protected void onDestroy() {
		disposables.clear();
		super.onDestroy();
	}
}
