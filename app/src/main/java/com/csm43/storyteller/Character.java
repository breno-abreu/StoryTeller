package com.csm43.storyteller;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Character extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 123;
    ImageView charImage;
    Bitmap imgBitmap;
    private EditText name;
    private EditText physicalChar;
    private EditText personality;
    private EditText background;
    private String originalName;
    private String storyTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        Intent intent = getIntent();
        storyTitle = ((FileManager)this.getApplication()).getCurrentStory();

        charImage = (ImageView)findViewById(R.id.charImage);
        name = (EditText)findViewById(R.id.characterName);
        physicalChar = (EditText)findViewById(R.id.characterPhysicalChar);
        personality = (EditText)findViewById(R.id.characterPersonality);
        background = (EditText)findViewById(R.id.characterBackground);

        Drawable drawable = charImage.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
        imgBitmap = bitmapDrawable.getBitmap();

        if(intent.getStringExtra("NAME") != null){
            originalName = intent.getStringExtra("NAME");
            loadCharacter();
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
            charImage.setImageURI(imageData);

            Drawable drawable = charImage.getDrawable();
            BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
            imgBitmap = bitmapDrawable.getBitmap();
        }
    }

    public void saveCharacter(View v){
        if(originalName.equals("") || originalName.equals(name.getText().toString())){
            ((FileManager)this.getApplication()).writeCharacter(storyTitle, name.getText().toString(),
                    physicalChar.getText().toString(), personality.getText().toString(),
                    background.getText().toString(), imgBitmap);
        }
    }

    private void loadCharacter(){
        String nameTemp = ((FileManager)this.getApplication()).loadFile(storyTitle,
                "Personagens", originalName, "nome.stf");
        String physicalTemp = ((FileManager)this.getApplication()).loadFile(storyTitle,
                "Personagens", originalName, "características.stf");
        String personalityTemp = ((FileManager)this.getApplication()).loadFile(storyTitle,
                "Personagens", originalName, "personalidade.stf");
        String backgroundTemp = ((FileManager)this.getApplication()).loadFile(storyTitle,
                "Personagens", originalName, "background.stf");
        Bitmap imgTemp = ((FileManager)this.getApplication()).loadImg(storyTitle,
                "Personagens", originalName);

        name.setText(nameTemp);
        physicalChar.setText(physicalTemp);
        personality.setText(personalityTemp);
        background.setText(backgroundTemp);
        charImage.setImageBitmap(imgTemp);
    }
}