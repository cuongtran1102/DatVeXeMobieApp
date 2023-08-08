package com.tvc.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.DialogInterface;
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
import java.text.NumberFormat;
import java.util.Locale;

import Service.DatVeService;
import Service.SupportService;

public class DatVeActivity extends AppCompatActivity {
    private AlertDialog alertDialog;
    private GridLayout grGhe;
    private Button btnDatVe;
    private ImageButton btnBack;
    private TextView txtBenDi, txtBenDen, txtNgayDi, txtGioDi, txtSLGhe, txtTienVe;

    private List<Button> buttonList;
    private int buttonMargin = 8; // Khoảng cách mặc định
    private DatVeService datVeService;
    private SupportService supportService;
    private int selectedSeatCount = 0;
    private List<Integer> dsGheDangDuocChon;
    private List<Integer> dsGheDaDat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_ve);
        dsGheDangDuocChon = new ArrayList<>();
        datVeService = new DatVeService(this);
        supportService = new SupportService();
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
        int user_ID = intent.getIntExtra("USER_ID", 0);

        txtBenDi.setText(benDi);
        txtBenDen.setText(benDen);
        txtNgayDi.setText(ngayDi.toString());
        txtGioDi.setText(gioXuatPhat.toString());

        dsGheDaDat = datVeService.DSGheDaDatCuaCX(idChuyenXe);

        for (int i = 1; i <= soLuongGhe; i++) {
            Button button = new Button(this);
            button.setText(String.valueOf(i));
            button.setBackgroundColor(Color.WHITE);
//            buttonList.add(button); // Thêm button vào 1 danh sách
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.setMargins(buttonMargin, buttonMargin, buttonMargin, buttonMargin);
            button.setLayoutParams(layoutParams);
            grGhe.addView(button);

            if (dsGheDaDat.contains(i)) {
                // Nếu số ghế đã được đặt, thiết lập màu nền và vô hiệu hóa click
                button.setBackgroundColor(Color.RED);
                button.setEnabled(false);
            } else {
                // Nếu số ghế chưa được đặt, thiết lập sự kiện click
                button.setOnClickListener(new View.OnClickListener() {
                    boolean isButtonSelected = false;

                    @Override
                    public void onClick(View v) {
                        if (!button.isEnabled()) {
                            return; // Không thực hiện gì nếu button đã bị vô hiệu hóa
                        }

                        int soGheDuocChon = Integer.parseInt(button.getText().toString());
                        if (isButtonSelected) {
                            button.setBackgroundColor(Color.WHITE);
                            selectedSeatCount--; // Giảm số lượng ghế đã chọn
                            buttonList.remove(button);// Remove button có màu nền White khỏi butonList
                        } else {
                            button.setBackgroundColor(Color.GREEN);
                            selectedSeatCount++;// Tăng số lượng ghế khi chọn
                            buttonList.add(button);// Thêm button có màu nền Green vào 1 buttonList
                        }
                        isButtonSelected = !isButtonSelected;
                        txtSLGhe.setText(String.valueOf(selectedSeatCount));

                        float tongTien = selectedSeatCount * giaVe;

                        NumberFormat format = NumberFormat.getCurrencyInstance(new
                                Locale("vi", "VN"));
                        String formattedTongTien = format.format(tongTien);

                        // Hiển thị tổng tiền vé lên txtTienVe
                        txtTienVe.setText(formattedTongTien);
                    }
                });

            }
        }
        btnDatVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(DatVeActivity.this);
                alertDialogBuilder.setTitle("Thông báo");
                alertDialogBuilder.setCancelable(false); // Ngăn chặn đóng khi nhấn bên ngoài

                if (buttonList.size() == 0) {
                    alertDialogBuilder.setMessage("Hãy chọn ghế muốn đặt");
                    alertDialogBuilder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                } else {
                    for (Button btn : buttonList) {
                        dsGheDangDuocChon.add(Integer.parseInt(btn.getText().toString()));
                    }
                    datVeService.luuVe(dsGheDangDuocChon, giaVe, supportService.getStringCurrentDate(),
                            supportService.getStringCurrentTime(), idChuyenXe, user_ID);
                    alertDialogBuilder.setMessage("Đặt vé thành công");
                    alertDialogBuilder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Khi người dùng nhấn OK, kết thúc Activity và khởi động lại nó
                                    Intent intent = getIntent();
                                    finish(); // Kết thúc Activity hiện tại
                                    startActivity(intent); // Khởi động lại Activity
                                }
                            });
                }

                alertDialog = alertDialogBuilder.create(); // Gán alertDialog để có thể đóng khi cần
                alertDialog.show();

                txtSLGhe.setText("");
                txtTienVe.setText("");
            }
        });
    }
}