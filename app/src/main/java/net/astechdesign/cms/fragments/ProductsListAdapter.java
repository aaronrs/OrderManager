package net.astechdesign.cms.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;

import net.astechdesign.cms.database.tables.ProductsTable;

public class ProductsListAdapter extends SimpleCursorAdapter {

    private static final String[] from = {ProductsTable.PRODUCT_NAME};
    private static final int[] to = {android.R.id.text1};

    public ProductsListAdapter(final Activity activity) {
        super(activity, android.R.layout.simple_spinner_dropdown_item, null, from, to, 0);

        setFilterQueryProvider(
                new FilterQueryProvider() {
                    public Cursor runQuery(CharSequence str) {
                        return str == null ? null : ((NewOrderFragment.ProductDataCallback) activity).getProductCursor(str.toString());
                    }
                }
        );

        setCursorToStringConverter(
                new SimpleCursorAdapter.CursorToStringConverter() {
                    public CharSequence convertToString(Cursor cur) {
                        int index = cur.getColumnIndex(ProductsTable.PRODUCT_NAME);
                        return cur.getString(index);
                    }
                }
        );
    }
}