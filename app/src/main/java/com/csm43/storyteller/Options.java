package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Options extends AppCompatActivity {
    private String storyTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Intent intent = getIntent();
        storyTitle = intent.getStringExtra("TITLE");

        if(storyTitle != null)
            ((FileManager)this.getApplication()).setCurrentStory(storyTitle);
    }

    public void showCharacterListActivity(View v){
        Intent intent = new Intent(this, CharacterList.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }

    public void showLocationListActivity(View v){
        Intent intent = new Intent(this, LocationList.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }

    public void showChapterListActivity(View v){
        Intent intent = new Intent(this, ChapterList.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }

    public void deleteStory(View v){
        ((FileManager)this.getApplication()).deleteStoryFolder(storyTitle);
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}