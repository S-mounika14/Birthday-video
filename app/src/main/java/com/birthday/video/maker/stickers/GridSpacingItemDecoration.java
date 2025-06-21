package com.birthday.video.maker.stickers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;
    private Paint paint;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge, int color) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;

        // Initialize the paint with the specified color
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount;
            outRect.right = spacing - (column + 1) * spacing / spanCount;
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            // Draw the spacing with the specified grey color
            if (includeEdge) {
                // Left edge
                canvas.drawRect(child.getLeft() - params.leftMargin - spacing,
                        child.getTop() - params.topMargin - spacing,
                        child.getLeft() - params.leftMargin,
                        child.getBottom() + params.bottomMargin + spacing,
                        paint);

                // Top edge
                canvas.drawRect(child.getLeft() - params.leftMargin - spacing,
                        child.getTop() - params.topMargin - spacing,
                        child.getRight() + params.rightMargin + spacing,
                        child.getTop() - params.topMargin,
                        paint);
            }

            // Right edge
            canvas.drawRect(child.getRight() + params.rightMargin,
                    child.getTop() - params.topMargin - spacing,
                    child.getRight() + params.rightMargin + spacing,
                    child.getBottom() + params.bottomMargin + spacing,
                    paint);

            // Bottom edge
            canvas.drawRect(child.getLeft() - params.leftMargin - spacing,
                    child.getBottom() + params.bottomMargin,
                    child.getRight() + params.rightMargin + spacing,
                    child.getBottom() + params.bottomMargin + spacing,
                    paint);
        }
    }
}