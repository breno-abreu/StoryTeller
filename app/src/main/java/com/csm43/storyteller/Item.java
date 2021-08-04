package com.csm43.storyteller;

public class Item {
    private String title;
    private int imageid;
    private int arrowid;

    public Item(String title, int imageid, int arrowid){
        this.title = title;
        this.imageid = imageid;
        this.arrowid = arrowid;
    }

    public String getTitle(){
        return title;
    }

    public int getArrowId(){
        return arrowid;
    }

    public int getImageId(){
        return imageid;
    }
}
