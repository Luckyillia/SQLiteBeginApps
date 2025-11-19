package com.example.contactlistsqliteapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.UserViewHolder> {

    private List<Contact> contactList;
    private OnContactDeleteListener deleteListener;

    public interface OnContactDeleteListener {
        void onContactDelete(long id);
    }

    public ContactAdapter(List<Contact> contactList, OnContactDeleteListener deleteListener) {
        this.contactList = contactList;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Contact currentContact = contactList.get(position);

        holder.idText.setText("ID: " + currentContact.getId());
        holder.nameText.setText(currentContact.getName());
        holder.surnameText.setText(currentContact.getSurname());
        holder.phoneNumberText.setText(currentContact.getPhone());

        holder.itemView.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onContactDelete(currentContact.getId());
                Toast.makeText(v.getContext(),
                        "Usunięto: " + currentContact.getName(),
                        Toast.LENGTH_SHORT).show();
            }
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView idText;
        public TextView nameText;
        public TextView surnameText;
        public TextView phoneNumberText;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            idText = itemView.findViewById(R.id.idText);
            nameText = itemView.findViewById(R.id.nameText);
            surnameText = itemView.findViewById(R.id.surnameText);
            phoneNumberText = itemView.findViewById(R.id.phoneNumber);
        }
    }
}