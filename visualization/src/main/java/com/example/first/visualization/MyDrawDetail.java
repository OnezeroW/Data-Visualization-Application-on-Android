package com.example.first.visualization;

/**
 * Created by wanjialin on 2015/6/1.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MyDrawDetail extends Activity {

    //ChartView chartView;
    RelativeLayout layout;

    //ArrayList<String> id = new ArrayList<>();
    //ArrayList<String> value = new ArrayList<>();

    public String id_detail[];
    public String value_detail[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        DetailChartView view = new DetailChartView(this);
        layout = (RelativeLayout) this.findViewById(R.id.canvas);

        Bundle b = this.getIntent().getExtras();
        id_detail = b.getStringArray("id_detail");
        value_detail = b.getStringArray("value_detail");

        view.SetInfo(id_detail, value_detail, getWindowManager().getDefaultDisplay());

        layout.addView(view);
        //AlertDialog.Builder alert = new AlertDialog.Builder(MyDraw.this);
    }

}