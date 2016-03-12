package net.astechdesign.cms.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

import static net.astechdesign.cms.database.tables.OrdersTable.createOrder;
import static net.astechdesign.cms.database.tables.OrdersTable.createOrdersTable;
import static net.astechdesign.cms.database.tables.OrdersTable.upgradeOrdersTable;
import static net.astechdesign.cms.database.tables.ProductsTable.createProductsTable;
import static net.astechdesign.cms.database.tables.ProductsTable.upgradeProductsTable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "orders.db";
    public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DB_TIME_FORMAT = "HH:mm:ss";

    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createProductsTable(db, context.getAssets());
        createOrdersTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        upgradeProductsTable(db, oldVersion, newVersion);
        upgradeOrdersTable(db, oldVersion, newVersion);
    }

    public Cursor getProductsCursor(String filter) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(QueryHelper.getProductsFilterQuery(filter), null);
    }

    public Cursor getOrdersCursor(String name, Date orderDate) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(QueryHelper.getOrdersFilterQuery(name, orderDate), null);
    }

    public void saveOrder(String customer, Date orderDate, String invoice, String product, String batch, int quantity, float price, Date deliveryDate) {
        createOrder(getWritableDatabase(), customer, orderDate, invoice, product, batch, quantity, price, deliveryDate);
    }
}
