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
        ArrayList<String> titles = getTitlesFromServer();
        ArrayList<Item> list = new ArrayList<Item>();

        if(titles != null && titles.size() != 0) {
            for (String title : titles) {
                list.add(new Item(title, R.drawable.database, R.drawable.arrow_down));
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

    public ArrayList<String> getTitlesFromServer(){
        Handler handler = new Handler();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    PutData putData = new PutData("http://192.168.15.144/StoryTeller/get_story_list.php", "POST", null, null);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            ((FileManager) getApplication()).writeTempFile(result);
                        }
                    }
                    notify();
                }
            }
        };

        Thread thread = new Thread(task);
        thread.start();

        //handler.post(task);
        synchronized(task){
            try{
                task.wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        String text = ((FileManager)getApplication()).loadTempFile();

        if(text != null){
            String[] titles = text.split("&&");
            return new ArrayList<String>(Arrays.asList(titles));
        }
        return null;
    }
    // https://www.youtube.com/watch?v=X8oD4q3XtQQ&ab_channel=CodesEasy, ver github
    public ArrayList<ArrayList<String>> getDataFromServer(String title, String table) {
        //Handler handler = new Handler();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    String[] fields = new String[2];
                    fields[0] = "story";
                    fields[1] = "table";

                    String[] data = new String[2];
                    data[0] = title;
                    data[1] = table;

                    PutData putData = new PutData("http://192.168.15.144/StoryTeller/get_info.php", "POST", fields, data);
                    String result = "";
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            result = putData.getResult();
                            ((FileManager) getApplication()).writeTempFile(result);
                        }
                    }
                    notify();
                }
            }
        };

        Thread thread = new Thread(task);
        thread.start();

        //handler.post(task);
        synchronized (task){
            try{
                task.wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        String text = ((FileManager)this.getApplication()).loadTempFile();
        ArrayList<ArrayList<String>> allInfo = new ArrayList<ArrayList<String>>();

        if(text != null){
            String[] data = text.split("&&");
            for(String content : data){
                String[] info = content.split("%%");
                ArrayList<String> aux = new ArrayList<String>(Arrays.asList(info));
                allInfo.add(aux);
            }
            return allInfo;
        }
        return null;
    }

    public void writeFiles(String title) {
        ((FileManager)this.getApplication()).createStoryFolder(title);
        ArrayList<ArrayList<String>> characters = getDataFromServer(title, "personagens");
        ArrayList<ArrayList<String>> locations = getDataFromServer(title, "lugares");
        ArrayList<ArrayList<String>> chapters = getDataFromServer(title, "capitulos");

        if(characters != null){
            for(ArrayList<String> info : characters){
                ((FileManager)this.getApplication()).writeCharacter(title, info.get(0), info.get(1), null);
            }
        }

        if(locations != null) {
            for (ArrayList<String> info : locations) {
                ((FileManager) this.getApplication()).writeLocation(title, info.get(0), info.get(1), null);
            }
        }

        if(chapters != null) {
            for (ArrayList<String> info : chapters) {
                ((FileManager) this.getApplication()).writeChapter(title, info.get(0), info.get(1));
            }
        }

        Toast.makeText(this, "História: \"" + title + "\" baixada!", Toast.LENGTH_LONG).show();
    }

    public void goToPreviousActivity(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void downloadStory(String title){
        writeFiles(title);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}