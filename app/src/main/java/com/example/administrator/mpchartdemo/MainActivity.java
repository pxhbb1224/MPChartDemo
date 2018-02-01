package com.example.administrator.mpchartdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ListView listView = findViewById(R.id.base_list_view);
    List<String> array = Arrays.asList(getResources().getStringArray(R.array.base));
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.view_base_list_item, R.id.base_list_item, array);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(this);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    switch (position) {
      case 0:
        startActivity(new Intent(this, LineChartActivity.class));
        break;
      default:
        break;
    }
  }
}
