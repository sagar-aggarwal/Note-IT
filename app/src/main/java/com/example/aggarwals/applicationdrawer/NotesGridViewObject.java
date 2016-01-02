package com.example.aggarwals.applicationdrawer;

/**
 * Created by AGGARWAL'S on 8/12/2015.
 */
public class NotesGridViewObject {
    private String title;
    private String icon;
    private String color;
    private String Date;
    private Boolean alarmselected;

    public NotesGridViewObject(String title, String icon,String Date, boolean alarmselected,String color){
        this.title = title;
        this.icon = icon;
        this.Date = Date;
        this.alarmselected = alarmselected;
        this.color = color;
    }

    public String getTitle(){
        return this.title;
    }
    
    public String getColor(){
        return this.color;
    }

    public String getDate() { return this.Date;}

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

}
