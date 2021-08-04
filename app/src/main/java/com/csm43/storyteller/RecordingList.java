package com.csm43.storyteller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecordingList extends AppCompatActivity {
    private String storyTitle;
    private RecyclerView recyclerView;
    private StoryAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView titleBottom;
    private TextView emptyText;
    private int fileCounter;
    private ImageButton startRecordingButton;
    private ImageButton stopRecordingButton;
    private String filePath;
    private MediaRecorder mediaRecorder;
    private ArrayList<Item> list;
    private final int REQUEST_PERMISSION_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_list);

        startRecordingButton = findViewById(R.id.startRecording);
        stopRecordingButton = findViewById(R.id.stopRecording);

        stopRecordingButton.setEnabled(false);

        Intent intent = getIntent();
        storyTitle = intent.getStringExtra("TITLE");

        titleBottom = findViewById(R.id.recordingListTitle);
        titleBottom.setText(storyTitle);

        emptyText = findViewById(R.id.recordingsEmptyText);

        if(buildRecyclerView())
            emptyText.setText("");
        else
            emptyText.setText("Nenhuma gravação a ser exibida");

        if(!checkPermissionFromDevice()){
            requestPermission();
        }
    }

    private void requestPermission() {
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

    public boolean buildRecyclerView(){
        ArrayList<String> names = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) +
                "/Histórias/" + storyTitle + "/Gravações");
        list = new ArrayList<Item>();

        if(names != null && names.size() != 0) {
            for (String name : names) {
                list.add(new Item(name, R.drawable.mic, R.drawable.arrow_right));
            }
        }
        else
            return false;

        recyclerView = findViewById(R.id.recordingsRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new StoryAdapter(list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new StoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String title = names.get(position);
                showRecordingActivity(title);
            }
        });

        return true;
    }

    public void startRecording(View v){
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = sdf.format(calendar.getTime());

        filePath = getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Gravações/"
                + "STR" + dateString + ".3gp";
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(filePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        try{
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.d("breno", "prepare() fail");
        }

        mediaRecorder.start();
        Toast.makeText(this, "Gravando", Toast.LENGTH_SHORT).show();
        stopRecordingButton.setEnabled(true);
        startRecordingButton.setEnabled(false);
        titleBottom.setText("Gravando...");
    }

    public void stopRecording(View v){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        Toast.makeText(this, "Fim da Gravação", Toast.LENGTH_SHORT).show();
        stopRecordingButton.setEnabled(false);
        startRecordingButton.setEnabled(true);
        titleBottom.setText(storyTitle);
        list.clear();
        emptyText.setText("");
        buildRecyclerView();
    }

    public void showRecordingActivity(String recordingName){
        Intent intent = new Intent(this, Recording.class);
        intent.putExtra("TITLE", storyTitle);
        intent.putExtra("NAME", recordingName);
        startActivity(intent);
    }

    public void goToPreviousActivity(View v){
        Intent intent = new Intent(this, Options.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }
}