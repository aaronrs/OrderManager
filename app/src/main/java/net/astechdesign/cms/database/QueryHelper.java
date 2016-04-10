package net.astechdesign.cms.database;

import android.provider.BaseColumns;

import net.astechdesign.cms.database.tables.OrdersTable;
import net.astechdesign.cms.database.tables.ProductsTable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryHelper {

    public static final String PRODUCTS_FILTER_QUERY =
            "SELECT " + BaseColumns._ID + ", " + ProductsTable.PRODUCT_NAME
                    + " FROM " + ProductsTable.TABLE_NAME
                    + " WHERE " + ProductsTable.PRODUCT_NAME + "%s"
                    + " ORDER BY " + ProductsTable.PRODUCT_NAME;

    public static String getProductsFilterQuery(String filter) {
        String like = " LIKE '" + filter + "%' OR "+ ProductsTable.PRODUCT_NAME + "  LIKE '% " + filter + "%'";
        return String.format(PRODUCTS_FILTER_QUERY, like);
    }

    public static final String ORDERS_FILTER_QUERY =
            "SELECT " + BaseColumns._ID + ", " + OrdersTable.PRODUCT_NAME
                    + " FROM " + OrdersTable.TABLE_NAME
                    + " /* WHERE " + OrdersTable.CUSTOMER_NAME + "='%s'"
                    + " AND " + OrdersTable.ORDER_DATE + "='%s' */"
                    + " ORDER BY " + BaseColumns._ID;

    public static String getOrdersFilterQuery(String customerName, Date orderDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DBHelper.DB_DATE_FORMAT);
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
                    + " FROM " + OrdersTable.TABLE_NAME;

    public static String getOrdersQuery() {
        return ORDERS_QUERY;
    }

}
