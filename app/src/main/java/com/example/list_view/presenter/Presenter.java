package com.example.list_view.presenter;

import android.database.Cursor;

import com.example.list_view.AppUtils;
import com.example.list_view.DatabaseHandler;
import com.example.list_view.Interactor;
import com.example.list_view.model.Contact;

import java.util.ArrayList;

public class Presenter implements Interactor.Presenter {

    Interactor.View mView;

    @Override
    public void setView(Interactor.View view) {
        this.mView = view;
    }

    @Override
    public void loadExistingData(DatabaseHandler db) {
        ArrayList<Contact> contacts = new ArrayList<>();

        try {
            Cursor c = db.selectSQL("select * from Contacts");

            if (c != null) {
                while (c.moveToNext()) {
                    Contact contact = new Contact();
                    contact.setImageID(c.getInt(c.getColumnIndex("id")));
                    contact.setImageName(c.getString(c.getColumnIndex("fname")));
                    contacts.add(contact);
                }
                c.close();
                mView.loadData(contacts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }

    }

    @Override
    public void addContact(DatabaseHandler db, Contact contact) {
        try {
            String columns = "id,fname";
            String values = "" + contact.getImageID() + "," + AppUtils.QT(contact.getImageName()) + "";
            db.insertSQL("Contacts", columns, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
    }

    @Override
    public void deleteContact(DatabaseHandler dbUtils, Contact contact) {

    }
}
