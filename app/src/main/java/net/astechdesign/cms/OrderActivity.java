package net.astechdesign.cms;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import net.astechdesign.cms.database.DBHelper;
import net.astechdesign.cms.database.tables.OrdersTable;
import net.astechdesign.cms.model.Customer;
import net.astechdesign.cms.model.Order;
import net.astechdesign.cms.repo.OrderRepository;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private SimpleCursorAdapter productAdapter;
    private OrderRepository orderRepository;
    private String customerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        int customerId = (int) getIntent().getExtras().get("id");
        customerName = (String) getIntent().getExtras().get("customerName");
        ((TextView) findViewById(R.id.customer_name)).setText(customerName);

        orderRepository = new OrderRepository(new DBHelper(this));
        ListView orderList = (ListView) findViewById(R.id.orderList);
        Cursor ordersCursor = orderRepository.get(new Customer(customerId, customerName));

        String[] fromColumns = {OrdersTable.CUSTOMER_NAME};
        int[] toViews = {android.R.id.text1};
        orderList.setAdapter(new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, ordersCursor, fromColumns, toViews, 0));
    }

    private class OrderListAdapter extends ArrayAdapter<Order> {
        private final List<Order> orders;

        public OrderListAdapter(Context context, int resource, List<Order> orders) {
            super(context, resource, orders);
            this.orders = orders;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView != null)
                ((TextView)convertView).setText(orders.get(position).toString());
            return super.getView(position, convertView, parent);
        }
    }
}
