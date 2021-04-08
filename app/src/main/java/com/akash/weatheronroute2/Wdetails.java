package com.akash.weatheronroute2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;

public class Wdetails extends AppCompatActivity implements Serializable {
    RecyclerView recyclerView;
    WdetailsAdapter wdetailsAdapter;
    ArrayList<WeatherObject> filelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdetails);
        filelist =  (ArrayList<WeatherObject>)getIntent().getSerializableExtra("MY_LIST");

        recyclerView = findViewById(R.id.recyclerview);

        wdetailsAdapter = new WdetailsAdapter(this,filelist);
        recyclerView.setAdapter(wdetailsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
