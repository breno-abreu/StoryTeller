package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

// https://www.youtube.com/watch?v=X8oD4q3XtQQ&ab_channel=CodesEasy, ver github
public class SQLTest extends AppCompatActivity {
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqltest);

        text = findViewById(R.id.sqlText);
    }

    public void click(View v){
        String name = text.getText().toString();

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] fields = new String[1];
                fields[0] = "name";

                String[] data = new String[1];
                data[0] = name;

                PutData putData = new PutData("http://192.168.15.144/Teste/login.php", "POST", fields, data);
                if(putData.startPut()){
                    if(putData.onComplete()){
                        String result = putData.getResult();
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        /*BackgroundWorker bw = new BackgroundWorker(this);
        bw.execute(type, name);*/
    }
}