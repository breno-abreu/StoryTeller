package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Options extends AppCompatActivity {
    private String storyTitle;
    private EditText titleTop;
    private TextView titleBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Intent intent = getIntent();
        storyTitle = intent.getStringExtra("TITLE");

        titleTop = findViewById(R.id.optionsTitleTop);
        titleBottom = findViewById(R.id.optionsTitleBottom);

        titleTop.setText(storyTitle);
        titleBottom.setText(storyTitle);
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

    public void showRecordingListActivity(View v){
        Intent intent = new Intent(this, RecordingList.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }

    public void deleteStory(View v){
        ((FileManager)this.getApplication()).deleteStoryFolder(storyTitle);
        Toast.makeText(this, "História \"" + storyTitle + "\" excluída!", Toast.LENGTH_LONG).show();
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void goToPreviousActivity(View v){
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}