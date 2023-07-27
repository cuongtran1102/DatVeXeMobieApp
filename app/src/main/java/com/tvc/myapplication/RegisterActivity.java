package com.tvc.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Service.UserService;

public class RegisterActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnRegister;
    private EditText etUserName, etPassWord, etConfirmPassWord, etName,
            etPhone, etSelectedDate;
    private Calendar selectedDate;
    private UserService userService;
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userService = new UserService(this);
        builder = new AlertDialog.Builder(this);
        etUserName = findViewById(R.id.etUserName);
        etPassWord = findViewById(R.id.etPassWord);
        etConfirmPassWord = findViewById(R.id.etConfirmPassWord);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etSDT);
        etSelectedDate = findViewById(R.id.etNgaySinh);
        btnRegister = findViewById(R.id.btnRegister);
        selectedDate = Calendar.getInstance();

        etSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().contains(" ")) {
                    String withoutSpaces = charSequence.toString().replace(" ", "");
                    etUserName.setText(withoutSpaces);
                    etUserName.setSelection(withoutSpaces.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().contains(" ")) {
                    String withoutSpaces = charSequence.toString().replace(" ", "");
                    etPassWord.setText(withoutSpaces);
                    etPassWord.setSelection(withoutSpaces.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etConfirmPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().contains(" ")) {
                    String withoutSpaces = charSequence.toString().replace(" ", "");
                    etConfirmPassWord.setText(withoutSpaces);
                    etConfirmPassWord.setSelection(withoutSpaces.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable.toString()) && !isNumeric(editable.toString())) {
                    etPhone.setText("");
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUserName.getText().toString().isEmpty())
                    Toast.makeText(RegisterActivity.this, "Hãy nhập tên tài khoản", Toast.LENGTH_SHORT).show();
                else if(etPassWord.getText().toString().isEmpty())
                    Toast.makeText(RegisterActivity.this, "Hãy nhập mật khẩu", Toast.LENGTH_SHORT).show();
                else if(etConfirmPassWord.getText().toString().isEmpty())
                    Toast.makeText(RegisterActivity.this, "Hãy nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                else if(etName.getText().toString().isEmpty())
                    Toast.makeText(RegisterActivity.this, "Hãy nhập tên của bạn", Toast.LENGTH_SHORT).show();
                else if(etPhone.getText().toString().isEmpty())
                    Toast.makeText(RegisterActivity.this, "Hãy nhập số điện thoại", Toast.LENGTH_SHORT).show();
                else if(etSelectedDate.getText().toString().isEmpty())
                    Toast.makeText(RegisterActivity.this, "Hãy chọn ngày sinh", Toast.LENGTH_SHORT).show();
                else if(!etPassWord.getText().toString().equals(etConfirmPassWord.getText().toString()))
                    Toast.makeText(RegisterActivity.this, "Mật khẩu nhập lại không Trùng Khớp", Toast.LENGTH_SHORT).show();
                else if(userService.checkUserName(etUserName.getText().toString()) == false)
                    Toast.makeText(RegisterActivity.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                else{
                    userService.insertUser(1, etUserName.getText().toString(), etPassWord.getText().toString(),
                            etName.getText().toString(), etPhone.getText().toString(), etSelectedDate.getText().toString());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Đăng ký tài khoản thành công");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }
    private void showDatePickerDialog() {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        selectedDate.set(year, month, dayOfMonth);


                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        String selectedDateString = sdf.format(selectedDate.getTime());
                        etSelectedDate.setText(selectedDateString);
                    }
                }, year, month, day);


        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
    }
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}