package com.ntubus.ntubus.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ntubus.ntubus.BusDatabaseApi;
import com.ntubus.ntubus.MainActivity;
import com.ntubus.ntubus.R;
import com.ntubus.ntubus.entity.BusStop;
import com.ntubus.ntubus.widget.BusRecyclerView;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BusStopListFragment extends BaseScrollableFragment {
    public final static String ARG_ROUTE_ID = "route_id";

    public final static String ROUTE_ID_ALL = null;
    public final static String ROUTE_ID_RED = "1";
    public final static String ROUTE_ID_BLUE = "2";
    public final static String ROUTE_ID_GREEN = "3";

    String mRouteId = null;

    @InjectView(android.R.id.list)
    BusRecyclerView mRecyclerView;

    private BusStopListAdapter mAdapter;
    private TabHidingScrollListener mScrollListener;

    public BusStopListFragment() {
    }

    public void loadData(String routeId) {
        if (mRecyclerView == null)
            return;

        mAdapter.clear();
        mRouteId = routeId;
        if (routeId == null) {
            BusDatabaseApi.getBusStops(new FindCallback<BusStop>() {
                @Override
                public void done(List<BusStop> list, ParseException e) {
                    if (e == null) {
                        mAdapter.setItems(list);
                    } else {
                        Log.e("ParseError", e.getMessage());
                    }
                }
            });
        } else {
            BusDatabaseApi.getBusStops(routeId, new FindCallback<BusStop>() {
                @Override
                public void done(List<BusStop> list, ParseException e) {
                    if (e == null) {
                        mAdapter.setItems(list);
                    } else {
                        Log.e("ParseError", e.getMessage());
                    }
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bus_stop_fragment, container, false);
        ButterKnife.inject(this, view);

        mAdapter = new BusStopListAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        LinearLayout mToolbarContainer = ((MainActivity) getActivity()).getToolbarContainer();
        mScrollListener = new TabHidingScrollListener(getActivity(), mRecyclerView, mToolbarContainer);

        mRecyclerView.addOnScrollListener(mScrollListener);

        if (savedInstanceState != null)
            mRouteId = savedInstanceState.getString(ARG_ROUTE_ID);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData(mRouteId);
    }

    @Override
    public BusRecyclerView getRecylerView() {
        return mRecyclerView;
    }

    @Override
    public TabHidingScrollListener getScrollListener() {
        return mScrollListener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
