package net.astechdesign.cms.view;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import net.astechdesign.cms.R;

import static net.astechdesign.cms.database.tables.ProductsTable.PRODUCT_NAME;
import static net.astechdesign.cms.database.tables.ProductsTable.PRODUCT_PRICE;

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, CursorAdapter.IGNORE_ITEM_VIEW_TYPE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView productName = (TextView) view.findViewById(R.id.productName);
        productName.setText(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_NAME)));

        TextView productPrice = (TextView) view.findViewById(R.id.productPrice);
        productPrice.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(PRODUCT_PRICE))));
    }
}
