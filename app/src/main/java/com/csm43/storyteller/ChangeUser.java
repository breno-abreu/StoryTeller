package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class ChangeUser extends AppCompatActivity {
    /* Classe para a activity de mudança de usuário
     * Contém um Switch que alterna entre usuário básico e premium */

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
        userSwitch.setChecked(userSwitch.isChecked());
    }

    public void goToPreviousActivity(View v){
        Intent intent = new Intent(this, MainActivity.class);
        if(userSwitch.isChecked())
            ((FileManager)this.getApplication()).setUser("premium");
        else
            ((FileManager)this.getApplication()).setUser("basic");

        startActivity(intent);
    }
}