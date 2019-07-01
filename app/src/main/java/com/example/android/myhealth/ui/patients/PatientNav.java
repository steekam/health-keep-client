package com.example.android.myhealth.ui.patients;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.myhealth.R;
import com.example.android.myhealth.ui.onboarding.OnboardingActivity;
import com.example.android.myhealth.ui.patients.mFragments.Account;
import com.example.android.myhealth.ui.patients.mFragments.Appointments;
import com.example.android.myhealth.ui.patients.mFragments.Chat;
import com.example.android.myhealth.ui.patients.mFragments.Dashboard;
import com.example.android.myhealth.ui.patients.mFragments.Prescriptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;

public class PatientNav extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private final CompositeDisposable disposables = new CompositeDisposable();
	private PatientNavViewModel patientNavViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_nav);
		init(savedInstanceState);
	}

	void init(Bundle savedInstanceState) {
		//view model
		patientNavViewModel = ViewModelProviders.of(this, new PatientNavViewModelFactory(getApplication(), this)).get(PatientNavViewModel.class);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar = findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
				.setAction("Action", null).show());

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		toolbar = findViewById(R.id.toolbar);
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_frame, new Dashboard())
					.commit();
			navigationView.setCheckedItem(R.id.dashboard);
		}
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
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
		int id = item.getItemId();
		FragmentManager fragmentManager = getSupportFragmentManager();

		if (id == R.id.appointments) {
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_frame
							, new Appointments())
					.commit();
		} else if (id == R.id.chat) {
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_frame
							, new Chat())
					.commit();
		} else if (id == R.id.prescriptions) {
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_frame
							, new Prescriptions())
					.commit();
		} else if (id == R.id.dashboard) {
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_frame
							, new Dashboard())
					.commit();
		} else if (id == R.id.account) {
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_frame
							, new Account())
					.commit();
		} else if (id == R.id.logout) {
			disposables.add(
					patientNavViewModel.logout().andThen(
							(CompletableSource) co -> {
								Intent intent = new Intent(PatientNav.this, OnboardingActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}
					).subscribe()
			);
		}

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	@Override
	protected void onDestroy() {
		disposables.clear();
		super.onDestroy();
	}
}
