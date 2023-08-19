package com.tvc.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import Fragment.FragmentVeHienTai;
import Fragment.FragmentLichSuVe;






public class VeCuaToiActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    FragmentVeHienTai fragmentVeHienTai;
    FragmentLichSuVe fragmentLichSuVe;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ve_cua_toi);
        tabLayout = findViewById(R.id.tabLayoutVe);
        frameLayout = findViewById(R.id.frameLayoutVe);
        btnBack = findViewById(R.id.btnBackVe);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fragmentVeHienTai = new FragmentVeHienTai();
        fragmentLichSuVe = new FragmentLichSuVe();
        setFragment(fragmentVeHienTai);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setFragment(fragmentVeHienTai);
                        break;
                    case 1:
                        setFragment(fragmentLichSuVe);
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
        fragmentTransaction.replace(R.id.frameLayoutVe, fragment);
        fragmentTransaction.commit();
    }
}




