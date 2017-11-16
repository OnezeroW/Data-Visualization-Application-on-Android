
package com.example.first.mydraw;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    //ChartView chartView;
    RelativeLayout layout;
    String value[] = new String[]{
            "37.5", "30.0", "7.5", "22.8", "37.5", "30.0", "41", "22.8", "37.5",
            "30.0", "7.5", "22.8", "11.0", "21.5", "33.9"
    };
    String time[] = new String[]{
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ChartView view = new ChartView(this);
        layout = (RelativeLayout) this.findViewById(R.id.rela);

        view.SetInfo(time,value,getWindowManager().getDefaultDisplay());

        layout.addView(view);
    }

}
