package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button newStoryButton;
    private ArrayList<Button> titleButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((FileManager)this.getApplication()).createMainFolder();

        ArrayList<String> titles = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) + "/Histórias");

        if(titles != null) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.storiesLinearLayout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            for (String title : titles) {
                Button button = new Button(this);
                button.setText(title);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showOptionsActivity(title);
                    }
                });
                ll.addView(button, lp);
            }
        }
    }

    public void showNewStoryActivity(View v){
        Intent intent = new Intent(this, NewStory.class);
        startActivity(intent);
    }

    public void showOptionsActivity(String title) {
        Intent intent = new Intent(this, Options.class);
        intent.putExtra("TITLE", title);
        startActivity(intent);
    }
}