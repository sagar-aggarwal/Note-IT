package com.example.aggarwals.applicationdrawer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;

/**
 * Created by AGGARWAL'S on 8/9/2015.
 */
public class AddDataBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final File path = new File(Environment.getExternalStorageDirectory()+"/Note IT");
    public static final String DATABASE_NAME = path.getAbsolutePath() +"/Databases"+"/Notes.db";


    private static final String INT_TYPE = " INT";
    private static final String NOT_NULL = " NOT NULL";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES_NOTES =
            "CREATE TABLE " + TableEntry.TABLE_NAME + " (" +
                    TableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TableEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_NOTE_BODY + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_LAYOUT_COLOR + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TableEntry.COLUMN_NAME_LABEL + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_DATE_CRETED + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TableEntry.COLUMN_NAME_LIST + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_CHECKED_LIST + TEXT_TYPE  + COMMA_SEP +
                    TableEntry.COLUMN_NAME_PHOTOPATH_NUMBER + INT_TYPE  + COMMA_SEP +
                    TableEntry.COLUMN_NAME_ALARMSET + INT_TYPE +
                    " )";

    private static final String SQL_CREATE_ENTRIES_ARCHIVE =
            "CREATE TABLE " + TableEntry.TABLE_NAME_ARCHIVE + " (" +
                    TableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TableEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_NOTE_BODY + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_CURRENT_PHOTO_PATH + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_LAYOUT_COLOR + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TableEntry.COLUMN_NAME_LABEL + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_DATE_CRETED + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TableEntry.COLUMN_NAME_LIST + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_CHECKED_LIST + TEXT_TYPE  + COMMA_SEP +
                    TableEntry.COLUMN_NAME_PHOTOPATH_NUMBER + INT_TYPE  + COMMA_SEP +
                    TableEntry.COLUMN_NAME_ALARMSET + INT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES_NOTES=
            "DROP TABLE IF EXISTS " + TableEntry.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES_ARCHIVE=
            "DROP TABLE IF EXISTS " + TableEntry.TABLE_NAME_ARCHIVE;

    AddDataBaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_NOTES);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_ARCHIVE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_NOTES);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_ARCHIVE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
