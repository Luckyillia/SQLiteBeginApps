package com.example.contactlistsqliteapp;

public class Contact {
    private final String name;
    private final String surname;
    private final String phone;
    public Contact(String name, String surname, String phone) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
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
