package com.csm43.storyteller;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager extends Application {
    private String currentStory;

    public String getCurrentStory(){
        return currentStory;
    }

    public void setCurrentStory(String story){
        currentStory = story;
    }

    public void createMainFolder(){
        File dir = new File(getExternalFilesDir(null) + "/Histórias");
        if(!dir.exists())
            if(dir.mkdir())
                Log.d("BRENO", "Diretório Criado");
            else
                Log.d("BRENO", "Diretório Não Foi Criado");
    }

    public void createStoryFolder(String title){
        File dir = new File(getExternalFilesDir(null) + "/Histórias/" + title);
        if(!dir.exists()){
            dir.mkdir();
            File characters = new File(dir.getPath() + "/Personagens");
            characters.mkdir();
            File locations = new File(dir.getPath() + "/Lugares");
            locations.mkdir();
            File chapters = new File(dir.getPath() + "/Capítulos");
            chapters.mkdir();
            File recordings = new File(dir.getPath() + "/Gravações");
            recordings.mkdir();
        }
    }

    public ArrayList<String> getFileNames(String path){
        File dir = new File(path);
        File[] files = dir.listFiles();
        ArrayList<String> fileNames = new ArrayList<String>();
        if(files != null && files.length != 0){
            for(File file : files){
                fileNames.add(file.getName());
            }
            return fileNames;
        }
        return null;
    }

    public void deleteStoryFolder(String title){
        File dir = new File(getExternalFilesDir(null) + "/Histórias/" + title);
        deleteRecursively(dir);
    }

    public void deleteCharacter(String storyTitle, String name){
        File dir = new File(getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Personagens/" + name);
        deleteRecursively(dir);
    }

    public void deleteLocation(String storyTitle, String name){
        File dir = new File(getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Lugares/" + name);
        deleteRecursively(dir);
    }

    public void deleteChapter(String storyTitle, String name){
        File dir = new File(getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Capítulos/" + name);
        deleteRecursively(dir);
    }

    public void deleteRecording(String storyTitle, String name){
        File dir = new File(getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Gravações/" + name);
        deleteRecursively(dir);
    }

    private void deleteRecursively(File dirOrFile){
        if(dirOrFile != null && dirOrFile.isDirectory()){
            for(File child : dirOrFile.listFiles())
                deleteRecursively(child);
        }
        dirOrFile.delete();
    }

    public void writeCharacter(String storyTitle, String name, String description, Bitmap imgBitmap){
        File dir = new File(getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Personagens/" + name);
        if(!dir.exists()){
            dir.mkdir();
        }
        writeFile(dir.getPath(), "nome.stf", name);
        writeFile(dir.getPath(), "descrição.stf", description);
        writeImage(dir.getPath(), "img.png", imgBitmap);
    }

    public void writeLocation(String storyTitle, String name, String description, Bitmap imgBitmap){
        File dir = new File(getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Lugares/" + name);
        if(!dir.exists()){
            dir.mkdir();
        }
        writeFile(dir.getPath(), "nome.stf", name);
        writeFile(dir.getPath(), "descrição.stf", description);
        writeImage(dir.getPath(), "img.png", imgBitmap);
    }

    public void writeChapter(String storyTitle, String name, String description){
        File dir = new File(getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/Capítulos/" + name);
        if(!dir.exists()){
            dir.mkdir();
        }
        writeFile(dir.getPath(), "nome.stf", name);
        writeFile(dir.getPath(), "descrição.stf", description);
    }

    public void writeFile(String path, String name, String content){
        File file = new File(path, name);
        try (FileOutputStream nameStream = new FileOutputStream(file)) {
            nameStream.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeImage(String path, String name, Bitmap img){
        File file = new File(path, name);
        try(FileOutputStream stream = new FileOutputStream(file)){
            img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadFile(String storyTitle, String option, String name, String field){
        String path = getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/" + option + "/" + name + "/" + field;
        File file = new File(path);
        if(file.exists()){
            int length = (int)file.length();
            byte[] bytes = new byte[length];
            try(FileInputStream stream = new FileInputStream(file)){
                stream.read(bytes);
                return new String(bytes);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Bitmap loadImg(String storyTitle, String option, String name){
        String path = getExternalFilesDir(null) + "/Histórias/" + storyTitle + "/" + option + "/" + name + "/" + "img.png";
        File file = new File(path);
        if(file.exists()){
            try(FileInputStream stream = new FileInputStream(file)){
                return BitmapFactory.decodeFile(file.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
