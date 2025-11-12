package com.example.contactlistsqliteapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AddContact extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        EditText name = findViewById(R.id.name);
        EditText surname = findViewById(R.id.surname);
        EditText phoneNumber = findViewById(R.id.phoneNumber);

        Button addButton = findViewById(R.id.add);

        addButton.setOnClickListener(v -> {
            String nameString = name.getText().toString();
            String surnameString = surname.getText().toString();
            String phoneNumberString = phoneNumber.getText().toString();

            if (nameString.isEmpty() || surnameString.isEmpty() || phoneNumberString.isEmpty()) {
                return;
            }

            // Uzyskanie dostępu do bazy danych w trybie zapisu
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Użycie ContentValues do bezpiecznego wstawiania danych
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_NAME, nameString);
            values.put(DatabaseHelper.COLUMN_SURNAME, surnameString);
            values.put(DatabaseHelper.COLUMN_PHONE_NUMBER, phoneNumberString);

            // Wstawienie nowego wiersza
            db.insert(DatabaseHelper.TABLE_CONTACTS, null, values);
            db.close();

            Intent intent = new Intent(AddContact.this, MainActivity.class);
            startActivity(intent);
        });
    }
}