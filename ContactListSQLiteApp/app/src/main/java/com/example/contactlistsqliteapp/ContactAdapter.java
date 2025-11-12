package com.example.contactlistsqliteapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.UserViewHolder> {

    private List<Contact> contactList;

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    // Ta metoda tworzy nowy ViewHolder, gdy RecyclerView go potrzebuje.
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new UserViewHolder(view);
    }

    // Ta metoda wypełnia ViewHolder danymi dla określonej pozycji na liście.
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Contact currentContact = contactList.get(position);
        holder.nameText.setText(currentContact.getName());
        holder.surnameText.setText(currentContact.getSurname());
        holder.phoneNumberText.setText(currentContact.getPhone());
    }

    // Ta metoda zwraca liczbę elementów na liście.
    @Override
    public int getItemCount() {
        return contactList.size();
    }

    // Wewnętrzna klasa ViewHolder przechowuje referencje do widoków wiersza.
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView surnameText;
        public TextView phoneNumberText;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            surnameText = itemView.findViewById(R.id.surnameText);
            phoneNumberText = itemView.findViewById(R.id.phoneNumber);
        }
    }
}