package com.tvc.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        String[] danhSach = {"Tuyến xe trong ngày", "Các tuyến trong tuần"};

        ListView tuyenxe = findViewById(R.id.tuyenxe);

        // Tạo ArrayAdapter để hiển thị danh sách các đề mục
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, danhSach);

        // Đặt adapter cho ListView
        tuyenxe.setAdapter(adapter);

        // Xử lý sự kiện khi người dùng chọn một đề mục trong ListView
        tuyenxe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedDeMuc = danhSach[position];
                Toast.makeText(NotificationActivity.this, "Bạn đã chọn: " + selectedDeMuc, Toast.LENGTH_SHORT).show();
                // Xử lý hành động khi người dùng chọn một đề mục cụ thể ở đây...
            }
        });
    }
}
