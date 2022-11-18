package db;

//描述记录一条数据的相关内容类
//收支记录表

import android.content.ContentValues;

import java.io.Serializable;

public class AccountBean implements Serializable {
    private int id;
    private String typename;            //类型
    private int sImageId;               //被选中类型图片
    private String description;         //备注
    private float money;                //价格
    private String time;                //时间戳
    private int year,month,day;         //记录日期(便于加速数据库查询)
    private int kind;                   //类型  收入=1   支出=0

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getsImageId() {
        return sImageId;
    }

    public void setsImageId(int sImageId) {
        this.sImageId = sImageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public AccountBean() {}
    public AccountBean(AccountBean data){
        this.id = data.id;
        this.typename = data.typename;
        this.sImageId = data.sImageId;
        this.description = data.description;
        this.money = data.money;
        this.time = data.time;
        this.year = data.year;
        this.month = data.month;
        this.day = data.day;
        this.kind = data.kind;
    }


    public AccountBean(int id, String typename, int sImageId, String description, float money, String time, int year, int month, int day, int kind) {
        this.id = id;
        this.typename = typename;
        this.sImageId = sImageId;
        this.description = description;
        this.money = money;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
        this.kind = kind;
    }

    public ContentValues toContentValues(){
        ContentValues values=new ContentValues();
        values.put("typename",typename);
        values.put("sImageId",sImageId);
        values.put("description",description);
        values.put("money",money);
        values.put("time",time);
        values.put("year",year);
        values.put("month",month);
        values.put("day",day);
        values.put("kind",kind);
        return values;
    }
}
