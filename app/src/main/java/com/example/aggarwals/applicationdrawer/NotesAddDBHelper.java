package com.example.aggarwals.applicationdrawer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by AGGARWAL'S on 8/10/2015.
 */
public class NotesAddDBHelper {

    private final Context mContext;
    private AddDataBaseHelper addDataBaseHelper;
    private SQLiteDatabase database;


    public NotesAddDBHelper(Context context) throws SQLException{
        mContext = context;
        addDataBaseHelper = new AddDataBaseHelper(mContext);
    }

    public NotesAddDBHelper connect() throws SQLException{
        database = addDataBaseHelper.getWritableDatabase();
        return  this;
    }

    public void disconnect(){
        addDataBaseHelper.close();
    }

    public long insertNotes(String Title,
                            String Body,
                            String Currentphotopath,
                            String LayoutColor,
                            String Label,
                            String Date_created,
                            String List,
                            String Checked_List,
                            int Alarmset,
                            int PathNumber) throws SQLException{
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableEntry.COLUMN_NAME_TITLE,Title);
        contentValues.put(TableEntry.COLUMN_NAME_NOTE_BODY,Body);
        contentValues.put(TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH,Currentphotopath);
        contentValues.put(TableEntry.COLUMN_NAME_LAYOUT_COLOR,LayoutColor);
        contentValues.put(TableEntry.COLUMN_NAME_LABEL,Label);
        contentValues.put(TableEntry.COLUMN_NAME_DATE_CRETED,Date_created);
        contentValues.put(TableEntry.COLUMN_NAME_LIST,List);
        contentValues.put(TableEntry.COLUMN_NAME_CHECKED_LIST, Checked_List);
        contentValues.put(TableEntry.COLUMN_NAME_ALARMSET,Alarmset);
        contentValues.put(TableEntry.COLUMN_NAME_PHOTOPATH_NUMBER,PathNumber);
        this.connect();
        return database.insert(TableEntry.TABLE_NAME,null,contentValues);
    }

    public long insertArchive(String Title,
                            String Body,
                            String Currentphotopath,
                            String LayoutColor,
                            String Label,
                            String Date_created,
                            String List,
                            String Checked_List,
                            int Alarmset,
                            int PathNumber) throws SQLException{
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableEntry.COLUMN_NAME_TITLE,Title);
        contentValues.put(TableEntry.COLUMN_NAME_NOTE_BODY,Body);
        contentValues.put(TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH,Currentphotopath);
        contentValues.put(TableEntry.COLUMN_NAME_LAYOUT_COLOR,LayoutColor);
        contentValues.put(TableEntry.COLUMN_NAME_LABEL,Label);
        contentValues.put(TableEntry.COLUMN_NAME_DATE_CRETED,Date_created);
        contentValues.put(TableEntry.COLUMN_NAME_LIST,List);
        contentValues.put(TableEntry.COLUMN_NAME_CHECKED_LIST, Checked_List);
        contentValues.put(TableEntry.COLUMN_NAME_ALARMSET,Alarmset);
        contentValues.put(TableEntry.COLUMN_NAME_PHOTOPATH_NUMBER,PathNumber);
        this.connect();
        return database.insert(TableEntry.TABLE_NAME_ARCHIVE,null,contentValues);
    }

    public Cursor retrieveAllNotes() throws SQLException{
        this.connect();
        return database.query(TableEntry.TABLE_NAME, new String[]{TableEntry._ID,
                TableEntry.COLUMN_NAME_TITLE,
                TableEntry.COLUMN_NAME_NOTE_BODY,
                TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH,
                TableEntry.COLUMN_NAME_LAYOUT_COLOR,
                TableEntry.COLUMN_NAME_LABEL,
                TableEntry.COLUMN_NAME_DATE_CRETED,
                TableEntry.COLUMN_NAME_LIST,
                TableEntry.COLUMN_NAME_CHECKED_LIST,
                TableEntry.COLUMN_NAME_ALARMSET,
                TableEntry.COLUMN_NAME_PHOTOPATH_NUMBER
        }, null, null, null, null, null);

    }
    public Cursor retrieveAllArchive() throws SQLException{
        this.connect();
        return database.query(TableEntry.TABLE_NAME_ARCHIVE, new String[]{TableEntry._ID,
                TableEntry.COLUMN_NAME_TITLE,
                TableEntry.COLUMN_NAME_NOTE_BODY,
                TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH,
                TableEntry.COLUMN_NAME_LAYOUT_COLOR,
                TableEntry.COLUMN_NAME_LABEL,
                TableEntry.COLUMN_NAME_DATE_CRETED,
                TableEntry.COLUMN_NAME_LIST,
                TableEntry.COLUMN_NAME_CHECKED_LIST,
                TableEntry.COLUMN_NAME_ALARMSET,
                TableEntry.COLUMN_NAME_PHOTOPATH_NUMBER
        }, null, null, null, null, null);

    }

    public Cursor retrieveNote(String Path) throws SQLException{
        this.connect();
        Cursor c = database.query(true,TableEntry.TABLE_NAME,new String[] {TableEntry._ID,
                TableEntry.COLUMN_NAME_TITLE,
                TableEntry.COLUMN_NAME_NOTE_BODY,
                TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH,
                TableEntry.COLUMN_NAME_LAYOUT_COLOR,
                TableEntry.COLUMN_NAME_LABEL,
                TableEntry.COLUMN_NAME_DATE_CRETED,
                TableEntry.COLUMN_NAME_LIST,
                TableEntry.COLUMN_NAME_CHECKED_LIST,
                TableEntry.COLUMN_NAME_ALARMSET,
                TableEntry.COLUMN_NAME_PHOTOPATH_NUMBER
        },TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH + " = ?", new String[] { Path},null,null,null,null);
        if (c!=null){
            c.moveToFirst();
        }
        return c;
    }

    public Cursor retrieveRemainderNotes() throws SQLException{
        this.connect();
        Cursor c = database.query(true,TableEntry.TABLE_NAME,new String[] {TableEntry._ID,
                TableEntry.COLUMN_NAME_TITLE,
                TableEntry.COLUMN_NAME_NOTE_BODY,
                TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH,
                TableEntry.COLUMN_NAME_LAYOUT_COLOR,
                TableEntry.COLUMN_NAME_LABEL,
                TableEntry.COLUMN_NAME_DATE_CRETED,
                TableEntry.COLUMN_NAME_LIST,
                TableEntry.COLUMN_NAME_CHECKED_LIST,
                TableEntry.COLUMN_NAME_ALARMSET,
                TableEntry.COLUMN_NAME_PHOTOPATH_NUMBER
        },TableEntry.COLUMN_NAME_ALARMSET + " = 1",null,null,null,null,null);
        if (c!=null){
            c.moveToFirst();
        }
        return c;
    }
    public Cursor retrieveArchive(String Path) throws SQLException{
        this.connect();
        Cursor c = database.query(true,TableEntry.TABLE_NAME_ARCHIVE,new String[] {TableEntry._ID,
                TableEntry.COLUMN_NAME_TITLE,
                TableEntry.COLUMN_NAME_NOTE_BODY,
                TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH,
                TableEntry.COLUMN_NAME_LAYOUT_COLOR,
                TableEntry.COLUMN_NAME_LABEL,
                TableEntry.COLUMN_NAME_DATE_CRETED,
                TableEntry.COLUMN_NAME_LIST,
                TableEntry.COLUMN_NAME_CHECKED_LIST,
                TableEntry.COLUMN_NAME_ALARMSET,
                TableEntry.COLUMN_NAME_PHOTOPATH_NUMBER
        },TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH + " = ?", new String[] { Path},null,null,null,null);
        if (c!=null){
            c.moveToFirst();
        }
        return c;
    }

    public  boolean deletNoteByPath(String CurrentPhotoPath) throws SQLException{
        this.connect();
        return database.delete(TableEntry.TABLE_NAME,TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH + " = ?", new String[] { CurrentPhotoPath}) >0;
    }
    public  boolean deletArchiveByPath(String CurrentPhotoPath) throws SQLException{
        this.connect();
        return database.delete(TableEntry.TABLE_NAME_ARCHIVE,TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH + " = ?", new String[] { CurrentPhotoPath}) >0;
    }

    public boolean updateNote(String OrignalPath,
                              String Title,
                              String Body,
                              String Currentphotopath,
                              String LayoutColor,
                              String Label,
                              String Date_created,
                              String List,
                              String Checked_List,
                              int Alarmset,
                              int PathNumber) throws SQLException{
        this.connect();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableEntry.COLUMN_NAME_TITLE, Title);
        contentValues.put(TableEntry.COLUMN_NAME_NOTE_BODY,Body);
        contentValues.put(TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH,Currentphotopath);
        contentValues.put(TableEntry.COLUMN_NAME_LAYOUT_COLOR,LayoutColor);
        contentValues.put(TableEntry.COLUMN_NAME_LABEL,Label);
        contentValues.put(TableEntry.COLUMN_NAME_DATE_CRETED,Date_created);
        contentValues.put(TableEntry.COLUMN_NAME_LIST,List);
        contentValues.put(TableEntry.COLUMN_NAME_CHECKED_LIST, Checked_List);
        contentValues.put(TableEntry.COLUMN_NAME_ALARMSET,Alarmset);
        contentValues.put(TableEntry.COLUMN_NAME_PHOTOPATH_NUMBER,PathNumber);
        return database.update(TableEntry.TABLE_NAME,contentValues,TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH + " = ?", new String[] {OrignalPath}) >0;
    }
    public boolean updateArchive(String OrignalPath,
                              String Title,
                              String Body,
                              String Currentphotopath,
                              String LayoutColor,
                              String Label,
                              String Date_created,
                              String List,
                              String Checked_List,
                              int Alarmset,
                              int PathNumber) throws SQLException{
        this.connect();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableEntry.COLUMN_NAME_TITLE, Title);
        contentValues.put(TableEntry.COLUMN_NAME_NOTE_BODY,Body);
        contentValues.put(TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH,Currentphotopath);
        contentValues.put(TableEntry.COLUMN_NAME_LAYOUT_COLOR,LayoutColor);
        contentValues.put(TableEntry.COLUMN_NAME_LABEL,Label);
        contentValues.put(TableEntry.COLUMN_NAME_DATE_CRETED,Date_created);
        contentValues.put(TableEntry.COLUMN_NAME_LIST,List);
        contentValues.put(TableEntry.COLUMN_NAME_CHECKED_LIST, Checked_List);
        contentValues.put(TableEntry.COLUMN_NAME_ALARMSET,Alarmset);
        contentValues.put(TableEntry.COLUMN_NAME_PHOTOPATH_NUMBER,PathNumber);
        return database.update(TableEntry.TABLE_NAME_ARCHIVE,contentValues,TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH + " = ?", new String[] {OrignalPath}) >0;
    }
}
