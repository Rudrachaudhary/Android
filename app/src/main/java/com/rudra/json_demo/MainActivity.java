package com.rudra.json_demo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;

    private CoordinatorLayout mCLayout;
    private Button mButtonDo;
    private TextView mTextView;
    private String mJSONURLString = "https://jsonplaceholder.typicode.com/posts/";

    DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         dbManager=new DBManager(this);

        mContext = getApplicationContext();
        mActivity = MainActivity.this;

        mCLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mButtonDo = (Button) findViewById(R.id.btn_do);
        mTextView = (TextView) findViewById(R.id.tv);

        mButtonDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTextView.setText("");

                RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET,
                        mJSONURLString,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                try{
                                    for(int i=0;i<response.length();i++){

                                        JSONObject student = response.getJSONObject(i);

                                        String userId = student.getString("userId");
                                        String id = student.getString("id");
                                        String title = student.getString("title");
                                        String body = student.getString("body");
                                        ContentValues values = new ContentValues();
                                        values.put(DBManager.ColUserId,userId);
                                        values.put(DBManager.ColId,id);
                                        values.put(DBManager.ColTitle,title);
                                        values.put(DBManager.ColBody,body);
                                        long Id=dbManager.Insert(values);
                                        if (Id>0)
                                            Toast.makeText(getApplicationContext(),"user id:"+Id,Toast.LENGTH_LONG).show();
                                        else
                                            Toast.makeText(getApplicationContext(),"can't insert!"+Id,Toast.LENGTH_LONG).show();


                                        mTextView.append("userId:"+userId +" " +"id:" +id +"\ntitle: " + title+"\nbody: " + body);
                                        mTextView.append("\n\n");
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){
                                Snackbar.make(
                                        mCLayout,
                                        "Error...",
                                        Snackbar.LENGTH_LONG
                                ).show();
                            }
                        }
                );

                requestQueue.add(jsonArrayRequest);
            }
        });
    }
}
