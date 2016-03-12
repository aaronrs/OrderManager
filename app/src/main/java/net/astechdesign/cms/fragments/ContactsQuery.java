package net.astechdesign.cms.fragments;

import android.net.Uri;
import android.provider.ContactsContract;

public interface ContactsQuery {

    // An identifier for the loader
    final static int QUERY_ID = 1;
    final static Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
    final static Uri FILTER_URI = ContactsContract.Contacts.CONTENT_FILTER_URI;
    final static String SELECTION = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;
    final static String SORT_ORDER = ContactsContract.Contacts.SORT_KEY_PRIMARY;

    final static String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
            SORT_ORDER,
    };

    final static int ID = 0;
    final static int LOOKUP_KEY = 1;
    final static int DISPLAY_NAME = 2;
    final static int PHOTO_THUMBNAIL_DATA = 3;
    final static int SORT_KEY = 4;
}
