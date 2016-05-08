package net.astechdesign.cms;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import net.astechdesign.cms.database.DBHelper;
import net.astechdesign.cms.database.tables.CMSTable;
import net.astechdesign.cms.fragments.ContactsListFragment;
import net.astechdesign.cms.fragments.NewOrderFragment;
import net.astechdesign.cms.fragments.OrderDatePickerFragment;
import net.astechdesign.cms.fragments.OrdersFragment;
import net.astechdesign.cms.fragments.TodosFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.provider.CalendarContract.EXTRA_EVENT_BEGIN_TIME;
import static android.provider.CalendarContract.EXTRA_EVENT_END_TIME;
import static android.provider.CalendarContract.Events;
import static android.provider.ContactsContract.CommonDataKinds.Phone;

public class OldCMSActivity extends Activity implements ContactsListFragment.OnContactsInteractionListener,
        NewOrderFragment.ProductDataCallback,
        NewOrderFragment.OrderDataCallback {

    private static final int RESULT_PICK_CONTACT = 90001;
    private TodosFragment todosFragment;
    private OrdersFragment ordersFragment;
    private NewOrderFragment newOrderFragment;

    private DBHelper dbHelper;
    private String contactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        todosFragment = new TodosFragment();
        ordersFragment = new OrdersFragment();
        newOrderFragment = new NewOrderFragment();

//        showTodos();
        showNewOrder();
    }

    public void showTodos() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_contents, todosFragment);
        fragmentTransaction.commit();
    }

    public void selectContact(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, Phone.CONTENT_URI);
        startActivityForResult(intent, RESULT_PICK_CONTACT);
    }

    public void showOrders() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_contents, ordersFragment);
        fragmentTransaction.commit();
    }

    public void showNewOrder() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_contents, newOrderFragment);
        fragmentTransaction.commit();
    }

    public void addContact(View view) {
        Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
        intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
        startActivity(intent);
    }

    public void addEvent(View view) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.setTimeInMillis(System.currentTimeMillis());
        Calendar endTime = Calendar.getInstance();
        endTime.setTimeInMillis(System.currentTimeMillis() + 360000);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(Events.CONTENT_URI)
                .putExtra(EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(Events.TITLE, "Yoga")
                .putExtra(Events.DESCRIPTION, "Group class")
                .putExtra(Events.EVENT_LOCATION, "The gym")
                .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactChosen(data);
                    showOrders();
                    break;
            }
        }
    }

    private void contactChosen(Intent data) {
        Cursor cursor;
        try {
            String phoneNo;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int  nameIndex = cursor.getColumnIndex(Phone.DISPLAY_NAME_PRIMARY);
            int  phoneIndex = cursor.getColumnIndex(Phone.NUMBER);
            phoneNo = cursor.getString(phoneIndex);
            contactName = cursor.getString(nameIndex);
            updateContactView(contactName, phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateContactView(String name, String phoneNo) {
        TextView contactName = (TextView) findViewById(R.id.contactName);
        TextView telephone = (TextView) findViewById(R.id.contactTelephone);
        contactName.setText(name);
        telephone.setText(phoneNo);
    }

    public void removeContact(View view) {
        updateContactView("select contact", "");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_contents, todosFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onContactSelected(Uri contactUri) {

    }

    @Override
    public void onSelectionCleared() {

    }


    public void newOrders(View view) {
        Intent intent = new Intent(this, NewOrderFragment.class);
        intent.putExtra("customerName", ((TextView) findViewById(R.id.contactName)).getText());
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_contents, newOrderFragment);
        fragmentTransaction.commit();
    }

    public void saveOrder(View view) {
        String invoice = getEntryText(R.id.new_order_invoice);
        String product = getEntryText(R.id.new_order_product);
        String batch = getEntryText(R.id.new_order_batch);
        String price = getEntryText(R.id.new_order_price);
        String quantity = getEntryText(R.id.new_order_quantity);
        String deliveryDate = getEntryText(R.id.new_order_deliveryDate);

//        dbHelper.saveOrder(contactName, new Date(), invoice, product, batch, convertInt(quantity), convertFloat(price), convertDate(deliveryDate));
    }

    private String getEntryText(int viewName) {
        return ((TextView) findViewById(viewName)).getText().toString();
    }

    private int convertInt(String text) {
        return Integer.parseInt(text);
    }

    private float convertFloat(String text) {
        return Float.parseFloat(text);
    }

    private Date convertDate(String deliveryDate) {
        try {
            return new SimpleDateFormat(CMSTable.DB_DATE_FORMAT).parse(deliveryDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cursor getProductCursor(String filter) {
        return dbHelper.getProductsCursor(filter);
    }

    @Override
    public Cursor getOrderCursor(String name, Date orderDate) {
        return dbHelper.getOrdersCursor(name, orderDate);
    }
}
