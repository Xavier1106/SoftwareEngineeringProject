package com.example.test_sqlite.Pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_sqlite.Utils.Database.DBManager;
import com.example.test_sqlite.Utils.Property.PPManager;
import com.example.test_sqlite.R;

import java.security.spec.ECField;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    EditText et_id,et_name;
    DBManager dbm;
    PPManager ppm;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.MAIN_TV);
        et_id=findViewById(R.id.ET_ID);
        et_name=findViewById(R.id.ET_NAME);

        tv.setText(DBManager.initDBM(MainActivity.this)+"");
        dbm=DBManager.getDBM();

        ppm=PPManager.getPPM();

        //TODO:第一次输入时清空提示语
        findViewById(R.id.SHOW).setOnClickListener(view -> tv.setText("id="+et_id.getText()+",name="+et_name.getText()));
        findViewById(R.id.CLR_tv).setOnClickListener(view -> tv.setText(""));
        findViewById(R.id.CLR_in).setOnClickListener(view -> {et_name.setText("");et_id.setText("");});

        //增删改查
        findViewById(R.id.INSERT).setOnClickListener(view -> {
            try{
                if(canParseID()){

                    dbm.useTable("TEST");
                    Object[] vals=new Object[]{Integer.valueOf(
                            et_id.getText().toString()),
                            et_name.getText().toString()
                    };
                    long ret=dbm.INSERT(vals);
                    if(ret!=-1){
                        tv.setText("done");
                    }
                    else{
                        tv.setText("nah"+ret);
                    }
                }
                else{
                    tv.setText("number plz");
                }
            } catch(Exception e){
                tv.setText("failed");
            }
        });
        findViewById(R.id.DELETE).setOnClickListener(view -> {
            try{
                dbm.DELETE("id > 500",null);
                tv.setText("done");
            } catch(Exception e){
                tv.setText("failed");
            }

        });
        findViewById(R.id.UPDATE).setOnClickListener(view -> {

            int resId1 = getResources().getIdentifier("db", "raw", "com.example.test_sqlite");
            if(R.raw.db==resId1){
                tv.setText("TRUE");
            }
        });
        findViewById(R.id.QUERY).setOnClickListener(view -> {
            try{
                dbm.useTable("TEST");
                String[] results = dbm.QUERY(null,null);
                if(results==null){tv.setText("NULL!");return;}
                StringBuilder all= new StringBuilder();
                for(String res:results){all.append(res).append("\n");}
                tv.setText(all.toString());
            }catch(Exception e){
                tv.setText(tv.getText()+"query failed");
            }
        });

        findViewById(R.id.TEST_BTN).setOnClickListener(view -> {
            Intent i=new Intent(this,TestActivity.class);
            startActivity(i);//TEST:pass info between Activity
        });

    }

    private boolean canParseID(){
        try{
            Integer.valueOf(et_id.getText().toString());
            return true;
        }
        catch(Exception e){return false;}
    }
}