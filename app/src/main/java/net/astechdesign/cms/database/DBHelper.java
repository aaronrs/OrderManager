package net.astechdesign.cms.database;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import net.astechdesign.cms.CMSActivity;
import net.astechdesign.cms.database.tables.CMSTable;
import net.astechdesign.cms.database.tables.CustomersTable;
import net.astechdesign.cms.database.tables.InvoicesTable;
import net.astechdesign.cms.database.tables.ProductsTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "orders";

    private final List<CMSTable> tables = new ArrayList<>();
    private final InvoicesTable invoicesTable;
    private final ProductsTable productsTable;
    private final CustomersTable customersTable;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        invoicesTable = new InvoicesTable(context);
        productsTable = new ProductsTable(context);
        customersTable = new CustomersTable();
        tables.add(invoicesTable);
        tables.add(productsTable);
        tables.add(customersTable);
        resetDB();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (CMSTable table : tables) {
            table.create(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (CMSTable table : tables) {
            table.upgrade(db, oldVersion, newVersion);
        }
    }

    private void resetDB() {
        SQLiteDatabase db = getWritableDatabase();
        for (CMSTable table : tables) {
            db.execSQL("DROP TABLE IF EXISTS " + table.getTableName());
        }
        onCreate(db);
    }

    public Cursor getProductsCursor(String filter) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(productsTable.getProductsFilterQuery(filter), null);
    }

    public Cursor getOrdersCursor(String name, Date orderDate) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(QueryHelper.getOrdersFilterQuery(name, orderDate), null);
    }

    public ProductsTable getProductsTable() {
        return productsTable;
    }

    public Cursor getInvoicesCursor(String name) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(invoicesTable.getInvoicesQuery(name), null);
    }

    static final String[] CUSTOMER_SUMMARY_PROJECTION = new String[] {
                CustomersTable.CONTACT_ID,
                CustomersTable.CUSTOMER_NAME,
                CustomersTable.CUSTOMER_ADDRESS,
                CustomersTable.CUSTOMER_POSTCODE,
                CustomersTable.CUSTOMER_PHONE,
                CustomersTable.CUSTOMER_EMAIL
            };

    public CursorLoader getCustomerLoader(Context context) {
        return new CursorLoader(
                context,
                Uri.EMPTY,
                CUSTOMER_SUMMARY_PROJECTION,
                null,
                null,
                CustomersTable.CUSTOMER_NAME + " COLLATE LOCALIZED ASC");
    }
}
