package com.ntubus.ntubus.ui;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListAdapter<V, K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {
    protected List<V> mData = new ArrayList<V>();

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItems(List<V> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void setItems(List<V> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
    }
}
