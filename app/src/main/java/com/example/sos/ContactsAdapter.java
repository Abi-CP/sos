package com.example.sos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Contact> contactList;
    private boolean isDeleteMode; // Flag to determine layout type
    private DatabaseHelper dbHelper;

    public ContactsAdapter(List<Contact> contactList, DatabaseHelper dbHelper, boolean isDeleteMode) {
        this.contactList = contactList;
        this.dbHelper = dbHelper;
        this.isDeleteMode = isDeleteMode;
    }

    @Override
    public int getItemViewType(int position) {
        return isDeleteMode ? 1 : 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_delete, parent, false);
            return new ContactDeleteViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
            return new ContactViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        if (getItemViewType(position) == 1) {
            ((ContactDeleteViewHolder) holder).bind(contact, position);
        } else {
            ((ContactViewHolder) holder).bind(contact);
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewPhone;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
        }

        public void bind(Contact contact) {
            textViewName.setText(contact.getName());
            textViewPhone.setText(contact.getPhoneNumber());
        }
    }

    public class ContactDeleteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewPhone;
        Button buttonDelete;

        public ContactDeleteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            buttonDelete = itemView.findViewById(R.id.button_view_delete);
        }

        public void bind(Contact contact, int position) {
            textViewName.setText(contact.getName());
            textViewPhone.setText(contact.getPhoneNumber());
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.deleteContact(contact.getId()); // Remove from database
                    contactList.remove(position); // Remove from the list
                    notifyItemRemoved(position); // Notify RecyclerView
                    notifyItemRangeChanged(position, contactList.size());
                }
            });
        }
    }
}
