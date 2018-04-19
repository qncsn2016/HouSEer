package com.example.zq.houseer0410;

//20180417 调试到数据库 数据一直插不进去  原来是 表名没改 字段名多了个字母 我的天啊
//20180417 登录 记住密码 自动登陆 测试成功

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.*;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.facebook.stetho.Stetho;

public class Login extends AppCompatActivity {

    private EditText et_UserName;
    private EditText et_Password;
    private CheckBox cb_RmemPass;
    private CheckBox cb_AutoLogin;
    private Button bt_Login;
    private TextView tv_Rigister;

    private MyDatabaseHelper dbHelper;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private String InputUserName;
    private String InputPassword;
    private boolean isRemember;
    private boolean isAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Stetho.initializeWithDefaults(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        et_UserName =(EditText)findViewById(R.id.eT_UserName);
        et_Password =(EditText)findViewById(R.id.eT_Password);
        cb_RmemPass=(CheckBox)findViewById(R.id.cB_RemePass);
        cb_AutoLogin=(CheckBox)findViewById(R.id.cB_AutoLogin);
        bt_Login=(Button)findViewById(R.id.bT_Login);
        tv_Rigister=(TextView)findViewById(R.id.tV_Register);

        dbHelper=new MyDatabaseHelper(this,"User.db",null,2);
        final SQLiteDatabase db=dbHelper.getWritableDatabase();
        InitInfo(db);   //数据库信息初始化

        pref= PreferenceManager.getDefaultSharedPreferences(this);
        isRemember=pref.getBoolean("remember_password",false); // 开始不存在remember_password对应的值 默认false
        if(isRemember){
            //将账号和密码都设置到文本框中
            Log.d("调试 记住密码","是");
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            et_UserName.setText(account);
            et_Password.setText(password);
            cb_RmemPass.setChecked(true);
        } else { Log.d("调试 记住密码","否"); }
        isAutoLogin=pref.getBoolean("auto_login",false);

        if(isAutoLogin){
            Log.d("调试 自动登录","是");
            //自动登录的跳转
            Intent intent=new Intent(Login.this,Register.class);
            startActivity(intent);
        }else{
            Log.d("调试 自动登录","否");
        }


        //登录按钮点击事件
        bt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLoginRight(db); //是否成功登录及对应事件
            }
        });

        //注册按钮点击事件
        tv_Rigister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_Rigister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
                Login.this.finish();
            }
        });

    }

    //UserInfo数据库信息初始化
    private void InitInfo(SQLiteDatabase database){

        ContentValues values=new ContentValues();

        //组装、插入第一条数据
        values.put("username","王靖懿");
        values.put("password","wjy");
        values.put("issubscriber",0);
        database.insert("UserInfo",null,values);
        values.clear();

        //组装、插入第二条数据
        values.put("username","王琳");
        values.put("password","wl");
        values.put("issubscriber",0);
        database.insert("UserInfo",null,values);
        values.clear();

        Log.d("调试 数据库","初始化成功");

        Cursor cursor=database.query("UserInfo",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                //遍历cursor对象
                Log.d("调试 数据库",cursor.getString(cursor.getColumnIndex("username")));
            }while(cursor.moveToNext());
        }
    }

    //是否成功登录及对应事件
    private void isLoginRight(SQLiteDatabase database){
        Cursor cursor=database.query("UserInfo",null,null,null,null,null,null);
        String namefromdb;  //数据库中的用户名
        String passwordfromdb;  //数据库中的密码
        InputUserName= et_UserName.getText().toString();  //用户输入的用户名
        Log.d("调试 登录","用户输入的用户名是   "+InputUserName);
        InputPassword= et_Password.getText().toString();  //用户输入的密码
        Log.d("调试 登录","用户输入的密码是   "+InputPassword);

        int LonginCode=1;

        if(cursor.moveToFirst()){
            do{
                //遍历cursor对象
                namefromdb=cursor.getString(cursor.getColumnIndex("username"));
                if(namefromdb.equals(InputUserName)){  //找到该用户名
                    passwordfromdb=cursor.getString(cursor.getColumnIndex("password"));
                    if(passwordfromdb.equals(InputPassword)){  //密码正确，LoginCode=0
                        LonginCode=0;
                        break;
                    }
                    else{  //用户名正确，密码错误，LoginCode=2
                        LonginCode=2;
                        break;
                    }
                }

            }while(cursor.moveToNext());
        }

        switch (LonginCode){
            case 0:
                Log.d("调试 登录","成功，LoginCode=0");
                Toast.makeText(Login.this,"登录成功！",Toast.LENGTH_SHORT).show();
                editor=pref.edit();
                if(cb_RmemPass.isChecked()){
                    editor.putBoolean("remember_password",true);
                    editor.putString("account",InputUserName);
                    editor.putString("password",InputPassword);
                } else {
                    editor.putBoolean("remember_password",false);
                }
                if(cb_AutoLogin.isChecked()){
                    editor.putBoolean("auto_login",true);
                }
                editor.apply(); //提交修改
                Intent intent=new Intent(Login.this,HouSEerMain.class);
                startActivity(intent);
                Login.this.finish();
                break;
            case 1:Log.d("调试 登录","用户名未注册，LoginCode=1");
                Toast.makeText(Login.this,"该用户名未注册",Toast.LENGTH_SHORT).show();
                break;
            case 2: Log.d("调试 登录","用户名正确，密码错误，LoginCode=2");
                Toast.makeText(Login.this,"密码输入错误",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
