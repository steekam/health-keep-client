package com.example.android.myhealth.ui.doctors;

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
import com.example.android.myhealth.ui.doctors.mFragments.Account;
import com.example.android.myhealth.ui.doctors.mFragments.Chat;
import com.example.android.myhealth.ui.doctors.mFragments.Patients;
import com.example.android.myhealth.ui.onboarding.OnboardingActivity;
import com.google.android.material.navigation.NavigationView;

import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;

public class DoctorNav extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private final CompositeDisposable disposables = new CompositeDisposable();
	private DoctorNavViewModel doctorNavViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_nav);
	    init(savedInstanceState);
    }

	void init(Bundle savedInstanceState) {
	    //view model
	    doctorNavViewModel = ViewModelProviders.of(this, new DoctorNavViewModelFactory(getApplication(), this)).get(DoctorNavViewModel.class);

	    Toolbar toolbar = findViewById(R.id.toolbar);
	    setSupportActionBar(toolbar);

	    DrawerLayout drawer = findViewById(R.id.drawer_layout);
	    NavigationView navigationView = findViewById(R.id.nav_view);
	    navigationView.setNavigationItemSelectedListener(this);

	    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
	    drawer.addDrawerListener(toggle);
	    toggle.syncState();

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, new Patients())
					.commit();
			navigationView.setCheckedItem(R.id.patients);
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
		getMenuInflater().inflate(R.menu.doctor_nav, menu);
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

		int id = item.getItemId();
		FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.patients) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new Patients())
                    .commit();
        } else if (id == R.id.chat) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Chat())
                    .commit();
        } else if (id == R.id.account) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Account())
                    .commit();
        } else if (id == R.id.logout) {
	        disposables.add(
			        doctorNavViewModel.logout().andThen(
					        (CompletableSource) co -> {
						        Intent intent = new Intent(DoctorNav.this, OnboardingActivity.class);
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
