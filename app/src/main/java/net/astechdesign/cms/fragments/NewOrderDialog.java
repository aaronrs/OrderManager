package net.astechdesign.cms.fragments;

import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import net.astechdesign.cms.CMSActivity;
import net.astechdesign.cms.R;

public class NewOrderDialog extends DialogFragment {

    public static NewOrderDialog newInstance() {
        NewOrderDialog frag = new NewOrderDialog();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_order_view, container, false);

        AutoCompleteTextView productsText = (AutoCompleteTextView)v.findViewById(R.id.new_order_product);
        productsText.setAdapter(new ProductsListAdapter(getActivity()));

        return v;
    }

    public interface ProductDataCallback {
        Cursor getProductCursor(String filter);
    }


}