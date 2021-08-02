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
    private RecyclerView recyclerView;
    private StoryAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
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

        recyclerView = findViewById(R.id.chapterRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new StoryAdapter(list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new StoryAdapter.OnItemClickListener() {
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