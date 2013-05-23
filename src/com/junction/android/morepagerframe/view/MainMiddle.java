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
		//setSelector(new ColorDrawable(Color.TRANSPARENT));// ѡ�е�ʱ��Ϊ͸��ɫ  
        setGravity(Gravity.CENTER);// λ�þ���  
        
        ImageAdapter imageAdapter = new ImageAdapter(context);    //����ͼƬ������������ͼƬ����ߺͿ�  
        setAdapter(imageAdapter);// ���ò˵�Adapter  
	}

}
