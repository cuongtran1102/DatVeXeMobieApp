package com.tvc.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import Fragment.*;
import Service.ChuyenXeService;


import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private static final int FRAGMENT_CHUYENXE = 0, FRAGMENT_QLTAIKHOAN = 1,
            FRAGMENT_THONGKE = 2, FRAGMENT_MYPROFILE = 3;
    private int currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new FragmentChuyenXe());
        navigationView.getMenu().findItem(R.id.nav_quan_ly_chuyen_xe).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_quan_ly_chuyen_xe){
            if(currentFragment != FRAGMENT_CHUYENXE){
                replaceFragment(new FragmentChuyenXe());
                currentFragment = FRAGMENT_CHUYENXE;
            }
        }
        else if(id == R.id.nav_quan_ly_tai_khoan){
            if(currentFragment != FRAGMENT_QLTAIKHOAN){
                replaceFragment(new FragmentTaiKhoan());
                currentFragment = FRAGMENT_QLTAIKHOAN;
            }
        }
        else if(id == R.id.nav_thong_ke){
            if(currentFragment != FRAGMENT_THONGKE){
                replaceFragment(new FragmentThongKe());
                currentFragment = FRAGMENT_THONGKE;
            }
        }
        else if(id == R.id.nav_profile){
            if(currentFragment != FRAGMENT_MYPROFILE){
                replaceFragment(new FragmentMyProfile());
                currentFragment = FRAGMENT_MYPROFILE;
            }
        }
        else if(id == R.id.nav_logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
            builder.setTitle("Confirmation");
            builder.setMessage("Bạn có muốn đăng xuất");
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentFrame, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation");
            builder.setMessage("Bạn có muốn thoát ứng dụng ?");
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Thoát ứng dụng
                    finishAffinity();
                    System.exit(0);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}