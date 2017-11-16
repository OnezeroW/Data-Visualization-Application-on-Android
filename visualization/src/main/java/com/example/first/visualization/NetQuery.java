package com.example.first.visualization;

/**
 * Created by wanjialin on 2015/4/20.
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NetQuery extends Thread{
    //public String Query(String url, String id){
    public String url;
    public String time;
    //public String result = null;
    public String id[];
    public String value[];
    ArrayList<String> id_list = new ArrayList<>();
    ArrayList<String> value_list = new ArrayList<>();

    private CountDownLatch latch;

    public NetQuery(CountDownLatch latch, String url, String time)
    {
        this.latch = latch;
        this.url = url;
        this.time = time;
    }
    public void run(){
        HttpClient client;
        HttpEntity entity;
        HttpGet get;
        HttpResponse response;
        //List<NameValuePair> pairList = new ArrayList<>();

        //NameValuePair pair1 = new BasicNameValuePair("time", time);
        //pairList.add(pair1);

        //url = url + URLEncodedUtils.format(pairList, HTTP.UTF_8);

        url = url + "?time=" + time;

        try {
            client = new DefaultHttpClient();
            get = new HttpGet(url);
            response = client.execute(get);

            //if (response == null) {result = "null"; return;}

                InputStream is;
                BufferedReader reader;
                StringBuilder sb;
                String line;

                entity = response.getEntity();
                is = entity.getContent();

                //if (is == null) {result = "null"; return;};
                reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                //result = new JSONObject(new JSONTokener(reader));

                sb = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                is.close();
                //result = sb.toString();
            JSONArray json = new JSONArray(sb.toString());
            //JSONObject json = new JSONObject(sb.toString());
            //result = json.getString("value");
            for (int i = 0; i < json.length(); i++) {
                JSONObject temp = (JSONObject) json.get(i);
                id_list.add(temp.getString("id"));
                value_list.add(temp.getString("value"));
            }
            //id = id_list.toArray(new String[id_list.size()]);
            //value = value_list.toArray(new String[value_list.size()]);
            id = new String[id_list.size()];
            id_list.toArray(id);
            value = new String[value_list.size()];
            value_list.toArray(value);

        } catch (Exception e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
        //NetQuery.sleep(1000);
        this.latch.countDown();

    }

}
