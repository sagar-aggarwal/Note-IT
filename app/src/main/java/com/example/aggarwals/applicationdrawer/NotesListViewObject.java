package com.example.aggarwals.applicationdrawer;

/**
 * Created by AGGARWAL'S on 8/14/2015.
 */
public class NotesListViewObject {
    private String title;
    private String icon;
    private String color;
    private String content;
    private String Date;
    private Boolean alarmselected;

    public NotesListViewObject(String title, String icon,String Date, boolean alarmselected,String color,String content){
        this.title = title;
        this.icon = icon;
        this.Date = Date;
        this.alarmselected = alarmselected;
        this.color = color;
        this.content = content;
    }

    public String getTitle(){
        return this.title;
    }

    public String getColor(){
        return this.color;
    }

    public String getDate() { return this.Date;}

    public String getContent() {return this.content;}

    public boolean getselected() { return  this.alarmselected ;}

    public String getIcon(){
        return this.icon;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public void setDate(String Date){this.Date = Date;}

    public void setAlarmselected(boolean alarmselected){this.alarmselected = alarmselected;}

    public void setColor(String color){ this.color = color ;}

    public void setContent(String content) {this.content = content;}
}
