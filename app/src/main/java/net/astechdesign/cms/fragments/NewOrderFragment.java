package net.astechdesign.cms.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import net.astechdesign.cms.R;
import net.astechdesign.cms.database.tables.OrdersTable;

import java.util.Date;

public class NewOrderFragment extends Fragment {

    private TextView priceEditText;
    private TextView quantityView;
    private TextView cost;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AutoCompleteTextView productsSpinner = (AutoCompleteTextView) getActivity().findViewById(R.id.productsDropdown);
        productsSpinner.setAdapter(new ProductsListAdapter(getActivity()));

        priceEditText = (TextView) getActivity().findViewById(R.id.priceEditText);
        quantityView = (TextView) getActivity().findViewById(R.id.quantityEditText);
        cost = (TextView) getActivity().findViewById(R.id.costEditText);


        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String priceText = priceEditText.getText().toString();
                String quantitytext = quantityView.getText().toString();
                if (priceText.length() > 0 && quantitytext.length() > 0) {
                    float price = Float.parseFloat(priceText);
                    int quantity = Integer.parseInt(quantitytext);
                    cost.setText(Float.toString(price * quantity));
                } else {
                    cost.setText("");
                }
            }
        };
        priceEditText.setOnFocusChangeListener(onFocusChangeListener);
        quantityView.setOnFocusChangeListener(onFocusChangeListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_order_view, container, false);
    }

    public interface ProductDataCallback {
        Cursor getProductCursor(String filter);
    }

    public interface OrderDataCallback {
        Cursor getOrderCursor(String name, Date orderDate);
    }

    private void listOrders() {
        ListView lineItems = (ListView) getActivity().findViewById(R.id.lineItemsListView);
        String[] from = {OrdersTable.PRODUCT_NAME};
        int[] to = {android.R.id.text1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, null, from, to, 0);
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence str) {
                return str == null ? null : ((ProductDataCallback) getActivity()).getProductCursor(str.toString());
            } });

        lineItems.setAdapter(adapter);
    }
}
