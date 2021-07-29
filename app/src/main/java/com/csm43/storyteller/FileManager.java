package com.csm43.storyteller;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager extends Application {

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

    private void deleteRecursively(File dirOrFile){
        if(dirOrFile != null && dirOrFile.isDirectory()){
            for(File child : dirOrFile.listFiles())
                deleteRecursively(child);
        }
        dirOrFile.delete();
    }

    public void createCharacterFolder(String title){
        File dir = new File(getExternalFilesDir(null) + "/Histórias/" + title + "/Personagens");
        if(!dir.exists()) {
            dir.mkdir();
            writeFile(dir.getPath(), "nome.stf");
            writeFile(dir.getPath(), "carFísicas.stf");
            writeFile(dir.getPath(), "personalidade.stf");
            writeFile(dir.getPath(), "background.stf");
        }
    }

    public void createLocationFolder(String title){
        File dir = new File(getExternalFilesDir(null) + "/Histórias/" + title + "/Lugares");
        if(!dir.exists()){
            dir.mkdir();
            writeFile(dir.getPath(), "nome.stf");
            writeFile(dir.getPath(), "descrição.stf");
        }
    }

    public void createChapterFolder(String title){
        File dir = new File(getExternalFilesDir(null) + "/Histórias/" + title + "/Capítulos");
        if(!dir.exists()) {
            dir.mkdir();
            writeFile(dir.getPath(), "titulo.stf");
            writeFile(dir.getPath(), "descrição.stf");
        }
    }

    public void writeFile(String path, String name){
        File file = new File(path, name);
        try (FileOutputStream nameStream = new FileOutputStream(file)) {
            nameStream.write("".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
