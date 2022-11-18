package com.example.dailycost;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.widget.TimePicker;

import db.AlarmReceiver;
import db.DBManager;

import java.util.Calendar;
import java.util.TimeZone;

public class SettingActivity extends AppCompatActivity {

    TextView setting_tv_user;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        setting_tv_user = findViewById(R.id.setting_tv_user);
//        Intent intent = getIntent();
//        int flag = intent.getExtras().getInt("flag");
//        if (flag == 1){
//            setting_tv_user.setText(intent.getExtras().getString("name")+"");
//        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_iv_back:
                finish();
                break;
//            case R.id.setting_bt_user:
//                user();
//                break;
//            case R.id.setting_bt_time:
//                time();
//                break;
//            case R.id.setting_bt_cancelTime:
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    cancelTime();
//                }
//                break;
            case R.id.setting_tv_clear:
                showDeleteDialog();
                break;
        }
    }

//    private void user() {
//        Log.d("TAG", "user: 11111");
//        Intent intent = new Intent();
//        intent.setClass(SettingActivity.this,RegisterActivity.class);
//        startActivityForResult(intent,1);
//        finish();
//    }

//    private void time() {
//        Calendar calendar = Calendar.getInstance();
//        int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY);    //得到小时
//        int minute  = calendar.get(Calendar.MINUTE);            //得到分钟
//        new TimePickerDialog(SettingActivity.this, (view, HourOfDay, Minute) -> {
//            //得到选择后的小时、分钟 —> hourOfDay、minute
//            String time = HourOfDay +":"+ Minute;
//            setTime(HourOfDay, Minute);
//            Toast.makeText(SettingActivity.this,"你设置了每天"+time+"的提醒",Toast.LENGTH_SHORT).show();
//        }, hourOfDay, minute, true).show();
//    }
//
//    private void setTime(int hourOfDay, int minute) {
//        //得到日历实例，用于的获取时间
//        Calendar mCalendar  = Calendar.getInstance();
//        mCalendar.setTimeInMillis(System.currentTimeMillis());
//
//        //获取当前毫秒值
//        long systemTime = System.currentTimeMillis();
//        //是设置日历的时间，主要是让日历的年月日和当前同步
//        mCalendar.setTimeInMillis(System.currentTimeMillis());
//        //将时区设置为东八区(北京时间)
//        mCalendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        //设置在几点提醒
//        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        mCalendar.set(Calendar.MINUTE, minute);
//        //设置在几分提醒
//        mCalendar.set(Calendar.SECOND, 0);
//        mCalendar.set(Calendar.MILLISECOND, 0);
//
//        //获取设置的毫秒值
//        long selectTime = mCalendar.getTimeInMillis();
//        //如果当前时间大于设置的时间，那么就从第二天的设定时间开始
//        if(systemTime > selectTime) {
//            mCalendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//
//        Intent intent = new Intent(SettingActivity.this, AlarmReceiver.class);
//        @SuppressLint("UnspecifiedImmutableFlag")
//        PendingIntent pi = PendingIntent.getBroadcast(SettingActivity.this, 0, intent, 0);
//        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
//
//        am.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), (1000 * 60 * 60 * 24), pi);
//        //参数说明:硬件闹钟，设置提醒时间，提醒周期
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void cancelTime() {
//        Intent intent = new Intent(SettingActivity.this, AlarmReceiver.class);
//        PendingIntent pi = PendingIntent.getBroadcast(SettingActivity.this, 0,
//                intent, 0);
//        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
//        //取消警报
//        am.cancel(pi);
//        Toast.makeText(SettingActivity.this, "关闭了提醒", Toast.LENGTH_SHORT).show();
//    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除提示")
                .setMessage("您确定要删除所有记录么？\n注意：删除后无法恢复，请慎重选择！")
                .setPositiveButton("取消",null)
                .setNegativeButton("确定", (dialog, which) -> {
                    DBManager.deleteAllAccount();
                    Toast.makeText(SettingActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                });
        builder.create().show();
    }
}
