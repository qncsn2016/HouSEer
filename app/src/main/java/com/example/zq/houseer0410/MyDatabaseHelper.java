package com.example.zq.houseer0410;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by wjy on 2018/4/16.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_USER = "create table UserInfo("
            //+"id integer primary key autoincrement,"
            + "username text primary key,"  //用户名 字符串
            + "password text,"  //密码 字符串
            + "issubscriber integer)";  //是否订阅 0表示未订阅 1表示订阅

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        Log.d("调试 数据库","成功创建");
//        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("调试 数据库","成功更新");
        //Toast.makeText(mContext,"Update succeeded",Toast.LENGTH_SHORT).show()
    }
}
