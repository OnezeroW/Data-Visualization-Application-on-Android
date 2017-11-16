package com.example.first.myfirstapplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity {
    private Button query;
    private EditText input;
    private TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setupViewComponent();
    }

    private void setupViewComponent() {
        query = (Button) findViewById(R.id.query_button);
        input = (EditText) findViewById(R.id.input_txt);
        output = (TextView) findViewById(R.id.output_txt);

        query.setOnClickListener(queryOnClick);
    }

    private Button.OnClickListener queryOnClick = new Button.OnClickListener() {
        public void onClick(View v) {
            String id = input.getText().toString();

            if (id.equals("")) {
                Toast t = Toast.makeText(Main.this, "请输入误差",
                        Toast.LENGTH_LONG);
                t.show();
                return;
            }

            String url = "http://172.17.23.30/firstpart/getuser.php";

            HttpClient client;
            HttpEntity entity;
            HttpPost post;
            HttpResponse response = null;
            List<NameValuePair> pairList = new ArrayList<NameValuePair>();

            NameValuePair pair1 = new BasicNameValuePair("input", id);
            pairList.add(pair1);

            try {
                client = new DefaultHttpClient();
                entity = new UrlEncodedFormEntity(pairList, HTTP.UTF_8);
                post = new HttpPost(url);
                post.setEntity(entity);

                response = client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            entity = response.getEntity();
            InputStream is;
            BufferedReader reader;
            StringBuilder sb;
            String line, result = null;

            // 获得JSON格式对象字符串
            try {
                is = entity.getContent();
                reader = new BufferedReader(new InputStreamReader(is, "utf-8"),
                        8);
                sb = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                output.setText(result);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!result.equals("")) {
                Toast t = Toast.makeText(Main.this, "查询错误！",
                        Toast.LENGTH_LONG);
                t.show();
                return;
            }
        }
    };
}