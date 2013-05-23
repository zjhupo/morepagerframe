package com.junction.android.morepagerframe;

import com.junction.android.morepagerframe.R;
import com.junction.android.util.DipAndPxChange;
import com.junction.android.util.ValueBean;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class AppaddActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_appadd);
		
		LinearLayout layoutContent = (LinearLayout)findViewById(R.id.layout_content) ;
		layoutContent.addView(addView());
	}
	 //添加右翻页
    private View addView(){
    	
    	LinearLayout lastPage = new LinearLayout(AppaddActivity.this);
    	lastPage.setOrientation(LinearLayout.VERTICAL);
    	lastPage.setGravity(Gravity.CENTER_HORIZONTAL);
    	
		Drawable mDial = getResources().getDrawable(R.drawable.bg_content);
		int mDialHeight = mDial.getIntrinsicHeight();
		int high = DipAndPxChange.imageToDip(AppaddActivity.this, mDialHeight);
        int mDialWidth = mDial.getIntrinsicWidth();
        int width = DipAndPxChange.imageToDip(AppaddActivity.this, mDialWidth);

        lastPage.addView(addItem(width,high,1));
    	lastPage.addView(addItem(width,high,2));
    	lastPage.addView(addItem(width,high,3));
    	lastPage.addView(addItem(width,high,4));
    	
    	
    	ScrollView sv = new ScrollView(AppaddActivity.this);
    	sv.addView(lastPage);
    	return sv ;
    }
    //添加每个栏目
    private View addItem(int width,int high,int num){
    	 LinearLayout item1 = (LinearLayout)getLayoutInflater().inflate(
 				R.layout.item_content, null);
    	 
         LayoutParams para;
         ImageView imageBg = (ImageView)item1.findViewById(R.id.image_bg);
         para = imageBg.getLayoutParams();
         para.height = high;
         para.width = width;
         imageBg.setLayoutParams(para);
         
         RelativeLayout layoutTitle = (RelativeLayout)item1.findViewById(R.id.layout_title);
         layoutTitle.setClickable(true);
         
         ImageView image = (ImageView)item1.findViewById(R.id.image_icon);
         RelativeLayout.LayoutParams lp = ( RelativeLayout.LayoutParams)image.getLayoutParams(); 
         lp.leftMargin=width/14 ;
        
        //view要设置margin的view
         image.setLayoutParams(lp);  
         
         
         
         final LinearLayout layoutContent = (LinearLayout)item1.findViewById(R.id.layout_content);
         layoutTitle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ImageView imageBg = (ImageView)v.findViewById(R.id.image_right);
				if(layoutContent.getVisibility()==View.GONE){
					layoutContent.setVisibility(View.VISIBLE);
					imageBg.setImageResource(R.drawable.ic_down);
				}else if(layoutContent.getVisibility()==View.VISIBLE){
					layoutContent.setVisibility(View.GONE);
					imageBg.setImageResource(R.drawable.ic_right);
				}
				
			}
		});
         
         ImageView imageIcon = (ImageView)item1.findViewById(R.id.image_icon);
         TextView textTitle = (TextView)item1.findViewById(R.id.text_title);
         TextView textDescription = (TextView)item1.findViewById(R.id.text_description);
         Button btn1 = (Button)item1.findViewById(R.id.btn1);
         Button btn2 = (Button)item1.findViewById(R.id.btn2);
         Button btn3 = (Button)item1.findViewById(R.id.btn3);

         buttonSet(btn1,3*(num-1));
         buttonSet(btn2,3*(num-1)+1);
         
    	 if(num==4){
    		 btn3.setBackgroundResource(R.drawable.ic_tianjia_none);
    	 }else{
    		 buttonSet(btn3,3*(num-1)+2);
    	 }
    	
    	 
         if(num==1){
        	 imageIcon.setImageResource(R.drawable.ic_wifi);
        	 textTitle.setText("wifi热点");
        	 textDescription.setText("wifi热点介绍介绍");
        	
         }else if(num==2){
        	 imageIcon.setImageResource(R.drawable.ic_jiaotong);
        	 textTitle.setText("智慧交通");
        	 textDescription.setText("智慧交通介绍介绍");
        	 
         }else if(num==3){
        	 imageIcon.setImageResource(R.drawable.ic_lvyou);
        	 textTitle.setText("智慧旅游");
        	 textDescription.setText("智慧旅游介绍介绍");
        	 
         }else if(num==4){
        	 imageIcon.setImageResource(R.drawable.ic_huashu);
        	 textTitle.setText("华数在线");
        	 textDescription.setText("华数在线介绍介绍");
        	
         }
         return item1 ;
    }
    
    private void buttonSet(Button btn,final int number){
    	if(ValueBean.getMap().containsValue(number)){
    		btn.setBackgroundResource(ValueBean.hasArray[number]);
    		btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(AppaddActivity.this, "该应用已在首页显示", Toast.LENGTH_SHORT).show();
					
				}
			});
        }else{
        	btn.setBackgroundResource(ValueBean.noneArray[number]);
        	btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ValueBean.addApp(number);
		        	finish();
				}
			});
        	//
        }
    }
}
