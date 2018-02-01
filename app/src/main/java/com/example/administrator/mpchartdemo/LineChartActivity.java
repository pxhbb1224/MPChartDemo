package com.example.administrator.mpchartdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author huangwei E-mail: huangwei@tigerbrokers.com
 * @version 创建时间： 2018/02/01/15:11
 */

public class LineChartActivity extends AppCompatActivity {
  LineChart chartSimple;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_line_chart);
    chartSimple = findViewById(R.id.line_chart_simple);
    initData();
  }

  private void initData() {
    List<Entry> entries = new ArrayList<>();
    for (int i = 0; i < 12; i++) {
      entries.add(new Entry(i, 200 * i * (i)));
    }
    LineDataSet dataSet = new LineDataSet(entries,"Label");
    dataSet.setColor(Color.RED);
    dataSet.setValueTextColor(Color.BLUE);
    LineData lineData = new LineData(dataSet);
    chartSimple.setData(lineData);
    chartSimple.invalidate();
  }
}
