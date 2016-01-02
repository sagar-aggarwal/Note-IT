package com.example.aggarwals.applicationdrawer;

/**
 * Created by AGGARWAL'S on 8/5/2015.
 */
public class ListItemObject {
    private String editText;
    private boolean selected;

    public ListItemObject(String edit,boolean sel){
        editText = edit;
        selected = sel;
    }

    public String geteditText(){
        return this.editText;
    }

    public boolean getselected(){
        return this.selected;
    }

    public void seteditText(String title){
        this.editText = title;
    }

    public void setselected(boolean sel){
        this.selected = sel;
    }
}
