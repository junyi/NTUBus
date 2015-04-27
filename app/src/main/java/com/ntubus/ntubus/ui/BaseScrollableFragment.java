package com.ntubus.ntubus.ui;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.ntubus.ntubus.widget.BusRecyclerView;

public abstract class BaseScrollableFragment extends Fragment {
    public abstract BusRecyclerView getRecylerView();

    public abstract TabHidingScrollListener getScrollListener();
}
