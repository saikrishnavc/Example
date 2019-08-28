package com.example.list_view;

import com.example.list_view.model.Contact;

import java.util.ArrayList;

public interface Interactor {

    interface View{
        void loadData(ArrayList<Contact> contacts);
        void showRecords(ArrayList<Contact> contacts);
        void showToast(String message);
    }

    interface Presenter{
        void setView(View view);
        void loadExistingData(DatabaseHandler dbUtils);
        void addContact(DatabaseHandler dbUtils, Contact contact);
        void deleteContact(DatabaseHandler dbUtils, Contact contact);
    }
}
