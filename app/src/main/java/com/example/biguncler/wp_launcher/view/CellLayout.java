package com.example.biguncler.wp_launcher.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.biguncler.wp_launcher.R;
import com.example.biguncler.wp_launcher.adapter.CellLayoutAdapter;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CellLayout extends ViewGroup {
    private int columCount = 3;
    private int space = 0;
    private DataSetObserver observer=new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateView();
        }
    };

    ArrayList<View>[] views;
    private BaseAdapter adapter;


    public CellLayout(Context context) {
        this(context, null);
        init();
    }

    public CellLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }


    public CellLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CellLayout);
        space = typedArray.getDimensionPixelSize(R.styleable.CellLayout_cellSpace, 0);
        setPadding(space,getPaddingTop(),space,0);
        columCount = typedArray.getInteger(R.styleable.CellLayout_columCount, 3);
        typedArray.recycle();
    }

    private void init(){
        Observable.interval(3,15, java.util.concurrent.TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                    int count = getChildCount();
                    if(count>0){
                        int postion=(int)(Math.random()*(count));
                        View view=getChildAt(postion);
                        if(view!=null){
                            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotationX", 0, 360);
                            animator.setDuration(2000).start();
                        }
                    }
                }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //根据列数columCount和间隙space求出每个格子宽度
        int hs = space * (columCount - 1);
        int cellHeight = getCellWidth();
        int childCount = getChildCount();

        int cell = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutParams layoutParams = child.getLayoutParams();
            int scale = layoutParams.width / layoutParams.height;
            if (scale < 1) scale = 1;
            if (scale > columCount) scale = columCount;
            layoutParams.height = cellHeight;
            layoutParams.width = layoutParams.height * scale + space * (scale - 1);

            // 显示不下时，换行
            int b = cell % columCount;
            if (b != 0 && (b + scale) > columCount) {
                cell += columCount - b;
            }
            cell += scale;

            // 计算哪行哪列
            int row = (int) Math.ceil(((double) cell) / columCount);//行数
            int colum = 0;
            int c = cell % columCount;
            if (c == 0) {
                colum = columCount - scale + 1;
            } else {
                colum = c - scale + 1;
            }

            Point point = new Point(colum, row);
            child.setTag(point);

            //测量子view
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //保存子view宽高
            child.measure(getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), layoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec, 0, layoutParams.height));
        }

        //总宽度
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        //总行高
        int totalRowHeight = 0;
        int rowCount = (int) Math.ceil(((double) cell) / columCount);//行数
        if (rowCount < 1) rowCount = 1;
        totalRowHeight = rowCount * cellHeight;
        int heightSize = space * (rowCount - 1) + totalRowHeight + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(widthSize, heightSize);

    }


    /**
     * 获取child所在的行数
     * 从第0行算
     *
     * @param childIndex
     * @return
     */
    public int getRowIndex(int childIndex) {
        return (int) Math.ceil((childIndex + 1) / (double) columCount) - 1;
    }

    /**
     * 获取总列数
     *
     * @return
     */
    public int getColumCount() {
        return columCount;
    }

    /**
     * 获取每格宽度
     *
     * @return
     */
    public int getCellWidth() {
        return (getMeasuredWidth() - space * (columCount - 1) - getPaddingRight() - getPaddingRight()) / columCount;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Point point = (Point) child.getTag();
            int left = getPaddingLeft() + getCellWidth() * (point.x - 1) + (point.x - 1) * space;
            int top = getPaddingTop() + getCellWidth() * (point.y - 1) + (point.y - 1) * space;
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();
            child.layout(left, top, right, bottom);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(ViewGroup parent, View view, int position, long id);
    }

    @Override
    public void removeViewAt(int index) {
        super.removeViewAt(index);
        if (onItemClickListener != null) {
            setOnItemClickListener(onItemClickListener);
        }
    }

    private OnItemClickListener onItemClickListener;


    /**
     * 写个适配器简化一下添加view
     * 暂不支持回收
     *
     * @param adapter
     */
    public void setAdapter(BaseAdapter adapter) {
        if (adapter == null) {
            return;
        }
       this.adapter=adapter;
        if( observer!=null){
            try{
                adapter.unregisterDataSetObserver(observer);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        adapter.registerDataSetObserver(observer);
        updateView();
    }



    public void updateView(){
        if (adapter == null) {
            return;
        }
        removeAllViews();
        if(views==null){
            views=new ArrayList[adapter.getViewTypeCount()];
        }
        for (int i = 0; i < adapter.getCount(); i++) {
            int type=adapter.getItemViewType(i);
            if(views[type]==null){
                views[type] = new ArrayList<>();
            }
            if(views[type].isEmpty()){
                View view=adapter.getView(i, null, this);
                views[type].add(view);
                addView(view);
            }else{
                View temp=null;
                for(View v:views[type]){
                    boolean isAdd=false;
                    for(int j=0;j<getChildCount();j++){
                        if(getChildAt(j)==v){
                            isAdd=true;
                            break;
                        }
                    }
                    if(!isAdd){
                        temp=v;
                        break;
                    }
                }
                View view=adapter.getView(i, temp, this);
                if(temp==null){
                    views[type].add(view);
                }
                addView(view);
            }
        }
        if(onItemClickListener!=null){
            setOnItemClickListener(onItemClickListener);
        }
    }

    /**
     * Item的点击事件
     *
     * @param listener
     */
    public void setOnItemClickListener(final OnItemClickListener listener) {
        if (listener == null) {
            return;
        }
        this.onItemClickListener = listener;
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            final int position = i;
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(CellLayout.this, child, position, child.getId());
                }
            });
        }
    }

    public void setColumCount(int columCount) {
        this.columCount = columCount;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;

        setPadding(space,getPaddingTop(),space,0);
    }


}