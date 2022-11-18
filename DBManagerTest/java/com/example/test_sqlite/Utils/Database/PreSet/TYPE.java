package com.example.test_sqlite.Utils.Database.PreSet;

import com.example.test_sqlite.Utils.Common.PackAndParser;

public enum TYPE {
    //T:TYPE->colType
    T_AccountBook("[int,boolean,int,int,int,double,String,String]"),
    T_TEST("[int,String]");

    private TYPE(String typeList) {
        statement = typeList;
    }

    private final String statement;

    @Override
    public String toString() {
        return statement;
    }

    public String[] toStringList(){
        return PackAndParser.parseStringToList(statement);
    }
}
