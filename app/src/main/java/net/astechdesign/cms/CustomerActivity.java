package net.astechdesign.cms;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import net.astechdesign.cms.database.DBHelper;
import net.astechdesign.cms.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    // Contacts rows
    static final String[] PROJECTION = new String[] {ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME_PRIMARY};
    static final String SELECTION = "((" +
            ContactsContract.Data.DISPLAY_NAME_PRIMARY + " NOTNULL) AND (" +
            ContactsContract.Data.DISPLAY_NAME_PRIMARY + " != '' ))";

    private SimpleCursorAdapter mAdapter;
    private ListView customerList;
    private AutoCompleteTextView acCustomerName;
    private List<Customer> customers;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        dbHelper = new DBHelper(this);

        customerList = (ListView) findViewById(R.id.customerList);

        // For the cursor adapter, specify which columns go into which views
        String[] fromColumns = {ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME_PRIMARY};
        int[] toViews = {R.id.contactListId, R.id.contactListName};

//        Cursor contacts = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, " DISPLAY_NAME ASC")
        mAdapter = new SimpleCursorAdapter(this,
                R.layout.contact_list, null,
                fromColumns, toViews, 0) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }
        };
        customerList.setAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    private String findPhone(String[] id) {
        ArrayList<String> phones = new ArrayList<String>();

        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", id, null);

        while (cursor.moveToNext())
        {
            phones.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }

        cursor.close();
        return phones.get(0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Now createProductsTable and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(this, ContactsContract.Data.CONTENT_URI,
                PROJECTION, SELECTION, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }

    private void auto(AutoCompleteTextView actv) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null,
                new String[] {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER},
                new int[] {android.R.id.text1, android.R.id.text2, },
                0);
        FilterQueryProvider provider = new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                if (constraint == null) {
                    return null;
                }
                return getContentResolver().query(Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, constraint.toString()), null, null, null, null);
            }
        };
        adapter.setFilterQueryProvider(provider);
        actv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
        Intent orderActivity = new Intent(CustomerActivity.this, OrderActivity.class);
        orderActivity.putExtra("id", cursor.getInt(cursor.getColumnIndexOrThrow(ContactsContract.Data._ID)));
        orderActivity.putExtra("name", cursor.getInt(cursor.getColumnIndexOrThrow(ContactsContract.Data.DISPLAY_NAME)));
        startActivity(orderActivity);
    }
}
