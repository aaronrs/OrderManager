package net.astechdesign.cms;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import net.astechdesign.cms.database.DBHelper;
import net.astechdesign.cms.database.tables.InvoicesTable;
import net.astechdesign.cms.fragments.InvoiceListFragment;

public class CMSActivity extends Activity implements InvoiceListFragment.InvoicesFragmentActions {

    private  DBHelper dbHelper;
    private InvoiceListFragment invoiceListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(this);
        setContentView(R.layout.activity_main);

        invoiceListFragment = new InvoiceListFragment();

        showInvoices();
    }

    public void showInvoices() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_contents, invoiceListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public Cursor getInvoicesCursor() {
        return dbHelper.getInvoicesCursor();
    }

    @Override
    public void showInvoice(int position) {
        Cursor invoicesCursor = getInvoicesCursor();
        invoicesCursor.moveToPosition(position);
        Toast.makeText(this, invoicesCursor.getString(invoicesCursor.getColumnIndex(InvoicesTable.CUSTOMER_NAME)), Toast.LENGTH_SHORT).show();
    }
}

