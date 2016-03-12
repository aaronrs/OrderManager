package net.astechdesign.cms.fragments;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;

import net.astechdesign.cms.R;

public class ContactsListFragment2 extends ListFragment implements
        AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String STATE_PREVIOUSLY_SELECTED_KEY =
            "com.example.android.contactslist.ui.SELECTED_ITEM";

    private String mSearchTerm; // Stores the current search query term
    private int mPreviouslySelectedSearchItem = 0;

    private ContactsAdapter mAdapter;
    private OnContactsInteractionListener mOnContactSelectedListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mAdapter = new ContactsAdapter(getActivity());
        if (savedInstanceState != null) {
            mSearchTerm = savedInstanceState.getString(SearchManager.QUERY);
            mPreviouslySelectedSearchItem =
                    savedInstanceState.getInt(STATE_PREVIOUSLY_SELECTED_KEY, 0);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(this);
        if (mPreviouslySelectedSearchItem == 0) {
            // Initialize the loader, and create a loader identified by ContactsQuery.QUERY_ID
            getLoaderManager().initLoader(ContactsQuery.QUERY_ID, null, this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnContactSelectedListener = (OnContactsInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnContactsInteractionListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu items
        inflater.inflate(R.menu.contact_list_menu, menu);
        // Locate the search item
        MenuItem searchItem = menu.findItem(R.id.menu_search);

        // Retrieves the system search manager service
        final SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        // Retrieves the SearchView from the search menu item
        final SearchView searchView = (SearchView) searchItem.getActionView();

        // Assign searchable info to SearchView
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        // Set listeners for SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                // Nothing needs to happen when the user submits the search string
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the action bar search text has changed.  Updates
                // the search filter, and restarts the loader to do a new query
                // using the new search string.
                String newFilter = !TextUtils.isEmpty(newText) ? newText : null;

                // Don't do anything if the filter is empty
                if (mSearchTerm == null && newFilter == null) {
                    return true;
                }

                // Don't do anything if the new filter is the same as the current filter
                if (mSearchTerm != null && mSearchTerm.equals(newFilter)) {
                    return true;
                }

                // Updates current filter to new filter
                mSearchTerm = newFilter;

                // Restarts the loader. This triggers onCreateLoader(), which builds the
                // necessary content Uri from mSearchTerm.
                getLoaderManager().restartLoader(
                        ContactsQuery.QUERY_ID, null, ContactsListFragment2.this);
                return true;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                // Nothing to do when the action item is expanded
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                mSearchTerm = null;
                getLoaderManager().restartLoader(
                        ContactsQuery.QUERY_ID, null, ContactsListFragment2.this);
                return true;
            }
        });

        if (mSearchTerm != null) {
            final String savedSearchTerm = mSearchTerm;
            searchItem.expandActionView();
            searchView.setQuery(savedSearchTerm, false);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!TextUtils.isEmpty(mSearchTerm)) {
            outState.putString(SearchManager.QUERY, mSearchTerm);
            outState.putInt(STATE_PREVIOUSLY_SELECTED_KEY, getListView().getCheckedItemPosition());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_contact:
                final Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                startActivity(intent);
                break;
            case R.id.menu_search:
                getActivity().onSearchRequested();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Cursor cursor = mAdapter.getCursor();
        cursor.moveToPosition(position);

        final Uri uri = ContactsContract.Contacts.getLookupUri(
                cursor.getLong(ContactsQuery.ID),
                cursor.getString(ContactsQuery.LOOKUP_KEY));

        mOnContactSelectedListener.onContactSelected(uri);
    }

    public interface OnContactsInteractionListener {
        public void onContactSelected(Uri contactUri);
    }
}
