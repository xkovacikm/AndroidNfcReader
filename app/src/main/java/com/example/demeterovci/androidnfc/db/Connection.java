package com.example.demeterovci.androidnfc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 27. 10. 2017.
 */

public class Connection extends SQLiteOpenHelper {
    // All Static variables

    // Database instance
    private static Connection sInstance;
    private static Context context;

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "nfc";

    // Contacts table name
    private static final String TABLE_MENU = "menu";
    private static final String TABLE_CUSTOMER = "customer";

    // Column names
    private static final String MENU_ID = "id";
    private static final String MENU_NAME = "name";
    private static final String MENU_COST = "cost";
    private static final String CUSTOMER_ID = "id";
    private static final String CUSTOMER_CARD_ID = "card_id";
    private static final String CUSTOMER_MONEY = "money";

    public static synchronized Connection getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new Connection(context.getApplicationContext());
        }
        return sInstance;
    }

    public Connection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Customer CRUD
    public long addCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put( CUSTOMER_ID, customer.getId() );
        values.put( CUSTOMER_CARD_ID, customer.getCard_id() );
        values.put( CUSTOMER_MONEY, customer.getMoney() );

        // Inserting Row
        long result = db.insert(TABLE_CUSTOMER, null, values);
        db.close(); // Closing database connection
        return result;
    }

    public Customer getCustomerById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_CUSTOMER,
                new String[] { CUSTOMER_ID, CUSTOMER_CARD_ID, CUSTOMER_MONEY },
                CUSTOMER_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Customer customer = new Customer(
                Integer.parseInt( cursor.getString(0) ),
                cursor.getString(1),
                Double.parseDouble( cursor.getString(2) )
        );
        // return contact
        return customer;
    }

    public Customer getCustomerByCardId(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_CUSTOMER,
                new String[] { CUSTOMER_ID, CUSTOMER_CARD_ID, CUSTOMER_MONEY },
                CUSTOMER_CARD_ID + "=?",
                new String[] { id }, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        else{
            return new Customer();
        }

        Customer customer = new Customer(
                Integer.parseInt( cursor.getString(0) ),
                cursor.getString(1),
                Double.parseDouble( cursor.getString(2) )
        );
        // return contact
        return customer;
    }

    public List<Customer> getCustomers() {
        List<Customer> contactList = new ArrayList<Customer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setId(Integer.parseInt( cursor.getString(0)) );
                customer.setCard_id( cursor.getString(1) );
                customer.setMoney( Double.parseDouble( cursor.getString(2) ) );
                // Adding contact to list
                contactList.add(customer);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public int updateCustomer(Customer customer) {
        int result;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CUSTOMER_CARD_ID, customer.getCard_id());
        values.put(CUSTOMER_MONEY, customer.getMoney());

        // updating row
        result = db.update(TABLE_CUSTOMER, values, CUSTOMER_ID + " = ?",
                new String[] { String.valueOf(customer.getId()) });

        return result;
    }

    public void deleteCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CUSTOMER, CUSTOMER_ID + " = ?",
                new String[] { String.valueOf(customer.getId()) });
        db.close();
    }

    // Menu CRUD
    public long addMenu(Menu menu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put( MENU_NAME, menu.getName() );
        values.put( MENU_COST, menu.getCost() );

        // Inserting Row
        long result = db.insert(TABLE_MENU, null, values);

        db.close(); // Closing database connection
        return result;
    }

    public Menu getMenuById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_MENU,
                new String[] { MENU_ID, MENU_NAME, MENU_COST },
                MENU_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Menu menu = new Menu(
                Integer.parseInt( cursor.getString(0) ),
                cursor.getString(1),
                Double.parseDouble( cursor.getString(2) )
        );
        // return contact
        return menu;
    }

    public Menu getMenuByCardId(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_MENU,
                new String[] { MENU_ID, MENU_NAME, MENU_COST },
                MENU_NAME + "=?",
                new String[] { id }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Menu menu = new Menu(
                Integer.parseInt( cursor.getString(0) ),
                cursor.getString(1),
                Double.parseDouble( cursor.getString(2) )
        );
        // return contact
        return menu;
    }

    public List<Menu> getMenus() {
        List<Menu> menuList = new ArrayList<Menu>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MENU;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Menu menu = new Menu();
                menu.setId(Integer.parseInt( cursor.getString(0)) );
                menu.setName( cursor.getString(1) );
                menu.setCost( Double.parseDouble( cursor.getString(2) ) );
                // Adding contact to list
                menuList.add(menu);
            } while (cursor.moveToNext());
        }

        // return contact list
        return menuList;
    }

    public int updateMenu(Menu menu) {
        int result;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MENU_NAME, menu.getName());
        values.put(MENU_COST, menu.getCost());

        // updating row
        result = db.update(TABLE_MENU, values, MENU_ID + " = ?",
                new String[] { String.valueOf(menu.getId()) });

        return result;
    }

    public void deleteMenu(Menu menu) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENU, MENU_ID + " = ?",
                new String[] { String.valueOf(menu.getId()) });
        db.close();
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CUSTOMER + "("
                + CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + CUSTOMER_CARD_ID + " TEXT NOT NULL UNIQUE,"
                + CUSTOMER_MONEY + " DOUBLE NOT NULL" + ")";
        db.execSQL(CREATE_CUSTOMER_TABLE);

        String CREATE_MENU_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MENU + "("
                + MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + MENU_NAME + " TEXT NOT NULL,"
                + MENU_COST + " DOUBLE NOT NULL" + ")";
        db.execSQL(CREATE_MENU_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);

        // Create tables again
        onCreate(db);
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Connection.context = context;
    }
}
