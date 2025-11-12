package com.example.contactlistsqliteapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText noteInput;
    private Button saveButton;
    private TextView notesDisplay;
    private RecyclerView contactsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicjalizacja DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        contactsRecyclerView = findViewById(R.id.contactsRecyclerView);

        List<Contact> contacts = new ArrayList<>();

        // Wyświetl notatki przy starcie
        loadNotes(contacts);
    }

    private void loadNotes(List<Contact> contacts) {
        // Uzyskanie dostępu do bazy danych w trybie odczytu
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Zapytanie, które pobierze wszystkie dane z tabeli
        String[] projection = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_SURNAME, DatabaseHelper.COLUMN_PHONE_NUMBER};
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_CONTACTS,
                projection,
                null, null, null, null, null
        );

        StringBuilder notes = new StringBuilder();
        while (cursor.moveToNext()) {
            contacts.add(new Contact(
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SURNAME),
                                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE_NUMBER)));
        }
        cursor.close();
        db.close();

        notesDisplay.setText(notes.toString());
    }
}