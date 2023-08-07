package com.tvc.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        String[] danhSachDeMuc = {"Thông tin cá nhân", "Thay đổi Mật Khẩu", "Liên kết thanh toán", "Thông tin khuyến mãi","Đăng xuất "};

        // Ánh xạ ListView
        ListView thongtin = findViewById(R.id.thongtin);

        // Tạo ArrayAdapter để hiển thị danh sách các đề mục
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, danhSachDeMuc);

        // Đặt adapter cho ListView
        thongtin.setAdapter(adapter);

        // Xử lý sự kiện khi người dùng chọn một đề mục trong ListView
        thongtin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedDeMuc = danhSachDeMuc[position];
                Toast.makeText(AccountActivity.this, "Bạn đã chọn: " + selectedDeMuc, Toast.LENGTH_SHORT).show();
                // Xử lý hành động khi người dùng chọn một đề mục cụ thể ở đây...
            }
        });
    }
}
