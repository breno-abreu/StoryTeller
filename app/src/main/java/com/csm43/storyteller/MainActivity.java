package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView storyRecyclerView;
    private StoryAdapter storyAdapter;
    private RecyclerView.LayoutManager storyLayoutManager;
    private TextView emptyText;
    private TextView userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((FileManager)this.getApplication()).createMainFolder();

        userType = findViewById(R.id.userType);

        Intent intent = getIntent();
        String user = intent.getStringExtra("USER");

        if(user == null)
            user = "basic";

        userType.setText(user);
        ((FileManager)this.getApplication()).setUser(user);

        emptyText = findViewById(R.id.mainEmptyText);

        if(buildRecyclerView())
            emptyText.setText("");
        else
            emptyText.setText("Nenhuma história a ser exibida");
    }

    public boolean buildRecyclerView(){
        ArrayList<String> titles = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) + "/Histórias");
        ArrayList<Item> storyList = new ArrayList<Item>();

        if(titles != null && titles.size() != 0) {
            for (String title : titles) {
                storyList.add(new Item(title, R.drawable.book, R.drawable.arrow_right));
            }
        }
        else
            return false;

        storyRecyclerView = findViewById(R.id.storyRecyclerView);
        storyRecyclerView.setHasFixedSize(true);
        storyLayoutManager = new LinearLayoutManager(this);
        storyAdapter = new StoryAdapter(storyList);
        storyRecyclerView.setLayoutManager(storyLayoutManager);
        storyRecyclerView.setAdapter(storyAdapter);

        storyAdapter.setOnItemClickListener(new StoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String title = titles.get(position);
                showOptionsActivity(title);
            }
        });

        return true;
    }

    public void showNewStoryActivity(View v){
        Intent intent = new Intent(this, ServerStoryList.class);
        startActivity(intent);
    }

    public void showOptionsActivity(String title) {
        Intent intent = new Intent(this, Options.class);
        intent.putExtra("TITLE", title);
        startActivity(intent);
    }

    public void showChangeUserActivity(View v){
        Intent intent = new Intent(this,ChangeUser.class);
        intent.putExtra("USER", ((FileManager)this.getApplication()).getUser());
        startActivity(intent);
    }
}