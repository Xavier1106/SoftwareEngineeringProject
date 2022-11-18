package com.example.test_sqlite.Pages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_sqlite.R;
import com.example.test_sqlite.Utils.Database.DBManager;
import com.example.test_sqlite.Utils.Property.PPManager;

import java.util.List;

//测试数据在不同Activity之间传递是否正确
public class TestActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        String ppm_test = "",dbm_test = "";
        try{
            PPManager ppm=PPManager.getPPM();
            //ppm_test=ppm.readFromSharedPreferences();
        }
        catch(Exception e){ppm_test="ppm:failed";}
        try{
//            DBManager dbm=DBManager.getDBM();
//            List<String> results = dbm.QUERY();
//            StringBuilder all= new StringBuilder();
//            for(String res:results){all.append(res).append("\n");}
//            dbm_test=all.toString();
        }
        catch(Exception e){dbm_test="dbm:failed";}
        TextView tv = findViewById(R.id.TEST_TV);
        tv.setText(ppm_test+"\n"+dbm_test);
    }
}