package com.tvc.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import Pojo.DatVeXeKhach;

public class MainActivity extends AppCompatActivity {
    private DatVeXeKhach db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatVeXeKhach(this);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }
}