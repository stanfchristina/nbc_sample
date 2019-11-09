package edu.uw.cstanf.nbcsample.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class VerticalItemDecoration extends RecyclerView.ItemDecoration {
    private final int verticalSpace;

    public VerticalItemDecoration(int verticalSpace) {
        this.verticalSpace = verticalSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int endItemPosition = parent.getAdapter().getItemCount() - 1;
        if (parent.getChildAdapterPosition(view) != endItemPosition) {
            outRect.bottom = verticalSpace;
        }
    }
}