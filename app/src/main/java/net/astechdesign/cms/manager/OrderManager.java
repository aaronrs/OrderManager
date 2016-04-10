package net.astechdesign.cms.manager;

import android.app.Activity;
import android.database.Cursor;
import android.widget.TextView;

import net.astechdesign.cms.R;
import net.astechdesign.cms.database.DBHelper;
import net.astechdesign.cms.model.Invoice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderManager {
    private DBHelper dbHelper;

    public OrderManager(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void addOrder(Activity activity, String contactName) {
        Invoice invoice = Invoice.getInstance(activity);
        String product = ((TextView) activity.findViewById(R.id.new_order_product)).getText().toString();
        String batch = ((TextView) activity.findViewById(R.id.new_order_batch)).getText().toString();
        String price = ((TextView) activity.findViewById(R.id.new_order_price)).getText().toString();
        String quantity = ((TextView) activity.findViewById(R.id.new_order_quantity)).getText().toString();
        String deliveryDate = ((TextView) activity.findViewById(R.id.new_order_deliveryDate)).getText().toString();

        dbHelper.saveOrder(contactName, new Date(), invoice, product, batch, convertInt(quantity), convertFloat(price), convertDate(deliveryDate));
    }

    private int convertInt(String text) {
        return Integer.parseInt(text);
    }

    private float convertFloat(String text) {
        return Float.parseFloat(text);
    }

    private Date convertDate(String deliveryDate) {
        try {
            return new SimpleDateFormat(DBHelper.DB_DATE_FORMAT).parse(deliveryDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public Cursor getProductsCursor(String filter) {
        return dbHelper.getProductsCursor(filter);
    }
}
