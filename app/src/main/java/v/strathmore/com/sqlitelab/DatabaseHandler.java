package v.strathmore.com.sqlitelab;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wainj on 19/10/2017.
 */

//sqliteopenhelper has mandatory methods

public class DatabaseHandler extends SQLiteOpenHelper {

    //DATABASE VERSION
    private  static final int DATABASE_VERSION = 1;

    //DATABASE NAME
    private static final String DATABASE_NAME = "contactsManager";

    //CONTACTS TABLE NAME
    private static final String TABLE_CONTACTS = "contacts";

    //CONTACTS TABLE COLUMN_NAMES
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //CREATING TABLE
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    //UPGRADING DATABASE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        //CREATE TABLE AGAIN
        onCreate(db);
    }

    //adding new contact creating class contact
    public void addContact(Contacts contacts) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contacts.getName()); //contact name
        values.put(KEY_PH_NO, contacts.getPhoneNumber()); //contact number

        //inserting row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); //closing db connection
    }

    //getting single contact
    public Contacts getContact (int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contacts contacts = new Contacts(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        //return contact
        return contacts;
    }

    //getting all contacts
    public List <Contacts> getAllContacts (){
        List <Contacts> contactList = new ArrayList<Contacts>();
        //select all query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to the list
        if (cursor.moveToFirst()) {
            do {
                Contacts contacts = new Contacts();
                contacts.setID(Integer.parseInt(cursor.getString(0)));
                contacts.setName(cursor.getString(1));
                contacts.setPhoneNumber(cursor.getString(2));
                //adding contacts to list
                contactList.add(contacts);
            } while (cursor.moveToNext());
            }
        //return contact list
        return  contactList;
        }

    //getting contacts count
    public int getContactsCount () {
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        //return count
        return cursor.getCount();
    }

    //updating a single contact
    public int updateContacts (Contacts contacts) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contacts.getName());
        values.put(KEY_PH_NO, contacts.getPhoneNumber());

        //updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ? ",
                new String[] { String.valueOf(contacts.getID()) });
    }

    //deleting single contact
    public void deleteContacts (Contacts contacts) {
        SQLiteDatabase db =  this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] {String.valueOf(contacts.getID()) });
        db.close();
    }
}
