package com.example.test_sqlite.Utils.Database.PreSet;

import com.example.test_sqlite.Utils.Common.PackAndParser;

public enum NAME {
    //N:NAME->colNames
    N_AccountBook("[id,isIncome,year,month,day,money,description,type]"),
    N_TEST("[id,name]");

    private NAME(String nameList) {
        statement = nameList;
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
