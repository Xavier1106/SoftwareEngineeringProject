package com.example.test_sqlite.Utils.Property;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.test_sqlite.Pages.MainActivity;
import com.example.test_sqlite.R;
import com.example.test_sqlite.Utils.Database.DBManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//Properties Manager
public class PPManager {

    private static boolean isAvailable=false;
    private static PPManager ppm;
    private static Context mContext;
    private static String resourceName; //SharedPreferences' Name
    private static int    resourceId;   //Properties' Id

    //first initialed in MainActivity
    public static boolean initPPM(Context context){
        if(!isAvailable){
            ppm=new PPManager();//construct singleton
            mContext=context;
            resourceName=null;
            resourceId=-1;
            isAvailable=true;//INIT COMPLETE
            return true;
        }
        return true;
    }
    public static PPManager getPPM(){
        if(isAvailable){return ppm;}//Ensured INIT before INVOKE
        else return null;
    }
    private PPManager(){}

    public boolean useSharedPreferences(String spName){
        try{
            SharedPreferences sp=mContext.getSharedPreferences(spName,MODE_PRIVATE);
            resourceName=spName;
            return true;
        }
        catch (Exception e){
            resourceName=null;
            return false;
        }
    }
    //SharedPreferences 数据保存在应用程序专用的主存空间
    public Object readFromSharedPreferences(String key,String type){
        try{
            if(resourceName==null){return null;}
            SharedPreferences sp=mContext.getSharedPreferences(resourceName,MODE_PRIVATE);
            Object ret;
            switch (type){
                case "int":
                    ret=sp.getInt(key,0);
                    break;
                case "boolean":
                    ret=sp.getBoolean(key,false);
                    break;
                case "float":
                    ret=sp.getFloat(key,0.0f);
                    break;
                case "String":
                    ret=sp.getString(key,"");
                    break;
                default:
                    return null;
            }
            return ret;
        }
        catch (Exception e){
            return "read SharedPreferences failed";
        }
    }

    public boolean writeToSharedPreferences(String key,Object val){
        try{
            SharedPreferences sp=mContext.getSharedPreferences("test",MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putInt("just_try",999);
            editor.apply();//commit()
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean useProperties(int ppId){
        try {
            Properties pptest=new Properties();
            InputStream is=mContext.getResources().openRawResource(ppId);
            pptest.load(is);
            is.close();
            resourceId=ppId;
            return true;
        } catch (IOException e) {
            resourceId=-1;
            return false;
        }
    }
    //res.raw 路径下的配置文件,只读 用于启动时初始化全局设置
    public String readFromProperties(String key){
        try {
            /* wrong code: only available in runtime
             * properties.put("maybe","hello");
             * properties.getProperty("maybe");
             * should: read hard-code settings only
             */
            if (resourceId==-1){return null;}
            Properties properties=new Properties();
            InputStream is=mContext.getResources().openRawResource(resourceId);
            properties.load(is);
            String ret=properties.getProperty(key);
            is.close();
            return ret;
        } catch (IOException e) {
            return "read Properties failed";
        }
    }
}
