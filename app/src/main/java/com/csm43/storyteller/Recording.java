package com.csm43.storyteller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Recording extends AppCompatActivity {
    /* Classe para a activity que irá reproduzir um arquivo de áudio gravado pelo usuário */

    private String storyTitle;
    private String fileName;
    private TextView titleBottom;
    private TextView fileNameView;
    private TextView statusText;
    private ImageButton playButton;
    private ImageButton stopButton;
    private MediaPlayer mediaPlayer;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        Intent intent = getIntent();
        storyTitle = intent.getStringExtra("TITLE");
        fileName = intent.getStringExtra("NAME");

        fileNameView = findViewById(R.id.recordingFileName);
        fileNameView.setText(fileName);

        titleBottom = findViewById(R.id.recordingTitle);
        titleBottom.setText(storyTitle);

        statusText = findViewById(R.id.recordingStatus);
        statusText.setText("");

        playButton = findViewById(R.id.playAudio);
        stopButton = findViewById(R.id.stopAudio);

        stopButton.setEnabled(false);

        filePath = getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Gravações/" +  fileName;
    }

    public void playAudio(View v){
        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(this, "Tocando Áudio", Toast.LENGTH_SHORT).show();
            playButton.setEnabled(false);
            stopButton.setEnabled(true);
            statusText.setText("Tocando");
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopAudio();
                }
            });

        } catch (IOException e){
            Log.d("breno", "prepare() fail");
        }
    }

    public void stopAudio(View v){
        mediaPlayer.stop();
        mediaPlayer.release();
        Toast.makeText(this, "Parando Áudio", Toast.LENGTH_SHORT).show();
        playButton.setEnabled(true);
        stopButton.setEnabled(false);
        statusText.setText("");
    }

    public void stopAudio(){
        mediaPlayer.stop();
        mediaPlayer.release();
        Toast.makeText(this, "Parando Áudio", Toast.LENGTH_SHORT).show();
        playButton.setEnabled(true);
        stopButton.setEnabled(false);
        statusText.setText("");
    }

    public void deleteRecording(){
        ((FileManager)this.getApplication()).deleteRecording(storyTitle, fileName);
        Toast.makeText(this, "Gravação \"" + fileName + "\" excluída!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, RecordingList.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }

    public void deleteRecording(View v){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja mesmo excluir essa gravação?");
        builder.setCancelable(true);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteRecording();
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

    public void goToPreviousActivity(View v){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }

        Intent intent = new Intent(this, RecordingList.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}