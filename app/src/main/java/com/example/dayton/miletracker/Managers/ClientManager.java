package com.example.dayton.miletracker.Managers;

import android.provider.BaseColumns;

/**
 * Created by Dayton on 5/20/2017.
 */

public class ClientManager implements BaseColumns {
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String ISDELETED = "isDeleted";
    public static final String TABLE_NAME = "Client";

    public static final String CREATE_QUERY = "create table if not exists " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT NOT NULL, " +
            ADDRESS + " TEXT NOT NULL, " +
            ISDELETED + " INTEGER DEFAULT 0);";

    public static final String DROP_QUERY = "drop table " + TABLE_NAME;
    public static final String SElECT_QUERY = "select * from " + TABLE_NAME;
}