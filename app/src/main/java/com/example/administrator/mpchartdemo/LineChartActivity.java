package com.example.administrator.mpchartdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangwei E-mail: huangwei@tigerbrokers.com
 * @version 创建时间： 2018/02/01/15:11
 */

public class LineChartActivity extends AppCompatActivity implements View.OnClickListener {
  LineChart chartSimple;

  Button btnTestControl;
  CheckBox checkBox1, checkBox2, checkBox3;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_line_chart);
    chartSimple = findViewById(R.id.line_chart_simple);
    btnTestControl = findViewById(R.id.btn_test_control);
    btnTestControl.setOnClickListener(this);
    findViewById(R.id.btn_next_step).setOnClickListener(this);
    findViewById(R.id.btn_add_limit_line).setOnClickListener(this);
    checkBox1 = findViewById(R.id.checkbox_enable);
    checkBox2 = findViewById(R.id.checkbox_left_y);
    checkBox3 = findViewById(R.id.checkbox_right_y);
    checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (checkBox3 != null && checkBox3.isChecked()) {
          checkBox2.setChecked(false);
          Toast.makeText(LineChartActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
      }
    });
    checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (checkBox2 != null && checkBox2.isChecked()) {
          checkBox3.setChecked(false);
          Toast.makeText(LineChartActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
      }
    });
    initData();
  }

  private void initData() {
    List<Entry> entries = new ArrayList<>();
    for (int i = 0; i < 12; i++) {
      entries.add(new Entry(i, 200 * i * (i)));
    }
    LineDataSet dataSet = new LineDataSet(entries, "Label");
    dataSet.setColor(Color.RED);
    dataSet.setValueTextColor(Color.BLUE);
    LineData lineData = new LineData(dataSet);
    chartSimple.setData(lineData);
    chartSimple.invalidate();
  }

  private void testControl() {
    boolean enable = checkBox1 != null && checkBox1.isChecked();
    boolean isLeftY = checkBox2 != null && checkBox2.isChecked() && !checkBox3.isChecked();
    boolean isRightY = checkBox3 != null && checkBox3.isChecked() && !isLeftY;
    AxisBase axisBase =
        isLeftY ? chartSimple.getAxisLeft() : isRightY ? chartSimple.getAxisRight() : chartSimple.getXAxis();
    Control currentControl = Control.getControl(btnTestControl.getText().toString());
    if (currentControl != null) {
      Control.doMethod(axisBase, enable, currentControl);
      chartSimple.invalidate();
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_test_control:
        testControl();
        break;
      case R.id.btn_next_step:
        changeText();
      case R.id.btn_add_limit_line:
        addLimitLine();
      default:
        break;
    }
  }

  private void addLimitLine() {
    LimitLine ll = new LimitLine(7500, "Critical Blood Pressure");
    ll.setLineColor(Color.RED);
    ll.setLineWidth(4f);
    ll.setTextColor(Color.BLACK);
    ll.setTextSize(12f);
    YAxis axis = checkBox2.isChecked() ? chartSimple.getAxisLeft() : chartSimple.getAxisRight();
    axis.addLimitLine(ll);
    chartSimple.invalidate();
  }

  private void changeText() {
    if (btnTestControl == null) {
      return;
    }
    int type = Control.values().length;
    Control[] values = Control.values();
    String defaultString = getString(R.string.btn_text_test_control);
    String currentString = btnTestControl.getText().toString();
    if (TextUtils.equals(defaultString, currentString)) {
      currentString = values[0].toString();
    } else {
      for (int i = 0; i < type; i++) {
        if (TextUtils.equals(values[i].toString().toLowerCase(), currentString.toLowerCase())) {
          currentString = values[(i + 1) % type].toString();
          break;
        }
      }
    }
    btnTestControl.setText(currentString);
  }

  enum Control {
    setEnabled, setDrawLabels, setDrawAxisLine, setDrawGridLines;

    @Override
    public String toString() {
      return this.name();
    }

    static Control getControl(String str) {
      if (TextUtils.isEmpty(str)) {
        return null;
      }
      for (Control find : Control.values()) {
        if (TextUtils.equals(find.toString(), str)) {
          return find;
        }
      }
      return null;
    }

    static void doMethod(AxisBase axisBase, boolean enable, Control control) {
      if (axisBase == null) {
        return;
      }
      switch (control) {
        case setEnabled:
          axisBase.setEnabled(enable);
          break;
        case setDrawLabels:
          axisBase.setDrawLabels(enable);
          break;
        case setDrawAxisLine:
          axisBase.setDrawAxisLine(enable);
          break;
        case setDrawGridLines:
          axisBase.setDrawGridLines(enable);
          break;
        default:
          break;
      }
    }
  }
}
