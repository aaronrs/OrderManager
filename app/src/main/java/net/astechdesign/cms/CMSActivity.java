package net.astechdesign.cms;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import net.astechdesign.cms.database.DBHelper;
import net.astechdesign.cms.fragments.NewOrderDialog;
import net.astechdesign.cms.fragments.OrderFragment;
import net.astechdesign.cms.manager.OrderManager;
import net.astechdesign.cms.model.Invoice;
import net.astechdesign.cms.model.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CMSActivity extends Activity implements NewOrderDialog.ProductDataCallback, OrderFragment.OnListFragmentInteractionListener {

    private  DBHelper dbHelper;
    private OrderManager orderManager;
    private OrderFragment orderFragment;

    private String contactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(this);
        orderManager = new OrderManager(dbHelper);

        setContentView(R.layout.activity_main);

        orderFragment = OrderFragment.newInstance();

//        showOrder();
        showDialog();
    }

    void showDialog() {
        DialogFragment newFragment = NewOrderDialog.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }

    public void showOrder() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_contents, orderFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(Order item) {

    }

    public void addOrder() {
        orderManager.addOrder(this, contactName);
    }

    @Override
    public Cursor getProductCursor(String filter) {
        return orderManager.getProductsCursor(filter);
    }

    public Cursor getOrderCursor() {
        return dbHelper.getOrders();
    }
}
