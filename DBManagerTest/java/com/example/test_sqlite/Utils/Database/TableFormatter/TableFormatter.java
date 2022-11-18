package com.example.test_sqlite.Utils.Database.TableFormatter;

import com.example.test_sqlite.Utils.Common.PackAndParser;
import com.example.test_sqlite.Utils.Database.PreSet.*;
import com.example.test_sqlite.Utils.Property.PPManager;

//对应到每个表格自有的语句集
public class TableFormatter {

    public CREATE create;
    public NAME names;
    public TYPE types;
    public DROP drop;
    public QUERY[] queries;
    public String[] tables;

}

