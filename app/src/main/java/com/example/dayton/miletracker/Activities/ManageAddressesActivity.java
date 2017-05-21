package com.example.dayton.miletracker.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.dayton.miletracker.Managers.ClientManager;
import com.example.dayton.miletracker.Managers.ResourceManager;
import com.example.dayton.miletracker.Models.Client;
import com.example.dayton.miletracker.R;

import java.util.ArrayList;
import java.util.List;

public class ManageAddressesActivity extends AppCompatActivity {
    TableLayout AddressTable;
    List<Client> Clients = new ArrayList<>();
    ResourceManager DatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_addresses);

        DatabaseHelper = new ResourceManager(this);
        AddressTable = (TableLayout) findViewById(R.id.addresses_table);

        InitializeComponents();
        InitializeTable();
        FetchData();
        PopulateTable();
    }

    private void InitializeComponents() {
        InitializeAddAddressDialog();
        InitializeEditAddressDialog();
        InitializeDeleteAddressDialog();
    }

    private void InitializeAddAddressDialog() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                ShowAddAddressDialog();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ShowAddAddressDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View v = getLayoutInflater().inflate(R.layout.dialog_new_address, null);
        builder.setTitle("Enter Client Details")
                .setView(v)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                final EditText clientName = (EditText)v.findViewById(R.id.clientname);
                final EditText address = (EditText)v.findViewById(R.id.address);
                if (clientName.getText().toString().equals("") || address.getText().toString().equals("")) {
                    Snackbar.make(vi, "Please enter all fields", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    DatabaseHelper.InsertClient(clientName.getText().toString(), address.getText().toString());
                    alertDialog.dismiss();
                }
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                alertDialog.dismiss();
            }
        });
    }

    private void InitializeEditAddressDialog() {

    }

    private void InitializeDeleteAddressDialog() {

    }

    private void ShowDeleteClientDialog(Client client) {
        final Client thisClient = client;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete " + client.getName() + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseHelper.deleteClient(thisClient.getId());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void InitializeTable() {
        TableRow tr_head = new TableRow(this);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT
        ));

        TextView label_clientName = new TextView(this);
        label_clientName.setText("Client");
        label_clientName.setTextColor(Color.BLACK);
        label_clientName.setPadding(5, 5, 5, 5);
        label_clientName.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT
        ));
        tr_head.addView(label_clientName);

        TextView label_delete = new TextView(this);
        label_delete.setText("Delete");
        label_delete.setTextColor(Color.BLACK);
        label_delete.setPadding(5, 5, 5, 5);
        label_delete.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT
        ));
        tr_head.addView(label_delete);

        TextView label_edit = new TextView(this);
        label_edit.setText("Edit");
        label_edit.setTextColor(Color.BLACK);
        label_edit.setPadding(5, 5, 5, 5);
        label_edit.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT
        ));
        tr_head.addView(label_edit);
        AddressTable.addView(tr_head);
    }

    private void FetchData() {
        String[] projection = new String[]{
                ClientManager._ID,
                ClientManager.NAME,
                ClientManager.ADDRESS,
                ClientManager.ISDELETED};

        String selection = ClientManager.ISDELETED + " = ?";
        String[] selectionArgs = {"0"};

        String sortOrder =
                ClientManager.NAME + " DESC";

        Cursor cursor = DatabaseHelper.getReadableDatabase().query(
                ClientManager.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()) {
            Client client = new Client(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ClientManager._ID))),
                    cursor.getString(cursor.getColumnIndexOrThrow(ClientManager.NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ClientManager.ADDRESS)),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ClientManager.ISDELETED)))
            );
            Log.d("ClientName: ", client.getName());
            Log.d("IsDeleted: ", client.getIsDeleted() + "");
            Clients.add(client);
        }
    }

    private void PopulateTable() {
        for (Client client : Clients) {
            CreateTableRow(client);
        }
    }

    private void CreateTableRow(Client client) {
        final Client thisClient = client;
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        ));

        TextView clientName = new TextView(this);
        clientName.setText(client.getName());

        Button deleteButton = new Button(this);
        deleteButton.setText("Delete");
        deleteButton.setTextColor(Color.RED);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDeleteClientDialog(thisClient);
            }
        });

        Button editButton = new Button(this);
        editButton.setText("Edit");
        editButton.setTextColor(Color.BLACK);

        row.addView(clientName);
        row.addView(deleteButton);
        row.addView(editButton);
        AddressTable.addView(row);
    }

    public void AddAddress(View view) {
        Intent intent = new Intent(this, ManageAddressesActivity.class);
        startActivity(intent);
    }
}