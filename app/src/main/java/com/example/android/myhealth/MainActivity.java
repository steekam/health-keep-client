package com.example.android.myhealth;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends FragmentActivity {
    ViewPager pager;
    SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = findViewById(R.id.pager);

        sliderAdapter = new SliderAdapter(this);

        pager.setAdapter(sliderAdapter);
    }

    public void launchlogin(View view){
        Intent intent = new Intent(MainActivity.this, login.class);
        startActivity(intent);
    }

    public void launchsignup(View view){
        Intent intent = new Intent(MainActivity.this, signup.class);
        startActivity(intent);
    }
}
