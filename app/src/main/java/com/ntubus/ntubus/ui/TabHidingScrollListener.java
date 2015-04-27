package com.ntubus.ntubus.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.ntubus.ntubus.Utils;

public class TabHidingScrollListener extends HidingScrollListener {

    private RecyclerView mRecyclerView;
    private LinearLayout mToolbarContainer;
    private int mToolbarHeight;

    public TabHidingScrollListener(Context context, RecyclerView recyclerView, LinearLayout toolbarContainer) {
        super(context);
        mRecyclerView = recyclerView;
        mToolbarContainer = toolbarContainer;
        mToolbarHeight = Utils.getToolbarHeight(context);

        int paddingTop = Utils.getToolbarHeight(context) + Utils.getTabsHeight(context);
        mRecyclerView.setPadding(mRecyclerView.getPaddingLeft(), paddingTop, mRecyclerView.getPaddingRight(), mRecyclerView.getPaddingBottom());
    }

    @Override
    public void onMoved(int distance) {
        mToolbarContainer.setTranslationY(-distance);
    }

    @Override
    public void onShow() {
        mToolbarContainer.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        setVisibility(true);
    }

    @Override
    public void onHide() {
        mToolbarContainer.animate().translationY(-mToolbarHeight).setInterpolator(new AccelerateInterpolator(2)).start();
        setVisibility(false);
    }
}
