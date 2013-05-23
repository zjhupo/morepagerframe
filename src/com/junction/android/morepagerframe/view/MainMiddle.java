package com.junction.android.morepagerframe.view;


import com.junction.android.morepagerframe.R;
import com.junction.android.morepagerframe.adapter.ImageAdapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.widget.GridView;

public class MainMiddle extends GridView {
	
	public MainMiddle(Context context) {
		super(context);
		
		setNumColumns(3);
		setVerticalSpacing(20);
		setHorizontalSpacing(20);
		//setSelector(new ColorDrawable(Color.TRANSPARENT));// 选中的时候为透明色  
        setGravity(Gravity.CENTER);// 位置居中  
        
        ImageAdapter imageAdapter = new ImageAdapter(context);    //创建图片适配器，传递图片所需高和宽  
        setAdapter(imageAdapter);// 设置菜单Adapter  
	}

}
