package com.tvc.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Pojo.ChuyenXe;
import Service.CarAdapter;
import Service.ChuyenXeService;
import Service.DatVeService;
import Service.UserService;

public class HomeActivity extends AppCompatActivity implements CarAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<ChuyenXe> dsChuyenXe;
    private Button btnSearch;
    private EditText etNgayKhoiHanh, etDiaDiemDen, etDiaDiemDi;
    private Calendar calendar;
    private ChuyenXeService chuyenXeService;
    private DatVeService datVeService;
    private int user_ID;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");
        UserService userService = new UserService(this);
        setUser_ID(userService.getIDByUserName(userName));
        swipeRefreshLayout = findViewById(R.id.refreshLayout_Home);
        chuyenXeService = new ChuyenXeService(this);
        etDiaDiemDi = findViewById(R.id.etBenDiHome);
        etDiaDiemDen = findViewById(R.id.etBenDenHome);
        etNgayKhoiHanh = findViewById(R.id.etNgayKhoiHanhHome);
        etNgayKhoiHanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        btnSearch = findViewById(R.id.btnTraCuu);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
                alertDialogBuilder.setTitle("Thông báo");
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                if(etDiaDiemDi.getText().toString().isEmpty() || etDiaDiemDen.getText().toString().isEmpty()
                        || etNgayKhoiHanh.getText().toString().isEmpty()){
                    alertDialogBuilder.setTitle("Hãy nhập đủ thông tin chuyến xe cần tra cứu");
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
                else{
                    String diaDiemDi = etDiaDiemDi.getText().toString().strip();
                    String diaDiemDen = etDiaDiemDen.getText().toString().strip();
                    List<ChuyenXe> dsCX = chuyenXeService.getListChuyenXe(diaDiemDi,
                            diaDiemDen, etNgayKhoiHanh.getText().toString());
                    if(dsCX.isEmpty()){
                        alertDialogBuilder.setTitle("Không tìm thấy chuyến xe cần tra cứu");
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        carAdapter = new CarAdapter(dsCX);
                        recyclerView.setAdapter(carAdapter);
                    }
                    else{
                        carAdapter = new CarAdapter(dsCX);
                        recyclerView.setAdapter(carAdapter);

                        // Đặt sự kiện click sau khi đã khởi tạo carAdapter
                        carAdapter.setOnItemClickListener(HomeActivity.this);
                    }
                }
            }
        });

        dsChuyenXe = getCarListFromDatabase();
        carAdapter = new CarAdapter(dsChuyenXe);
        carAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(carAdapter);

        calendar = Calendar.getInstance();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dsChuyenXe = getCarListFromDatabase();
                carAdapter = new CarAdapter(dsChuyenXe);
                carAdapter.setOnItemClickListener(HomeActivity.this);
                recyclerView.setAdapter(carAdapter);

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        // Set the minimum date to tomorrow
        Calendar minDateCalendar = Calendar.getInstance();
        minDateCalendar.add(Calendar.DAY_OF_MONTH, 1);
        datePickerDialog.getDatePicker().setMinDate(minDateCalendar.getTimeInMillis());

        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            etNgayKhoiHanh.setText(sdf.format(calendar.getTime()));
        }
    };

    private List<ChuyenXe> getCarListFromDatabase() {
        LocalDate currentDate = LocalDate.now();
        List<ChuyenXe> listChuyenXe = chuyenXeService.getListChuyenXe();
        List<ChuyenXe> resultListCX = new ArrayList<>();
        for (ChuyenXe chuyenXe : listChuyenXe) {
            LocalDate ngayDi = chuyenXe.getNgayDi();
            if (currentDate.compareTo(ngayDi) < 0) {
                resultListCX.add(chuyenXe);
            }
        }
        return resultListCX;
    }

    @Override
    public void onItemClick(ChuyenXe chuyenXe) {
        Intent intent = new Intent(this, DatVeActivity.class);
        intent.putExtra("ID_TUYEN_DUONG", chuyenXe.getIdTuyenDuong());
        intent.putExtra("ID_CHUYEN_XE", chuyenXe.getIdChuyenXe());
        intent.putExtra("TEN_CHUYEN_XE", chuyenXe.getTenChuyenXe());
        intent.putExtra("BEN_DEN", chuyenXe.getBenDen());
        intent.putExtra("BEN_DI", chuyenXe.getBenDi());
        intent.putExtra("NGAY_DI", chuyenXe.getNgayDi());
        intent.putExtra("GIO_DI", chuyenXe.getGioXuatPhat());
        intent.putExtra("GIA_VE", chuyenXe.getGiaVe());
        intent.putExtra("SO_LUONG_GHE", chuyenXe.getSoLuongGhe());
        intent.putExtra("USER_ID", getUser_ID());
        startActivity(intent);
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    @Override
    public void onBackPressed() {
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