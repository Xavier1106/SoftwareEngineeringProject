package com.example.test_sqlite.Utils.Common;

import android.content.ContentValues;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class PackAndParser {
    //通过字符串生成匹配串(通过插入%的方式实现)
    public static String toMatchChar(String toBeMatched){
        //match every char
        return null;
    }
    public static String toMatchPhrase(String toBeMatched){
        //toBeMatched is considered as a phrase
        return "%"+toBeMatched+"%";
    }
    public static String toMatchPhrases(String toBeMatched){
        return null;
    }

    //stringList->[s1,longStringToBeParsed,hello_world]
    private static boolean isThisStringEmpty(String toBeChecked){
        if(toBeChecked.isEmpty()){return true;}
        for(char c:toBeChecked.toCharArray()){
            if(c!=' '){return false;}
        }
        return true;
    }

    private static boolean isThisStringList(String toBeChecked){

        if(
                isThisStringEmpty(toBeChecked)
                ||toBeChecked.charAt(0)!='['
                ||toBeChecked.charAt(toBeChecked.length()-1)!=']'){return false;}
        return true;
    }
    //null/empty/blank String is not Valid
    private static boolean isThisStringValid(String toBeChecked){
        if (toBeChecked==null){return false;}
        for(char c:toBeChecked.toCharArray()){
            switch (c){//String in StringList Can't contains "[,]"
                case'[':
                case']':
                case',':
                    return false;
                default:
            }
        }
        return true;
    }

    public static String[] parseStringToList(String stringList){

        if(!isThisStringList(stringList)){return null;}//wrong format

        String temp=stringList.substring(1,stringList.length()-1);//trim brackets

        List<String> ret=new ArrayList<>();

        for(String s:temp.split("," )){//cut into Strings

            if(isThisStringEmpty(s)){continue;}//filter empty String s
            ret.add(s);
        }

        if(ret.isEmpty()){return null;}//empty List

        return ret.toArray(new String[0]);
    }

    public static String packListToString(Object[] listString){
        if (listString == null) {return null;}//null list
        StringBuffer ret=new StringBuffer("[");
        for(Object s:listString){
            if(isThisStringValid(s.toString())){//null/empty/blank String is not Valid
                ret.append(s+",");
            }
            else{return null;}//once invalid,all collapse
        }
        int len= ret.length();
        ret.replace(len-1,len,"]" );
        return ret.toString();
    }
    //ContentValues:put(String,Object) can't find specific method
    public static ContentValues packData(String[] cols, Object[] vals){

        if(cols.length!= vals.length){return null;}//length unmatched
        ContentValues cv=new ContentValues();
        for (int i = 0; i < cols.length; i++) {
            if(!makePair(cols[i], vals[i], cv)){return null;}//col & val pairing failed
        }
        return cv;
    }

    private static boolean makePair(String col, @NonNull Object val, ContentValues Data){
        String clsName=val.getClass().getName();
        //categorize val to its class
        switch (clsName){
            case "java.lang.String": Data.put(col, ((String) val));break;
            case "java.lang.Integer":Data.put(col, ((Integer) val));break;
            case "java.lang.Double":Data.put(col, ((Double) val));break;
            case "java.lang.Boolean":Data.put(col, ((Boolean) val));break;

            case "java.lang.Long":Data.put(col, ((Long) val));break;
            case "java.lang.Float":Data.put(col, ((Float) val));break;
            case "java.lang.Byte":Data.put(col, ((Byte) val));break;
            case "java.lang.Short":Data.put(col, ((Short) val));break;

            case "[Ljava.lang.byte":Data.put(col, ((byte[]) val));break;
            default:return false;//clsName unmatched
        }
        return true;

    }

    private PackAndParser(){}
}