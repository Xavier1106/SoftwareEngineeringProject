package com.example.dailycost;

import static utils.DpUtil.dip2px;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.AccountAdapter;
import db.AccountBean;
import db.DBManager;
import utils.BudgetDialog;
import utils.MoreDialog;
import utils.SelectTimeDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //展示今日收支情况
    SwipeMenuListView todayLv;
    ImageView searchIv;
    ImageButton moreBtn,editBtn;
    TextView graphText;
    //数据源
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    int year,month,day;
    //头布局相关控件
    View headerView;
    TextView topOutTv,topInTv,topbudgetTv,topConTv;
    ImageView topShowIv;
    SharedPreferences preferences;
    //日历导航
    SelectTimeDialog dialogtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTime();
        initView();
        preferences =
                getSharedPreferences("budget", Context.MODE_PRIVATE);   //添加ListView
        addLVHeaderView();
        mDatas = new ArrayList<>();
        adapter = new AccountAdapter(this,mDatas);                     //设置适配器
        todayLv.setAdapter(adapter);
    }

    private void initSwipeMenu(){
        SwipeMenuCreator creator = menu -> {
            //改写记录内容
            SwipeMenuItem changeItem = new SwipeMenuItem(getApplicationContext());
            changeItem.setWidth(dip2px(MainActivity.this,65));  //设置宽度
            changeItem.setIcon(R.mipmap.ic_change);                             //设置图标
            menu.addMenuItem(changeItem);                                       //添加到滑动菜单
            //删除记录
            SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
            deleteItem.setWidth(dip2px(MainActivity.this,65));
            deleteItem.setIcon(R.mipmap.ic_delete);
            menu.addMenuItem(deleteItem);
        };

        todayLv.setMenuCreator(creator);
        todayLv.setOnMenuItemClickListener((position, menu, index) -> {
            int pos = position;
            AccountBean clickBean = mDatas.get(pos);    //获取正在被点击的这条信息
            switch (index) {
                case 0://改写
                    Intent it1 = new Intent(this, RecordActivity.class);  //跳转界面
                    Bundle bundle =new Bundle();
                    bundle.putSerializable("accoutbean",clickBean);
                    it1.putExtras(bundle);
                    startActivity(it1);
                    break;
                case 1://删除
                    showDeleteItemDialog(clickBean);            //弹出提示用户是否删除的对话框
                    break;
            }
            //false:关闭菜单
            //true :保留菜单
            return false;
        });
    }

    //初始化自带的View的方法
    private void initView() {
        todayLv = findViewById(R.id.main_lv);
        editBtn = findViewById(R.id.main_btn_edit);
        moreBtn = findViewById(R.id.main_btn_more);
        searchIv = findViewById(R.id.main_iv_search);
        editBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        searchIv.setOnClickListener(this);
        setLVLongClickListener();
        initSwipeMenu();
    }

    //设置listview的长按事件
    private void setLVLongClickListener() {
        todayLv.setOnItemLongClickListener((parent, view, position, id) -> {
            if (position == 0) {return false;}          //点击了头布局
            int pos = position-1;
            AccountBean clickBean = mDatas.get(pos);    //获取正在被点击的这条信息
            showDeleteItemDialog(clickBean);            //弹出提示用户是否删除的对话框
            return false;
        });
    }

    //弹出是否删除某一条记录的对话框
    private void showDeleteItemDialog(final AccountBean clickBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录么？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", (dialog, which) -> {
                    int click_id = clickBean.getId();
                    DBManager.deleteItemFromAccounttbById(click_id);
                    mDatas.remove(clickBean);
                    adapter.notifyDataSetChanged();
                    setTopTvShow();
                });
        builder.create().show();
    }

    //给ListView添加头布局的方法
    private void addLVHeaderView() {
        //给布局转换成view对象
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top, null);
        todayLv.addHeaderView(headerView);
        //查找头布局可用控件
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_tv_out);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_tv_in);
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_budget);
        topConTv = headerView.findViewById(R.id.item_mainlv_top_tv_day);
        topShowIv = headerView.findViewById(R.id.item_mainlv_top_iv_hide);
        graphText = headerView.findViewById(R.id.item_mainlv_top_statistics);

        graphText.setOnClickListener(this);
        topbudgetTv.setOnClickListener(this);
        headerView.setOnClickListener(this);
        topShowIv.setOnClickListener(this);
        topConTv.setOnClickListener(this);
    }

    //获取今日的具体时间
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    //当actviity获取焦点时(切出切回)
    @Override
    protected void onResume() {
        super.onResume();
        loadDBData();
        setTopTvShow();
    }

    @SuppressLint("SetTextI18n")
    private void setTopTvShow() {
        //获取今日支出和收入总金额，显示在view当中
        float incomeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 1);
        float outcomeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 0);
        String infoOneDay = month+"月"+day+"日"+" ￥"+outcomeOneDay+"  收入 ￥"+incomeOneDay;
        topConTv.setText(infoOneDay);

        float incomeOneMonth =
                DBManager.getSumMoneyOneMonth(year, month, 1);  //获取本月收入总金额
        float outcomeOneMonth =
                DBManager.getSumMoneyOneMonth(year, month, 0);  //获取本月支出总金额
        topInTv.setText("￥"+incomeOneMonth);
        topOutTv.setText("￥"+outcomeOneMonth);                       //设置显示运算剩余

        float bmoney = preferences.getFloat("bmoney", 0);       //预算
        if (bmoney == 0) {
            topbudgetTv.setText("￥ 0");
        }else{
            float syMoney = bmoney-outcomeOneMonth;
            topbudgetTv.setText("￥"+syMoney);
        }
    }

    private void loadDBData() {
        List<AccountBean> list = DBManager.getAccountListOneDayFromAccounttb(year,month,day);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_iv_search:
                Intent it = new Intent(this, SearchActivity.class);  //跳转界面
                startActivity(it);
                break;
            case R.id.main_btn_edit:
                Intent it1 = new Intent(this, RecordActivity.class);  //跳转界面
                startActivity(it1);
                break;
            case R.id.main_btn_more:
                MoreDialog moreDialog = new MoreDialog(this);
                moreDialog.show();
                moreDialog.setDialogSize();
                break;
            case R.id.item_mainlv_top_tv_budget:
                showBudgetDialog();
                break;
            case R.id.item_mainlv_top_iv_hide:
                toggleShow();
                break;
            case R.id.item_mainlv_top_statistics:
                Intent intent = new Intent();
                intent.setClass(this, MonthChartActivity.class);
                startActivity(intent);
                break;
            case R.id.item_mainlv_top_tv_day:
                showTimeDialog();
                break;
        }
    }

    public void showTimeDialog(){
        if(dialogtime==null)
            dialogtime = new SelectTimeDialog(this);
        dialogtime.show();
        dialogtime.setOnEnsureListener(//设定确定按钮被点击了的监听器
                (time, myear, mmonth, mday) -> {
            year=myear;
            month =mmonth;
            day =mday;
            loadDBData();
            setTopTvShow();
        });
    }

    //显示运算设置对话框
    private void showBudgetDialog() {
        BudgetDialog dialog = new BudgetDialog(this);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(money -> {
            SharedPreferences.Editor editor = preferences.edit();   //将预算金额写入SharedPreferences
            editor.putFloat("bmoney",money);
            editor.apply();
            float outcomeOneMonth = DBManager.
                    getSumMoneyOneMonth(year, month, 0);       //计算剩余金额
            float syMoney = money-outcomeOneMonth;                  //预算剩余 = 预算-支出
            topbudgetTv.setText("￥"+syMoney);
        });
    }

    Boolean isShow = true;//默认显示收支明细

    //切换TextView明文和密文
    private void toggleShow() {
        if (isShow) {//转为密文
            PasswordTransformationMethod passwordMethod = PasswordTransformationMethod.getInstance();
            topInTv.setTransformationMethod(passwordMethod);
            topOutTv.setTransformationMethod(passwordMethod);
            topbudgetTv.setTransformationMethod(passwordMethod);
            topShowIv.setImageResource(R.mipmap.ih_hide);
            isShow = false;
        }else {//转为明文
            HideReturnsTransformationMethod hideMethod = HideReturnsTransformationMethod.getInstance();
            topInTv.setTransformationMethod(hideMethod);
            topOutTv.setTransformationMethod(hideMethod);
            topbudgetTv.setTransformationMethod(hideMethod);
            topShowIv.setImageResource(R.mipmap.ih_show);
            isShow = true;
        }
    }


}
