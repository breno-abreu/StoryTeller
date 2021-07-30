package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class CharacterList extends AppCompatActivity {
    private String storyTitle;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        storyTitle = ((FileManager)this.getApplication()).getCurrentStory();

        updateList();
    }

    private void updateList(){
        ArrayList<String> names = ((FileManager)this.getApplication()).getFileNames(getExternalFilesDir(null) +
                "/Histórias/" + storyTitle + "/Personagens");

        if(names != null) {
            linearLayout = (LinearLayout) findViewById(R.id.charactersLinearLayout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            for (String name : names) {
                Button button = new Button(this);
                button.setText(name);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCharacterActivity(name);
                    }
                });
                linearLayout.addView(button, lp);
            }
        }
    }

    public void showCharacterActivity(View v){
        Intent intent = new Intent(this, Character.class);
        startActivity(intent);
    }

    public void showCharacterActivity(String characterName){
        Intent intent = new Intent(this, Character.class);
        intent.putExtra("NAME", characterName);
        startActivity(intent);
    }
}