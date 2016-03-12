package net.astechdesign.cms.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.astechdesign.cms.R;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private List<String> staticData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        return inflater.inflate(R.layout.orders_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        staticData = new ArrayList<>();
        reset();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, staticData);
        ListView viewById = (ListView) getActivity().findViewById(R.id.orders);
        viewById.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void reset() {
        staticData.add("12 December 2015 - 12 Boxes");
        staticData.add("2 February 2016 - 6 Boxes");
        staticData.add("26 February 2016 - 2 Boxes");
    }
}
