package com.cos.busanbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ListActivity2 extends AppCompatActivity {
    private static final String TAG = "List";
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        Log.d(TAG, "onCreate: "+MyData.data);
        init();
        initLr();
    }
    private void init(){
        bottomNavigationView= findViewById(R.id.bottomNavigation);
    }
    private void initLr(){
        bottomNavigationView.setOnItemSelectedListener((item -> {
            switch (item.getItemId()){
                case R.id.search_icon:
                    Log.d(TAG, "initLr: search 클릭됨" );
                    Intent intent =new Intent(
                            ListActivity2.this,
                            ListActivity2.class
                    );
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.map_icon:
                    Log.d(TAG, "initLr: map 클릭됨" );
                    Intent intent1 =new Intent(
                            ListActivity2.this,
                            MainActivity.class
                    );
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    finish();
                    break;
            }
            return true;
        }));
    }
}