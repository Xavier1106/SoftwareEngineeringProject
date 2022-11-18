package com.example.dailycost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import adapter.RecordPagerAdapter;
import db.AccountBean;
import frag_record.IncomeFragment;
import frag_record.OutcomeFragment;

public class RecordActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        tabLayout = findViewById(R.id.record_tabs);
        viewPager = findViewById(R.id.record_vp);
        initPager();
    }

    private void initPager() {
        List<Fragment>fragmentList = new ArrayList<>();
        Intent intent = getIntent();
        AccountBean accountBean = (AccountBean)intent.getSerializableExtra("accoutbean");
        OutcomeFragment outFrag = null;
        IncomeFragment inFrag = null;
        outFrag = new OutcomeFragment();
        inFrag = new IncomeFragment();
        if(accountBean!=null){
            if(accountBean.getKind()==0){
                outFrag = new OutcomeFragment(accountBean);
            }
            else if(accountBean.getKind()==1){
                inFrag = new IncomeFragment(accountBean);
            }

        }

        fragmentList.add(outFrag);
        fragmentList.add(inFrag);

        RecordPagerAdapter pagerAdapter = new RecordPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    //点击事件
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        if (view.getId() == R.id.record_iv_back) {
            finish();
        }
    }
}
