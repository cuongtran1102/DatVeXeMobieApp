package com.tvc.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.google.android.material.tabs.TabLayout;

import Fragment.FragmentAddTuyenDuong;
import Fragment.FragmentManageTuyenDuong;

public class QLTuyenDuongActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    FragmentAddTuyenDuong fragmentAddTuyenDuong;
    FragmentManageTuyenDuong fragmentManageTuyenDuong;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltuyen_duong);
        tabLayout = findViewById(R.id.tabLayoutQLTuyenDuong);
        frameLayout = findViewById(R.id.frameLayoutQLTuyenDuong);
        btnBack = findViewById(R.id.btnBack_QLTuyenDuong);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fragmentAddTuyenDuong = new FragmentAddTuyenDuong();
        fragmentManageTuyenDuong = new FragmentManageTuyenDuong();
        setFragment(fragmentAddTuyenDuong);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        setFragment(fragmentAddTuyenDuong);
                        break;
                    case 1:
                        setFragment(fragmentManageTuyenDuong);
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
        fragmentTransaction.replace(R.id.frameLayoutQLTuyenDuong, fragment);
        fragmentTransaction.commit();
    }
}