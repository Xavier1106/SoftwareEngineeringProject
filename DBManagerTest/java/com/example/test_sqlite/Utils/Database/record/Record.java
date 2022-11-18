package com.example.test_sqlite.Utils.Database.record;

import com.example.test_sqlite.Utils.Common.PackAndParser;
//TODO:record不再承载数据，仅作为翻译器
abstract class AbstractRecord {

    boolean isAvailable=false;
    public abstract boolean parse(Object[] data);//父类统一检查参数正确性
    public abstract boolean parse(String info);
    public abstract String pack();
    public abstract Object[] extract();
    public abstract String toString();
}

class AccountBook_Record{
    //invisible
    int id;
    boolean isIncome;
    //TODO:boolean isFixed;

    //visible
    int year,month,day;//Date
    Double money;
    String description;
    String type;

    AccountBook_Record(){}

    public boolean parse(Object[] data){
        try{
            id= (int) data[0];
            isIncome=(boolean)data[1];
            year = (int) data[2];
            month= (int) data[3];
            day= (int) data[4];
            money= (Double) data[5];
            description= (String) data[6];
            type= (String) data[7];
            return true;
        } catch(Exception e){
            return false;
        }
    }
    @Override
    public String toString(){
        return PackAndParser.packListToString(new Object[]{id,isIncome,year,month,day,money,description,type});
    }

    public boolean parse(String info) {
        String[] data=PackAndParser.parseStringToList(info);
        if(data==null){return false;}
        if(data.length!=8){throw new NumberFormatException("param num not equal!");}
        id=Integer.parseInt(data[0]);
        isIncome=Boolean.parseBoolean(data[1]);
        year = Integer.parseInt(data[2]);
        month=Integer.parseInt(data[3]);
        day=Integer.parseInt(data[4]);
        money=Double.parseDouble(data[5]);
        description=data[6];
        type=data[7];
        return false;
    }
}

class TEST_Record{

    int id;
    String name;

    public  TEST_Record(Object[] data){
        id= (int) data[0];
        name= (String) data[1];
    }

    //parse
    public TEST_Record(String info)throws NumberFormatException{
        String[] data=PackAndParser.parseStringToList(info);
        if(data.length!=2){throw new NumberFormatException("param num not equal!");}
        id=Integer.parseInt(data[0]);
        name= (String) data[1];
    }
    //pack
    @Override
    public String toString(){
        return PackAndParser.packListToString(new Object[]{id,name});
    }
}

