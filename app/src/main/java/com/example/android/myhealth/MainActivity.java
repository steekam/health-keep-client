package com.example.android.myhealth;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
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

        mDotLayout = (LinearLayout) findViewById(R.id.dots);
        addDotsIndicator(0);

        pager.addOnPageChangeListener(viewListener);

        join = (Button) findViewById(R.id.join);
        login = (Button) findViewById(R.id.login);
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
        Intent intent = new Intent(MainActivity.this, login.class);
        startActivity(intent);
    }

    public void launchsignup(View view){
        Intent intent = new Intent(MainActivity.this, signup.class);
        startActivity(intent);
    }
}
