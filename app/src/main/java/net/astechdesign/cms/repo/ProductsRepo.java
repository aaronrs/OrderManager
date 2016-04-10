package net.astechdesign.cms.repo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.astechdesign.cms.database.DBHelper;

import static net.astechdesign.cms.database.tables.ProductsTable.TABLE_NAME;
import static net.astechdesign.cms.database.tables.ProductsTable.PRODUCT_NAME;

public class ProductsRepo {

    private final DBHelper dbHelper;

    public ProductsRepo(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public Cursor getProducts() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT " + PRODUCT_NAME + " FROM " + TABLE_NAME + " ORDER BY " + PRODUCT_NAME, null);
    }
}
