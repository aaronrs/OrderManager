package net.astechdesign.cms.fragments;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.astechdesign.cms.R;
import net.astechdesign.cms.database.tables.OrdersTable;
import net.astechdesign.cms.model.Order;

public class MyInvoiceRecyclerViewAdapter extends RecyclerView.Adapter<MyInvoiceRecyclerViewAdapter.ViewHolder> {

    private Cursor orderCursor;
    private final InvoiceListFragment.OnListFragmentInteractionListener mListener;

    public MyInvoiceRecyclerViewAdapter(Cursor orders, InvoiceListFragment.OnListFragmentInteractionListener listener) {
        orderCursor = orders;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        orderCursor.move(position);
        Order order = new Order.Builder().withCustomer(orderCursor.getString(orderCursor.getColumnIndex(OrdersTable.CUSTOMER_NAME))).build();
        holder.order = order;
//        holder.mIdView.setText(orderCursor.move(position).id);
//        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.order);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
//        return mValues.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView customer;
        public final TextView product;
        public Order order;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            customer = (TextView) view.findViewById(R.id.id);
            product = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + product.getText() + "'";
        }
    }
}
