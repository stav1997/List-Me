/* Assignment: final
 *   Campus: Ashdod
 *   Author: Stav Shlomovich, ID: 316132802
 * */
package com.example.androidproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The type Product helper.
 */
public class ProductHelper extends SQLiteOpenHelper
    {
        /**
         * The constant DATABASE_NAME.
         */
        public static final String DATABASE_NAME = "lists.db";
        /**
         * The constant TABLE_LISTS.
         */
        public static final String TABLE_LISTS = "lists_table";
        /**
         * The constant TABLE_PRODUCTS.
         */
        public static final String TABLE_PRODUCTS = "products_table";
        /**
         * The constant DATABASEVERTION.
         */
        public static final int DATABASEVERTION = 1;

        /**
         * The constant COLUMN_PRODUCT_ID.
         */
        /*columns for the product table*/
        public static final String COLUMN_PRODUCT_ID = "productId";
        /**
         * The constant COLUMN_PRODUCT_NAME.
         */
        public static final String COLUMN_PRODUCT_NAME = "productName";
        /**
         * The constant COLUMN_PRODUCT_QUANTITY.
         */
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        /**
         * The constant COLUMN_PRODUCT_PRICE.
         */
        public static final String COLUMN_PRODUCT_PRICE = "price";
        /**
         * The constant COLUMN_PRODUCT_NOTE.
         */
        public static final String COLUMN_PRODUCT_NOTE = "note";
        /**
         * The constant COLUMN_PRODUCT_LIST_ID.
         */
        public static final String COLUMN_PRODUCT_LIST_ID = "listId";
        /**
         * The constant COLUMN_PRODUCT_STATE.
         */
        public static final String COLUMN_PRODUCT_STATE = "productState";


        /**
         * The constant COLUMN_LIST_NAME.
         */
        /*columns for the lists table*/
        public static final String COLUMN_LIST_NAME = "listName";
        /**
         * The constant COLUMN_LIST_ID.
         */
        public static final String COLUMN_LIST_ID = "listId";
        /**
         * The constant COLUMN_LIST_STATE.
         */
        public static final String COLUMN_LIST_STATE = "listState";


        /**
         * The Db.
         */
        SQLiteDatabase db;

        private static final String CREATE_TABLE_LIST = "CREATE TABLE IF NOT EXISTS"+ TABLE_LISTS+ " ("+COLUMN_LIST_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_LIST_NAME +"TEXT"+ COLUMN_LIST_STATE+ "TEXT" +");";

        private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE IF NOT EXISTS"+ TABLE_PRODUCTS+ " ("+COLUMN_PRODUCT_ID + "INTEGER,"+ COLUMN_PRODUCT_LIST_ID +"INTEGER," + COLUMN_PRODUCT_NAME +
        "TEXT,"+COLUMN_PRODUCT_QUANTITY+ "TEXT,"+ COLUMN_PRODUCT_PRICE+ "TEXT," +COLUMN_PRODUCT_NOTE+ "TEXT,"+ COLUMN_PRODUCT_STATE+ "TEXT, PRIMARY KEY("+COLUMN_PRODUCT_ID + ", "+COLUMN_PRODUCT_LIST_ID+"));";


        /**
         * Instantiates a new Product helper.
         *
         * @param context the context
         */
        public ProductHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASEVERTION);
    }

        @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_LISTS +" (listId INTEGER PRIMARY KEY AUTOINCREMENT,listName TEXT,listState TEXT)");
        db.execSQL("create table " + TABLE_PRODUCTS +" (productId INTEGER ,listId INTEGER,productName TEXT,quantity TEXT,price TEXT, note TEXT ,productState TEXT, PRIMARY KEY (productId, listId))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS "+TABLE_LISTS);
      db.execSQL("DROP TABLE IF EXISTS "+TABLE_PRODUCTS);
      onCreate(db);
     }

        /**
         * Update data.
         *
         * @param productId   the product id
         * @param productName the product name
         * @param quantity    the quantity
         * @param price       the price
         * @param note        the note
         * @param listId      the list id
         * @param state       the state
         */
        public void updateData(int productId, String productName, String quantity, String price, String note, int listId, String state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_PRODUCT_ID,productId);
        contentValues.put(COLUMN_PRODUCT_LIST_ID,listId);
        contentValues.put(COLUMN_PRODUCT_NAME,productName);
        contentValues.put(COLUMN_PRODUCT_QUANTITY,quantity);
        contentValues.put(COLUMN_PRODUCT_PRICE,price);
        contentValues.put(COLUMN_PRODUCT_NOTE,note);
        contentValues.put(COLUMN_PRODUCT_STATE,state);

        String id= String.valueOf(productId);
        String list= String.valueOf(listId);
         db.update(TABLE_PRODUCTS, contentValues, "productId ="+id+" and listId ="+list,null);
    }

        /**
         * Update list data.
         *
         * @param listName the list name
         * @param listId   the list id
         * @param state    the state
         */
        public void updateListData(String listName, int listId, String state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_LIST_ID,listId);
        contentValues.put(COLUMN_LIST_NAME,listName);
        contentValues.put(COLUMN_LIST_STATE,state);

        String id= String.valueOf(listId);
        db.update(TABLE_LISTS, contentValues, "listId ="+id,null);
    }

        /**
         * Delete products data.
         *
         * @param id the id
         */
        public void deleteProductsData(int id) {
        String list= String.valueOf(id);
        String state = "true";
        int c = db.delete(TABLE_PRODUCTS,COLUMN_PRODUCT_STATE + " = " + "\"" + state + "\" and "+COLUMN_PRODUCT_LIST_ID+ " = "+"\"" + id+ "\"",null);

    }

        /**
         * Delete lists data.
         */
        public void deleteListsData() {

        String state = "true";
        Cursor res = getAllLists();
        while (res.moveToNext()) {
            if (res.getString(2).matches("true")) {
                String listId= String.valueOf(res.getInt(0));
                int c = db.delete(TABLE_PRODUCTS,COLUMN_PRODUCT_LIST_ID+ " = "+"\"" + listId+ "\"",null);
            }
        }
        int d = db.delete(TABLE_LISTS,COLUMN_LIST_STATE + " = " + "\"" + state+ "\"",null);

    }

        /**
         * Insert new list boolean.
         *
         * @param name  the name
         * @param id    the id
         * @param state the state
         * @return the boolean
         */
        /*insert new list to the lists table*/
    public boolean insertNewList(String name,int id, String state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LIST_NAME,name);
        contentValues.put(COLUMN_LIST_ID,id);
        contentValues.put(COLUMN_LIST_STATE,state);

        long result = db.insert(TABLE_LISTS,null ,contentValues);
        if(result == -1){
            return false;
        }

        else{
            return true;
        }

    }

        /**
         * Insert new product boolean.
         * @param productId   the product id
         * @param productName the product name
         * @param quantity    the quantity
         * @param price       the price
         * @param note        the note
         * @param listId      the list id
         * @param state       the state
         * @return the boolean
         */
        /*insert new product to the chosen list on the product table*/
    public boolean insertNewProduct(int productId, String productName, String quantity, String price, String note, int listId, String state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_ID,productId);
        contentValues.put(COLUMN_PRODUCT_LIST_ID,listId);
        contentValues.put(COLUMN_PRODUCT_NAME,productName);
        contentValues.put(COLUMN_PRODUCT_QUANTITY,quantity);
        contentValues.put(COLUMN_PRODUCT_PRICE,price);
        contentValues.put(COLUMN_PRODUCT_NOTE,note);
        contentValues.put(COLUMN_PRODUCT_STATE,state);

        long result = db.insert(TABLE_PRODUCTS,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

        /**
         * Open.
         */
        public void open(){
        db = this.getWritableDatabase();
    }

        /**
         * Gets all products.
         *
         * @param listId the list id
         * @return the all products
         */
        public Cursor getAllProducts(int listId) {

        SQLiteDatabase db = this.getWritableDatabase();
        String id= String.valueOf(listId);
        Cursor res = db.rawQuery("select * from products_table where listId = ?", new String[]{id});
        return res;
    }


        /**
         * Gets all lists.
         *
         * @return the all lists
         */
        public Cursor getAllLists() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_LISTS,null);
        return res;
    }

        /**
         * Gets products.
         *
         * @param listId the list id
         * @return the products
         */
        public Cursor getProducts(int listId) {

        SQLiteDatabase db = this.getWritableDatabase();
        String id= String.valueOf(listId);
        String state = "false";
        Cursor res = db.rawQuery("select productName,quantity,price, note from products_table where "+COLUMN_PRODUCT_STATE + " = " + "\"" + state + "\" and "+COLUMN_PRODUCT_LIST_ID+ " = "+"\"" + id+ "\"",null);
        return res;
    }

        /**
         * Check product name boolean.
         *
         * @param listId the list id
         * @param name   the name
         * @return the boolean
         */
        public boolean checkProductName(int listId, String name){
        int i;
        String id= String.valueOf(listId);

        Cursor res = db.rawQuery("select productName from products_table where "+COLUMN_PRODUCT_NAME + " = " + "\"" + name + "\" and "+COLUMN_PRODUCT_LIST_ID+ " = "+"\"" + id+ "\"",null);
        i = res.getCount();
        if (i==0){
            return true;
        }
        else
            return false;
    }


        /**
         * Check list name boolean.
         *
         * @param name the name
         * @return the boolean
         */
        public boolean checkListName( String name){
        int i;

        Cursor res = db.rawQuery("select listName from lists_table where "+COLUMN_LIST_NAME + " = " + "\"" + name + "\"",null);
        i = res.getCount();
        if (i==0){
            return true;
        }
        else
            return false;
    }

        /**
         * Check product id boolean.
         *
         * @param listId    the list id
         * @param productId the product id
         * @return the boolean
         */
        public boolean checkProductId(int listId, int productId){
        int i;
        String lId= String.valueOf(listId);
        String pId= String.valueOf(productId);

        Cursor res = db.rawQuery("select productName from products_table where "+COLUMN_PRODUCT_ID + " = " + "\"" + pId + "\" and "+COLUMN_PRODUCT_LIST_ID+ " = "+"\"" + lId+ "\"",null);
        i = res.getCount();
        if (i==0){
            return true;
        }
        else
            return false;
    }

}
