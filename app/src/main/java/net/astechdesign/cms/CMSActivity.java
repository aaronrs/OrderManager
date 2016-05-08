package net.astechdesign.cms;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import net.astechdesign.cms.database.DBHelper;
import net.astechdesign.cms.fragments.InvoiceListFragment;
import net.astechdesign.cms.model.Order;

public class CMSActivity extends Activity implements InvoiceListFragment.OnListFragmentInteractionListener {

    private static final int RESULT_PICK_CONTACT = 90001;

    private  DBHelper dbHelper;

    private String contactName;
    private InvoiceListFragment invoiceListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(this);
        setContentView(R.layout.activity_main);

        invoiceListFragment = new InvoiceListFragment();
    }

    public Cursor getInvoiceCursor() {
        return null;
    }

    public Cursor getOrderCursor() {
        return null;
    }

    @Override
    public void onListFragmentInteraction(Order order) {

    }

    public void selectContact(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, RESULT_PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactChosen(data);
                    showInvoices();
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
            int  nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY);
            int  phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
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

    private void showInvoices() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_contents, invoiceListFragment);
        fragmentTransaction.commit();
    }
}
