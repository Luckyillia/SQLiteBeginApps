package com.example.contactlistsqliteapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView contactsRecyclerView;
    private ContactAdapter adapter;
    private List<Contact> contacts;
    private TextView emptyMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        contactsRecyclerView = findViewById(R.id.contactsRecyclerView);
        Button addContactButton = findViewById(R.id.addContact);
        emptyMessageTextView = findViewById(R.id.emptyMessageTextView);

        contacts = new ArrayList<>();

        adapter = new ContactAdapter(contacts, this::deleteContact);
        contactsRecyclerView.setAdapter(adapter);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        addContactButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddContact.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
    }

    private void loadContacts() {
        contacts.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_SURNAME,
                DatabaseHelper.COLUMN_PHONE_NUMBER
        };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_CONTACTS,
                projection,
                null, null, null, null,
                DatabaseHelper.COLUMN_NAME + " ASC"
        );

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            String surname = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SURNAME));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE_NUMBER));

            contacts.add(new Contact(id, name, surname, phone));
        }

        cursor.close();
        db.close();

        adapter.notifyDataSetChanged();

        if (contacts.isEmpty()) {
            contactsRecyclerView.setVisibility(View.GONE);
            emptyMessageTextView.setVisibility(View.VISIBLE);
        } else {
            contactsRecyclerView.setVisibility(View.VISIBLE);
            emptyMessageTextView.setVisibility(View.GONE);
        }
    }

    public void deleteContact(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_CONTACTS,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();

        loadContacts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}