package net.astechdesign.cms.repo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import net.astechdesign.cms.model.Customer;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID;
import static android.provider.ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME;

public class ContactRepo {

    private ContentResolver contentResolver;

    public ContactRepo(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public List<Customer> listContacts() {
        List<Customer> customerList = new ArrayList<>();
        String whereName = ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereNameParams = new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE };
        Cursor nameCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, whereName, whereNameParams, ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
        String contacts = "";
        while (nameCur.moveToNext()) {
//            String given = nameCur.getString(nameCur.getColumnIndex(GIVEN_NAME));
//            String family = nameCur.getString(nameCur.getColumnIndex(FAMILY_NAME));
            String display = nameCur.getString(nameCur.getColumnIndex(DISPLAY_NAME));
            int id = nameCur.getInt(nameCur.getColumnIndex(CONTACT_ID));
            customerList.add(new Customer(id, display));
        }
        nameCur.close();
        return customerList;
    }
}
