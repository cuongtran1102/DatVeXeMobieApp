package com.tvc.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import Fragment.*;

import com.google.android.material.tabs.TabLayout;

public class QLChuyenXeActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    FragmentAddTrip fragmentAddTrip;
    FragmentManageTrip fragmentManageTrip;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlchuyen_xe);
        tabLayout = findViewById(R.id.tabLayoutQLChuyenXe);
        frameLayout = findViewById(R.id.frameLayoutQLChuyenXe);

        btnBack = findViewById(R.id.btnBackQLChuyenXe);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fragmentAddTrip = new FragmentAddTrip();
        fragmentManageTrip = new FragmentManageTrip();
        setFragment(fragmentAddTrip);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        setFragment(fragmentAddTrip);
                        break;
                    case 1:
                        setFragment(fragmentManageTrip);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutQLChuyenXe, fragment);
        fragmentTransaction.commit();
    }
}
