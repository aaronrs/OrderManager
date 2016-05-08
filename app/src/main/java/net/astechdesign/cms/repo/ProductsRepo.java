package net.astechdesign.cms.repo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.astechdesign.cms.database.DBHelper;
import net.astechdesign.cms.database.tables.ProductsTable;

public class ProductsRepo {

    private final DBHelper dbHelper;
    private final ProductsTable productsTable;

    public ProductsRepo(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
        productsTable = dbHelper.getProductsTable();
    }

    public Cursor getProducts() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT " + productsTable.PRODUCT_NAME + " FROM " + productsTable.getTableName() + " ORDER BY " + productsTable.PRODUCT_NAME, null);
    }
}
