package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CharacterList extends AppCompatActivity {
    private String storyTitle;
    //private LinearLayout linearLayout;
    private RecyclerView storyRecyclerView;
    private StoryAdapter storyAdapter;
    private RecyclerView.LayoutManager storyLayoutManager;
    private TextView titleBottom;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        //storyTitle = ((FileManager)this.getApplication()).getCurrentStory();

        Intent intent = getIntent();
        storyTitle = intent.getStringExtra("TITLE");

        titleBottom = findViewById(R.id.characterListTitle);
        titleBottom.setText(storyTitle);

        emptyText = findViewById(R.id.charactersEmptyText);

        if(buildRecyclerView())
            emptyText.setText("");
        else
            emptyText.setText("Nenhum personagem a ser exibido");
    }

    public boolean buildRecyclerView(){
        ArrayList<String> names = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) +
                "/Histórias/" + storyTitle + "/Personagens");
        ArrayList<Item> list = new ArrayList<Item>();

        if(names != null && names.size() != 0) {
            for (String name : names) {
                list.add(new Item(name, R.drawable.character));
            }
        }
        else
            return false;

        storyRecyclerView = findViewById(R.id.characterRecyclerView);
        storyRecyclerView.setHasFixedSize(true);
        storyLayoutManager = new LinearLayoutManager(this);
        storyAdapter = new StoryAdapter(list);
        storyRecyclerView.setLayoutManager(storyLayoutManager);
        storyRecyclerView.setAdapter(storyAdapter);

        storyAdapter.setOnItemClickListener(new StoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String title = names.get(position);
                showCharacterActivity(title);
            }
        });

        return true;
    }

    public void showCharacterActivity(View v){
        Intent intent = new Intent(this, Character.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }

    public void showCharacterActivity(String characterName){
        Intent intent = new Intent(this, Character.class);
        intent.putExtra("TITLE", storyTitle);
        intent.putExtra("NAME", characterName);
        startActivity(intent);
    }

    public void goToPreviousActivity(View v){
        Intent intent = new Intent(this, Options.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }
}