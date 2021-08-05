package com.csm43.storyteller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Chapter extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private String originalName;
    private String storyTitle;
    private TextView titleBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Intent intent = getIntent();
        storyTitle = intent.getStringExtra("TITLE");

        titleBottom = findViewById(R.id.chapterTitle);
        titleBottom.setText(storyTitle);

        name = (EditText)findViewById(R.id.chapterName);
        description = (EditText)findViewById(R.id.chapterDescription);

        if(intent.getStringExtra("NAME") != null){
            originalName = intent.getStringExtra("NAME");
            loadLocation();
        }
        else
            originalName = "";
    }

    public void saveChapter(View v){
        if(originalName.equals("") || originalName.equals(name.getText().toString())){
            ((FileManager)this.getApplication()).writeChapter(storyTitle, name.getText().toString(),
                    description.getText().toString());
            Toast.makeText(this, "Capítulo salvo!", Toast.LENGTH_LONG).show();

            //Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadLocation(){
        String nameTemp = ((FileManager)this.getApplication()).loadFile(storyTitle,
                "Capítulos", originalName, "nome.stf");
        String physicalTemp = ((FileManager)this.getApplication()).loadFile(storyTitle,
                "Capítulos", originalName, "descrição.stf");

        name.setText(nameTemp);
        description.setText(physicalTemp);
    }

    public void deleteChapter(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja mesmo excluir esse capítulo?");
        builder.setCancelable(true);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteChapter();
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

    public void deleteChapter(){
        ((FileManager)this.getApplication()).deleteChapter(storyTitle, name.getText().toString());
        Toast.makeText(this, "Capítulo \"" + originalName + "\" excluído!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ChapterList.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }

    public void goToPreviousActivity(View v){
        Intent intent = new Intent(this, ChapterList.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }
}