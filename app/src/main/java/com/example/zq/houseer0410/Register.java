package com.example.zq.houseer0410;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

//20180418 检测是否符合注册要求的逻辑有问题 没写数据库插入
//20180419 写完 未调试

public class Register extends AppCompatActivity {

    private EditText et_UserName;
    private EditText et_Password1;
    private EditText et_Password2;
    private Button bt_Register;

    MyDatabaseHelper dbHelper;

    private String InputUserName;
    private String InputPassword1;
    private String InputPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_UserName = findViewById(R.id.eT_RegiUserName);
        et_Password1 = findViewById(R.id.eT_RegiPassWord1);
        et_Password2 = findViewById(R.id.eT_RegiPassWord2);
        bt_Register = findViewById(R.id.bT_Register);

        dbHelper = new MyDatabaseHelper(this, "User.db", null, 2);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        bt_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRegisterRight(db);
            }
        });

    }

    private void isRegisterRight(SQLiteDatabase database) {
        InputUserName = et_UserName.getText().toString();
        InputPassword1 = et_Password1.getText().toString();
        InputPassword2 = et_Password2.getText().toString();
        int RegisterCode = 0;
        String namefromdb;  //数据库中的用户名
        int tab = 0;  //用户输入是否出问题 0没问题 1有问题 不再判断是否有RegisterCode更大的问题

        if (InputUserName.isEmpty() && tab == 0) {
            Log.d("调试 注册", "用户名为空");
            RegisterCode = 1;
            et_UserName.requestFocus();
            InputMethodManager imm = (InputMethodManager) et_UserName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            tab = 1;
        }

        Cursor cursor = database.query("UserInfo", null, null, null, null, null, null);
        if (cursor.moveToFirst() && tab == 0) {
            do {
                //遍历cursor对象
                namefromdb = cursor.getString(cursor.getColumnIndex("username"));
                if (namefromdb.equals(InputUserName)) {  //找到该用户名
                    Log.d("调试 注册", "用户名已被注册");
                    et_UserName.setText("");
                    et_UserName.requestFocus();
                    InputMethodManager imm = (InputMethodManager) et_UserName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                    RegisterCode = 2;
                    tab = 1;
                }
            } while (cursor.moveToNext());
        }

        if (InputPassword1.isEmpty() && tab == 0) {
            Log.d("调试 注册", "密码为空");
            et_Password1.requestFocus();
            InputMethodManager imm = (InputMethodManager) et_Password1.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            RegisterCode = 3;
            tab = 1;
        }

        if (InputPassword1.length() < 5 && tab == 0) {
            Log.d("调试 注册", "密码太短");
            et_Password1.setText("");
            et_Password2.setText("");
            et_Password1.requestFocus();
            InputMethodManager imm = (InputMethodManager) et_Password1.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            RegisterCode = 4;
            tab = 1;
        }

        if (InputPassword2.isEmpty() && tab == 0) {
            Log.d("调试 注册", "没有再次输入密码");
            et_Password2.requestFocus();
            InputMethodManager imm = (InputMethodManager) et_Password2.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            RegisterCode = 5;
            tab = 1;
        }
        if (!(InputPassword1.equals(InputPassword2)) && tab == 0) {
            Log.d("调试 注册", "两次输入密码不一致");
            et_Password1.setText("");
            et_Password2.setText("");
            et_Password1.requestFocus();
            InputMethodManager imm = (InputMethodManager) et_Password1.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            RegisterCode = 6;
            tab = 1;
        }

        switch (RegisterCode) {
            case 0:   //注册成功
                Toast.makeText(Register.this, "注册成功！", Toast.LENGTH_SHORT).show();
                Log.d("调试 注册", "0");

                ContentValues values = new ContentValues();
                //组装、插入新的注册用户数据
                values.put("username", InputUserName);
                values.put("password", InputPassword1);
                values.put("issubscriber", 0);
                database.insert("UserInfo", null, values);
                values.clear();

                Intent intent = new Intent(Register.this, RegisterSucceeded.class);
                startActivity(intent);
                this.finish();
                break;
            case 1:
                Toast.makeText(Register.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                Log.d("调试 注册", "1");
                break;
            case 2:
                Toast.makeText(Register.this, "该用户名已被注册！", Toast.LENGTH_SHORT).show();
                Log.d("调试 注册", "2");
                break;
            case 3:
                Toast.makeText(Register.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                Log.d("调试 注册", "3");
                break;
            case 4:
                Toast.makeText(Register.this, "密码太短，请重新输入！", Toast.LENGTH_SHORT).show();
                Log.d("调试 注册", "4");
                break;
            case 5:
                Toast.makeText(Register.this, "请再次输入密码！", Toast.LENGTH_SHORT).show();
                Log.d("调试 注册", "5");
                break;
            case 6:
                Toast.makeText(Register.this, "两次输入密码不一致！", Toast.LENGTH_SHORT).show();
                Log.d("调试 注册", "6");
                break;
        }
    }
}