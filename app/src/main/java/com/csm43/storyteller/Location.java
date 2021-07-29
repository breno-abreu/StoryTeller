package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Location extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 123;
    ImageView locationImage;

    private EditText name;
    private EditText description;
    private String originalName;
    private String storyTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationImage = (ImageView)findViewById(R.id.locationImage);
        name = (EditText)findViewById(R.id.locationName);
        description = (EditText)findViewById(R.id.locationDescription);
        originalName = name.getText().toString();

        Intent parent = getIntent();
        storyTitle = parent.getStringExtra("TITLE");
    }

    public void chooseImgFromGallery(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Escolha uma Imagem"), GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri imageData = data.getData();
            locationImage.setImageURI(imageData);
        }
    }

    public void saveLocation(View v){
        if(originalName.equals("") || originalName.equals(name.getText().toString())){
            ((FileManager)this.getApplication()).writeLocation(storyTitle, name.getText().toString(), description.getText().toString());

            //Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show();
        }
    }

}