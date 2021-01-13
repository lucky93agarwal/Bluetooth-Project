package com.mslji.mybluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.mslji.mybluetooth.Adapter.TabsAdapter;
import com.mslji.mybluetooth.R;

public class TestDetailsActivity extends AppCompatActivity {
    ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.top_one_two,
            R.drawable.top_two_one
    };
    private String[] titles = {
            "Result",
            "Form"
    };
    TabLayout tabLayout;
    public String number,id,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_details);

        viewPager = findViewById(R.id.viewPager);

        SharedPreferences sharedPreferences = getSharedPreferences("datanew",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();


        number = getIntent().getStringExtra("num");
        id = getIntent().getStringExtra("id");
        date = getIntent().getStringExtra("date");
        edit.putString("num",number);
        edit.putString("id",id);
        edit.putString("date",date);
        edit.apply();

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);


        tabLayout.addTab(tabLayout.newTab().setText("Form"));
        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                tab.setText(titles[tab.getPosition()]);

                int tabIconColor = ContextCompat.getColor(TestDetailsActivity.this, R.color.tabSelectedIconColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

                Log.d("WhatisNuameLcky", "postion  = = " + String.valueOf(position));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(TestDetailsActivity.this, R.color.tabUnselectedIconColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                tab.setText("");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setupTabIcons();

        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);

    }
}