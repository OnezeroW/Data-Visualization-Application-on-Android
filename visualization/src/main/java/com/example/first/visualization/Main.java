package com.example.first.visualization;

import java.util.concurrent.CountDownLatch;
import java.lang.InterruptedException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class Main extends Activity {
    private Button query_line;
    private Button query_bar;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setupViewComponent();
    }

    private void setupViewComponent() {
        query_line = (Button) findViewById(R.id.query_button_line);
        query_bar = (Button) findViewById(R.id.query_button_bar);
        input = (EditText) findViewById(R.id.input_txt);

        query_line.setOnClickListener(queryOnClick);
        query_bar.setOnClickListener(queryOnClick);
    }

    private Button.OnClickListener queryOnClick = new Button.OnClickListener() {

        public void onClick(View v) {

            String time = input.getText().toString();

            String id[];
            String value[];

            if (time.equals("")) {
                Toast t = Toast.makeText(Main.this, "请输入查询参数",
                        Toast.LENGTH_LONG);
                t.show();
                return;
            }

            //String url = "http://192.168.1.139/firstpart/getuser.php";
            //String url = "http://192.168.5.119/firstpart/getuser.php";
            //String url = "http://172.17.23.30/firstpart/getuser.php";
            String url = "http://192.168.191.1/firstpart/getuser.php";
            //String url = "http://192.168.191.6/firstpart/getuser.php";
            //String url = "http://192.168.5.109/firstpart/getuser.php";
            //String url = "http://127.0.0.1/firstpart/getuser.php";
            //String url = "http://localhost/firstpart/getuser.php";

            CountDownLatch latch = new CountDownLatch(1);
            NetQuery query = new NetQuery(latch, url, time);

            //ProgressDialog.show(Main.this,"","Loading. Please wait…",true);

            query.start();
            //query.join();

            try
            {
                latch.await();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            id = query.id;
            value = query.value;

            //Intent intent = new Intent(Main.this, MyDraw.class);
            Intent intent = new Intent();
            if(v == query_line){
                intent.setClass(Main.this, MyDraw.class);     //跳到line chart的activity
            }
            else if(v == query_bar){
                intent.setClass(Main.this, MyDrawBar.class);    //跳到bar chart的activity
            }

            Bundle b = new Bundle();
            b.putStringArray("id",id);

            b.putStringArray("value",value);
            intent.putExtras(b);

            Main.this.startActivity(intent);
        }
    };
}