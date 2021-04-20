package com.example.yaxin.webprofile;

/**
 * Created by Yaxin on 10/24/2017.
 */

/**
 * Use for build array of items, store the variables from json file
 */

public class item {
    public String name;
    public String link;
    public String descrip;
    public String proj;
    //public Drawable icon;//ArrayList<String> starred = new ArrayList<String>();
    public int icon;
    public item(
            String name,
            String link,
            String descrip,
            String proj,int icon){
        this.name = name;
        this.link = link;
        this.descrip= descrip;
        this.proj = proj;
        this.icon = icon;
    }

}
