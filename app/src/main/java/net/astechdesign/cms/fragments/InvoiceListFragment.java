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
        invoicesCursor.moveToPosition(position);
        int invoiceNumber = invoicesCursor.getInt(invoicesCursor.getColumnIndex(InvoicesTable.INVOICE_NUMBER));
        Toast.makeText(getActivity(), Integer.toString(invoiceNumber), Toast.LENGTH_SHORT).show();
        ((InvoiceListActions) getActivity()).invoiceSelected(invoiceNumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] from = {InvoicesTable.INVOICE_NUMBER, InvoicesTable.CUSTOMER_NAME, InvoicesTable.ORDER_DATE};
        int[] to = {R.id.invoice_id, R.id.customer_name, R.id.order_date};
        invoicesCursor = ((InvoiceListActions) getActivity()).getInvoicesCursor();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.invoice, invoicesCursor, from, to, 0);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                inflater.getContext(), android.R.layout.simple_list_item_1,
//                numbers_text);
        setListAdapter(cursorAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        return getListView();
//    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        String[] from = {InvoicesTable.INVOICE_NUMBER, InvoicesTable.CUSTOMER_NAME, InvoicesTable.ORDER_DATE};
//        int[] to = {R.id.invoice_id, R.id.customer_name, R.id.order_date};
//        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.invoice, ((OnLoadFragment)getActivity()).getInvoicesCursor(), from, to, 0);
//        ListView invoicesListView = getListView();
//        invoicesListView.setAdapter(cursorAdapter);
//    }

    public interface InvoiceListActions {
        Cursor getInvoicesCursor();
        void invoiceSelected(int invoice);
    }

}
