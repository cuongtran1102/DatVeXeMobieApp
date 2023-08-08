package com.tvc.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Pojo.DatVeXeKhach;
import Service.UserService;

public class MainActivity extends AppCompatActivity {
    private DatVeXeKhach db;
    private EditText etUserName, etPassWord;
    private Button btnLogin, btnRegister;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatVeXeKhach(this);
        userService = new UserService(this);

        etUserName = findViewById(R.id.username);
        etPassWord = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnregister);
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
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUserName.getText().toString().isEmpty()
                        || etPassWord.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Hãy nhập tên tài khoản hoặc mật khẩu",
                            Toast.LENGTH_SHORT).show();
                }
                else if(userService.checkLogin(etUserName.getText().toString(),
                        etPassWord.getText().toString())){
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("USER_NAME", etUserName.getText().toString());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Sai tên tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }
}