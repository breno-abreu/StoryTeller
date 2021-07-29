package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewStory extends AppCompatActivity {
    private EditText storyTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_story);

        storyTitle = (EditText)findViewById(R.id.storyTitle);
    }

    public void createNewStory(View v){
        ((FileManager)this.getApplication()).createStoryFolder(storyTitle.getText().toString());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}