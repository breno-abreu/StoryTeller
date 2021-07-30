package com.csm43.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Location extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 123;
    ImageView locationImage;
    Bitmap imgBitmap;
    private EditText name;
    private EditText description;
    private String originalName;
    private String storyTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        storyTitle = ((FileManager)this.getApplication()).getCurrentStory();

        locationImage = (ImageView)findViewById(R.id.locationImage);
        name = (EditText)findViewById(R.id.locationName);
        description = (EditText)findViewById(R.id.locationDescription);

        Drawable drawable = locationImage.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
        imgBitmap = bitmapDrawable.getBitmap();

        Intent intent = getIntent();

        if(intent.getStringExtra("NAME") != null){
            originalName = intent.getStringExtra("NAME");
            loadLocation();
        }
        else
            originalName = "";
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

            Drawable drawable = locationImage.getDrawable();
            BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
            imgBitmap = bitmapDrawable.getBitmap();
        }
    }

    public void saveLocation(View v){
        if(originalName.equals("") || originalName.equals(name.getText().toString())){
            ((FileManager)this.getApplication()).writeLocation(storyTitle, name.getText().toString(),
                    description.getText().toString(), imgBitmap);

            //Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadLocation(){
        String nameTemp = ((FileManager)this.getApplication()).loadFile(storyTitle,
                "Lugares", originalName, "nome.stf");
        String physicalTemp = ((FileManager)this.getApplication()).loadFile(storyTitle,
                "Lugares", originalName, "descrição.stf");
        Bitmap imgTemp = ((FileManager)this.getApplication()).loadImg(storyTitle,
                "Lugares", originalName);

        name.setText(nameTemp);
        description.setText(physicalTemp);
        locationImage.setImageBitmap(imgTemp);

        Drawable drawable = locationImage.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
        imgBitmap = bitmapDrawable.getBitmap();
    }

    public void deleteLocation(View v){
        ((FileManager)this.getApplication()).deleteLocation(storyTitle, name.getText().toString());
        Intent intent = new Intent(this, LocationList.class);
        startActivity(intent);
    }
}