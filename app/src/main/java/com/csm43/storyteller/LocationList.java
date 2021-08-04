package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class LocationList extends AppCompatActivity {
    private String storyTitle;

    private RecyclerView storyRecyclerView;
    private StoryAdapter storyAdapter;
    private RecyclerView.LayoutManager storyLayoutManager;
    private TextView titleBottom;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        //storyTitle = ((FileManager)this.getApplication()).getCurrentStory();
        Intent intent = getIntent();
        storyTitle = intent.getStringExtra("TITLE");

        buildRecyclerView();

        titleBottom = findViewById(R.id.locationListTitle);
        titleBottom.setText(storyTitle);

        emptyText = findViewById(R.id.locationsEmptyText);

        if(buildRecyclerView())
            emptyText.setText("");
        else
            emptyText.setText("Nenhum lugar a ser exibido");

    }

    public boolean buildRecyclerView(){
        ArrayList<String> names = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) +
                "/Histórias/" + storyTitle + "/Lugares");
        ArrayList<Item> list = new ArrayList<Item>();

        if(names != null && names.size() != 0) {
            for (String name : names) {
                list.add(new Item(name, R.drawable.compass, R.drawable.arrow_right));
            }
        }
        else
            return false;

        storyRecyclerView = findViewById(R.id.locationRecyclerView);
        storyRecyclerView.setHasFixedSize(true);
        storyLayoutManager = new LinearLayoutManager(this);
        storyAdapter = new StoryAdapter(list);
        storyRecyclerView.setLayoutManager(storyLayoutManager);
        storyRecyclerView.setAdapter(storyAdapter);

        storyAdapter.setOnItemClickListener(new StoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String title = names.get(position);
                showLocationActivity(title);
            }
        });

        return true;
    }

    public void showLocationActivity(View v){
        Intent intent = new Intent(this, Location.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }

    public void showLocationActivity(String locationName){
        Intent intent = new Intent(this, Location.class);
        intent.putExtra("TITLE", storyTitle);
        intent.putExtra("NAME", locationName);
        startActivity(intent);
    }

    public void goToPreviousActivity(View v){
        Intent intent = new Intent(this, Options.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }
}