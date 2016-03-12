package net.astechdesign.cms.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;

import net.astechdesign.cms.database.tables.OrdersTable;
import net.astechdesign.cms.database.tables.ProductsTable;

import static net.astechdesign.cms.database.tables.OrdersTable.*;

public class OrdersListAdapter extends SimpleCursorAdapter {

    private static final String[] from = {PRODUCT_NAME, PRODUCT_QUANTITY, PRODUCT_PRICE, DELIVERY_DATE};
    private static final int[] to = {android.R.id.text1, android.R.id.text1, android.R.id.text1, android.R.id.text1};

    public OrdersListAdapter(final Activity activity) {
        super(activity, android.R.layout.simple_spinner_dropdown_item, null, from, to, 0);

        setFilterQueryProvider(
                new FilterQueryProvider() {
                    public Cursor runQuery(CharSequence str) {
                        return str == null ? null : ((NewOrderFragment.OrderDataCallback) activity).getOrderCursor(str.toString(), null);
                    }
                }
        );

        setCursorToStringConverter(
                new CursorToStringConverter() {
                    public CharSequence convertToString(Cursor cur) {
                        int index = cur.getColumnIndex(PRODUCT_NAME);
                        return cur.getString(index);
                    }
                }
        );
    }
}