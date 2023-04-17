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
        db.execSQL(sql,new Object[]{"其他", R.mipmap.ic_qita,R.mipmap.ic_qita_fs,0});
        db.execSQL(sql,new Object[]{"餐饮", R.mipmap.ic_canyin,R.mipmap.ic_canyin_fs,0});
        db.execSQL(sql,new Object[]{"交通", R.mipmap.ic_jiaotong,R.mipmap.ic_jiaotong_fs,0});
        db.execSQL(sql,new Object[]{"购物", R.mipmap.ic_gouwu,R.mipmap.ic_gouwu_fs,0});
        db.execSQL(sql,new Object[]{"服饰", R.mipmap.ic_fushi,R.mipmap.ic_fushi_fs,0});
        db.execSQL(sql,new Object[]{"日用品", R.mipmap.ic_riyongpin,R.mipmap.ic_riyongpin_fs,0});
        db.execSQL(sql,new Object[]{"娱乐", R.mipmap.ic_yule,R.mipmap.ic_yule_fs,0});
        db.execSQL(sql,new Object[]{"零食", R.mipmap.ic_lingshi,R.mipmap.ic_lingshi_fs,0});
        db.execSQL(sql,new Object[]{"烟酒茶", R.mipmap.ic_yanjiu,R.mipmap.ic_yanjiu_fs,0});
        db.execSQL(sql,new Object[]{"学习", R.mipmap.ic_xuexi,R.mipmap.ic_xuexi_fs,0});
        db.execSQL(sql,new Object[]{"医疗", R.mipmap.ic_yiliao,R.mipmap.ic_yiliao_fs,0});
        db.execSQL(sql,new Object[]{"住宅", R.mipmap.ic_zhufang,R.mipmap.ic_zhufang_fs,0});
        db.execSQL(sql,new Object[]{"水电煤", R.mipmap.ic_shuidianfei,R.mipmap.ic_shuidianfei_fs,0});
        db.execSQL(sql,new Object[]{"通讯", R.mipmap.ic_tongxun,R.mipmap.ic_tongxun_fs,0});
        db.execSQL(sql,new Object[]{"人情", R.mipmap.ic_renqingwanglai,R.mipmap.ic_renqingwanglai_fs,0});
        db.execSQL(sql,new Object[]{"游戏充值", R.mipmap.ic_youxi,R.mipmap.ic_youxi_fs,0});
        db.execSQL(sql,new Object[]{"转账", R.mipmap.ic_zhuanzhang,R.mipmap.ic_zhuanzhang_fs,0});
        db.execSQL(sql,new Object[]{"社交", R.mipmap.ic_shejiao,R.mipmap.ic_shejiao_fs,0});

        //收入部分
        db.execSQL(sql,new Object[]{"其他", R.mipmap.in_qt,R.mipmap.in_qt_fs,1});
        db.execSQL(sql,new Object[]{"薪资", R.mipmap.in_xinzi,R.mipmap.in_xinzi_fs,1});
        db.execSQL(sql,new Object[]{"奖金", R.mipmap.in_jiangjin,R.mipmap.in_jiangjin_fs,1});
        db.execSQL(sql,new Object[]{"借入", R.mipmap.in_jieru,R.mipmap.in_jieru_fs,1});
        db.execSQL(sql,new Object[]{"收款", R.mipmap.in_shoukuan,R.mipmap.in_shoukuan_fs,1});
        db.execSQL(sql,new Object[]{"利息", R.mipmap.in_lixi,R.mipmap.in_lixi_fs,1});
        db.execSQL(sql,new Object[]{"投资", R.mipmap.in_touzi,R.mipmap.in_touzi_fs,1});
    }

    //数据库版本更新时调用，本次实践过程不涉及
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
