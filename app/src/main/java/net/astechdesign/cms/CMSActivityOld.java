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

import net.astechdesign.cms.fragments.ContactsListFragment;
import net.astechdesign.cms.fragments.OrderDatePickerFragment;
import net.astechdesign.cms.fragments.NewOrderFragment;
import net.astechdesign.cms.fragments.OrdersFragment;
import net.astechdesign.cms.fragments.TodosFragment;

import java.util.Calendar;

import static android.provider.CalendarContract.EXTRA_EVENT_BEGIN_TIME;
import static android.provider.CalendarContract.EXTRA_EVENT_END_TIME;
import static android.provider.CalendarContract.Events;
import static android.provider.ContactsContract.CommonDataKinds.Phone;

public class CMSActivityOld extends Activity implements ContactsListFragment.OnContactsInteractionListener {

    private static final int RESULT_PICK_CONTACT = 90001;
    private TodosFragment todosFragment;
    private OrdersFragment ordersFragment;
    private NewOrderFragment newOrderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        todosFragment = new TodosFragment();
        ordersFragment = new OrdersFragment();
        newOrderFragment = new NewOrderFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_contents, todosFragment);
        fragmentTransaction.commit();
    }

    public void selectContact(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, Phone.CONTENT_URI);
        startActivityForResult(intent, RESULT_PICK_CONTACT);
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
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_contents, ordersFragment);
                    fragmentTransaction.commit();
                    break;
            }
        }
    }

    private void contactChosen(Intent data) {
        Cursor cursor;
        try {
            String name;
            String phoneNo;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int  nameIndex = cursor.getColumnIndex(Phone.DISPLAY_NAME_PRIMARY);
            int  phoneIndex = cursor.getColumnIndex(Phone.NUMBER);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            updateContactView(name, phoneNo);
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

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new OrderDatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void addOrder(View view) {
        Intent intent = new Intent(this, NewOrderActivity.class);
        intent.putExtra("customerName", ((TextView) findViewById(R.id.contactName)).getText());
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_contents, newOrderFragment);
        fragmentTransaction.commit();
    }

}
