package com.ntubus.ntubus.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int verticalSpace;
    private int horizontalSpace;

    public SpacesItemDecoration(int verticalSpace, int horizontalSpace) {
        this.verticalSpace = verticalSpace;
        this.horizontalSpace = horizontalSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.set(horizontalSpace, verticalSpace, horizontalSpace, verticalSpace);
        } else {
            outRect.set(horizontalSpace, 0, horizontalSpace, verticalSpace);
        }
    }
}