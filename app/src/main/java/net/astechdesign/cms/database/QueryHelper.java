package net.astechdesign.cms.database;

import android.provider.BaseColumns;

import net.astechdesign.cms.database.tables.CMSTable;
import net.astechdesign.cms.database.tables.OrdersTable;
import net.astechdesign.cms.database.tables.ProductsTable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryHelper {

    private static final OrdersTable ordersTable = new OrdersTable();


    public static final String ORDERS_FILTER_QUERY =
            "SELECT " + BaseColumns._ID + ", " + OrdersTable.PRODUCT_NAME
                    + " FROM " + ordersTable.getTableName()
                    + " /* WHERE " + OrdersTable.CUSTOMER_NAME + "='%s'"
                    + " AND " + OrdersTable.ORDER_DATE + "='%s' */"
                    + " ORDER BY " + BaseColumns._ID;

    public static String getOrdersFilterQuery(String customerName, Date orderDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CMSTable.DB_DATE_FORMAT);
        return String.format(ORDERS_FILTER_QUERY, customerName, dateFormat.format(orderDate));
    }

    public static final String ORDERS_QUERY =
            "SELECT " + BaseColumns._ID + ", "
                    + OrdersTable.CUSTOMER_NAME + ", "
                    + OrdersTable.ORDER_DATE + ", "
                    + OrdersTable.INVOICE_NO + ", "
                    + OrdersTable.PRODUCT_NAME + ", "
                    + OrdersTable.PRODUCT_BATCH + ", "
                    + OrdersTable.PRODUCT_QUANTITY + ", "
                    + OrdersTable.PRODUCT_PRICE + ", "
                    + OrdersTable.DELIVERY_DATE
                    + " FROM " + ordersTable.getTableName();

    public static String getOrdersQuery() {
        return ORDERS_QUERY;
    }

}
