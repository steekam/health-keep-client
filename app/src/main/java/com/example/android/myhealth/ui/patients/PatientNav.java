package com.example.android.myhealth.ui.patients;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseActivity;
import com.example.android.myhealth.ui.onboarding.OnboardingActivity;
import com.example.android.myhealth.ui.patients.mFragments.Account;
import com.example.android.myhealth.ui.patients.mFragments.Appointments;
import com.example.android.myhealth.ui.patients.mFragments.Chat;
import com.example.android.myhealth.ui.patients.mFragments.Dashboard;
import com.example.android.myhealth.ui.patients.mFragments.Prescriptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.CompletableSource;

public class PatientNav extends BaseActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	//views
	@BindView(R.id.fab)
	FloatingActionButton fab;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.drawer_layout)
	DrawerLayout drawerLayout;
	@BindView(R.id.nav_view)
	NavigationView navigationView;
	private PatientNavViewModel patientNavViewModel;
	private FragmentManager fragmentManager = getSupportFragmentManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_nav);
		init(savedInstanceState);
	}

	void init(Bundle savedInstanceState) {
		// Butter knife
		ButterKnife.bind(this);

		//view model
		patientNavViewModel = ViewModelProviders.of(this, new PatientNavViewModelFactory(getApplication(), this)).get(PatientNavViewModel.class);

		setSupportActionBar(toolbar);

		//TODO: Check on this
		fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show());

		navigationView.setNavigationItemSelectedListener(this);

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();

		if (savedInstanceState == null) {
			navigationView.setCheckedItem(R.id.dashboard);
			switchFragment(R.id.dashboard);
		}
	}

	@Override
	public void onBackPressed() {
		if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
			drawerLayout.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_nav, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

        /*noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        */
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		item.setChecked(true);
		switchFragment(item.getItemId());
		drawerLayout.closeDrawer(GravityCompat.START);
		return true;
	}

	private void switchFragment(int menuItemId) {
		String actionBarTitle = "";
		switch (menuItemId) {
			case R.id.appointments:
				fragmentManager.beginTransaction()
						.replace(R.id.fragment_frame
								, new Appointments(R.layout.patient_appointments))
						.commit();
				fab.setVisibility(View.VISIBLE);
				actionBarTitle = getString(R.string.appointments);
				break;
			case R.id.chat:
				fragmentManager.beginTransaction()
						.replace(R.id.fragment_frame
								, new Chat(R.layout.patient_chat))
						.commit();
				fab.setVisibility(View.VISIBLE);
				actionBarTitle = getString(R.string.chat);
				break;
			case R.id.prescriptions:
				fragmentManager.beginTransaction()
						.replace(R.id.fragment_frame
								, new Prescriptions(R.layout.patient_prescriptions))
						.commit();
				actionBarTitle = getString(R.string.prescriptions);
				fab.setVisibility(View.VISIBLE);
				break;
			case R.id.dashboard:
				fragmentManager.beginTransaction()
						.replace(R.id.fragment_frame
								, new Dashboard(R.layout.patient_dashboard))
						.commit();
				fab.setVisibility(View.INVISIBLE);
				actionBarTitle = getString(R.string.dashboard);
				break;
			case R.id.account:
				fragmentManager.beginTransaction()
						.replace(R.id.fragment_frame
								, new Account(R.layout.patient_account))
						.commit();
				fab.setVisibility(View.INVISIBLE);
				actionBarTitle = getString(R.string.account);
				break;
			case R.id.logout:
				disposables.add(
						patientNavViewModel.logout().andThen(
								(CompletableSource) co -> {
									Intent intent = new Intent(PatientNav.this, OnboardingActivity.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
									startActivity(intent);
								}
						).subscribe()
				);
				actionBarTitle = getString(R.string.logout);
				break;
		}
		// set new title
		Objects.requireNonNull(getSupportActionBar()).setTitle(actionBarTitle);
	}

}
