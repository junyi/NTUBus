package com.ntubus.ntubus.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ntubus.ntubus.MainActivity;
import com.ntubus.ntubus.R;
import com.ntubus.ntubus.Utils;
import com.ntubus.ntubus.widget.BusRecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeFragment extends BaseScrollableFragment {

    @InjectView(android.R.id.list)
    BusRecyclerView mRecyclerView;

    private TabHidingScrollListener mScrollListener;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.inject(this, view);
        HomeListAdapter adapter = new HomeListAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        int vSpace = Utils.convertDpToPx(getActivity(), 8);
        int hSpace = Utils.convertDpToPx(getActivity(), 4);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(vSpace, hSpace));
        mRecyclerView.setAdapter(adapter);

        LinearLayout mToolbarContainer = ((MainActivity) getActivity()).getToolbarContainer();
        mScrollListener = new TabHidingScrollListener(getActivity(), mRecyclerView, mToolbarContainer);

        mRecyclerView.addOnScrollListener(mScrollListener);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public BusRecyclerView getRecylerView() {
        return mRecyclerView;
    }

    @Override
    public TabHidingScrollListener getScrollListener() {
        return mScrollListener;
    }
}
