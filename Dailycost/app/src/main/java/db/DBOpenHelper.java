package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dailycost.R;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context, "TEST.db",null,1);
    }

    //初始化项目数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表示实例的表
        //收支信息实例表
        String sql = "create table typetb(" +
                "id integer primary key autoincrement," +
                "typename varchar(10)," +
                "imageId integer," +
                "sImageId integer," +
                "kind integer)";
        db.execSQL(sql);
        insertType(db);
        //创建记账本
        //收支记录表
        sql = "create table accounttb(" +
                "id integer primary key autoincrement," +
                "typename varchar(10)," +
                "sImageId integer," +
                "description varchar(80)," +
                "money float," +
                "time varchar(60)," +
                "year integer," +
                "month integer," +
                "day integer," +
                "kind integer)";
        db.execSQL(sql);
    }

    private void insertType(SQLiteDatabase db) {
        //向typetb表当中一次性写入图片素材描述和id
        String sql = "insert into typetb (typename,imageId,sImageId,kind) values (?,?,?,?)";

        //支出部分
        db.execSQL(sql,new Object[]{"其他", R.mipmap.ic_others,R.mipmap.ic_others_fs,0});
        db.execSQL(sql,new Object[]{"餐饮", R.mipmap.ic_dinning,R.mipmap.ic_dinning_fs,0});
        db.execSQL(sql,new Object[]{"交通", R.mipmap.ic_traffic,R.mipmap.ic_traffic_fs,0});
        db.execSQL(sql,new Object[]{"购物", R.mipmap.ic_shopping,R.mipmap.ic_shopping_fs,0});
        db.execSQL(sql,new Object[]{"服饰", R.mipmap.ic_clothing,R.mipmap.ic_clothing_fs,0});
        db.execSQL(sql,new Object[]{"日用品", R.mipmap.ic_daily_use,R.mipmap.ic_daily_use_fs,0});
        db.execSQL(sql,new Object[]{"娱乐", R.mipmap.ic_entertaining,R.mipmap.ic_entertaining_fs,0});
        db.execSQL(sql,new Object[]{"零食", R.mipmap.ic_snacks,R.mipmap.ic_snacks_fs,0});
        db.execSQL(sql,new Object[]{"烟酒茶", R.mipmap.ic_smk_wine,R.mipmap.ic_smk_wine_fs,0});
        db.execSQL(sql,new Object[]{"学习", R.mipmap.ic_study,R.mipmap.ic_study_fs,0});
        db.execSQL(sql,new Object[]{"医疗", R.mipmap.ic_medical,R.mipmap.ic_medical_fs,0});
        db.execSQL(sql,new Object[]{"住宅", R.mipmap.ic_housing,R.mipmap.ic_housing_fs,0});
        db.execSQL(sql,new Object[]{"水电煤", R.mipmap.ic_utility_fee,R.mipmap.ic_utility_fee_fs,0});
        db.execSQL(sql,new Object[]{"通讯", R.mipmap.ic_phone_bill,R.mipmap.ic_phone_bill_fs,0});
        db.execSQL(sql,new Object[]{"人情", R.mipmap.ic_relations,R.mipmap.ic_relations_fs,0});
        db.execSQL(sql,new Object[]{"游戏充值", R.mipmap.ic_gaming,R.mipmap.ic_gaming_fs,0});
        db.execSQL(sql,new Object[]{"转账", R.mipmap.ic_transfer,R.mipmap.ic_transfer_fs,0});
        db.execSQL(sql,new Object[]{"社交", R.mipmap.ic_social,R.mipmap.ic_social_fs,0});

        //收入部分
        db.execSQL(sql,new Object[]{"其他", R.mipmap.in_others,R.mipmap.in_others_fs,1});
        db.execSQL(sql,new Object[]{"薪资", R.mipmap.in_salary,R.mipmap.in_salary_fs,1});
        db.execSQL(sql,new Object[]{"奖金", R.mipmap.in_bonus,R.mipmap.in_bonus_fs,1});
        db.execSQL(sql,new Object[]{"借入", R.mipmap.in_loan,R.mipmap.in_loan_fs,1});
        db.execSQL(sql,new Object[]{"收款", R.mipmap.in_receive,R.mipmap.in_receive_fs,1});
        db.execSQL(sql,new Object[]{"利息", R.mipmap.in_interest,R.mipmap.in_interest_fs,1});
        db.execSQL(sql,new Object[]{"投资", R.mipmap.in_investment,R.mipmap.in_investment_fs,1});
    }

    //数据库版本更新时调用，本次实践过程不涉及
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
