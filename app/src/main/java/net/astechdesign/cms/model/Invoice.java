package net.astechdesign.cms.model;

import android.app.Activity;
import android.widget.TextView;

import net.astechdesign.cms.R;

public class Invoice {

    private static int id = R.id.new_order_invoice;

    private String invoiceNumber;

    private Invoice(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public static Invoice getInstance(Activity cmsActivity) {
        String invoiceNumber = ((TextView) cmsActivity.findViewById(id)).getText().toString();
        return new Invoice(invoiceNumber);
    }

    public String get() {
        return invoiceNumber;
    }
}
