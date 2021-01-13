package com.mslji.mybluetooth.first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mslji.mybluetooth.DashBoardActivity;
import com.mslji.mybluetooth.Database.MyDbHandler;
import com.mslji.mybluetooth.Database.Temp;
import com.mslji.mybluetooth.ListActivity;
import com.mslji.mybluetooth.R;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

public class FirstPageActivity extends AppCompatActivity {

    public AppCompatButton btnone,btntwo;
    public MyDbHandler myDbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        try {
            SharedPreferences sharedPreferenceslucky = getSharedPreferences("datanewlucky",MODE_PRIVATE);
//            Gson gson = new Gson();
            String datanewnew = sharedPreferenceslucky.getString("datakey","777");
//            Type type = new TypeToken<List<String>>() {
//            }.getType();
//            List<String> arrPackageData = gson.fromJson(datanewnew, type);
            Log.d("llloijhgfxcggfcv","row data row = = "+datanewnew);
            Log.d("llloijhgfxcggfcv","row Size = = "+datanewnew.length());
        }catch (Exception e){
            e.printStackTrace();
        }




        SharedPreferences sharedPreferences =getSharedPreferences("lucky",MODE_PRIVATE);
        SharedPreferences.Editor edit =sharedPreferences.edit();
        edit.putBoolean("check",true);
        edit.apply();

        myDbHandler = new MyDbHandler(getApplicationContext(),"userbd",null,1);

        Temp.setMyDbHandler(myDbHandler);

        btnone = (AppCompatButton)findViewById(R.id.onebtn);
        btntwo = (AppCompatButton)findViewById(R.id.twobtn);



        btnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnone.setBackgroundColor(Color.parseColor("#c38fff"));
                btnone.setTextColor(Color.parseColor("#2c2039"));
                btntwo.setBackgroundColor(Color.parseColor("#1e1e1e"));
                btntwo.setTextColor(Color.parseColor("#777777"));
                Intent intent = new Intent(FirstPageActivity.this, DashBoardActivity.class);
                startActivity(intent);
            }
        });
        btntwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnone.setBackgroundColor(Color.parseColor("#1e1e1e"));
                btnone.setTextColor(Color.parseColor("#777777"));
                btntwo.setBackgroundColor(Color.parseColor("#c38fff"));
                btntwo.setTextColor(Color.parseColor("#2c2039"));
                Intent intent = new Intent(FirstPageActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }
}