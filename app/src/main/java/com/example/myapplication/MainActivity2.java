package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    RequestQueue queque;
    String url = "https://jsonplaceholder.typicode.com/users";
    TextView textView2;
    List<String> datos = new ArrayList<String>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        queque = Volley.newRequestQueue(this);
        textView2 = (TextView) findViewById(R.id.textView2);
        listView = (ListView) findViewById(R.id.listView);

        String d = getIntent().getStringExtra("dato");
        textView2.setText(R.string.app_name +", "+d);

        GetApiData();
    }

    public void GetApiData(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0){
                    for (int i = 0; i < response.length(); i++){
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Objetos o  = new Objetos();
                            o.setId(obj.getInt("id"));
                            o.setName(obj.getString("name"));
                            o.setUsername(obj.getString("username"));
                            o.setEmail(obj.getString("email"));

                            datos.add(String.valueOf(obj.getInt("id"))+" - "+obj.getString("name")+" - "+ obj.getString("username")+" - "+obj.getString("email"));

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item, datos);
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queque.add(request);
    }
}