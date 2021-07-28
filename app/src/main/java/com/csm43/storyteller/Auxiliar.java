package com.csm43.storyteller;

import android.app.Application;

import java.util.ArrayList;

public class Auxiliar extends Application {
    private ArrayList<String> titles;

    public Auxiliar(){
        titles = new ArrayList<String>();
    }

    public void addTitle(String title){
        titles.add(title);
    }

    public ArrayList<String> getTitles(){
        return titles;
    }
}
