package com.example.dayton.miletracker.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dayton.miletracker.Models.Client;

import java.io.FileNotFoundException;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static java.util.Arrays.asList;

/**
 * Created by Dayton on 5/20/2017.
 */

public class ResourceManager extends SQLiteOpenHelper {
    SQLiteDatabase Db;
    public ResourceManager(Context context) {
        super(context, "Mileage", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Db = db;
        Db.execSQL(ClientManager.CREATE_QUERY);
        seedClients();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void seedClients()
    {
        List<Client> clients = asList (
            new Client("George", "1337 E. Natal Ave."),
            new Client("Dayton", "9181 226th Pl. NE")
        );

        for (Client client : clients) {
            ContentValues values = new ContentValues();
            values.put(ClientManager.NAME, client.getName());
            values.put(ClientManager.ADDRESS, client.getAddress());
            values.put(ClientManager.ISDELETED, client.getIsDeleted());

            Db.insert(ClientManager.TABLE_NAME, null, values);
        }
    }

    public Cursor getClientCursor() {
        return this.getWritableDatabase().rawQuery(ClientManager.SElECT_QUERY, null);
    }

    public void InsertClient(String name, String address) {
        Client client = new Client(name, address);
        ContentValues values = new ContentValues();
        values.put(ClientManager.NAME, client.getName());
        values.put(ClientManager.ADDRESS, client.getAddress());
        values.put(ClientManager.ISDELETED, client.getIsDeleted());

        this.getWritableDatabase().insert(ClientManager.TABLE_NAME, null, values);
    }

    public void deleteClient(int id) {
        ContentValues values = new ContentValues();
        values.put(ClientManager.ISDELETED, 1);

        String selection = ClientManager._ID + " = ?";
        String[] selectionArgs = { id + ""};

        this.getReadableDatabase().update(ClientManager.TABLE_NAME, values, selection, selectionArgs);
    }
}