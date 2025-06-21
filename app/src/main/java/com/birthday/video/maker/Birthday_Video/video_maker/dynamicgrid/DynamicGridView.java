package com.birthday.video.maker.Birthday_Video.video_maker.dynamicgrid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.birthday.video.maker.Birthday_Video.activity.GridBitmaps_Activity2;
import com.birthday.video.maker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class DynamicGridView extends GridView {
    desstroy ddddd;
    Exception3 exception3;
    public void setInterface(GridBitmaps_Activity2 gridBitmaps_activity2) {
        ddddd = gridBitmaps_activity2;
    }

    public void sendActivity(GridBitmaps_Activity2 gridBitmaps_activity2) {
        exception3 = gridBitmaps_activity2;
    }

    public interface  Exception3
    {
        public  void onException();
    }

    public interface desstroy {
        public void onDestroyy();
    }


    private List<Long> idList = new ArrayList();
    private int mActivePointerId = -1;
    private boolean mCellIsMobile = false;
    private DynamicGridModification mCurrentModification;
    private int mDownX = -1;
    private int mDownY = -1;
    private OnDragListener mDragListener;
    private OnDropListener mDropListener;
    private OnEditModeChangeListener mEditModeChangeListener;
    private boolean mHoverAnimation;
    private BitmapDrawable mHoverCell;
    private Rect mHoverCellCurrentBounds;
    private Rect mHoverCellOriginalBounds;
    private boolean mIsEditMode = false;
    private boolean mIsEditModeEnabled = true;
    private boolean mIsMobileScrolling;
    private boolean mIsWaitingForScrollFinish = false;
    private int mLastEventX = -1;
    private int mLastEventY = -1;
    private OnItemClickListener mLocalItemClickListener = new C05501();
    private long mMobileItemId = -1;
    private View mMobileView;
    private Stack<DynamicGridModification> mModificationStack;
    private int mOverlapIfSwitchStraightLine;
    private boolean mReorderAnimation;
    private OnScrollListener mScrollListener = new C05567();
    private int mScrollState = 0;
    private OnSelectedItemBitmapCreationListener mSelectedItemBitmapCreationListener;
    private int mSmoothScrollAmountAtEdge = 0;
    private int mTotalOffsetX = 0;
    private int mTotalOffsetY = 0;
    private boolean mUndoSupportEnabled;
    private OnItemClickListener mUserItemClickListener;
    private OnScrollListener mUserScrollListener;
    private List<ObjectAnimator> mWobbleAnimators = new LinkedList();
    private boolean mWobbleInEditMode = true;

    class C05501 implements OnItemClickListener {
        C05501() {
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!DynamicGridView.this.isEditMode() && DynamicGridView.this.isEnabled() && DynamicGridView.this.mUserItemClickListener != null) {
                DynamicGridView.this.mUserItemClickListener.onItemClick(parent, view, position, id);
            }
        }
    }

    class C05523 implements TypeEvaluator<Rect> {
        C05523() {
        }

        public Rect evaluate(float fraction, Rect startValue, Rect endValue) {
            return new Rect(interpolate(startValue.left, endValue.left, fraction), interpolate(startValue.top, endValue.top, fraction), interpolate(startValue.right, endValue.right, fraction), interpolate(startValue.bottom, endValue.bottom, fraction));
        }

        public int interpolate(int start, int end, float fraction) {
            return (int) (((float) start) + (((float) (end - start)) * fraction));
        }
    }

    class C05534 implements AnimatorUpdateListener {
        C05534() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            DynamicGridView.this.invalidate();
        }
    }

    class C05556 extends AnimatorListenerAdapter {
        C05556() {
        }

        public void onAnimationStart(Animator animation) {
            DynamicGridView.this.mReorderAnimation = true;
            DynamicGridView.this.updateEnableState();
        }

        public void onAnimationEnd(Animator animation) {
            DynamicGridView.this.mReorderAnimation = false;
            DynamicGridView.this.updateEnableState();
        }
    }

    class C05567 implements OnScrollListener {
        private int mCurrentFirstVisibleItem;
        private int mCurrentScrollState;
        private int mCurrentVisibleItemCount;
        private int mPreviousFirstVisibleItem = -1;
        private int mPreviousVisibleItemCount = -1;

        C05567() {
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            this.mCurrentFirstVisibleItem = firstVisibleItem;
            this.mCurrentVisibleItemCount = visibleItemCount;
            this.mPreviousFirstVisibleItem = this.mPreviousFirstVisibleItem == -1 ? this.mCurrentFirstVisibleItem : this.mPreviousFirstVisibleItem;
            this.mPreviousVisibleItemCount = this.mPreviousVisibleItemCount == -1 ? this.mCurrentVisibleItemCount : this.mPreviousVisibleItemCount;
            checkAndHandleFirstVisibleCellChange();
            checkAndHandleLastVisibleCellChange();
            this.mPreviousFirstVisibleItem = this.mCurrentFirstVisibleItem;
            this.mPreviousVisibleItemCount = this.mCurrentVisibleItemCount;
            if (DynamicGridView.this.isPostHoneycomb() && DynamicGridView.this.mWobbleInEditMode) {
                updateWobbleState(visibleItemCount);
            }
            if (DynamicGridView.this.mUserScrollListener != null) {
                DynamicGridView.this.mUserScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }

        @TargetApi(11)
        private void updateWobbleState(int visibleItemCount) {
            for (int i = 0; i < visibleItemCount; i++) {
                View child = DynamicGridView.this.getChildAt(i);
                if (child != null) {
                    if (DynamicGridView.this.mMobileItemId != -1) {
                        if (i % 2 == 0) {
                            DynamicGridView.this.animateWobble(child);
                        } else {
                            DynamicGridView.this.animateWobbleInverse(child);
                        }
                    } else if (DynamicGridView.this.mMobileItemId == -1 && child.getRotation() != 0.0f) {
                        child.setRotation(0.0f);
                    }
                }
            }
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            this.mCurrentScrollState = scrollState;
            DynamicGridView.this.mScrollState = scrollState;
            isScrollCompleted();
            if (DynamicGridView.this.mUserScrollListener != null) {
                DynamicGridView.this.mUserScrollListener.onScrollStateChanged(view, scrollState);
            }
        }

        private void isScrollCompleted() {
            if (this.mCurrentVisibleItemCount > 0 && this.mCurrentScrollState == 0) {
                if (DynamicGridView.this.mCellIsMobile && DynamicGridView.this.mIsMobileScrolling) {
                    DynamicGridView.this.handleMobileCellScroll();

                } else if (DynamicGridView.this.mIsWaitingForScrollFinish) {
                    DynamicGridView.this.touchEventsEnded();

                }
            }
        }

        public void checkAndHandleFirstVisibleCellChange() {
            if (this.mCurrentFirstVisibleItem != this.mPreviousFirstVisibleItem && DynamicGridView.this.mCellIsMobile && DynamicGridView.this.mMobileItemId != -1) {
                DynamicGridView.this.updateNeighborViewsForId(DynamicGridView.this.mMobileItemId);
                DynamicGridView.this.handleCellSwitch();
            }
        }

        public void checkAndHandleLastVisibleCellChange() {
            if (this.mCurrentFirstVisibleItem + this.mCurrentVisibleItemCount != this.mPreviousFirstVisibleItem + this.mPreviousVisibleItemCount && DynamicGridView.this.mCellIsMobile && DynamicGridView.this.mMobileItemId != -1) {
                DynamicGridView.this.updateNeighborViewsForId(DynamicGridView.this.mMobileItemId);
                DynamicGridView.this.handleCellSwitch();

            }
        }
    }

    private static class DynamicGridModification {
        private List<Pair<Integer, Integer>> transitions = new Stack();

        DynamicGridModification() {
        }

        public boolean hasTransitions() {
            return !this.transitions.isEmpty();
        }

        public void addTransition(int oldPosition, int newPosition) {
            this.transitions.add(new Pair(Integer.valueOf(oldPosition), Integer.valueOf(newPosition)));
        }

        public List<Pair<Integer, Integer>> getTransitions() {
            Collections.reverse(this.transitions);
            return this.transitions;
        }
    }

    public interface OnDragListener {
        void onDragPositionsChanged(int i, int i2);

        void onDragStarted(int i);
    }

    public interface OnDropListener {
        void onActionDrop();
    }

    public interface OnEditModeChangeListener {
        void onEditModeChanged(boolean z);
    }

    public interface OnSelectedItemBitmapCreationListener {
        void onPostSelectedItemBitmapCreation(View view, int i, long j);

        void onPreSelectedItemBitmapCreation(View view, int i, long j);
    }

    private interface SwitchCellAnimator {
        void animateSwitchCell(int i, int i2);
    }

    private class KitKatSwitchCellAnimator implements SwitchCellAnimator {
        final boolean $assertionsDisabled = (!DynamicGridView.class.desiredAssertionStatus());
        private int mDeltaX;
        private int mDeltaY;

        private class AnimateSwitchViewOnPreDrawListener implements OnPreDrawListener {
            private final int mOriginalPosition;
            private final View mPreviousMobileView;
            private final int mTargetPosition;

            AnimateSwitchViewOnPreDrawListener(View previousMobileView, int originalPosition, int targetPosition) {
                this.mPreviousMobileView = previousMobileView;
                this.mOriginalPosition = originalPosition;
                this.mTargetPosition = targetPosition;
            }

            public boolean onPreDraw() {
                DynamicGridView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                DynamicGridView.this.mTotalOffsetY = DynamicGridView.this.mTotalOffsetY + KitKatSwitchCellAnimator.this.mDeltaY;
                DynamicGridView.this.mTotalOffsetX = DynamicGridView.this.mTotalOffsetX + KitKatSwitchCellAnimator.this.mDeltaX;
                DynamicGridView.this.animateReorder(this.mOriginalPosition, this.mTargetPosition);

                if (mPreviousMobileView != null)
                    this.mPreviousMobileView.setVisibility(View.VISIBLE);

                if (DynamicGridView.this.mMobileView != null) {
                    DynamicGridView.this.mMobileView.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        }

        public KitKatSwitchCellAnimator(int deltaX, int deltaY) {
            this.mDeltaX = deltaX;
            this.mDeltaY = deltaY;
        }

        public void animateSwitchCell(int originalPosition, int targetPosition) {
            if ($assertionsDisabled || DynamicGridView.this.mMobileView != null) {
                DynamicGridView.this.getViewTreeObserver().addOnPreDrawListener(new AnimateSwitchViewOnPreDrawListener(DynamicGridView.this.mMobileView, originalPosition, targetPosition));
                DynamicGridView.this.mMobileView = DynamicGridView.this.getViewForId(DynamicGridView.this.mMobileItemId);
                return;
            }
            throw new AssertionError();
        }
    }

    private class LSwitchCellAnimator implements SwitchCellAnimator {
        private int mDeltaX;
        private int mDeltaY;

        private class AnimateSwitchViewOnPreDrawListener implements OnPreDrawListener {
            private final int mOriginalPosition;
            private final int mTargetPosition;

            AnimateSwitchViewOnPreDrawListener(int originalPosition, int targetPosition) {
                this.mOriginalPosition = originalPosition;
                this.mTargetPosition = targetPosition;
            }

            public boolean onPreDraw() {
                try {
                    DynamicGridView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                    DynamicGridView.this.mTotalOffsetY = DynamicGridView.this.mTotalOffsetY + LSwitchCellAnimator.this.mDeltaY;
                    DynamicGridView.this.mTotalOffsetX = DynamicGridView.this.mTotalOffsetX + LSwitchCellAnimator.this.mDeltaX;
                    DynamicGridView.this.animateReorder(this.mOriginalPosition, this.mTargetPosition);
                    if (mMobileView != null) {
                        mMobileView.setVisibility(View.VISIBLE);
                        mMobileView = getViewForId(DynamicGridView.this.mMobileItemId);
                        if ( mMobileView != null) {
                            mMobileView.setVisibility(View.INVISIBLE);
                            return true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }

        public LSwitchCellAnimator(int deltaX, int deltaY) {
            this.mDeltaX = deltaX;
            this.mDeltaY = deltaY;
        }

        public void animateSwitchCell(int originalPosition, int targetPosition) {
            DynamicGridView.this.getViewTreeObserver().addOnPreDrawListener(new AnimateSwitchViewOnPreDrawListener(originalPosition, targetPosition));
        }
    }

    private class PreHoneycombCellAnimator implements SwitchCellAnimator {
        private int mDeltaX;
        private int mDeltaY;

        public PreHoneycombCellAnimator(int deltaX, int deltaY) {
            this.mDeltaX = deltaX;
            this.mDeltaY = deltaY;
        }

        public void animateSwitchCell(int originalPosition, int targetPosition) {
            DynamicGridView.this.mTotalOffsetY = DynamicGridView.this.mTotalOffsetY + this.mDeltaY;
            DynamicGridView.this.mTotalOffsetX = DynamicGridView.this.mTotalOffsetX + this.mDeltaX;
        }
    }

    public DynamicGridView(Context context) {
        super(context);
        init(context);
    }

    public DynamicGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DynamicGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setOnScrollListener(OnScrollListener scrollListener) {
        this.mUserScrollListener = scrollListener;
    }

    public void setOnDropListener(OnDropListener dropListener) {
        this.mDropListener = dropListener;
    }

    public void setOnDragListener(OnDragListener dragListener) {
        this.mDragListener = dragListener;
    }

    public void startEditMode() {
        try {

            startEditMode(-1);
        } catch (Exception e) {
            ddddd.onDestroyy();
        }

    }

    public void startEditMode(int position) {
        if (this.mIsEditModeEnabled) {
            requestDisallowInterceptTouchEvent(true);
            if (isPostHoneycomb() && this.mWobbleInEditMode) {
                startWobbleAnimation();
            }
            if (position != -1) {
                startDragAtPosition(position);
            }
            this.mIsEditMode = true;
            if (this.mEditModeChangeListener != null) {
                this.mEditModeChangeListener.onEditModeChanged(true);
            }
        }
    }

    public void stopEditMode() {
        this.mIsEditMode = false;
        requestDisallowInterceptTouchEvent(false);
        if (isPostHoneycomb() && this.mWobbleInEditMode) {
            stopWobble(true);
        }
        if (this.mEditModeChangeListener != null) {
            this.mEditModeChangeListener.onEditModeChanged(false);
        }
    }


    public boolean isEditMode() {
        return this.mIsEditMode;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mUserItemClickListener = listener;
        super.setOnItemClickListener(this.mLocalItemClickListener);
    }



    @TargetApi(11)
    private void startWobbleAnimation() {
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (!(v == null)) {
                if (i % 2 == 0) {
                    animateWobble(v);
                } else {
                    animateWobbleInverse(v);
                }
            }
        }
    }

    @TargetApi(11)
    private void stopWobble(boolean resetRotation) {
        for (Animator wobbleAnimator : this.mWobbleAnimators) {
            wobbleAnimator.cancel();
        }
        this.mWobbleAnimators.clear();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v != null) {
                if (resetRotation) {
                    v.setRotation(0.0f);
                }

            }
        }
    }

    @TargetApi(11)
    private void restartWobble() {
        stopWobble(false);
        startWobbleAnimation();
    }

    public void init(Context context) {
        super.setOnScrollListener(this.mScrollListener);
        this.mSmoothScrollAmountAtEdge = (int) ((8.0f * context.getResources().getDisplayMetrics().density) + 0.5f);
        this.mOverlapIfSwitchStraightLine = getResources().getDimensionPixelSize(R.dimen.dgv_overlap_if_switch_straight_line);
    }

    @TargetApi(11)
    private void animateWobble(View v) {
        ObjectAnimator animator = createBaseWobble(v);
        animator.setFloatValues(new float[]{-2.0f, 2.0f});
        animator.start();
        this.mWobbleAnimators.add(animator);
    }

    @TargetApi(11)
    private void animateWobbleInverse(View v) {
        ObjectAnimator animator = createBaseWobble(v);
        animator.setFloatValues(new float[]{2.0f, -2.0f});
        animator.start();
        this.mWobbleAnimators.add(animator);
    }

    @TargetApi(11)
    private ObjectAnimator createBaseWobble(final View v) {
        if (!isPreLollipop()) {
            v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        ObjectAnimator animator = new ObjectAnimator();
        animator.setDuration(180);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(-1);
        animator.setPropertyName("rotation");
        animator.setTarget(v);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
            }
        });
        return animator;
    }

    private void reorderElements(int originalPosition, int targetPosition) {
        if (this.mDragListener != null) {
            this.mDragListener.onDragPositionsChanged(originalPosition, targetPosition);
        }
        getAdapterInterface().reorderItems(originalPosition, targetPosition);
    }

    private int getColumnCount() {
        return getAdapterInterface().getColumnCount();
    }

    private DynamicGridAdapterInterface getAdapterInterface() {
        return (DynamicGridAdapterInterface) getAdapter();
    }

    private BitmapDrawable getAndAddHoverView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        int top = v.getTop();
        int left = v.getLeft();
        BitmapDrawable drawable = new BitmapDrawable(getResources(), getBitmapFromView(v));
        this.mHoverCellOriginalBounds = new Rect(left, top, left + w, top + h);
        this.mHoverCellCurrentBounds = new Rect(this.mHoverCellOriginalBounds);
        drawable.setBounds(this.mHoverCellCurrentBounds);
        return drawable;
    }

    private Bitmap getBitmapFromView(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Config.ARGB_8888);
        v.draw(new Canvas(bitmap));
        return bitmap;
    }

    private void updateNeighborViewsForId(long itemId) {
        this.idList.clear();
        int draggedPos = getPositionForID(itemId);
        int pos = getFirstVisiblePosition();
        while (pos <= getLastVisiblePosition()) {
            if (draggedPos != pos && getAdapterInterface().canReorder(pos)) {
                this.idList.add(Long.valueOf(getId(pos)));

            }
            pos++;
        }
    }

    public int getPositionForID(long itemId) {
        View v = getViewForId(itemId);
        if (v == null) {
            return -1;
        }
        return getPositionForView(v);
    }

    public View getViewForId(long itemId) {
        int firstVisiblePosition = getFirstVisiblePosition();
        ListAdapter adapter = getAdapter();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (adapter.getItemId(firstVisiblePosition + i) == itemId) {
                return v;
            }
        }
        return null;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & 255) {
            case 0:
                this.mDownX = (int) event.getX();
                this.mDownY = (int) event.getY();
                this.mActivePointerId = event.getPointerId(0);
                if (this.mIsEditMode && isEnabled()) {
                    layoutChildren();
                    startDragAtPosition(pointToPosition(this.mDownX, this.mDownY));
                    break;
                } else if (!isEnabled()) {
                    return false;
                }
                break;
            case 1:

                touchEventsEnded();
                if (!(!this.mUndoSupportEnabled || this.mCurrentModification == null || this.mCurrentModification.getTransitions().isEmpty())) {
                    this.mModificationStack.push(this.mCurrentModification);
                    this.mCurrentModification = new DynamicGridModification();
                }
                if (!(this.mHoverCell == null || this.mDropListener == null)) {
                    this.mDropListener.onActionDrop();
                    break;
                }
            case 2:

                if (this.mActivePointerId != -1) {
                    int pointerIndex = event.findPointerIndex(this.mActivePointerId);
                    this.mLastEventY = (int) event.getY(pointerIndex);
                    this.mLastEventX = (int) event.getX(pointerIndex);
                    int deltaY = this.mLastEventY - this.mDownY;
                    int deltaX = this.mLastEventX - this.mDownX;
                    if (this.mCellIsMobile) {
                        this.mHoverCellCurrentBounds.offsetTo((this.mHoverCellOriginalBounds.left + deltaX) + this.mTotalOffsetX, (this.mHoverCellOriginalBounds.top + deltaY) + this.mTotalOffsetY);
                        this.mHoverCell.setBounds(this.mHoverCellCurrentBounds);
                        invalidate();
                        handleCellSwitch();
                        this.mIsMobileScrolling = false;
                        handleMobileCellScroll();
                        return false;
                    }
                }
                break;
            case 3:

                touchEventsCancelled();
                if (!(this.mHoverCell == null || this.mDropListener == null)) {
                    this.mDropListener.onActionDrop();
                    break;
                }
            case 6:

                if (event.getPointerId((event.getAction() & 65280) >> 8) == this.mActivePointerId) {
                    touchEventsEnded();
                    break;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void startDragAtPosition(int position) {
        this.mTotalOffsetY = 0;
        this.mTotalOffsetX = 0;
        View selectedView = getChildAt(position - getFirstVisiblePosition());
        if (selectedView != null) {
            this.mMobileItemId = getAdapter().getItemId(position);
            if (this.mSelectedItemBitmapCreationListener != null) {
                this.mSelectedItemBitmapCreationListener.onPreSelectedItemBitmapCreation(selectedView, position, this.mMobileItemId);
            }
            this.mHoverCell = getAndAddHoverView(selectedView);
            if (this.mSelectedItemBitmapCreationListener != null) {
                this.mSelectedItemBitmapCreationListener.onPostSelectedItemBitmapCreation(selectedView, position, this.mMobileItemId);
            }
            if (isPostHoneycomb()) {
                selectedView.setVisibility(View.INVISIBLE);
            }
            this.mCellIsMobile = true;
            updateNeighborViewsForId(this.mMobileItemId);
            if (this.mDragListener != null) {
                this.mDragListener.onDragStarted(position);
            }
        }
    }

    private void handleMobileCellScroll() {
        this.mIsMobileScrolling = handleMobileCellScroll(this.mHoverCellCurrentBounds);
    }

    public boolean handleMobileCellScroll(Rect r) {
        int offset = computeVerticalScrollOffset();
        int height = getHeight();
        int extent = computeVerticalScrollExtent();
        int range = computeVerticalScrollRange();
        int hoverViewTop = r.top;
        int hoverHeight = r.height();
        if (hoverViewTop <= 0 && offset > 0) {
            smoothScrollBy(-this.mSmoothScrollAmountAtEdge, 0);
            return true;
        } else if (hoverViewTop + hoverHeight < height || offset + extent >= range) {
            return false;
        } else {
            smoothScrollBy(this.mSmoothScrollAmountAtEdge, 0);
            return true;
        }
    }

    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    private void touchEventsEnded() {
        View mobileView = getViewForId(this.mMobileItemId);
        if (mobileView == null || !(this.mCellIsMobile || this.mIsWaitingForScrollFinish)) {
            touchEventsCancelled();
            return;
        }
        this.mCellIsMobile = false;
        this.mIsWaitingForScrollFinish = false;
        this.mIsMobileScrolling = false;
        this.mActivePointerId = -1;
        if (this.mScrollState != 0) {
            this.mIsWaitingForScrollFinish = true;
            return;
        }
        this.mHoverCellCurrentBounds.offsetTo(mobileView.getLeft(), mobileView.getTop());
        if (VERSION.SDK_INT > 11) {
            animateBounds(mobileView);
            return;
        }
        this.mHoverCell.setBounds(this.mHoverCellCurrentBounds);
        invalidate();
        reset(mobileView);
    }

    @TargetApi(11)
    private void animateBounds(final View mobileView) {
        ObjectAnimator hoverViewAnimator = ObjectAnimator.ofObject(this.mHoverCell, "bounds", new C05523(), new Object[]{this.mHoverCellCurrentBounds});
        hoverViewAnimator.addUpdateListener(new C05534());
        hoverViewAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                DynamicGridView.this.mHoverAnimation = true;
                DynamicGridView.this.updateEnableState();
            }

            public void onAnimationEnd(Animator animation) {
                DynamicGridView.this.mHoverAnimation = false;
                DynamicGridView.this.updateEnableState();
                DynamicGridView.this.reset(mobileView);
            }
        });
        hoverViewAnimator.start();
    }

    private void reset(View mobileView) {
        this.idList.clear();
        this.mMobileItemId = -1;
        if (mobileView != null)
            mobileView.setVisibility(View.VISIBLE);
        this.mHoverCell = null;
        if (isPostHoneycomb() && this.mWobbleInEditMode) {
            if (this.mIsEditMode) {
                restartWobble();
            } else {
                stopWobble(true);
            }
        }
        for (int i = 0; i < getLastVisiblePosition() - getFirstVisiblePosition(); i++) {
            View child = getChildAt(i);
            if (child != null) {
                child.setVisibility(View.VISIBLE);
            }
        }
        invalidate();
    }

    private void updateEnableState() {
        boolean z = (this.mHoverAnimation || this.mReorderAnimation) ? false : true;
        setEnabled(z);
    }

    private boolean isPostHoneycomb() {
        return VERSION.SDK_INT >= 11;
    }

    public static boolean isPreLollipop() {
        return VERSION.SDK_INT < 21;
    }

    private void touchEventsCancelled() {
        View mobileView = getViewForId(this.mMobileItemId);
        if (this.mCellIsMobile) {
            reset(mobileView);
        }
        this.mCellIsMobile = false;
        this.mIsMobileScrolling = false;
        this.mActivePointerId = -1;
    }

    private void handleCellSwitch() {
        int deltaY = this.mLastEventY - this.mDownY;
        int deltaX = this.mLastEventX - this.mDownX;
        int deltaYTotal = (this.mHoverCellOriginalBounds.centerY() + this.mTotalOffsetY) + deltaY;
        int deltaXTotal = (this.mHoverCellOriginalBounds.centerX() + this.mTotalOffsetX) + deltaX;
        this.mMobileView = getViewForId(this.mMobileItemId);
        View targetView = null;
        float vX = 0.0f;
        float vY = 0.0f;
        Point mobileColumnRowPair = getColumnAndRowForView(this.mMobileView);
        for (Long id : this.idList) {
            View view = getViewForId(id.longValue());
            if (view != null) {
                Point targetColumnRowPair = getColumnAndRowForView(view);
                if ((aboveRight(targetColumnRowPair, mobileColumnRowPair) && deltaYTotal < view.getBottom() && deltaXTotal > view.getLeft()) || ((aboveLeft(targetColumnRowPair, mobileColumnRowPair) && deltaYTotal < view.getBottom() && deltaXTotal < view.getRight()) || ((belowRight(targetColumnRowPair, mobileColumnRowPair) && deltaYTotal > view.getTop() && deltaXTotal > view.getLeft()) || ((belowLeft(targetColumnRowPair, mobileColumnRowPair) && deltaYTotal > view.getTop() && deltaXTotal < view.getRight()) || ((above(targetColumnRowPair, mobileColumnRowPair) && deltaYTotal < view.getBottom() - this.mOverlapIfSwitchStraightLine) || ((below(targetColumnRowPair, mobileColumnRowPair) && deltaYTotal > view.getTop() + this.mOverlapIfSwitchStraightLine) || ((right(targetColumnRowPair, mobileColumnRowPair) && deltaXTotal > view.getLeft() + this.mOverlapIfSwitchStraightLine) || (left(targetColumnRowPair, mobileColumnRowPair) && deltaXTotal < view.getRight() - this.mOverlapIfSwitchStraightLine)))))))) {
                    float xDiff = Math.abs(DynamicGridUtils.getViewX(view) - DynamicGridUtils.getViewX(this.mMobileView));
                    float yDiff = Math.abs(DynamicGridUtils.getViewY(view) - DynamicGridUtils.getViewY(this.mMobileView));
                    if (xDiff >= vX && yDiff >= vY) {
                        vX = xDiff;
                        vY = yDiff;
                        targetView = view;
                    }
                }
            }
        }
        if (targetView != null) {
            int originalPosition = getPositionForView(this.mMobileView);
            int targetPosition = getPositionForView(targetView);
            DynamicGridAdapterInterface adapter = getAdapterInterface();
            if (targetPosition != -1 && adapter.canReorder(originalPosition) && adapter.canReorder(targetPosition)) {
                SwitchCellAnimator switchCellAnimator;

                reorderElements(originalPosition, targetPosition);
                if (this.mUndoSupportEnabled) {
                    this.mCurrentModification.addTransition(originalPosition, targetPosition);
                }
                this.mDownY = this.mLastEventY;
                this.mDownX = this.mLastEventX;
                if (isPostHoneycomb() && isPreLollipop()) {
                    switchCellAnimator = new KitKatSwitchCellAnimator(deltaX, deltaY);
                } else if (isPreLollipop()) {
                    switchCellAnimator = new PreHoneycombCellAnimator(deltaX, deltaY);
                } else {
                    switchCellAnimator = new LSwitchCellAnimator(deltaX, deltaY);
                }
                updateNeighborViewsForId(this.mMobileItemId);
                switchCellAnimator.animateSwitchCell(originalPosition, targetPosition);
                return;
            }
            updateNeighborViewsForId(this.mMobileItemId);
        }
    }

    private boolean belowLeft(Point targetColumnRowPair, Point mobileColumnRowPair) {
        return targetColumnRowPair.y > mobileColumnRowPair.y && targetColumnRowPair.x < mobileColumnRowPair.x;
    }

    private boolean belowRight(Point targetColumnRowPair, Point mobileColumnRowPair) {
        return targetColumnRowPair.y > mobileColumnRowPair.y && targetColumnRowPair.x > mobileColumnRowPair.x;
    }

    private boolean aboveLeft(Point targetColumnRowPair, Point mobileColumnRowPair) {
        return targetColumnRowPair.y < mobileColumnRowPair.y && targetColumnRowPair.x < mobileColumnRowPair.x;
    }

    private boolean aboveRight(Point targetColumnRowPair, Point mobileColumnRowPair) {
        return targetColumnRowPair.y < mobileColumnRowPair.y && targetColumnRowPair.x > mobileColumnRowPair.x;
    }

    private boolean above(Point targetColumnRowPair, Point mobileColumnRowPair) {
        return targetColumnRowPair.y < mobileColumnRowPair.y && targetColumnRowPair.x == mobileColumnRowPair.x;
    }

    private boolean below(Point targetColumnRowPair, Point mobileColumnRowPair) {
        return targetColumnRowPair.y > mobileColumnRowPair.y && targetColumnRowPair.x == mobileColumnRowPair.x;
    }

    private boolean right(Point targetColumnRowPair, Point mobileColumnRowPair) {
        return targetColumnRowPair.y == mobileColumnRowPair.y && targetColumnRowPair.x > mobileColumnRowPair.x;
    }

    private boolean left(Point targetColumnRowPair, Point mobileColumnRowPair) {
        return targetColumnRowPair.y == mobileColumnRowPair.y && targetColumnRowPair.x < mobileColumnRowPair.x;
    }

    private Point getColumnAndRowForView(View view) {
        int pos = getPositionForView(view);
        int columns = getColumnCount();
        return new Point(pos % columns, pos / columns);
    }

    private long getId(int position) {
        return getAdapter().getItemId(position);
    }

    @TargetApi(11)
    private void animateReorder(int oldPosition, int newPosition)
    {
        try {

            boolean isForward = newPosition > oldPosition;
            List<Animator> resultList = new LinkedList();
            int pos;
            View view;
            if (isForward) {
                for (pos = Math.min(oldPosition, newPosition); pos < Math.max(oldPosition, newPosition); pos++) {
                    view = getViewForId(getId(pos));
                    if ((pos + 1) % getColumnCount() == 0) {
                        resultList.add(createTranslationAnimations(view, (float) ((-view.getWidth()) * (getColumnCount() - 1)), 0.0f, (float) view.getHeight(), 0.0f));
                    } else {
                        resultList.add(createTranslationAnimations(view, (float) view.getWidth(), 0.0f, 0.0f, 0.0f));
                    }
                }
            } else {
                for (pos = Math.max(oldPosition, newPosition); pos > Math.min(oldPosition, newPosition); pos--) {
                    view = getViewForId(getId(pos));
                    if ((getColumnCount() + pos) % getColumnCount() == 0) {
                        resultList.add(createTranslationAnimations(view, (float) (view.getWidth() * (getColumnCount() - 1)), 0.0f, (float) (-view.getHeight()), 0.0f));
                    } else {
                        resultList.add(createTranslationAnimations(view, (float) (-view.getWidth()), 0.0f, 0.0f, 0.0f));
                    }
                }
            }
            AnimatorSet resultSet = new AnimatorSet();
            resultSet.playTogether(resultList);
            resultSet.setDuration(300);
            resultSet.setInterpolator(new AccelerateDecelerateInterpolator());
            resultSet.addListener(new C05556());
            resultSet.start();
        }
        catch (Exception e)
        {
            if (exception3!=null)
            exception3.onException();
        }
    }

    @TargetApi(11)
    private AnimatorSet createTranslationAnimations(View view, float startX, float endX, float startY, float endY) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX", new float[]{startX, endX});
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "translationY", new float[]{startY, endY});
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(new Animator[]{animX, animY});
        return animSetXY;
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mHoverCell != null) {
            this.mHoverCell.draw(canvas);
        }
    }
}
