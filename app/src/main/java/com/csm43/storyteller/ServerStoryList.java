package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerStoryList extends AppCompatActivity {
    private RecyclerView storyRecyclerView;
    private StoryAdapter storyAdapter;
    private RecyclerView.LayoutManager storyLayoutManager;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_story_list);

        emptyText = findViewById(R.id.serverEmptyText);

        if(buildRecyclerView())
            emptyText.setText("");
        else
            emptyText.setText("Nenhuma história no servidor!");
    }

    public boolean buildRecyclerView(){
        ArrayList<String> titles = getTitlesOnServer();
        ArrayList<Item> list = new ArrayList<Item>();

        if(titles != null && titles.size() != 0) {
            for (String title : titles) {
                list.add(new Item(title, R.drawable.database));
            }
        }
        else
            return false;

        storyRecyclerView = findViewById(R.id.serverRecyclerView);
        storyRecyclerView.setHasFixedSize(true);
        storyLayoutManager = new LinearLayoutManager(this);
        storyAdapter = new StoryAdapter(list);
        storyRecyclerView.setLayoutManager(storyLayoutManager);
        storyRecyclerView.setAdapter(storyAdapter);

        storyAdapter.setOnItemClickListener(new StoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String title = titles.get(position);
                downloadStory(title);
            }
        });

        return true;
    }

    public ArrayList<String> getTitlesOnServer(){

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                PutData putData = new PutData("http://192.168.15.144/StoryTeller/get_story_list.php", "POST", null, null);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();

                        File file = new File(getExternalFilesDir(null) + "/tmp.txt");
                        try (FileOutputStream nameStream = new FileOutputStream(file)) {
                            nameStream.write(result.getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        File file = new File(getExternalFilesDir(null) + "/tmp.txt");
        String text;
        if(file.exists()){
            int length = (int)file.length();
            byte[] bytes = new byte[length];
            try(FileInputStream stream = new FileInputStream(file)){
                stream.read(bytes);
                text = new String(bytes);
                Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                String[] titles = text.split("&&");
                return new ArrayList<String>(Arrays.asList(titles));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void goToPreviousActivity(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void downloadStory(String title){


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}