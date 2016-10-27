package com.mcp1993.flowlayouttest.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mcp1993.flowlayouttest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/27 0027.
 */

public class FlowLayout extends ViewGroup {

    //储存行的list
    private List<Line> lines = new ArrayList<>();

    //
    private float vertical_space;
    private float horizontal_space;

    //当前行
    private Line mCurrentLine;

    //行的最大宽度，除去边距的宽度
    private int mMaxWidth;


    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        horizontal_space = array.getDimension(R.styleable.FlowLayout_width_space, 0);
        vertical_space = array.getDimension(R.styleable.FlowLayout_height_space, 0);
        array.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 每次测量之前都先清空集合，不然会覆盖掉以前
        lines.clear();
        mCurrentLine = null;
        // 获取总宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //计算最大宽度
        mMaxWidth = width - getPaddingLeft() - getPaddingRight();

        //测量孩子，遍历获取孩子
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 测量孩子
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            // 测量完需要将孩子添加到管理行的孩子的集合中，将行添加到管理行的集合中

            if (mCurrentLine == null) {
                //初次添加孩子
                mCurrentLine = new Line(mMaxWidth, horizontal_space);
                //添加孩子
                mCurrentLine.addView(childView);
                // 添加行
                lines.add(mCurrentLine);
            } else {
                // 行中有孩子的时候，判断时候能添加
                if (mCurrentLine.canAddView(childView)) {
                    //添加孩子
                    mCurrentLine.addView(childView);
                } else {
                    //添加到下一行
                    mCurrentLine = new Line(mMaxWidth, horizontal_space);
                    //添加孩子
                    mCurrentLine.addView(childView);
                    // 添加行
                    lines.add(mCurrentLine);

                }
            }
        }
        /**
         * 测量自己
         */
        // 测量自己只需要计算高度，宽度肯定会被填充满的

        int height = getPaddingTop() + getPaddingBottom();
        for (int i = 0; i < lines.size(); i++) {
            // 所有行的高度
            height += lines.get(i).height;
        }
        // 所有竖直的间距
        height += (lines.size() - 1) * vertical_space;

        // 测量
        setMeasuredDimension(width, height);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        // 这里只负责高度的位置，具体的宽度和子孩子的位置让具体的行去管理
        l = getPaddingLeft();
        t = getPaddingTop();

        for (int i = 0; i < lines.size(); i++) {

            // 获取行
            Line line = lines.get(i);

            //管理行
            line.layout(t, l);

            // 更新高度
            t += line.height;
            if (i != lines.size() - 1) {
                // 不是最后一条就添加间距
                t += vertical_space;
            }
        }

    }
}


