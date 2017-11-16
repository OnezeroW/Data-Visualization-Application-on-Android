package com.example.first.visualization;

/**
 * Created by wanjialin on 2015/4/26.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;


public class MyDrawBar extends Activity {

    RelativeLayout layout;

    public String id[];
    public String value[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        BarChartView view = new BarChartView(this);
        layout = (RelativeLayout) this.findViewById(R.id.canvas);

        Bundle b = this.getIntent().getExtras();
        id = b.getStringArray("id");
        value = b.getStringArray("value");

        view.SetInfo(id, value, getWindowManager().getDefaultDisplay());

        layout.addView(view);
    }

}