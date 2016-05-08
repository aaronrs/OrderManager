package net.astechdesign.cms.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.astechdesign.cms.database.tables.CMSTable;
import net.astechdesign.cms.database.tables.InvoicesTable;
import net.astechdesign.cms.database.tables.ProductsTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "orders.db";

    private Context context;
    private final List<CMSTable> tables = new ArrayList<>();
    private final InvoicesTable invoicesTable;
    private final ProductsTable productsTable;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        invoicesTable = new InvoicesTable(context);
        productsTable = new ProductsTable(context);
        tables.add(invoicesTable);
        tables.add(productsTable);
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
}
