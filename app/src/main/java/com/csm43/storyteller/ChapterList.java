package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ChapterList extends AppCompatActivity {
    private String storyTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        storyTitle = ((FileManager)this.getApplication()).getCurrentStory();

        ArrayList<String> names = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) +
                "/Histórias/" + storyTitle + "/Capítulos");

        if(names != null) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.chaptersLinearLayout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            for (String name : names) {
                Button button = new Button(this);
                button.setText(name);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showChapterActivity(name);
                    }
                });
                ll.addView(button, lp);
            }
        }
    }

    public void showChapterActivity(View v){
        Intent intent = new Intent(this, Chapter.class);
        startActivity(intent);
    }

    public void showChapterActivity(String chapterName){
        Intent intent = new Intent(this, Chapter.class);
        intent.putExtra("NAME", chapterName);
        startActivity(intent);
    }
}