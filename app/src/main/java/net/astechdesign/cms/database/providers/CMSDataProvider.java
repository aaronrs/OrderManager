package net.astechdesign.cms.database.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import net.astechdesign.cms.database.DBHelper;
import net.astechdesign.cms.database.tables.ProductsTable;

public class CMSDataProvider extends ContentProvider {

    public static final String AUTHORITY = "net.astechdesign.cms.database.provider";
    private static final String PROVIDER_NAME = "net.astechdesign.cms.database.providers.CMSDataProvider";
    public static final String BASE_CONTENT = "content://" + PROVIDER_NAME + "/%s";

    private static final int PRODUCTS = 1;
    private static final int PRODUCTS_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, ProductsTable.PRODUCTS_TABLE_NAME, PRODUCTS);
        uriMatcher.addURI(AUTHORITY, ProductsTable.PRODUCTS_TABLE_NAME + "/#", PRODUCTS_ID);
    }

    public static Uri getUri(String table) {
        return Uri.parse(String.format(BASE_CONTENT, table));
    }

    private DBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String table;
        String groupBy = null;
        String having = null;
        String limit = null;
        switch (uriMatcher.match(uri)) {
            case PRODUCTS:

                table = ProductsTable.PRODUCTS_TABLE_NAME;
                break;
            case PRODUCTS_ID:
                table = ProductsTable.PRODUCTS_TABLE_NAME;
                selection = selection + "_ID = " + uri.getLastPathSegment();
                break;
            default:
            throw new IllegalArgumentException();
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(table, projection, selection, selectionArgs, groupBy, having, sortOrder, limit);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
