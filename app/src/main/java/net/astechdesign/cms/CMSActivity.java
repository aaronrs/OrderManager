package net.astechdesign.cms;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;

import net.astechdesign.cms.database.DBHelper;
import net.astechdesign.cms.fragments.InvoiceListFragment;
/*
* Working Copy
*/
public class CMSActivity extends Activity implements InvoiceListFragment.OnLoadFragment {

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
}

