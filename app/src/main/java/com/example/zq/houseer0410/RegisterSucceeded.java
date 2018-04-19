package com.example.zq.houseer0410;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//20180418 成功注册页面 测试成功

public class RegisterSucceeded extends AppCompatActivity {

    private Button bt_InAftReg;
    private Button bt_OutAftReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_succeeded);

        bt_InAftReg=findViewById(R.id.bT_InAftReg);
        bt_OutAftReg=findViewById(R.id.bT_OutAftReg);

        bt_InAftReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterSucceeded.this,HouSEerMain.class);
                startActivity(intent);
                RegisterSucceeded.this.finish();
            }
        });

        bt_OutAftReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterSucceeded.this,Login.class);
                startActivity(intent);
                RegisterSucceeded.this.finish();
            }
        });
    }
}
