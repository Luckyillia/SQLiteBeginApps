package com.example.contactlistsqliteapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView contactsRecyclerView;
    private ContactAdapter adapter;
    private List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        contactsRecyclerView = findViewById(R.id.contactsRecyclerView);
        Button addContactButton = findViewById(R.id.addContact);

        contacts = new ArrayList<>();

        adapter = new ContactAdapter(contacts);
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

        // Запрос для получения всех контактов
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
                DatabaseHelper.COLUMN_NAME + " ASC" // Сортировка по имени
        );

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            String surname = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SURNAME));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE_NUMBER));

            contacts.add(new Contact(name, surname, phone));
        }

        cursor.close();
        db.close();

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}