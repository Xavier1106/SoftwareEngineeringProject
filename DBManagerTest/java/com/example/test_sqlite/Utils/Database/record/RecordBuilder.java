package com.example.test_sqlite.Utils.Database.record;

import com.example.test_sqlite.Utils.Common.PackAndParser;

public class RecordBuilder {

    private String[] colnames, coltypes;

    public boolean set(String[] coln,String[] colt){
        colnames=coln;
        coltypes=colt;
        return true;
    }
    public String pack(Object[] data) {
        return PackAndParser.packListToString(data);
    }

    public Object[] parse(String info) {
        return null;
    }

}
