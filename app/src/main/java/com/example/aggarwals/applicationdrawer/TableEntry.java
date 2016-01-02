package com.example.aggarwals.applicationdrawer;

import android.provider.BaseColumns;

/**
 * Created by AGGARWAL'S on 8/9/2015.
 */
public abstract class TableEntry implements BaseColumns {
    public static final String TABLE_NAME = "Notes";
    public static final String TABLE_NAME_ARCHIVE = "Notes_Archive";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_NOTE_BODY = "body";
    public static final String COLUMN_NAME_CURRENT_PHOTO_PATH = "photopath";
    public static final String COLUMN_NAME_LAYOUT_COLOR = "color";
    public static final String COLUMN_NAME_LABEL = "label";
    public static final String COLUMN_NAME_DATE_CRETED = "datecreated";
    public static final String COLUMN_NAME_LIST = "string_list";
    public static final String COLUMN_NAME_CHECKED_LIST = "checked_list";
    public static final String COLUMN_NAME_PHOTOPATH_NUMBER = "NumberPath";
    public static final String COLUMN_NAME_ALARMSET = "AlarmSet";

}
