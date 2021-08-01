package com.csm43.storyteller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class Recorder extends AppCompatActivity {
    private Button startRecordingButton;
    private Button stopRecordingButton;
    private Button playAudioButton;
    private Button stopAudioButton;
    private String path;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private final int REQUEST_PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);

        startRecordingButton = findViewById(R.id.startRecording);
        stopRecordingButton = findViewById(R.id.stopRecording);
        playAudioButton = findViewById(R.id.playAudio);
        stopAudioButton = findViewById(R.id.stopAudio);

        if(!checkPermissionFromDevice()){
            requestPermission();
        }
    }

    public void startRecording(View v){
        path = getExternalFilesDir(null) + "/teste.3gp";
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(path);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        try{
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.d("breo", "prepare() fail");
        }

        mediaRecorder.start();
        Toast.makeText(this, "Gravando", Toast.LENGTH_SHORT).show();
    }

    public void stopRecording(View v){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        Toast.makeText(this, "Fim da Gravação", Toast.LENGTH_SHORT).show();
    }

    public void playAudio(View v){
        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(this, "Executando Áudio", Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            Log.d("breo", "prepare() fail");
        }
    }

    public void stopAudio( View v){
        mediaPlayer.stop();
        mediaPlayer.release();
    }


    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.RECORD_AUDIO
        }, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case REQUEST_PERMISSION_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permissão Garantida", Toast.LENGTH_SHORT).show();
                else
                    finish();
            }
            break;
        }
    }

    private boolean checkPermissionFromDevice(){
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return record_audio_result == PackageManager.PERMISSION_GRANTED;
    }
}