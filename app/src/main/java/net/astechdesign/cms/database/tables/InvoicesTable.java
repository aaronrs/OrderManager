package net.astechdesign.cms.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.provider.BaseColumns._ID;

public class InvoicesTable implements CMSTable {

    public static final String INVOICE_NUMBER = "invoice_no";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String ORDER_DATE = "order_date";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_BATCH = "batch";
    public static final String PRODUCT_QUANTITY = "quantity";
    public static final String PRODUCT_PRICE = "price";
    public static final String DELIVERY_DATE = "delivery_date";

    private final AssetManager assetManager;

    public InvoicesTable(Context context) {
        assetManager = context.getAssets();
    }

    @Override
    public String getTableName() {
        return "invoices";
    }

    @Override
    public void create(SQLiteDatabase db) {
        String query = "CREATE TABLE " + getTableName() + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                INVOICE_NUMBER + " INTEGER, " +
                CUSTOMER_NAME + " INTEGER, " +
                ORDER_DATE + " DATE, " +
                PRODUCT_NAME + " TEXT, " +
                PRODUCT_BATCH + " TEXT, " +
                PRODUCT_QUANTITY + " NUMBER, " +
                PRODUCT_PRICE + " NUMBER, " +
                DELIVERY_DATE + " DATE" +
                ")";
        db.execSQL(query);
        initialise(db);
    }

    private void initialise(SQLiteDatabase db) {
        try {
            InputStream input = assetManager.open("db/testInvoices.txt");
            if (input == null) return;
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = br.readLine()) != null) {
                db.insert(getTableName(), null, getInsertValues(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

     }

    @Override
    public void upgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private ContentValues getInsertValues(String line) {
        ContentValues values = new ContentValues();
        String[] invoice = line.split("\\|");
        values.put(INVOICE_NUMBER, Integer.parseInt(invoice[0]));
        values.put(CUSTOMER_NAME, invoice[1]);
        values.put(ORDER_DATE, invoice[2]);
        values.put(PRODUCT_NAME, invoice[3]);
        values.put(PRODUCT_BATCH, invoice[4]);
        values.put(PRODUCT_QUANTITY, Integer.parseInt(invoice[5]));
        values.put(PRODUCT_PRICE, Double.parseDouble(invoice[6]));
        values.put(DELIVERY_DATE, invoice[7]);
        return values;
    }

    public String getInvoicesQuery() {
        return GET_INVOICES;
    }

    public final String GET_INVOICES =
            "SELECT DISTINCT " + BaseColumns._ID + ", "
                    + INVOICE_NUMBER + ", "
                    + CUSTOMER_NAME + ", "
                    + ORDER_DATE
                    + " FROM " + getTableName()
                    + " GROUP BY "
                    + INVOICE_NUMBER + ", "
                    + CUSTOMER_NAME + ", "
                    + ORDER_DATE
            ;


    public final String GET_INVOICE_DETAILS =
            "SELECT " + BaseColumns._ID + ", "
                    + CUSTOMER_NAME + ", "
                    + ORDER_DATE + ", "
                    + INVOICE_NUMBER + ", "
                    + PRODUCT_NAME + ", "
                    + PRODUCT_BATCH + ", "
                    + PRODUCT_QUANTITY + ", "
                    + PRODUCT_PRICE + ", "
                    + DELIVERY_DATE
                    + " FROM " + getTableName();


}
