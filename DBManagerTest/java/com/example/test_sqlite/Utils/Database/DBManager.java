package com.example.test_sqlite.Utils.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.test_sqlite.Utils.Common.PackAndParser;
import com.example.test_sqlite.Utils.Database.TableFormatter.Format_TEST;
import com.example.test_sqlite.Utils.Database.TableFormatter.TableFormatter;
import com.example.test_sqlite.Utils.Database.record.RecordBuilder;
import com.example.test_sqlite.Utils.Property.PPManager;

import java.util.ArrayList;
import java.util.List;

//Database Manager
public class DBManager {

    private static boolean isAvailable=false;
    private static DBManager dbm;
    private static SQLiteDatabase db;
    //for Database
    //tableNames:ensure tableInUse is Valid
    private static String[] tableNames;
    //columnNames:names of table(tableInUse)'s columns
    //columnTypes:types of each column
    private static String[] columnNames,columnTypes;

    private static TableFormatter tableFormat;
    private static RecordBuilder recordBuilder;
    private static String tableInUse;

    //first initialized in MainActivity
    public static boolean initDBM(Context context){
        if(!isAvailable){
            //连接数据库
            dbm=new DBManager();//construct singleton
            DBHelper dbh=new DBHelper(context,"TEST_DB",null,1);
            db=dbh.getWritableDatabase();
            //读取配置文件
            PPManager.initPPM(context);
            PPManager ppm=PPManager.getPPM();
            //生成表格格式
            tableFormat =new Format_TEST(ppm);
            boolean format_ok=useFormat(tableFormat);
            recordBuilder=new RecordBuilder();
            recordBuilder.set(columnNames,columnTypes);
            //检验初始化完整性
            isAvailable=db!=null&&format_ok;//INIT COMPLETE
            return isAvailable;
        }
        return true;//init once
    }
    private static boolean useFormat(TableFormatter tabFormat){
        //NameList is a String like "[TableA,hello_world,TEST]"
        if(tableNames==null||columnNames==null||columnTypes==null){//全部写入才算初始化完毕
            tableNames=tabFormat.tables;
            columnNames=tabFormat.names.toStringList();
            columnTypes=tabFormat.types.toStringList();

            return tableNames!=null&&columnNames!=null&&columnTypes!=null;
        }
        else return true;
    }
    public static DBManager getDBM() {
        //invoke in any Activity(without parameter)
        if(isAvailable){return dbm;}//Ensured INIT before INVOKE
        else return null;
    }
    private DBManager(){}//hidden initializer

    public boolean useTable(String tableName){
        if(!isThisNameAvailable(tableName)){Log.v("check_name","WRONG NAME !!!!!!!!!!");return false;}
        else{
            Log.v("check_name","USING!!!! NAME !!!!!!!!!!");
            tableInUse=tableName;
            return tableInUse!=null;
        }
    }
    private static boolean isThisNameAvailable(String name){
        //TODO:detected System_used_name conflict
        boolean contains=false;
        for(String s:tableNames){
            if(s.equals(name)){
                Log.d("check_name","HERE IT IS !!!!!!!!!!");
                contains=true;
                break;
            }
        }
        return contains;
    }

    public boolean CREATE(String tableName){
        if(isThisNameAvailable(tableName)){return false;}
        String sql_create=tableFormat.create.toString().replace("?",tableName);
        db.execSQL(sql_create);
        return true;
    }
    public boolean DROP(String tableName){
        if(!isThisNameAvailable(tableName)){return false;}
        String sql_drop=tableFormat.drop.toString().replace("?",tableName);
        db.execSQL(sql_drop);
        return true;
    }
    public long INSERT(Object[] vals){
    //TODO:change pack implementation
        ContentValues insert_data= toContentValues(vals);
        if(insert_data==null){return -1;}
        return db.insert(tableInUse,null,insert_data);//增
    }
    public long DELETE(String whereClause,String[] whereArgs){

        return db.delete(tableInUse,whereClause,whereArgs);//删
    }
    public long UPDATE(Object[] vals,String whereClause,String[] whereArgs){
        //TODO:change pack implementation
        ContentValues update_data= toContentValues(vals);
        if (update_data==null){return -1;}
        return db.update(tableInUse,update_data,whereClause,whereArgs);//改
    }
    public String[] QUERY(String whereClause,String[] whereArgs){

        List<Object> ret=new ArrayList<>();
        Cursor cs=db.query(
                tableInUse,
                columnNames,
                whereClause,
                whereArgs,
                null,
                null,
                null);//查
        int colNum=columnNames.length;
        Object temp;
        Object[] temp_row;
        while(cs.moveToNext()){//for every row
            temp_row=new Object[colNum];
            for(int i=0;i<colNum;i++){//for every column
                switch (columnTypes[i]){
                    case "int":
                        temp=cs.getInt(i);
                        break;
                    case "boolean":
                        temp=cs.getInt(i)==1;
                        break;
                    case"double":
                        temp=cs.getDouble(i);
                        break;
                    case"String":
                        temp=cs.getString(i);
                        break;
                    default:
                        return null;
                        //type not supported
                }
                temp_row[i]=temp;
            }
            ret.add(PackAndParser.packListToString(temp_row));
            //ret.add(recordBuilder.pack(temp_row));
        }
        cs.close();
        return ret.toArray(new String[0]);
    }

    private ContentValues toContentValues(Object[] vals){
        int len= vals.length;
        if(len!=columnNames.length){return null;}
        ContentValues cv_ret=new ContentValues();
        String name;
        for(int i=0;i<len;i++){
            name=columnNames[i];
            switch (columnTypes[i]){
                case "int":
                    cv_ret.put(name,(int)vals[i]);
                    break;
                case "boolean":
                    cv_ret.put(name,(boolean)vals[i]);
                    break;
                case "double":
                    cv_ret.put(name,(double)vals[i]);
                    break;
                case "String":
                    cv_ret.put(name,(String)vals[i]);
                    break;
                default:
                    return null;
                    //type not supported
            }
        }
        return cv_ret;
    }

}

