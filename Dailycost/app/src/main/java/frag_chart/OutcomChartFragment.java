package frag_chart;

import android.graphics.Color;
import android.view.View;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import db.BarChartItemBean;
import db.DBManager;

public class OutcomChartFragment extends BaseChartFragment {

    int kind = 0;
    @Override
    public void onResume() {
        super.onResume();
        loadData(year,month,kind);
    }


    @Override
    protected void setAxisData(int year, int month) {
        List<IBarDataSet> sets = new ArrayList<>();
        //获取这个月每天的支出总金额，
        List<BarChartItemBean> costList = DBManager.getSumMoneyOneDayInMonth(year, month, kind);
        if (costList.size() == 0) {
            barChart.setVisibility(View.GONE);
            chartTv.setVisibility(View.VISIBLE);
        }else{
            barChart.setVisibility(View.VISIBLE);
            chartTv.setVisibility(View.GONE);
            List<BarEntry> barEntries = new ArrayList<>();     //设置有多少根柱子
            //初始化每一根柱子，添加到柱状图当中
            for (int i = 0; i < 31; i++) {
                BarEntry entry = new BarEntry(i, 0.0f);
                barEntries.add(entry);
            }
            for (int i = 0; i < costList.size(); i++) {
                BarChartItemBean itemBean = costList.get(i);
                int day = itemBean.getDay();                    //获取日期
                int xIndex = day-1;                             //根据天数，获取x轴的位置
                BarEntry barEntry = barEntries.get(xIndex);
                barEntry.setY(itemBean.getSummoney());
            }
            BarDataSet barDataSet = new BarDataSet(barEntries, "");
            barDataSet.setValueTextColor(Color.BLACK);         //值的颜色
            barDataSet.setValueTextSize(8f);                   //值的大小
            barDataSet.setColor(Color.RED);                    //柱子的颜色

            //设置柱子上数据显示的格式
            barDataSet.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> {
                //此处的value默认保存一位小数
                if (value==0.0) {return "";}
                return value + "";
            });
            sets.add(barDataSet);

            BarData barData = new BarData(sets);
            barData.setBarWidth(0.2f);      //设置柱子的宽度
            barChart.setData(barData);
        }
    }

    @Override
    protected void setYAxis(int year, int month) {
        //获取本月收入最高的一天为多少，将他设定为y轴的最大值
        float maxMoney = DBManager.getMaxMoneyOneDayInMonth(year, month, kind);
        float max = (float) Math.ceil(maxMoney);        //将最大金额向上取整

        YAxis yAxis_right = barChart.getAxisRight();    //设置y轴
        yAxis_right.setAxisMaximum(max);                //设置y轴的最大值
        yAxis_right.setAxisMinimum(0f);                 //设置y轴的最小值
        yAxis_right.setEnabled(false);                  //不显示右边的y轴

        YAxis yAxis_left = barChart.getAxisLeft();
        yAxis_left.setAxisMaximum(max);
        yAxis_left.setAxisMinimum(0f);
        yAxis_left.setEnabled(false);                   //设置不显示图例

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
    }

    @Override
    public void setDate(int year, int month) {
        super.setDate(year, month);
        loadData(year,month,kind);
    }
}
