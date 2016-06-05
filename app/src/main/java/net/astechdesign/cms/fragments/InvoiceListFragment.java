package net.astechdesign.cms.fragments;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import net.astechdesign.cms.R;
import net.astechdesign.cms.database.tables.InvoicesTable;

public class InvoiceListFragment extends ListFragment {

    private Cursor invoicesCursor;

    public InvoiceListFragment() {
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ((InvoicesFragmentActions)getActivity()).showInvoice(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] from = {InvoicesTable.INVOICE_NUMBER, InvoicesTable.CUSTOMER_NAME, InvoicesTable.ORDER_DATE};
        int[] to = {R.id.invoice_id, R.id.customer_name, R.id.order_date};
        invoicesCursor = ((InvoicesFragmentActions) getActivity()).getInvoicesCursor();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.invoice, invoicesCursor, from, to, 0);
        setListAdapter(cursorAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        getListView().addHeaderView(inflater.inflate(R.layout.invoices_header, null));
        super.onViewCreated(view, savedInstanceState);
    }

    public interface InvoicesFragmentActions {
        Cursor getInvoicesCursor();
        void showInvoice(int position);
    }

}
