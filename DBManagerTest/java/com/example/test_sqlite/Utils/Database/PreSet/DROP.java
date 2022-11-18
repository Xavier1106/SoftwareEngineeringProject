package com.example.test_sqlite.Utils.Database.PreSet;


public enum DROP{
    //D:DROP->table
    D_FORALL("DROP TABLE ? ;");
    private DROP(String dropClause) {statement=dropClause;}
    private final String statement;
    @Override
    public String toString() {return statement;}
}

//TODO:拆分子集合->[F,T,C,D,Q[]]->作为一5个Formatter

//程序预设的语句集
