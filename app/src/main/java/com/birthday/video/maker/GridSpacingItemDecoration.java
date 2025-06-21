package com.birthday.video.maker;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.Birthday_Cakes.Photo_in_Cake_recyclerview;
import com.birthday.video.maker.Birthday_Gifs.Gif_Stickers;
import com.birthday.video.maker.Onlineframes.All_Fragment_greetings;


public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int spacingLarge; // e.g., 3dp in px
    private final int spacingSmall; // e.g., 1dp in px
    private final int spanCount;
    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;


    public GridSpacingItemDecoration(int spacingLargePx, int spacingSmallPx, int spanCount) {
        this.spacingLarge = spacingLargePx;
        this.spacingSmall = spacingSmallPx;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int adapterPosition = parent.getChildAdapterPosition(view);

        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null) return;

        int itemViewType = -1;

        if (adapter instanceof All_Fragment_greetings.GifsAdapter) {
            itemViewType = ((All_Fragment_greetings.GifsAdapter) adapter).getItemViewType(adapterPosition);
        }
//        else if (adapter instanceof Photo_in_Cake_recyclerview.PhotoOnCakesAdapter) {
//            itemViewType = ((Photo_in_Cake_recyclerview.PhotoOnCakesAdapter) adapter).getItemViewType(adapterPosition);
//        }

        // Skip if it's an ad view
        if (itemViewType == UNIFIED_NATIVE_AD_VIEW_TYPE) {
            outRect.set(0, 0, 0, 0);
            return;
        }

        // Adjust frameIndex to exclude ad at position 6
        int frameIndex = adapterPosition > 6 ? adapterPosition - 1 : adapterPosition;

        int column = frameIndex % spanCount;

        // Horizontal spacing
        if (column == 0) {
            outRect.left = spacingLarge;
            outRect.right = spacingSmall;
        } else {
            outRect.left = spacingSmall;
            outRect.right = spacingLarge;
        }

        // Vertical spacing
        if (frameIndex < spanCount) {
            outRect.top = spacingLarge;
        } else {
            outRect.top = spacingSmall;
        }

        // Bottom spacing
        outRect.bottom = spacingSmall;
    }


   /* @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int adapterPosition = parent.getChildAdapterPosition(view);

        // Skip if it's the ad view
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter != null && adapter instanceof All_Fragment_greetings.GifsAdapter) {
            if (((All_Fragment_greetings.GifsAdapter) adapter).getItemViewType(adapterPosition) == UNIFIED_NATIVE_AD_VIEW_TYPE) {
                outRect.set(0, 0, 0, 0);
                return;
            }
        }

        // Compute "frame index" by excluding the ad (position 6)
        int frameIndex = adapterPosition > 6 ? adapterPosition - 1 : adapterPosition;

        // Determine column for spacing
        int column = frameIndex % spanCount;

        // Apply left/right spacing
        if (column == 0) { // Left item in row
            outRect.left = spacingLarge;
            outRect.right = spacingSmall;
        } else { // Right item in row
            outRect.left = spacingSmall;
            outRect.right = spacingLarge;
        }

        // Apply top spacing for first row only
        if (frameIndex < spanCount) {
            outRect.top = spacingLarge;
        } else {
            outRect.top = spacingSmall;
        }

        // Always apply small bottom spacing
        outRect.bottom = spacingSmall;
    }*/
}



/*
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int mSizeGridSpacingPx;
    private int mGridSize;

    private boolean mNeedLeftSpacing = false;

    public GridSpacingItemDecoration(int gridSpacingPx, int gridSize) {
        mSizeGridSpacingPx = gridSpacingPx;
        mGridSize = gridSize;
    }

   */
/* @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int frameWidth = (int) ((parent.getWidth() - (float) mSizeGridSpacingPx * (mGridSize - 1)) / mGridSize);
        int padding = parent.getWidth() / mGridSize - frameWidth;
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        if (itemPosition < mGridSize) {
            outRect.top = 0;
        } else {
            outRect.top = mSizeGridSpacingPx;
        }
        if (itemPosition % mGridSize == 0) {
            outRect.left = 0;
            outRect.right = padding;
            mNeedLeftSpacing = true;
        } else if ((itemPosition + 1) % mGridSize == 0) {
            mNeedLeftSpacing = false;
            outRect.right = 0;
            outRect.left = padding;
        } else if (mNeedLeftSpacing) {
            mNeedLeftSpacing = false;
            outRect.left = mSizeGridSpacingPx - padding;
            if ((itemPosition + 2) % mGridSize == 0) {
                outRect.right = mSizeGridSpacingPx - padding;
            } else {
                outRect.right = mSizeGridSpacingPx / 2;
            }
        } else if ((itemPosition + 2) % mGridSize == 0) {
            mNeedLeftSpacing = false;
            outRect.left = mSizeGridSpacingPx / 2;
            outRect.right = mSizeGridSpacingPx - padding;
        } else {
            mNeedLeftSpacing = false;
            outRect.left = mSizeGridSpacingPx / 2;
            outRect.right = mSizeGridSpacingPx / 2;
        }
        outRect.bottom = 0;
    }*//*

   @Override
   public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
       int position = parent.getChildAdapterPosition(view); // item position
       int column = position % mGridSize; // 0 or 1 for 2 columns
       int spacing = mSizeGridSpacingPx; // 6dp for example (3dp each item edge)

       // Horizontal spacing
       if (column == 0) {
           // Left column
           outRect.left = spacing / 2;  // 3dp
           outRect.right = spacing / 4; // 1.5dp
       } else {
           // Right column
           outRect.left = spacing / 4;  // 1.5dp
           outRect.right = spacing / 2; // 3dp
       }

       // Vertical spacing
       if (position < mGridSize) {
           // First row
           outRect.top = spacing / 2; // 3dp
       } else {
           outRect.top = spacing / 4; // 1.5dp
       }
       outRect.bottom = spacing / 4; // 1.5dp for all
   }


}*/
