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

        dbHelper = new DatabaseHelper(this);

        EditText name = findViewById(R.id.name);
        EditText surname = findViewById(R.id.surname);
        EditText phoneNumber = findViewById(R.id.phoneNumber);

        Button addButton = findViewById(R.id.add);

        addButton.setOnClickListener(v -> {
            String nameString = name.getText().toString().trim();
            String surnameString = surname.getText().toString().trim();
            String phoneNumberString = phoneNumber.getText().toString().trim();

            if (nameString.isEmpty() || surnameString.isEmpty() || phoneNumberString.isEmpty()) {
                Toast.makeText(this, "Wszystkie pola muszą być wypełnione!", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_NAME, nameString);
            values.put(DatabaseHelper.COLUMN_SURNAME, surnameString);
            values.put(DatabaseHelper.COLUMN_PHONE_NUMBER, phoneNumberString);

            long newRowId = db.insert(DatabaseHelper.TABLE_CONTACTS, null, values);
            db.close();

            if (newRowId != -1) {
                Toast.makeText(this, "Kontakt dodany!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddContact.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Blad", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}