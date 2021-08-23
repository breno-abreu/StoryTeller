package com.csm43.storyteller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Options extends AppCompatActivity {
    /* Classe para a activity que mostra as opções para o usuário.
     * Além de levar às demais activities, também faz o upload e download de histórias do servidor */

    private String storyTitle;
    private EditText titleTop;
    private TextView titleBottom;
    private ImageButton uploadButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Intent intent = getIntent();
        storyTitle = intent.getStringExtra("TITLE");

        progressBar = findViewById(R.id.uploadProgressBar);
        progressBar.setVisibility(View.GONE);
        titleTop = findViewById(R.id.optionsTitleTop);
        titleBottom = findViewById(R.id.optionsTitleBottom);

        titleTop.setText(storyTitle);
        titleBottom.setText(storyTitle);

        String user = ((FileManager)this.getApplication()).getUser();
        uploadButton = findViewById(R.id.uploadButton);

        if(user.equals("basic")){
            uploadButton.setEnabled(false);
            uploadButton.setVisibility(View.GONE);
        }
        else{
            uploadButton.setEnabled(true);
            uploadButton.setVisibility(View.VISIBLE);
        }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja mesmo excluir essa história?");
        builder.setCancelable(true);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteStory();
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void deleteStory(){
        ((FileManager)this.getApplication()).deleteStoryFolder(storyTitle);
        Toast.makeText(this, "História \"" + storyTitle + "\" excluída!", Toast.LENGTH_LONG).show();
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void goToPreviousActivity(View v){
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void uploadStory(View v) {
        progressBar.setVisibility(View.VISIBLE);
        deleteStoryOnServer(storyTitle);
        sendTitleToServer(storyTitle);

        ArrayList<String> characters = ((FileManager) this.getApplication()).getFileNames(getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Personagens");
        if(characters != null) {
            for (String character : characters) {
                String name = ((FileManager) this.getApplication()).loadFile(storyTitle, "Personagens", character, "nome.stf");
                String description = ((FileManager) this.getApplication()).loadFile(storyTitle, "Personagens", character, "descrição.stf");
                sendDataToServer(storyTitle, "personagens", name, description);
            }
        }

        ArrayList<String> locations = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Lugares");
        if(locations != null) {
            for (String location : locations) {
                String name = ((FileManager) this.getApplication()).loadFile(storyTitle, "Lugares", location, "nome.stf");
                String description = ((FileManager) this.getApplication()).loadFile(storyTitle, "Lugares", location, "descrição.stf");
                sendDataToServer(storyTitle, "lugares", name, description);
            }
        }

        ArrayList<String> chapters = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Capítulos");
        if (chapters != null) {
            for (String chapter : chapters) {
                String name = ((FileManager) this.getApplication()).loadFile(storyTitle, "Capítulos", chapter, "nome.stf");
                String description = ((FileManager) this.getApplication()).loadFile(storyTitle, "Capítulos", chapter, "descrição.stf");
                sendDataToServer(storyTitle, "capitulos", name, description);
            }
        }
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "História: \"" + storyTitle + "\" salva no servidor!", Toast.LENGTH_LONG).show();
    }

    public void deleteStoryOnServer(String story){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    String[] fields = new String[1];
                    fields[0] = "story";

                    String[] data = new String[1];
                    data[0] = story;

                    PutData putData = new PutData("http://192.168.15.144/StoryTeller/delete_story.php", "POST", fields, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
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
    }

    public void sendDataToServer(String story, String table, String name, String description){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
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
    }

    public void sendTitleToServer(String story){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    String[] fields = new String[1];
                    fields[0] = "story";

                    String[] data = new String[1];
                    data[0] = story;

                    PutData putData = new PutData("http://192.168.15.144/StoryTeller/set_title.php", "POST", fields, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
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
    }
}