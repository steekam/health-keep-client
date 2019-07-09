package com.example.android.myhealth.ui.patients;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseActivity;
import com.example.android.myhealth.ui.onboarding.OnboardingActivity;
import com.example.android.myhealth.ui.patients.account.Account;
import com.example.android.myhealth.ui.patients.appointment.AddAppointment;
import com.example.android.myhealth.ui.patients.appointment.AppointmentsMaster;
import com.example.android.myhealth.ui.patients.mFragments.Chat;
import com.example.android.myhealth.ui.patients.mFragments.Dashboard;
import com.example.android.myhealth.ui.patients.mFragments.Prescriptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;

public class PatientNav extends BaseActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private static final String BACK_STACK_ROOT_TAG = "root_fragment";
	//views
	@BindView(R.id.fab)
	public FloatingActionButton fab;
	public ActionBarDrawerToggle toggle;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.drawer_layout)
	DrawerLayout drawerLayout;
	@BindView(R.id.nav_view)
	NavigationView navigationView;
	private PatientSharedViewModel patientSharedViewModel;
	private FragmentManager fragmentManager = getSupportFragmentManager();
	private NavHeaderViewHolder navHeaderViewHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_nav);
		init(savedInstanceState);
		loadUserDetailsOnNav();
	}

	void init(Bundle savedInstanceState) {
		// Butter knife
		ButterKnife.bind(this);

		//Get navigation header
		View navHeader = navigationView.getHeaderView(0);
		navHeaderViewHolder = new NavHeaderViewHolder(navHeader);

		//view model
		patientSharedViewModel = ViewModelProviders.of(this, new PatientSharedViewModelFactory(getApplication(), this)).get(PatientSharedViewModel.class);

		setSupportActionBar(toolbar);

		navigationView.setNavigationItemSelectedListener(this);

		toggle = new ActionBarDrawerToggle(
				this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		toggle.setToolbarNavigationClickListener(v -> onBackPressed());

		drawerLayout.addDrawerListener(toggle);


		toggle.syncState();

		if (savedInstanceState == null) {
			switchFragment(R.id.dashboard);
		}
		disposables.addAll(subscriptions());
	}

	@Override
	public void onBackPressed() {
		if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
			drawerLayout.closeDrawer(GravityCompat.START);
		} else {
			Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
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
		switchFragment(item.getItemId());
		drawerLayout.closeDrawer(GravityCompat.START);
		return true;
	}

	public void switchFragment(int menuItemId) {
		switch (menuItemId) {
			case R.id.dashboard:
				fragmentManager.beginTransaction()
						.replace(R.id.fragment_frame
								, new Dashboard(), "dashboard")
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
						.commit();
				break;
			case R.id.appointments:
				fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				fragmentManager.beginTransaction()
						.replace(R.id.fragment_frame
								, new AppointmentsMaster(), getString(R.string.appointments))
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
						.addToBackStack(BACK_STACK_ROOT_TAG)
						.commit();
				break;
			case R.id.appointments_add:
				fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				fragmentManager.beginTransaction()
						.replace(R.id.fragment_frame
								, new AddAppointment(), getString(R.string.add_appointments))
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
						.addToBackStack(BACK_STACK_ROOT_TAG)
						.commit();
				break;
			case R.id.prescriptions:
				fragmentManager.beginTransaction()
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
						.replace(R.id.fragment_frame
								, new Prescriptions(), "prescriptions")
						.commit();
				navigationView.setCheckedItem(R.id.prescriptions);
				patientSharedViewModel.fabVisibilityRelay.accept(View.VISIBLE);
				patientSharedViewModel.actionBarTitleRelay.accept(getString(R.string.prescriptions));
				break;
			case R.id.chat:
				fragmentManager.beginTransaction()
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
						.replace(R.id.fragment_frame
								, new Chat(), "chat")
						.commit();
				navigationView.setCheckedItem(R.id.chat);
				patientSharedViewModel.fabVisibilityRelay.accept(View.VISIBLE);
				patientSharedViewModel.actionBarTitleRelay.accept(getString(R.string.chat));
				break;
			case R.id.account:
				fragmentManager.beginTransaction()
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
						.replace(R.id.fragment_frame
								, new Account(), "account")
						.commit();
				navigationView.setCheckedItem(R.id.account);
				patientSharedViewModel.fabVisibilityRelay.accept(View.INVISIBLE);
				patientSharedViewModel.actionBarTitleRelay.accept(getString(R.string.account));
				break;
			case R.id.logout:
				disposables.add(
						patientSharedViewModel.logout().andThen(
								(CompletableSource) co -> {
									Intent intent = new Intent(PatientNav.this, OnboardingActivity.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
									startActivity(intent);
								}
						).subscribe()
				);
				patientSharedViewModel.actionBarTitleRelay.accept(getString(R.string.logout));
				break;
		}
	}

	private void loadUserDetailsOnNav() {
		disposables.add(
				patientSharedViewModel.getLoggedInUser()
						.subscribe(client -> {
							navHeaderViewHolder.tv_clientUsername.setText(client.username());
							navHeaderViewHolder.tv_clientEmail.setText(client.email());
						})
		);
	}

	private Disposable[] subscriptions() {
		return new Disposable[]{
				patientSharedViewModel.fabVisibility().subscribe(
						integer -> fab.setVisibility(integer)
				),
				patientSharedViewModel.actionBarTitle().subscribe(
						title -> Objects.requireNonNull(getSupportActionBar()).setTitle(title)
				),
				patientSharedViewModel.setCheckedItem().subscribe(id -> navigationView.setCheckedItem(id)),
				patientSharedViewModel.homeAsUp().subscribe(value -> {
					toggle.setDrawerIndicatorEnabled(!value);
					toggle.syncState();
					Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(value);
				})
		};
	}

	static class NavHeaderViewHolder {
		@BindView(R.id.patientNavUsername)
		TextView tv_clientUsername;
		@BindView(R.id.patientNavEmail)
		TextView tv_clientEmail;

		NavHeaderViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
