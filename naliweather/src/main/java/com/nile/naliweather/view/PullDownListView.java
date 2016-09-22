package com.nile.naliweather.view;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nalib.fwk.utils.NaLog;
import com.nile.app.naliweather.R;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

/**
 * @actor:taotao
 * @DATE: 16/9/2
 */
public class PullDownListView extends RelativeLayout implements OnScrollListener {
    private final static String Tag = "PullDownListView";
    static int MAX_PULL_TOP_HEIGHT;
    static int MAX_PULL_BOTTOM_HEIGHT;

    static int REFRESHING_TOP_HEIGHT;
    static int REFRESHING_BOTTOM_HEIGHT;
    RelativeLayout layoutHeader;
    RelativeLayout layoutFooter;
    boolean pullTag = false;
    OnScrollListener mOnScrollListener;
    OnPullHeightChangeListener mOnPullHeightChangeListener;
    private boolean isTop;
    private boolean isBottom;
    private boolean isRefreshing;
    private boolean isAnimation;
    private int mCurrentY = 0;
    private ListView mListView = new ListView(getContext()) {

        int lastY = 0;

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            if (isAnimation || isRefreshing) {
                return super.onTouchEvent(ev);
            }
            RelativeLayout parent = (RelativeLayout) mListView.getParent();

            int currentY = (int) ev.getRawY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastY = (int) ev.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE: {
                    boolean isToBottom = currentY - lastY >= 0 ? true : false;

                    int step = Math.abs(currentY - lastY);
                    lastY = currentY;

                    if (isTop && mListView.getTop() >= 0) {

                        if (isToBottom && mListView.getTop() <= MAX_PULL_TOP_HEIGHT) {
                            MotionEvent event = MotionEvent.obtain(ev);
                            ev.setAction(MotionEvent.ACTION_UP);
                            super.onTouchEvent(ev);
                            pullTag = true;

                            if (mListView.getTop() > layoutHeader.getHeight()) {
                                step = step / 2;
                            }
                            if ((mListView.getTop() + step) > MAX_PULL_TOP_HEIGHT) {
                                mCurrentY = MAX_PULL_TOP_HEIGHT;
                                scrollTopTo(mCurrentY);
                            } else {
                                mCurrentY += step;
                                scrollTopTo(mCurrentY);
                            }
                        } else if (!isToBottom && mListView.getTop() > 0) {
                            MotionEvent event = MotionEvent.obtain(ev);
                            ev.setAction(MotionEvent.ACTION_UP);
                            super.onTouchEvent(ev);
                            if ((mListView.getTop() - step) < 0) {
                                mCurrentY = 0;
                                scrollTopTo(mCurrentY);
                            } else {
                                mCurrentY -= step;
                                scrollTopTo(mCurrentY);
                            }
                        } else if (!isToBottom && mListView.getTop() == 0) {
                            if (!pullTag) {
                                return super.onTouchEvent(ev);
                            }

                        }

                        return true;
                    } else if (isBottom
                            && mListView.getBottom() <= parent.getHeight()) {
                        if (!isToBottom && (parent.getHeight() - mListView.getBottom()) <= MAX_PULL_BOTTOM_HEIGHT) {
                            MotionEvent event = MotionEvent.obtain(ev);
                            ev.setAction(MotionEvent.ACTION_UP);
                            super.onTouchEvent(ev);
                            pullTag = true;
                            if (parent.getHeight() - mListView.getBottom() > layoutFooter.getHeight()) {
                                step = step / 2;
                            }

                            if ((mListView.getBottom() - step) < (parent.getHeight() - MAX_PULL_BOTTOM_HEIGHT)) {
                                mCurrentY = -MAX_PULL_BOTTOM_HEIGHT;
                                scrollBottomTo(mCurrentY);
                            } else {
                                mCurrentY -= step;
                                scrollBottomTo(mCurrentY);
                            }
                        } else if (isToBottom && (mListView.getBottom() < parent.getHeight())) {
                            if ((mListView.getBottom() + step) > parent.getHeight()) {
                                mCurrentY = 0;
                                scrollBottomTo(mCurrentY);
                            } else {
                                mCurrentY += step;
                                scrollBottomTo(mCurrentY);
                            }
                        } else if (isToBottom && mListView.getBottom() == parent.getHeight()) {
                            if (!pullTag) {
                                return super.onTouchEvent(ev);
                            }
                        }
                        return true;
                    }
                    break;
                }
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    pullTag = false;

                    if (mListView.getTop() > 0) {
                        if (mListView.getTop() > REFRESHING_TOP_HEIGHT) {
                            animateTopTo(layoutHeader.getMeasuredHeight());
                            isRefreshing = true;
                            if (null != mOnPullHeightChangeListener) {
                                mOnPullHeightChangeListener.onRefreshing(true);
                            }
                        } else {
                            animateTopTo(0);
                        }

                    } else if (mListView.getBottom() < parent.getHeight()) {
                        if ((parent.getHeight() - mListView.getBottom()) > REFRESHING_BOTTOM_HEIGHT) {
                            animateBottomTo(-layoutFooter.getMeasuredHeight());
                            isRefreshing = true;
                            if (null != mOnPullHeightChangeListener) {
                                mOnPullHeightChangeListener.onRefreshing(false);
                            }
                        } else {
                            animateBottomTo(0);
                        }
                    }

            }


            return super.onTouchEvent(ev);
        }

    };

    public PullDownListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnPullHeightChangeListener(
            OnPullHeightChangeListener listener) {
        this.mOnPullHeightChangeListener = listener;
    }

    public void setOnScrollListener(OnScrollListener listener) {
        mOnScrollListener = listener;
    }

    public boolean isRefreshing() {
        return this.isRefreshing;
    }

    public void scrollBottomTo(int y) {
        mListView.layout(mListView.getLeft(), y, mListView.getRight(), this.getMeasuredHeight() + y);
        if (null != mOnPullHeightChangeListener) {
            mOnPullHeightChangeListener.onBottomHeightChange(layoutHeader.getHeight(), -y);
        }
    }

    public void animateBottomTo(final int y) {
        ValueAnimator animator = ValueAnimator.ofInt(mListView.getBottom() - this.getMeasuredHeight(), y);
        animator.setDuration(300);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int frameValue = (Integer) animation.getAnimatedValue();
                mCurrentY = frameValue;
                scrollBottomTo(frameValue);
                if (frameValue == y) {
                    isAnimation = false;
                }
            }
        });
        isAnimation = true;
        animator.start();
    }

    public void scrollTopTo(int y) {
        mListView.layout(mListView.getLeft(), y, mListView.getRight(), this.getMeasuredHeight() + y);
        if (null != mOnPullHeightChangeListener) {
            mOnPullHeightChangeListener.onTopHeightChange(layoutHeader.getHeight(), y);
        }
    }


    public void animateTopTo(final int y) {
        ValueAnimator animator = ValueAnimator.ofInt(mListView.getTop(), y);
        animator.setDuration(300);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int frameValue = (Integer) animation.getAnimatedValue();
                mCurrentY = frameValue;
                scrollTopTo(frameValue);
                if (frameValue == y) {
                    isAnimation = false;
                }
            }
        });
        isAnimation = true;
        animator.start();
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        REFRESHING_TOP_HEIGHT = layoutHeader.getMeasuredHeight();
        REFRESHING_BOTTOM_HEIGHT = layoutFooter.getMeasuredHeight();

        MAX_PULL_TOP_HEIGHT = this.getMeasuredHeight();
        MAX_PULL_BOTTOM_HEIGHT = this.getMeasuredHeight();
    }

    @Override
    public void onFinishInflate() {

        mListView.setBackgroundColor(0xffffffff);
        mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.setVerticalScrollBarEnabled(false);
        mListView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mListView.setOnScrollListener(this);
        this.addView(mListView);

        layoutHeader = (RelativeLayout) this.findViewById(R.id.layoutHeader);
        layoutFooter = (RelativeLayout) this.findViewById(R.id.layoutFooter);


        super.onFinishInflate();
    }


    public ListView getListView() {
        return this.mListView;
    }

    public void pullUp() {
        isRefreshing = false;
        if (mListView.getTop() > 0) {
            animateTopTo(0);
        } else if (mListView.getBottom() < this.getHeight()) {
            animateBottomTo(0);
        }

    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (null != mOnScrollListener) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
        if (mListView.getCount() > 0) {
            if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                View lastItem = mListView
                        .getChildAt(visibleItemCount - 1);
                if (null != lastItem) {

                    if (lastItem.getBottom() == mListView.getHeight()) {
                        NaLog.e(Tag, lastItem.getBottom() + "");
                        isBottom = true;
                    } else {
                        isBottom = false;
                    }
                }
            } else {
                isBottom = false;
            }
        } else {
            isBottom = false;
        }

        if (mListView.getCount() > 0) {
            if (firstVisibleItem == 0) {
                View firstItem = mListView.getChildAt(0);
                if (null != firstItem) {
                    isTop = firstItem.getTop() == 0;
                }
            } else {
                isTop = false;
            }
        } else {
            isTop = true;
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (null != mOnScrollListener) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    public void setAdapter(BaseAdapter adapter) {
        if (mListView != null) {
            mListView.setAdapter(adapter);
        }
    }

    // listener call back
    public interface OnPullHeightChangeListener {
        void onTopHeightChange(int headerHeight, int pullHeight);

        void onBottomHeightChange(int footerHeight, int pullHeight);

        void onRefreshing(boolean isTop);
    }
}