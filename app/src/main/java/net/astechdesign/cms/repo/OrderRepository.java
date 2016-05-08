package net.astechdesign.cms.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.astechdesign.cms.database.DBHelper;
import net.astechdesign.cms.database.tables.OrdersTable;
import net.astechdesign.cms.model.Customer;
import net.astechdesign.cms.model.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import static net.astechdesign.cms.database.tables.OrdersTable.TABLE_NAME;

public class OrderRepository {

    private final DBHelper dbHelper;

    public OrderRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public Cursor get(Customer customer) {
        SQLiteDatabase rDB = dbHelper.getReadableDatabase();
//        return rDB.query(TABLE_NAME, null, OrdersTable.CUSTOMER_NAME + " = " + customer.getId() , null, null, null, null);
        return rDB.query("table", new String[]{"columns"}, "selection", new String[]{"selectionArgs"}, "groupBy", "having", "orderBy", "limit");
    }

    public void insert() {
        SQLiteDatabase wDB = dbHelper.getWritableDatabase();
        ContentValues value = null;
        wDB.insert("table", "nullColumnHack", value);
    }

    private List<Order> testOrders(String customer) {
        List<Order> orders = new ArrayList<>();
        Order order = new Order.Builder()
                .withCustomer(customer)
                .build();
        orders.add(order);

        order = new Order.Builder()
                .withCustomer(customer)
                .build();
        orders.add(order);
        return orders;
    }

    public void saveOrder(int customerId, String invoice, Date date, String productName, String batch, int quantity, float price) {
//        ContentValues values = getInsertValues(customerId, invoice, date, productName, batch, quantity, price, price * quantity);
//        SQLiteDatabase wDB = dbHelper.getWritableDatabase();
//        wDB.insert(ProductsTable.PRODUCTS_TABLE_NAME, null, values);
    }
}
