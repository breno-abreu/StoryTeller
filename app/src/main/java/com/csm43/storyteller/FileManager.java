package com.csm43.storyteller;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;
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

    private void deleteRecursively(File dirOrFile){
        if(dirOrFile.isDirectory()){
            for(File child : dirOrFile.listFiles())
                deleteRecursively(child);
        }
        dirOrFile.delete();
    }
}
