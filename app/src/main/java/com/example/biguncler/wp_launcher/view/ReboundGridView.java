package com.example.biguncler.wp_launcher.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.GridView;

/**
 * Created by Biguncler on 05/29/17.
 */

public class ReboundGridView extends GridView {
    //移动因子, 是一个百分比, 比如手指移动了100px, 那么View就只移动50px
    //目的是达到一个延迟的效果
    private static final float MOVE_FACTOR = 0.5f;

    //松开手指后, 界面回到正常位置需要的动画时间
    private static final int ANIM_TIME = 150;
    //手指按下时的Y值, 用于在移动时计算移动距离
    //如果按下时不能上拉和下拉， 会在手指移动时更新为当前手指的Y值
    private float startY;

    //用于记录正常的布局位置
    private Rect originalRect = new Rect();

    //在手指滑动的过程中记录是否移动了布局
    private boolean isMoved = false;


    private boolean isTop;
    private boolean isBottom;
    private boolean isScrolled;
    private OnScrollBoundListener scrollBoundListener;
    private OnPullFinishListener pullFinishListener;

    public ReboundGridView(Context context) {
        super(context);
        init();
    }

    public ReboundGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReboundGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        // 消除拉到边界时的颜色
        setOverScrollMode(OVER_SCROLL_NEVER);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                // i的值有1（从静止到开始移动状态）,2（移动状态），0（从移动到静止状态）
                Log.i("weijunshu", "i=" + i);
                if(i==0){isScrolled=false;}
                if(i==2&&scrollBoundListener!=null) scrollBoundListener.onScrolling();
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                // i当前gridview显示的第一个item的下标， i1为gridvie当前页显示的acount， i2为adapter总item的数
                Log.i("weijunshu", "i=" + i + "/i1=" + i1 + "/i2=" + i2);
                isTop = false;
                isBottom = false;
                // 判断是否滑到了顶部
                if (i == 0) {
                    View child = getChildAt(0);
                    if (child != null && child.getTop() == 0) {
                        Log.i("weijunshu", "isTop");
                        isTop = true;
                        if(isScrolled&&scrollBoundListener!=null){
                            scrollBoundListener.onScrollTop();
                        }
                    }
                }
                // 判断是否滑到了底部
                if (i + i1 == i2) {
                    View child = getChildAt(i1 - 1);
                    if (child != null)
                        Log.i("weijunshu", "bottom=" + child.getBottom() + "/height=" + getHeight());
                    if (child != null && child.getBottom() == getHeight()) {
                        Log.i("weijunshu", "isBottom");
                        isBottom = true;
                        if(isScrolled&&scrollBoundListener!=null){
                            scrollBoundListener.onScrollBottom();
                        }
                    }
                }
                isScrolled=true;
            }
        });
    }


    /**
     * 在触摸事件中, 处理上拉和下拉的逻辑
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //记录按下时的Y值
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (!isMoved) break;  //如果没有移动布局， 则跳过执行

                if(getTop()>originalRect.top&&pullFinishListener!=null){
                    pullFinishListener.onPullDownFinish();
                }
                if(getTop()<originalRect.top&&pullFinishListener!=null){
                    pullFinishListener.onPullUpFinish();
                }
                // 开启动画
                TranslateAnimation anim = new TranslateAnimation(0, 0, this.getTop(), originalRect.top);
                anim.setDuration(ANIM_TIME);

                this.startAnimation(anim);

                // 设置回到正常的布局位置
                this.layout(originalRect.left, originalRect.top,
                        originalRect.right, originalRect.bottom);
                isMoved = false;

                break;
            case MotionEvent.ACTION_MOVE:
                //在移动的过程中， 既没有滚动到可以上拉的程度， 也没有滚动到可以下拉的程度
                if (!isTop && !isBottom) {
                    startY = ev.getY();
                    break;
                }
                //计算手指移动的距离
                float nowY = ev.getY();
                int deltaY = (int) (nowY - startY);
                //是否应该移动布局
                boolean shouldMove =
                        (isTop && deltaY > 0)    //可以下拉， 并且手指向下移动
                                || (isBottom && deltaY < 0)    //可以上拉， 并且手指向上移动
                                || (isTop && isBottom); //既可以上拉也可以下拉（这种情况出现在ScrollView包裹的控件比ScrollView还小）
                if (shouldMove) {
                    //计算偏移量
                    int offset = (int) (deltaY * MOVE_FACTOR);
                    // 记录初始状态
                    if (!isMoved) {
                        originalRect.set(getLeft(), getTop(), getRight(), getBottom());
                    }
                    //随着手指的移动而移动布局
                    layout(originalRect.left, originalRect.top + offset, originalRect.right, originalRect.bottom + offset);
                    isMoved = true;
                }
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 当gridview移动时，将事件给gridview消费掉
        if(isMoved) return isMoved;
        return super.onTouchEvent(ev);
    }

    public void setOnScrollBoundListener(OnScrollBoundListener listener) {
        this.scrollBoundListener = listener;
    }

    public void setPullFinishListener(OnPullFinishListener pullFinishListener) {
        this.pullFinishListener = pullFinishListener;
    }


    public interface OnScrollBoundListener{
        void onScrollTop();
        void onScrollBottom();
        void onScrolling();
    }


    public interface OnPullFinishListener{
        void onPullDownFinish();
        void onPullUpFinish();
    }


}
