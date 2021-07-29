package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void showCharacterListActivity(View v){
        Intent parent = getIntent();
        String title = parent.getStringExtra("TITLE");

        Intent intent = new Intent(this, CharacterList.class);
        intent.putExtra("TITLE", title);
        startActivity(intent);
    }

    public void showLocationListActivity(View v){
        Intent parent = getIntent();
        String title = parent.getStringExtra("TITLE");

        Intent intent = new Intent(this, LocationList.class);
        intent.putExtra("TITLE", title);
        startActivity(intent);
    }

    public void showChapterListActivity(View v){
        Intent parent = getIntent();
        String title = parent.getStringExtra("TITLE");

        Intent intent = new Intent(this, ChapterList.class);
        intent.putExtra("TITLE", title);
        startActivity(intent);
    }

    public void deleteStory(View v){
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");

        ((FileManager)this.getApplication()).deleteStoryFolder(title);
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}