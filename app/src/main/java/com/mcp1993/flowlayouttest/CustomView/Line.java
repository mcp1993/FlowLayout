package com.mcp1993.flowlayouttest.CustomView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/27 0027.
 * 行管理器，管理行中view
 */

public class Line {
    private List<View> views =new ArrayList<>();

    public int maxWidth;
    public int usedWidth;
    public int height;
    public float space;

    public Line(int MaxWidth,float horizontalSpace){
        this.maxWidth=MaxWidth;
        this.space=horizontalSpace;
    }

    /**
     *
     * @param view
     * 添加view到veiws中
     */
    public void addView(View view){
        int childWidth=view.getMeasuredWidth();
        int childHeight=view.getHeight();

        if(views.size()==0){

            if (childWidth > maxWidth){
                usedWidth=maxWidth;
                height=childHeight;
            }else {
                usedWidth = childWidth;
                height = childHeight;
            }

        }else {
            usedWidth += childWidth + space;
            height = childHeight > height ? childHeight : height;
        }
        views.add(view);
    }

    /**
     *
     * @param view
     * @return 判断该行是否可以添加view
     */
    public boolean canAddView(View view){
        if (views.size()==0){
            return true;
        }

        if (view.getMeasuredWidth()>(maxWidth - usedWidth - space)){
            return false;
        }
        // 默认可以添加
        return true;
    }

    /**
     * 绘制veiw的位置
     * @param i
     * @param l
     */
    public void layout(int i,int l){

        int average=(maxWidth-usedWidth)/views.size();

        //循环指定孩子的位置

        for(View view:views){
            int measuredWidth=view.getMeasuredWidth();
            int measureHeight=view.getMeasuredHeight();

            //重新测量
            view.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth + average, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(measureHeight , View.MeasureSpec.EXACTLY));

            // 重新获取宽度值
            measuredWidth = view.getMeasuredWidth();

            int top = i;
            int left = l;
            int right=measuredWidth + left;
            int bottom = measureHeight + top;

            view.layout(left, top, right, bottom);

            // 更新数据
            l += measuredWidth + space;

        }
    }
}
