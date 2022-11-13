package com.example.dailycost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AwaitActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView text;
    int time = 2;
    Handler handler;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_await);

        text = findViewById(R.id.txt_count);
        text.setOnClickListener(this);
        handler = new Handler(){
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

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.txt_count:
                if(intent==null) {
                    intent = new Intent(AwaitActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
