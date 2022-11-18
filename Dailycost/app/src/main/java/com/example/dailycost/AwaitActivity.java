package com.example.dailycost;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class AwaitActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView text;
    int time = 2;
    Handler handler;
    Intent intent;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_await);

        text = findViewById(R.id.txt_count_down);
        text.setOnClickListener(this);
        //new Handler已弃用
        handler = new Handler(Looper.myLooper()){
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (time>0){
                    text.setText(time -- + " S");
                    handler.sendEmptyMessageDelayed(100,1000);
                }else{
                    if(intent==null) {
                        intent = new Intent(AwaitActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };
        handler.sendEmptyMessage(100);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txt_count_down) {
            if (intent == null) {
                intent = new Intent(AwaitActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
