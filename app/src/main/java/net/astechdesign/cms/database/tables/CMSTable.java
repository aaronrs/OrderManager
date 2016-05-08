package net.astechdesign.cms.database.tables;

import android.database.sqlite.SQLiteDatabase;

public interface CMSTable {

    public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DB_TIME_FORMAT = "HH:mm:ss";

    public String getTableName();

    public void create(SQLiteDatabase db);
    public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion);

}
