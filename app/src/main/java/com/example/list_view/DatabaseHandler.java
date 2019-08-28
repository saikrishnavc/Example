package com.example.list_view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.list_view.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ContactsData";

    // Contacts table name
    private static final String TABLE_CONTACTS = "Contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CONTACTS = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_FNAME + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_CONTACTS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Insert values to the table contacts
    /*public void addContacts(Contact contact) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_FNAME, contact.getFName());
        values.put(KEY_PHOTO, contact.getImage());


        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }*/

    public Cursor selectSQL(String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (BuildConfig.DEBUG) {
            Log.d(this.getClass().getName(), sql);
        }
        return db.rawQuery(sql, null);
    }

    public void insertSQL(String tbl, String columns, String content) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "insert into " + tbl + "(" + columns + ") values("
                + content + ")";
        if (BuildConfig.DEBUG) {
            Log.d(this.getClass().getName(), sql);
        }

        db.execSQL(sql);
    }

    public void updateSQL(String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (BuildConfig.DEBUG) {
            Log.d(this.getClass().getName(), sql);
        }
        db.execSQL(sql);
    }

    public void deleteSQL(String tbl, String where, boolean all) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql;
        if (all)
            sql = "delete from " + tbl;
        else
            sql = "delete from " + tbl + " where " + where;
        if (BuildConfig.DEBUG) {
            Log.d(this.getClass().getName(), sql);
        }

        db.execSQL(sql);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.close();
    }

    /**
     * Getting All Contacts
     **/

    /*public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setFName(cursor.getString(1));
                contact.setImage(cursor.getBlob(2));


                // Adding contact to list
                contactList.add(contact);
            } while (((Cursor) cursor).moveToNext());
        }

        // return contact list
        return contactList;
    }*/






    /**
     * Updating single contact
     **/

    /*public int updateContact(Contact contact, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, contact.getFName());
        values.put(KEY_PHOTO, contact.getImage());


        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
*/
    /**
     * Deleting single contact
     **/

    /*public void deleteContact(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(Id)});
        db.close();
    }*/

}
