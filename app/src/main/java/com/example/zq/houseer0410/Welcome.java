package com.example.zq.houseer0410;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;

public class Welcome extends AppCompatActivity {

    ImageView imageView_background;
    TextView textView_touse;
    ImageView imageView_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

//        imageView_background=findViewById(R.id.welcome_imageView_background);
        textView_touse=findViewById(R.id.welcome_textView_touse);
        imageView_logo=findViewById(R.id.welcome_imageView_logo);

       /* //点击背景图进入登录页面
        imageView_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Welcome.this,Login.class);
                startActivity(intent);
            }
        });*/

        //点击“欢迎使用 > > >”进入登录页面
        textView_touse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Welcome.this,Login.class);
                startActivity(intent);
            }
        });

        //点击logo进入登录页面
        imageView_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Welcome.this,Login.class);
                startActivity(intent);
            }
        });

    }
}
