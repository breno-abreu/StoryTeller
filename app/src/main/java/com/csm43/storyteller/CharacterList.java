package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CharacterList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);
    }

    public void showCharacterActivity(View v){
        Intent parent = getIntent();
        String title = parent.getStringExtra("TITLE");
        Intent intent = new Intent(this, Character.class);
        startActivity(intent);
    }

    public void createEmptyLocation(View v){
        Intent parent = getIntent();
        String title = parent.getStringExtra("TITLE");
        Intent intent = new Intent(this, Character.class);
        startActivity(intent);
    }
}