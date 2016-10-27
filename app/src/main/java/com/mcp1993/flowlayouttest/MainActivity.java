package com.mcp1993.flowlayouttest;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mcp1993.flowlayouttest.CustomView.FlowLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private FlowLayout flowLayout;
    private Random random;

    private String[] datas={
            "jfeijfd",
            "订单",
            "判断的点点滴滴d",
            "QQ",
            "dkfep",
            "游戏",
            "熊出没之熊大快跑",
            "美图秀秀",
            "浏览器",
            "单机游戏",
            "我的世界",
            "电影电视",
            "QQ空间",
            "旅游",
            "免费游戏",
            "2048",
            "刀塔传奇",
            "壁纸",
            "节奏大师"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowLayout= (FlowLayout) findViewById(R.id.flowLayout);
        random=new Random();
        for (int i=0;i<datas.length;i++){
            final TextView textView = new TextView(this);
            textView.setText(datas[i]);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(5, 5, 5, 5);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(14);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,textView.getText(),Toast.LENGTH_SHORT).show();
                }
            });

            // 设置彩色背景
            GradientDrawable normalDrawable = new GradientDrawable();
            normalDrawable.setShape(GradientDrawable.RECTANGLE);
            int a = 255;
            int r = 50 + random.nextInt(100);
            int g = 50 + random.nextInt(100);
            int b = 50 + random.nextInt(100);
            normalDrawable.setColor(Color.argb(a, r, g, b));

            // 设置按下的灰色背景
            GradientDrawable pressedDrawable = new GradientDrawable();
            pressedDrawable.setShape(GradientDrawable.RECTANGLE);
            pressedDrawable.setColor(Color.RED);

            // 背景选择器
            StateListDrawable stateDrawable = new StateListDrawable();
            stateDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
            stateDrawable.addState(new int[]{}, normalDrawable);

            // 设置背景选择器到TextView上
            textView.setBackground(stateDrawable);

            flowLayout.addView(textView);
        }

    }
}
