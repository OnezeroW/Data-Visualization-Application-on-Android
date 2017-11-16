package com.example.first.visualization;

/**
 * Created by wanjialin on 2015/4/26.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MyDraw extends Activity {

    //ChartView chartView;
    RelativeLayout layout;

    //ArrayList<String> id = new ArrayList<>();
    //ArrayList<String> value = new ArrayList<>();

    public String id[];
    public String value[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ChartView view = new ChartView(this);
        layout = (RelativeLayout) this.findViewById(R.id.canvas);

        Bundle b = this.getIntent().getExtras();
        id = b.getStringArray("id");
        value = b.getStringArray("value");

        view.SetInfo(id, value, getWindowManager().getDefaultDisplay());

        layout.addView(view);
        //AlertDialog.Builder alert = new AlertDialog.Builder(MyDraw.this);
    }

}