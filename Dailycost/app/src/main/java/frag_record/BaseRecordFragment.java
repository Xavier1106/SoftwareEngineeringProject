package frag_record;

import static java.lang.Float.parseFloat;

import android.annotation.SuppressLint;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailycost.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import db.AccountBean;
import db.TypeBean;
import utils.DescriptionDialog;
import utils.KeyBoardUtils;
import utils.SelectTimeDialog;

//记录页面中的支出模块
public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener {

    KeyboardView keyboardView;
    EditText moneyEt;                   //钱数
    ImageView typeIv;
    TextView typeTv, descriptionTv,timeTv;
    GridView typeGv;
    List<TypeBean> typeList;
    TypeBaseAdapter adapter;
    AccountBean accountBean;            //将需要插入到记账本中的数据保存成对象形式
    SelectTimeDialog dialogtime;        //时间选择dialog
    DescriptionDialog dialogtext;       //备注dialog
    boolean flag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!flag){
            accountBean = new AccountBean(); //创建对象
            accountBean.setTypename("其他");
            accountBean.setsImageId(R.mipmap.ic_others_fs);
        }
    }

    public BaseRecordFragment() {flag=false;}//子类调用默认构造器

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //载入当前页面
        View view = inflater.inflate(R.layout.fragment_outcome,container,false);
        initViews(view);
        loadDataToGv();
        if(!flag){
            setInitTime();
        }
        setGVListener();
        return view;
    }

    //获取当前时间，显示在timeTv上
    private void setInitTime() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = sdf.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);
    }

    //设置GridView每一项的点击事件
    private void setGVListener() {
        typeGv.setOnItemClickListener((parent, view, position, id) -> {
            adapter.selectPos = position;
            //提示绘制发生变化
            adapter.notifyDataSetChanged();
            TypeBean typeBean = typeList.get(position);
            String typename = typeBean.getTypename();
            typeTv.setText(typename);
            accountBean.setTypename(typename);
            int simageId = typeBean.getSimageId();
            typeIv.setImageResource(simageId);
            accountBean.setsImageId(simageId);
        });
    }

    //给GridView填充数据的方法
    public void loadDataToGv() {
        typeList = new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);
    }

    private void initViews(View view) {
        keyboardView = view.findViewById(R.id.frag_record_keyboard);         //键盘
        moneyEt = view.findViewById(R.id.frag_record_et_money);              //右上角钱
        typeIv = view.findViewById(R.id.frag_record_iv);                     //左上角图标
        typeGv = view.findViewById(R.id.frag_record_gv);                     //类型选择表
        typeTv = view.findViewById(R.id.frag_record_tv_type);                //左上角文字
        descriptionTv = view.findViewById(R.id.frag_record_tv_description);  //备注
        timeTv = view.findViewById(R.id.frag_record_tv_time);                //时间
        descriptionTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        //显示自定义软键盘
//        if(flag){
//            moneyEt.setText(Float.toString(accountBean.getMoney()));
//        }
        KeyBoardUtils boardUtils = new KeyBoardUtils(keyboardView,moneyEt);
        boardUtils.showKeyboard();
        //设置接口，监听确定按钮
        boardUtils.setOnEnsureListener(() -> {
            //获取输入的钱数
            String moneyStr = moneyEt.getText().toString();
            if (TextUtils.isEmpty(moneyStr) || moneyStr.equals("0")){
                getActivity().finish();
                return;
            }
            else if(parseFloat(moneyStr)>10000000){
                Toast.makeText(getContext(),"输入的数据太大",Toast.LENGTH_SHORT).show();
                return;
            }
            float money = parseFloat(moneyStr);
            accountBean.setMoney(money);
            //获取记录的信息，保存在数据库当中
            saveAccountToDB();
            //返回上一级页面
            Objects.requireNonNull(getActivity()).finish();
        });
    }

    //收入记录和支出记录由两个不同的子类实现
    public abstract void saveAccountToDB();

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_record_tv_time:
                showTimeDialog();
                break;
            case R.id.frag_record_tv_description:
                showBZDialog();
                break;
        }
    }

    //时间对话框
    public void showTimeDialog(){
        if(dialogtime==null)
            dialogtime = new SelectTimeDialog(Objects.requireNonNull(getContext()));
        dialogtime.show();
        //设定确定按钮被点击了的监听器
        dialogtime.setOnEnsureListener((time, year, month, day) -> {
            timeTv.setText(time);
            accountBean.setTime(time);
            accountBean.setYear(year);
            accountBean.setMonth(month);
            accountBean.setDay(day);
            Log.d("TAG", "onEnsure");
        });
    }

    //备注对话框
    public void showBZDialog(){
        if(dialogtext==null)
            dialogtext = new DescriptionDialog(Objects.requireNonNull(getContext()));
        dialogtext.show();
        dialogtext.setDialogSize();
        dialogtext.setOnEnsureListener(() -> {
            String msg = dialogtext.getEditText();
            if (!TextUtils.isEmpty(msg)) {
                descriptionTv.setText(msg);
                accountBean.setDescription(msg);
            }
            dialogtext.cancel();
        });
    }
}
