package com.cos.busanbus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mBusEditText;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBusEditText = findViewById(R.id.busLineNum);
        mResult = findViewById(R.id.result);


        BusDb db = Room.databaseBuilder(this,BusDb.class,"Bus-db")
                .allowMainThreadQueries()
                .build();

        mResult.setText(db.busDao().findAll().toString());

        findViewById(R.id.button).setOnClickListener((view)->{
            db.busDao().findBusNum(new Bus(mBusEditText.getText().toString()));
            mResult.setText(db.busDao().findAll().toString());
        });
    }
}