package com.csm43.storyteller;

public class Item {
    private String title;
    private int imageid;

    public Item(String title, int imageid){
        this.title = title;
        this.imageid = imageid;
    }

    public String getTitle(){
        return title;
    }

    public int getImageId(){
        return imageid;
    }
}
