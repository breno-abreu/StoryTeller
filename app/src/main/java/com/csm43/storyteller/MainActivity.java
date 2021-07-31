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

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView storyRecyclerView;
    private RecyclerView.Adapter storyAdapter;
    private RecyclerView.LayoutManager storyLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((FileManager)this.getApplication()).createMainFolder();

        ArrayList<String> titles = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) + "/Histórias");
        ArrayList<StoryItem> storyList = new ArrayList<StoryItem>();

        if(titles != null) {
            for (String title : titles) {
                storyList.add(new StoryItem(title));
            }
        }

        storyRecyclerView = findViewById(R.id.storyRecyclerView);
        storyRecyclerView.setHasFixedSize(true);
        storyLayoutManager = new LinearLayoutManager(this);
        storyAdapter = new StoryAdapter(storyList);
        storyRecyclerView.setLayoutManager(storyLayoutManager);
        storyRecyclerView.setAdapter(storyAdapter);

    }

    public void showNewStoryActivity(View v){
        Intent intent = new Intent(this, NewStory.class);
        startActivity(intent);
    }

    public void showOptionsActivity(String title) {
        Intent intent = new Intent(this, Options.class);
        intent.putExtra("TITLE", title);
        startActivity(intent);
    }
}