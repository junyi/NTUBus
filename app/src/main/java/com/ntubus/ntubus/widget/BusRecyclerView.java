package com.ntubus.ntubus.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class BusRecyclerView extends RecyclerView {
    private View mEmptyView;
    private boolean mIsAtTop;

    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateEmptyView();
        }
    };


    public BusRecyclerView(Context context) {
        super(context);
    }

    public BusRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BusRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (getAdapter() != null) {
            getAdapter().unregisterAdapterDataObserver(mDataObserver);
        }
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mDataObserver);
        }
        super.setAdapter(adapter);
        updateEmptyView();
    }

    private void updateEmptyView() {
        if (mEmptyView != null && getAdapter() != null) {
            boolean showEmptyView = getAdapter().getItemCount() == 0;
            mEmptyView.setVisibility(showEmptyView ? VISIBLE : GONE);
            setVisibility(showEmptyView ? GONE : VISIBLE);
        }
    }

    public boolean isAtTop() {
        if (getLayoutManager().getChildCount() == 0)
            return true;

        View child = getLayoutManager().getChildAt(0);
        if (child != null) {
            int top = child.getTop() - getPaddingTop() - getLayoutManager().getTopDecorationHeight(child);
            System.out.println("Top: " + top);
            return top == 0;
        } else {
            System.out.println("Top: top not found!");
            return false;
        }

    }

}
