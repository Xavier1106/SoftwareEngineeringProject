package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FloatUtils {

    //进行除法运算，保留4位小数
    public static float div(float v1,float v2){
        float v3 = v1/v2;
        BigDecimal b1 = new BigDecimal(v3);
        return b1.setScale(4, RoundingMode.HALF_UP).floatValue();
    }

    //将浮点数类型，转换成百分比显示形式
    public static String ratioToPercent(float val){
        float v = val*100;
        BigDecimal b1 = new BigDecimal(v);
        float v1 = b1.setScale(2, RoundingMode.HALF_UP).floatValue();
        return v1+"%";
    }
}
