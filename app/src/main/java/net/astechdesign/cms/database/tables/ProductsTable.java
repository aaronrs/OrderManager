package net.astechdesign.cms.database.tables;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.provider.BaseColumns._ID;

public class ProductsTable {

    public static final String PRODUCTS_TABLE_PREFIX = "p";
    public static final String TABLE_NAME = "products";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_PRICE = "price";

    public static final String PRODUCT_DESCRIPTION = "description";

    public static void createProductsTable(SQLiteDatabase db, AssetManager assets) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRODUCT_NAME + " TEXT, " +
                PRODUCT_DESCRIPTION + " TEXT, " +
                PRODUCT_PRICE + " NUMBER " +
                ")";
        db.execSQL(query);

        initialise(db, assets);
    }

    public static void upgradeProductsTable(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void initialise(SQLiteDatabase db, AssetManager assets) {
        InputStream input = null;
        try {
            input = assets.open("db/products.sql");
            if (input == null) return;
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            String previous = "";
            while ((line = br.readLine()) != null) {
                String[] productInfo = line.split("\\|");
                if (! previous.equals(productInfo[2])) {
                    db.insert(ProductsTable.TABLE_NAME, null, getInsertValues(productInfo[2], "", 0));
                    previous = productInfo[2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ContentValues getInsertValues(String name, String description, float price) {
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, name);
        values.put(PRODUCT_DESCRIPTION, description);
        values.put(PRODUCT_PRICE, price);
        return values;
    }
}
