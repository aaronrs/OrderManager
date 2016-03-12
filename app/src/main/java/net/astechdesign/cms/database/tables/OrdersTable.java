package net.astechdesign.cms.database.tables;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import net.astechdesign.cms.database.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.BaseColumns._ID;

public class OrdersTable {

    public static final String TABLE_NAME = "orders";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String ORDER_DATE = "order_date";
    public static final String INVOICE_NO = "invoice_no";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_BATCH = "batch";
    public static final String PRODUCT_QUANTITY = "quantity";
    public static final String PRODUCT_PRICE = "price";
    public static final String DELIVERY_DATE = "delivery_date";

    public static void createOrdersTable(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CUSTOMER_NAME + " INTEGER, " +
                ORDER_DATE + " DATE, " +
                INVOICE_NO + " TEXT, " +
                PRODUCT_NAME + " TEXT, " +
                PRODUCT_BATCH + " TEXT, " +
                PRODUCT_QUANTITY + " NUMBER, " +
                PRODUCT_PRICE + " NUMBER, " +
                DELIVERY_DATE + " DATE, " +
                ")";
        db.execSQL(query);
        initialise(db);
    }

    public static void upgradeOrdersTable(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private static void initialise(SQLiteDatabase db) {
        db.insert(TABLE_NAME, null, getInsertValues("Tom Thumb", new Date(),"inv01", "Product X", "batch", 1, 10f, new Date()));
    }

    public static ContentValues getInsertValues(String customer, Date orderDate, String invoice, String product, String batch, int quantity, float price, Date deliveryDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DBHelper.DB_DATE_FORMAT);

        ContentValues values = new ContentValues();
        values.put(CUSTOMER_NAME, customer);
        values.put(ORDER_DATE, dateFormat.format(orderDate));
        values.put(INVOICE_NO, invoice);
        values.put(PRODUCT_NAME, product);
        values.put(PRODUCT_BATCH, batch);
        values.put(PRODUCT_QUANTITY, quantity);
        values.put(PRODUCT_PRICE, price);
        values.put(DELIVERY_DATE, dateFormat.format(deliveryDate));
        return values;
    }

    public static void createOrder(SQLiteDatabase db, String customer, Date orderDate, String invoice, String product, String batch, int quantity, float price, Date deliveryDate) {
        db.insert(TABLE_NAME, null, getInsertValues(customer, orderDate, invoice, product, batch, quantity, price, deliveryDate));
    }
}
