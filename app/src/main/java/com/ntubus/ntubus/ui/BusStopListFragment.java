package com.ntubus.ntubus.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntubus.ntubus.BusDatabaseApi;
import com.ntubus.ntubus.R;
import com.ntubus.ntubus.entity.BusStop;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BusStopListFragment extends Fragment {

    @InjectView(android.R.id.list)
    RecyclerView mRecyclerView;

    private BusStopListAdapter mAdapter;

    public BusStopListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bus_stop, container, false);
        ButterKnife.inject(this, view);

        mAdapter = new BusStopListAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BusDatabaseApi.getBusStops(new FindCallback<BusStop>() {
            @Override
            public void done(List<BusStop> list, ParseException e) {
                if (e == null) {
                    mAdapter.addItems(list);
                } else {
                    Log.e("ParseError", e.getMessage());
                }
            }
        });
    }
}
