package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChapterList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
    }

    public void showChapterActivity(View v){
        Intent parent = getIntent();
        String title = parent.getStringExtra("TITLE");
        Intent intent = new Intent(this, Chapter.class);
        startActivity(intent);
    }

    public void createEmptyChapter(View v){
        Intent parent = getIntent();
        String title = parent.getStringExtra("TITLE");
        Intent intent = new Intent(this, Chapter.class);
        startActivity(intent);
    }
}