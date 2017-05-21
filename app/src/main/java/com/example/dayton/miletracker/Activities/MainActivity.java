package com.example.dayton.miletracker.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dayton.miletracker.Managers.ClientManager;
import com.example.dayton.miletracker.Managers.ResourceManager;
import com.example.dayton.miletracker.R;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResourceManager databaseHelper = new ResourceManager(this);

        String[] projection = new String[] {
                ClientManager._ID,
                ClientManager.NAME,
                ClientManager.ADDRESS,
                ClientManager.ISDELETED};

        String selection = ClientManager.NAME + " = ?";
        String[] selectionArgs = {"George"};

        String sortOrder =
                ClientManager.NAME + " DESC";

        Cursor cursor = databaseHelper.getReadableDatabase().query(
          ClientManager.TABLE_NAME,
          projection,
          selection,
          selectionArgs,
          null,
          null,
          sortOrder
        );
//        Cursor cursor = databaseHelper.getWritableDatabase().rawQuery(ClientManager.SElECT_QUERY, null);

//        List itemIds = new ArrayList<>();
//        while(cursor.moveToNext()) {
//            long itemId = cursor.getLong(
//                cursor.getColumnIndexOrThrow(ClientManager._ID));
//            itemIds.add(itemId);
//            TextView text = (TextView)findViewById(R.id.editText2);
//            text.setText((cursor.getString(cursor.getColumnIndex(ClientManager.NAME))));
//        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void manageAddresses(View view) {
        Intent intent = new Intent(this, ManageAddressesActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
