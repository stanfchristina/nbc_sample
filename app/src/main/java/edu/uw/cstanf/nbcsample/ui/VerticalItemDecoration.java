package edu.uw.cstanf.nbcsample.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/** Adds the specified amount of vertical spacing between {@link RecyclerView} items. */
public final class VerticalItemDecoration extends RecyclerView.ItemDecoration {
    private final int verticalSpace;

    public VerticalItemDecoration(int verticalSpace) {
        this.verticalSpace = verticalSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = verticalSpace;
    }
}