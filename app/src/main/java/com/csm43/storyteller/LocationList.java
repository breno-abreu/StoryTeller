package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class LocationList extends AppCompatActivity {
    private String storyTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        storyTitle = ((FileManager)this.getApplication()).getCurrentStory();

        ArrayList<String> names = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) +
                "/Histórias/" + storyTitle + "/Lugares");

        if(names != null) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.locationsLinearLayout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            for (String name : names) {
                Button button = new Button(this);
                button.setText(name);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLocationActivity(name);
                    }
                });
                ll.addView(button, lp);
            }
        }
    }

    public void showLocationActivity(View v){
        Intent intent = new Intent(this, Location.class);
        startActivity(intent);
    }

    public void showLocationActivity(String locationName){
        Intent intent = new Intent(this, Location.class);
        intent.putExtra("NAME", locationName);
        startActivity(intent);
    }
}