package net.astechdesign.cms;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import net.astechdesign.cms.database.DBHelper;
import net.astechdesign.cms.model.Item;
import net.astechdesign.cms.repo.OrderRepository;
import net.astechdesign.cms.view.ProductCursorAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.astechdesign.cms.database.tables.ProductsTable.PRODUCTS_TABLE_NAME;
import static net.astechdesign.cms.database.tables.ProductsTable.PRODUCT_NAME;

public class NewOrderActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private Spinner spinner;
    private TextView productPriceView;
    private TextView productQuantityView;
    private List<Item> items = new ArrayList<Item>();
    private ListView itemList;

    private String customerName;
    private Date orderDate;
    private OrderRepository orderRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        customerName = (String) getIntent().getExtras().get("customerName");
        ((TextView) findViewById(R.id.customerName)).setText(customerName);

        orderDate = (Date) getIntent().getExtras().get("orderDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy");
        ((TextView) findViewById(R.id.orderDate)).setText(dateFormat.format(orderDate));

        spinner = (Spinner) findViewById(R.id.productSpinner);
        productPriceView = (TextView) findViewById(R.id.productPrice);
        productQuantityView = (TextView) findViewById(R.id.productQuantity);
        itemList = (ListView) findViewById(R.id.itemList);

        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + PRODUCT_NAME + " FROM " + PRODUCTS_TABLE_NAME, null);
        Spinner spinner = (Spinner) findViewById(R.id.productSpinner);
        spinner.setAdapter(new ProductCursorAdapter(this, cursor));
    }

    public void addItem(View v) {

        String productName = ((Cursor) spinner.getSelectedItem()).getString(1);
        String price = productPriceView.getText().toString();
        String quantity = productQuantityView.getText().toString();

        items.add(new Item(productName, price, quantity));
    }
}
