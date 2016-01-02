package com.example.aggarwals.applicationdrawer;

/**
 * Created by AGGARWAL'S on 7/25/2015.
 */
public class NavDrawerItem {

    private String title;
    private int icon;
    public NavDrawerItem(){}

    public NavDrawerItem(String title){
        this.title = title;
    }

    public NavDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }
    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setIcon(int icon){
        this.icon = icon;
    }
}