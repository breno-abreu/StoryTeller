package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class ChangeUser extends AppCompatActivity {
    private Switch userSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);

        userSwitch = findViewById(R.id.userSwitch);

        Intent intent = getIntent();
        String user = intent.getStringExtra("USER");
        userSwitch.setChecked(!user.equals("basic"));
    }

    public void changeType(View v){
        if(userSwitch.isChecked())
            userSwitch.setChecked(true);
        else
            userSwitch.setChecked(false);
    }

    public void goToPreviousActivity(View v){
        Intent intent = new Intent(this, MainActivity.class);
        if(userSwitch.isChecked())
            intent.putExtra("USER", "premium");
        else
            intent.putExtra("USER", "basic");

        startActivity(intent);
    }
}