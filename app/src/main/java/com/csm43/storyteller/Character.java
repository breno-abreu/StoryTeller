package com.csm43.storyteller;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Character extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 123;
    ImageView charImage;
    Bitmap imgBitmap;
    private EditText name;
    private EditText description;
    private String originalName;
    private String storyTitle;
    private TextView titleBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Intent intent = getIntent();
        storyTitle = intent.getStringExtra("TITLE");

        titleBottom = findViewById(R.id.characterTitle);
        titleBottom.setText(storyTitle);

        charImage = findViewById(R.id.charImage);
        name = findViewById(R.id.characterName);
        description = findViewById(R.id.characterDescription);

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
                    description.getText().toString(), imgBitmap);
            Toast.makeText(this, "Personagem salvo!", Toast.LENGTH_LONG).show();
        }
    }

    private void loadCharacter(){
        String nameTemp = ((FileManager)this.getApplication()).loadFile(storyTitle,
                "Personagens", originalName, "nome.stf");
        String descriptionTemp = ((FileManager)this.getApplication()).loadFile(storyTitle,
                "Personagens", originalName, "descrição.stf");
        Bitmap imgTemp = ((FileManager)this.getApplication()).loadImg(storyTitle,
                "Personagens", originalName);

        name.setText(nameTemp);
        description.setText(descriptionTemp);
        charImage.setImageBitmap(imgTemp);

        Drawable drawable = charImage.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
        imgBitmap = bitmapDrawable.getBitmap();
    }

    public void deleteCharacter(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja mesmo excluir esse capítulo?");
        builder.setCancelable(true);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteCharacter();
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

    public void deleteCharacter(){
        ((FileManager)this.getApplication()).deleteCharacter(storyTitle, name.getText().toString());
        Toast.makeText(this, "Personagem \"" + originalName + "\" excluído!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, CharacterList.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }

    public void goToPreviousActivity(View v){
        Intent intent = new Intent(this, CharacterList.class);
        intent.putExtra("TITLE", storyTitle);
        startActivity(intent);
    }
}