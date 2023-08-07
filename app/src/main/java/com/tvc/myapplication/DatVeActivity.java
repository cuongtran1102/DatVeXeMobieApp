package com.tvc.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DatVeActivity extends AppCompatActivity {
    private GridLayout grGhe;
    private Button btnDatVe;
    private ImageButton btnBack;
    private TextView txtBenDi, txtBenDen, txtNgayDi, txtGioDi, txtSLGhe, txtTienVe;

    private List<Button> buttonList;
    private int buttonMargin = 8; // Khoảng cách mặc định



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_ve);
        buttonList = new ArrayList<>();
        btnDatVe = findViewById(R.id.btnDatVe);
        btnBack = findViewById(R.id.btnBackdv);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        grGhe = findViewById(R.id.grGhe);
        txtBenDi = findViewById(R.id.txtNoiDidv);
        txtBenDen = findViewById(R.id.txtNoiDendv);
        txtNgayDi = findViewById(R.id.txtNgayDidv);
        txtGioDi = findViewById(R.id.txtGioDidv);
        txtSLGhe = findViewById(R.id.txtSLGhe);
        txtTienVe = findViewById(R.id.txtTienVe);

        Intent intent = getIntent();
        int soLuongGhe = intent.getIntExtra("SO_LUONG_GHE", 1);
        int idTuyenDuong = intent.getIntExtra("ID_TUYEN_DUONG", 1);
        int idChuyenXe = intent.getIntExtra("ID_CHUYEN_XE", 1);
        String tenChuyenXe = intent.getStringExtra("TEN_CHUYEN_XE");
        String benDen = intent.getStringExtra("BEN_DEN");
        String benDi = intent.getStringExtra("BEN_DI");
        float giaVe = intent.getFloatExtra("GIA_VE", 1);
        LocalDate ngayDi = (LocalDate) intent.getSerializableExtra("NGAY_DI");
        LocalTime gioXuatPhat = (LocalTime) intent.getSerializableExtra("GIO_DI");

        txtBenDi.setText(benDi);
        txtBenDen.setText(benDen);
        txtNgayDi.setText(ngayDi.toString());
        txtGioDi.setText(gioXuatPhat.toString());

        for (int i = 1; i <= soLuongGhe; i++) {
            Button button = new Button(this);
            button.setText(String.valueOf(i));
            button.setBackgroundColor(Color.GREEN);
            buttonList.add(button); // Thêm button vào danh sách
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.setMargins(buttonMargin, buttonMargin, buttonMargin, buttonMargin);
            button.setLayoutParams(layoutParams);
            grGhe.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                boolean isButtonSelected = false;

                @Override
                public void onClick(View v) {
                    if (isButtonSelected) {
                        button.setBackgroundColor(Color.GREEN);
                    } else {
                        button.setBackgroundColor(Color.RED);
                    }
                    isButtonSelected = !isButtonSelected;
                }
            });
        }
    }
}