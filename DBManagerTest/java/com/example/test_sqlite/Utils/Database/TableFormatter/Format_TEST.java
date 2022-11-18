package com.example.test_sqlite.Utils.Database.TableFormatter;

import android.util.Log;

import com.example.test_sqlite.R;
import com.example.test_sqlite.Utils.Common.PackAndParser;
import com.example.test_sqlite.Utils.Database.PreSet.CREATE;
import com.example.test_sqlite.Utils.Database.PreSet.DROP;
import com.example.test_sqlite.Utils.Database.PreSet.NAME;
import com.example.test_sqlite.Utils.Database.PreSet.TYPE;
import com.example.test_sqlite.Utils.Property.PPManager;

public final class Format_TEST extends TableFormatter {

    public Format_TEST(PPManager ppm){
        create= CREATE.C_TEST;
        names= NAME.N_TEST;
        types= TYPE.T_TEST;
        drop = DROP.D_FORALL;
        queries=null;
        ppm.useProperties(R.raw.db);
        tables = PackAndParser.parseStringToList((String) ppm.readFromProperties("TableNames"));
        for(String s:tables){
            Log.v("format_test",s+"!!!!!!!!!!!!!!!!");
        }
        for(String s:names.toStringList()){
            Log.v("format_test",s+"!!!!!!!!!!!!!!!!");

        }
        for(String s:types.toStringList()){
            Log.v("format_test",s+"!!!!!!!!!!!!!!!!");
        }
    }
}
