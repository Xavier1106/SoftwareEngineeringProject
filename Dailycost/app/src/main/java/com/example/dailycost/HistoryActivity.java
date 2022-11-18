package com.example.dailycost;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.AccountAdapter;
import db.AccountBean;
import db.DBManager;
import utils.CalendarDialog;

@SuppressLint("SetTextI18n")
public class HistoryActivity extends AppCompatActivity {
    ListView historyLv;
    TextView timeTv;
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    int year,month;
    int dialogSelPos = -1;
    int dialogSelMonth = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyLv = findViewById(R.id.history_lv);
        timeTv = findViewById(R.id.history_tv_time);
        mDatas = new ArrayList<>();
        adapter = new AccountAdapter(this,mDatas);//设置适配器
        historyLv.setAdapter(adapter);
        initTime();
        timeTv.setText(year+"年"+month+"月");
        loadData(year,month);
        setLVClickListener();
    }
    /*设置ListView每一个item的长按事件*/
    private void setLVClickListener() {
        historyLv.setOnItemLongClickListener((parent, view, position, id) -> {
            AccountBean accountBean = mDatas.get(position);
            deleteItem(accountBean);
            return false;
        });
    }

    private void deleteItem(final AccountBean accountBean) {
        final int delId = accountBean.getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录么？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", (dialog, which) -> {
                    DBManager.deleteItemFromAccounttbById(delId);
                    mDatas.remove(accountBean);//实时刷新，从数据源删除
                    adapter.notifyDataSetChanged();
                });
        builder.create().show();
    }

    /* 获取指定年份月份收支情况的列表*/
    private void loadData(int year,int month) {
        List<AccountBean> list = DBManager.getAccountListOneMonthFromAccounttb(year, month);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.history_iv_back:
                finish();
                break;
            case R.id.history_iv_calendar:
                CalendarDialog dialog = new CalendarDialog(this,dialogSelPos,dialogSelMonth);
                dialog.show();
                dialog.setDialogSize();
                dialog.setOnRefreshListener((selPos, year, month) -> {
                    timeTv.setText(year+"年"+month+"月");
                    loadData(year,month);
                    dialogSelPos = selPos;
                    dialogSelMonth = month;
                });
                break;
        }
    }
}

