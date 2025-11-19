package com.example.contactlistsqliteapp;

public class Contact {
    private final long id;
    private final String name;
    private final String surname;
    private final String phone;

    public Contact(long id, String name, String surname, String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSurname() {
        return surname;
    }
}