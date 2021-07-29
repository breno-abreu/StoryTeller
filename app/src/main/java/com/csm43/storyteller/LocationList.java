package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LocationList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
    }

    public void showLocationActivity(View v){
        Intent parent = getIntent();
        String title = parent.getStringExtra("TITLE");
        Intent intent = new Intent(this, Location.class);
        intent.putExtra("TITLE", title);
        startActivity(intent);
    }
}