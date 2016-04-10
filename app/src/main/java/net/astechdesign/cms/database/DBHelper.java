package net.astechdesign.cms.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.astechdesign.cms.database.tables.OrdersTable;
import net.astechdesign.cms.database.tables.ProductsTable;
import net.astechdesign.cms.model.Invoice;

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
        resetDB();
        testDb();
    }

    private void resetDB() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + ProductsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OrdersTable.TABLE_NAME);
        onCreate(db);
    }

    private void testDb() {
        Date orderDate1 = new Date(2015,12,1);
        Date orderDate2 = new Date(2016,1,1);
        Date orderDate3 = new Date(2016,2,1);
        Date orderDate4 = new Date(2016,3,1);
        saveOrder("Tom", orderDate1, "inv01", "prod01", "batch01", 1, 10.0f, orderDate1);
        saveOrder("Tom", orderDate1, "inv01", "prod02", "batch02", 2, 20.0f, orderDate1);
        saveOrder("Tom", orderDate1, "inv01", "prod03", "batch03", 1, 30.0f, orderDate1);
        saveOrder("Sam", orderDate2, "inv02", "prod01", "batch04", 1, 10.0f, orderDate2);
        saveOrder("Sam", orderDate2, "inv02", "prod02", "batch05", 2, 20.0f, orderDate2);
        saveOrder("Sam", orderDate2, "inv02", "prod03", "batch06", 1, 30.0f, orderDate2);
        saveOrder("Joe", orderDate3, "inv03", "prod01", "batch07", 1, 10.0f, orderDate3);
        saveOrder("Joe", orderDate3, "inv03", "prod02", "batch08", 2, 20.0f, orderDate4);
        saveOrder("Joe", orderDate4, "inv04", "prod01", "batch09", 1, 20.0f, orderDate4);
        saveOrder("Joe", orderDate4, "inv04", "prod02", "batch10", 1, 30.0f, orderDate4);
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

    public void saveOrder(String customer, Date orderDate, Invoice invoice, String product, String batch, int quantity, float price, Date deliveryDate) {
        saveOrder(customer, orderDate, invoice.get(), product, batch, quantity, price, deliveryDate);
    }

    private void saveOrder(String customer, Date orderDate, String invoice, String product, String batch, int quantity, float price, Date deliveryDate) {
        createOrder(getWritableDatabase(), customer, orderDate, invoice, product, batch, quantity, price, deliveryDate);
    }

    public Cursor getOrders() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(QueryHelper.getOrdersQuery(), null);
    }
}
