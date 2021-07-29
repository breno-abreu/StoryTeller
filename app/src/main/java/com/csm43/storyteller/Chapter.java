package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Chapter extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private String originalName;
    private String storyTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        storyTitle = ((FileManager)this.getApplication()).getCurrentStory();

        name = (EditText)findViewById(R.id.chapterName);
        description = (EditText)findViewById(R.id.chapterDescription);

        Intent intent = getIntent();

        if(intent.getStringExtra("NAME") != null){
            originalName = intent.getStringExtra("NAME");
            loadLocation();
        }
        else
            originalName = "";
    }

    public void saveChapter(View v){
        if(originalName.equals("") || originalName.equals(name.getText().toString())){
            ((FileManager)this.getApplication()).writeChapter(storyTitle, name.getText().toString(),
                    description.getText().toString());

            //Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadLocation(){
        String nameTemp = ((FileManager)this.getApplication()).loadFile(storyTitle,
                "Capítulos", originalName, "nome.stf");
        String physicalTemp = ((FileManager)this.getApplication()).loadFile(storyTitle,
                "Capítulos", originalName, "descrição.stf");

        name.setText(nameTemp);
        description.setText(physicalTemp);
    }
}