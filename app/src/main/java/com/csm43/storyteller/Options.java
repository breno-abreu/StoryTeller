package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void showCharacterListActivity(View v){
        Intent intent = new Intent(this, CharacterList.class);
        startActivity(intent);
    }

    public void showLocationListActivity(View v){
        Intent intent = new Intent(this, LocationList.class);
        startActivity(intent);
    }

    public void showChapterListActivity(View v){
        Intent intent = new Intent(this, ChapterList.class);
        startActivity(intent);
    }
}