package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

    public void uploadStory(View v){
        deleteStoryOnServer(storyTitle);
        ArrayList<String> characters = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Personagens");
        for(String character : characters){
            String name = ((FileManager)this.getApplication()).loadFile(storyTitle, "Personagens", character, "nome.stf");
            String description = ((FileManager)this.getApplication()).loadFile(storyTitle, "Personagens", character, "descrição.stf");
            sendDataToServer(storyTitle, "personagens", name, description);
        }

        ArrayList<String> locations = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Lugares");
        for(String location : locations){
            String name = ((FileManager)this.getApplication()).loadFile(storyTitle, "Lugares", location, "nome.stf");
            String description = ((FileManager)this.getApplication()).loadFile(storyTitle, "Lugares", location, "descrição.stf");
            sendDataToServer(storyTitle, "lugares", name, description);
        }

        ArrayList<String> chapters = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Capítulos");
        for(String chapter : chapters){
            String name = ((FileManager)this.getApplication()).loadFile(storyTitle, "Capítulos", chapter, "nome.stf");
            String description = ((FileManager)this.getApplication()).loadFile(storyTitle, "Capítulos", chapter, "descrição.stf");
            sendDataToServer(storyTitle, "capitulos", name, description);
        }
    }

    public void deleteStoryOnServer(String story){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] fields = new String[1];
                fields[0] = "story";

                String[] data = new String[1];
                data[0] = story;

                PutData putData = new PutData("http://192.168.15.144/StoryTeller/delete_story.php", "POST", fields, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        //((FileManager)getApplication()).writeTempFile(result);
                        //TODO mensagem
                    }
                }
            }
        });
    }

    public void sendDataToServer(String story, String table, String name, String description){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] fields = new String[4];
                fields[0] = "story";
                fields[1] = "table";
                fields[2] = "name";
                fields[3] = "description";

                String[] data = new String[4];
                data[0] = story;
                data[1] = table;
                data[2] = name;
                data[3] = description;

                PutData putData = new PutData("http://192.168.15.144/StoryTeller/set_info.php", "POST", fields, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        //((FileManager)getApplication()).writeTempFile(result);
                        //TODO mensagem
                    }
                }
            }
        });
    }
}