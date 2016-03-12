package net.astechdesign.cms.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.QuickContactBadge;
import android.widget.SectionIndexer;
import android.widget.TextView;

import net.astechdesign.cms.R;

public class ContactsAdapter  extends CursorAdapter implements SectionIndexer {

    private LayoutInflater mInflater; // Stores the layout inflater
    private AlphabetIndexer mAlphabetIndexer; // Stores the AlphabetIndexer instance
    private TextAppearanceSpan highlightTextSpan; // Stores the highlight text appearance style

    public ContactsAdapter(Context context) {
        super(context, null, 0);

        mInflater = LayoutInflater.from(context);

        final String alphabet = context.getString(R.string.alphabet);

        mAlphabetIndexer = new AlphabetIndexer(null, ContactsQuery.SORT_KEY, alphabet);

        highlightTextSpan = new TextAppearanceSpan(context, R.style.searchTextHiglight);
    }

    /**
     * Overrides newView() to inflate the list item views.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        // Inflates the list item layout.
        final View itemLayout =
                mInflater.inflate(R.layout.contact_list_item, viewGroup, false);

        // Creates a new ViewHolder in which to store handles to each view resource. This
        // allows bindView() to retrieve stored references instead of calling findViewById for
        // each instance of the layout.
        final ViewHolder holder = new ViewHolder();
        holder.text1 = (TextView) itemLayout.findViewById(android.R.id.text1);
        holder.text2 = (TextView) itemLayout.findViewById(android.R.id.text2);

        // Stores the resourceHolder instance in itemLayout. This makes resourceHolder
        // available to bindView and other methods that receive a handle to the item view.
        itemLayout.setTag(holder);

        // Returns the item layout view
        return itemLayout;
    }

    /**
     * Binds data from the Cursor to the provided view.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Gets handles to individual view resources
        final ViewHolder holder = (ViewHolder) view.getTag();

        final String displayName = cursor.getString(ContactsQuery.DISPLAY_NAME);
    }

    /**
     * Overrides swapCursor to move the new Cursor into the AlphabetIndex as well as the
     * CursorAdapter.
     */
    @Override
    public Cursor swapCursor(Cursor newCursor) {
        // Update the AlphabetIndexer with new cursor as well
        mAlphabetIndexer.setCursor(newCursor);
        return super.swapCursor(newCursor);
    }

    /**
     * An override of getCount that simplifies accessing the Cursor. If the Cursor is null,
     * getCount returns zero. As a result, no test for Cursor == null is needed.
     */
    @Override
    public int getCount() {
        if (getCursor() == null) {
            return 0;
        }
        return super.getCount();
    }

    /**
     * Defines the SectionIndexer.getSections() interface.
     */
    @Override
    public Object[] getSections() {
        return mAlphabetIndexer.getSections();
    }

    /**
     * Defines the SectionIndexer.getPositionForSection() interface.
     */
    @Override
    public int getPositionForSection(int i) {
        if (getCursor() == null) {
            return 0;
        }
        return mAlphabetIndexer.getPositionForSection(i);
    }

    /**
     * Defines the SectionIndexer.getSectionForPosition() interface.
     */
    @Override
    public int getSectionForPosition(int i) {
        if (getCursor() == null) {
            return 0;
        }
        return mAlphabetIndexer.getSectionForPosition(i);
    }

    /**
     * A class that defines fields for each resource ID in the list item layout. This allows
     * ContactsAdapter.newView() to store the IDs once, when it inflates the layout, instead of
     * calling findViewById in each iteration of bindView.
     */
    private class ViewHolder {
        TextView text1;
        TextView text2;
        QuickContactBadge icon;
    }
}
