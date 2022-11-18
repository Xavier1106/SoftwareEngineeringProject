package db;

//数据库增删改查  主要对于表中的内容进行操作，增删改查

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import utils.FloatUtils;

public class DBManager {

    private static SQLiteDatabase db;

    //初始化数据库对象
    public static void initDB(Context context){
        DBOpenHelper helper = new DBOpenHelper(context);//得到帮助类对象
        db = helper.getWritableDatabase();              //得到数据库对象
    }

    //读取数据库中的数据，写入内存集合里
    //kind 表示支出或收入

    public static List<TypeBean>getTypeList(int kind) {
        List<TypeBean> list = new ArrayList<>();
        //读取typetb表当中的数据
        String sql = "select * from typetb where kind = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{kind+""});
        while (cursor.moveToNext()){
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            TypeBean typeBean = new TypeBean(id, typename, imageId, sImageId, kind);
            list.add(typeBean);
        }
        cursor.close();
        return list;
    }

    //向记账表当中插入一条元素
    public static void insertItemToAccounttb(AccountBean bean){
        ContentValues values = bean.toContentValues();
        db.insert("accounttb",null,values);
        //Log.d("TAG", "insertItemToAccounttb");
    }

    //获取记账本中某一天的所有数据
    public static List<AccountBean>getAccountListOneDayFromAccounttb(int year,int month,int day){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? and day=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + ""});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, description, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        cursor.close();
        return list;
    }

    //获取记账表当中某一月的所有支出或者收入情况
    public static List<AccountBean>getAccountListOneMonthFromAccounttb(int year,int month){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + ""});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, description, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        cursor.close();
        return list;
    }

    //获取某一天的支付或者收入的总金额
    public static float getSumMoneyOneDay(int year,int month,int day,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=?";
        Cursor cursor = db.rawQuery(sql,new String[]{year + "", month + "", day + "", kind + ""});
        if (cursor.moveToNext()) {
            total = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        return total;
    }

    //获取某一月的支付或者收入的总金额
    public static float getSumMoneyOneMonth(int year,int month,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql,new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToNext()) {
            total = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        return total;
    }

    //统计某月份支出或者收入情况有多少条  kind:收入=1,支出=0
    public static int getCountItemOneMonth(int year,int month,int kind){
        int total = 0;
        String sql = "select count(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("count(money)"));
        }
        cursor.close();
        return total;
    }

    //获取某一年的支出或者收入的总金额
    public static float getSumMoneyOneYear(int year,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", kind + ""});
        if (cursor.moveToFirst()) {
            total = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        return total;
    }

    //根据传入的id，删除accounttb表当中的一条数据
    public static int deleteItemFromAccounttbById(int id){
        return db.delete("accounttb", "id=?", new String[]{id + ""});
    }

    //根据备注搜索收入或者支出的情况列表
    public static List<AccountBean> getAccountListByRemarkFromAccounttb(String description) {
        List<AccountBean> list = new ArrayList<>();
        String sql = "select * from accounttb where description like '%"+description+"%'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String bz = cursor.getString(cursor.getColumnIndex("description"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, bz, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        cursor.close();
        return list;
    }

    //查询记账的表当中有几个年份信息
    public static List<Integer>getYearListFromAccounttb(){
        List<Integer>list = new ArrayList<>();
        String sql = "select distinct(year) from accounttb order by year asc";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            list.add(year);
        }
        cursor.close();
        return list;
    }

    //删除accounttb表格当中的所有数据
    public static void deleteAllAccount(){
        String sql = "delete from accounttb";
        db.execSQL(sql);
    }

    //查询指定年份和月份的收入或者支出每一种类型的总钱数
    public static List<ChartItemBean>getChartListFromAccounttb(int year,int month,int kind){
        List<ChartItemBean>list = new ArrayList<>();
        float sumMoneyOneMonth = getSumMoneyOneMonth(year, month, kind);  //求出支出或者收入总钱数
        String sql = "select typename,sImageId,sum(money)as total from accounttb where year=? and month=? and kind=? group by typename order by total desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        while (cursor.moveToNext()) {
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            float total = cursor.getFloat(cursor.getColumnIndex("total"));
            //计算所占百分比  total /sumMonth
            float ratio = FloatUtils.div(total,sumMoneyOneMonth);
            ChartItemBean bean = new ChartItemBean(sImageId, typename, ratio, total);
            list.add(bean);
        }
        cursor.close();
        return list;
    }

    //获取这个月当中某一天收入支出最大的金额，金额是多少
    public static float getMaxMoneyOneDayInMonth(int year,int month,int kind){
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=? group by day order by sum(money) desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            return cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        return 0;
    }

    //根据指定月份每一日收入或者支出的总钱数的集合
    //返回柱状图信息List,在数据统计页面生成时调用
    public static List<BarChartItemBean>getSumMoneyOneDayInMonth(int year,int month,int kind){
        String sql = "select day,sum(money) from accounttb where year=? and month=? and kind=? group by day";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        List<BarChartItemBean>list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            float smoney = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            BarChartItemBean itemBean = new BarChartItemBean(year, month, day, smoney);
            list.add(itemBean);
        }
        cursor.close();
        return list;
    }

    public static void updateItemFromAccount(AccountBean bean){

        ContentValues values=bean.toContentValues();
        db.update("accounttb",values,"id=?",new String[]{bean.getId()+""});
    }

}
