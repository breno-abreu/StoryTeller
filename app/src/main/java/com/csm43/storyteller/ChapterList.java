package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ChapterList extends AppCompatActivity {
    private String storyTitle;
    private RecyclerView storyRecyclerView;
    private StoryAdapter storyAdapter;
    private RecyclerView.LayoutManager storyLayoutManager;
    private TextView titleBottom;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        //storyTitle = ((FileManager)this.getApplication()).getCurrentStory();
        Intent intent = getIntent();
        storyTitle = intent.getStringExtra("TITLE");

        titleBottom = findViewById(R.id.chapterListTitle);
        titleBottom.setText(storyTitle);

        emptyText = findViewById(R.id.chaptersEmptyText);

        if(buildRecyclerView())
            emptyText.setText("");
        else
            emptyText.setText("Nenhum capítulo a ser exibido");

        /*if(names != null) {
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
        }*/
    }

    public boolean buildRecyclerView(){
        ArrayList<String> names = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) +
                "/Histórias/" + storyTitle + "/Capítulos");
        ArrayList<Item> list = new ArrayList<Item>();

        if(names != null && names.size() != 0) {
            for (String name : names) {
                list.add(new Item(name, R.drawable.chapter));
            }
        }
        else
            return false;

        storyRecyclerView = findViewById(R.id.chapterRecyclerView);
        storyRecyclerView.setHasFixedSize(true);
        storyLayoutManager = new LinearLayoutManager(this);
        storyAdapter = new StoryAdapter(list);
        storyRecyclerView.setLayoutManager(storyLayoutManager);
        storyRecyclerView.setAdapter(storyAdapter);

        storyAdapter.setOnItemClickListener(new StoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String title = names.get(position);
                showChapterActivity(title);
            }
        });

        return true;
    }

    public void showChapterActivity(View v){
        Intent intent = new Intent(this, Chapter.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }

    public void showChapterActivity(String chapterName){
        Intent intent = new Intent(this, Chapter.class);
        intent.putExtra("TITLE", storyTitle);
        intent.putExtra("NAME", chapterName);
        startActivity(intent);
    }

    public void goToPreviousActivity(View v){
        Intent intent = new Intent(this, Options.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }
}