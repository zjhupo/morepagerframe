package com.junction.android.morepagerframe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.junction.android.morepagerframe.R;
import com.junction.android.morepagerframe.adapter.ImageAdapter;
import com.junction.android.morepagerframe.adapter.ImageLeftAdapter;
import com.junction.android.morepagerframe.adapter.WelcomePagerAdapter;
import com.junction.android.morepagerframe.view.MainMiddle;
import com.junction.android.util.DipAndPxChange;
import com.junction.android.util.ValueBean;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ViewPager mViewpager;
	private List<View> mListViews;
	private WelcomePagerAdapter mPagerAdapter;
	private RadioGroup mRadioGroup;
	private RadioButton mRadioBtn;
	private ImageAdapter imageAdapter ;
	
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        
        initView();
    }
    
    @Override
    protected void onResume() {
    	
    	super.onResume();
    	imageAdapter.notifyDataSetChanged();
    }
    private void initView(){
    	initBottom();
    	initViewPager();
    }
    
    private void initViewPager(){

		mListViews = new ArrayList<View>();
		//��ʼ����һҳ
		View firstPage = getLayoutInflater().inflate(
				R.layout.view_mainmidddle, null);
		GridView gridLeft = (GridView) firstPage.findViewById(R.id.gird_main);  
		gridLeft.setGravity(Gravity.CENTER);// λ�þ���  
		gridLeft.setNumColumns(2);
		gridLeft.setSelector(new ColorDrawable(Color.TRANSPARENT));// ѡ�е�ʱ��Ϊ͸��ɫ  
        
		ImageLeftAdapter imageleftAdapter = new ImageLeftAdapter(this);    //����ͼƬ������������ͼƬ����ߺͿ�  
        gridLeft.setAdapter(imageleftAdapter);// ���ò˵�Adapter  
        gridLeft.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Toast.makeText(MainActivity.this, "��ʽ�汾������µ�ҳ��", Toast.LENGTH_SHORT).show();
				//startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
				
			}
        	
        });
        
		//��ʼ���ڶ�ҳ
		View middlePage = getLayoutInflater().inflate(
				R.layout.view_mainmidddle, null);
		GridView gridMain = (GridView) middlePage.findViewById(R.id.gird_main);  
		gridMain.setGravity(Gravity.CENTER);// λ�þ���  
		gridMain.setSelector(new ColorDrawable(Color.TRANSPARENT));// ѡ�е�ʱ��Ϊ͸��ɫ  
        
        imageAdapter = new ImageAdapter(this);    //����ͼƬ������������ͼƬ����ߺͿ�  
        gridMain.setAdapter(imageAdapter);// ���ò˵�Adapter  
        gridMain.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(position<ValueBean.getMap().size()){
					int packagePosition = ValueBean.getMap().get(position);
		        	
		        	if(ValueBean.getSet().contains(packagePosition)){//�Ѱ�װӦ��
		        		Toast.makeText(MainActivity.this, "���Ѱ�װ��Ӧ�ã���ʽ�汾����򿪸�Ӧ��", Toast.LENGTH_SHORT).show();
		        	}else{//δ��װӦ��
		        		Toast.makeText(MainActivity.this, "����δ��װ��Ӧ�ã���ʽ�汾�����Ӧ��������ʾ", Toast.LENGTH_SHORT).show();
		        	}
				}else{//����������
					//Toast.makeText(MainActivity.this, "�������������Ӧ�����ҳ��", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, AppaddActivity.class);
					startActivity(intent);
				}

			}
        	
        });
        
        //��ʼ������ҳ
		View lastPage = initRight();

		mListViews.add(firstPage);
		mListViews.add(middlePage);
		mListViews.add(lastPage);

		mViewpager = (ViewPager) findViewById(R.id.welcomePager);
		mPagerAdapter = new WelcomePagerAdapter(mListViews);
		mViewpager.setAdapter(mPagerAdapter);
		mViewpager.setCurrentItem(1);

		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		int size = mListViews.size();
		for (int i = 0; i < size; i++) {
			mRadioBtn = new RadioButton(this);
			mRadioBtn.setBackgroundResource(R.drawable.img_page_indicator);
			mRadioBtn.setButtonDrawable(android.R.color.transparent);
			mRadioBtn.setPadding(10, 10, 10, 10);
			mRadioGroup.addView(mRadioBtn, new LayoutParams(22, 22));
		}
		mRadioGroup.check(mRadioGroup.getChildAt(1).getId());

		mViewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				mRadioGroup.check(mRadioGroup.getChildAt(position).getId());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
    }
    //����ҷ�ҳ
    private View initRight(){
    	
    	LinearLayout lastPage = new LinearLayout(MainActivity.this);
    	lastPage.setOrientation(LinearLayout.VERTICAL);
    	lastPage.setGravity(Gravity.CENTER_HORIZONTAL);
    	
		Drawable mDial = getResources().getDrawable(R.drawable.bg_content);
		int mDialHeight = mDial.getIntrinsicHeight();
		int high = DipAndPxChange.imageToDip(MainActivity.this, mDialHeight);
        int mDialWidth = mDial.getIntrinsicWidth();
        int width = DipAndPxChange.imageToDip(MainActivity.this, mDialWidth);

        lastPage.addView(addItem(width,high,1));
    	lastPage.addView(addItem(width,high,2));
    	lastPage.addView(addItem(width,high,3));
    	lastPage.addView(addItem(width,high,4));
    	
    	
    	ScrollView sv = new ScrollView(MainActivity.this);
    	sv.addView(lastPage);
    	return sv ;
    }
    //���ÿ����Ŀ
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
        
        //viewҪ����margin��view
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

         btn1.setBackgroundResource(ValueBean.hasArray[3*(num-1)]);
    	 btn2.setBackgroundResource(ValueBean.hasArray[3*(num-1)+1]);
    	 if(num==4){
    		 btn3.setBackgroundResource(R.drawable.ic_tianjia_none);
    	 }else{
    		 btn3.setBackgroundResource(ValueBean.hasArray[3*(num-1)+2]);
    	 }
    	
    	 
         if(num==1){
        	 imageIcon.setImageResource(R.drawable.ic_wifi);
        	 textTitle.setText("wifi�ȵ�");
        	 textDescription.setText("wifi�ȵ���ܽ���");
        	
         }else if(num==2){
        	 imageIcon.setImageResource(R.drawable.ic_jiaotong);
        	 textTitle.setText("�ǻ۽�ͨ");
        	 textDescription.setText("�ǻ۽�ͨ���ܽ���");
        	 
         }else if(num==3){
        	 imageIcon.setImageResource(R.drawable.ic_lvyou);
        	 textTitle.setText("�ǻ�����");
        	 textDescription.setText("�ǻ����ν��ܽ���");
        	 
         }else if(num==4){
        	 imageIcon.setImageResource(R.drawable.ic_huashu);
        	 textTitle.setText("��������");
        	 textDescription.setText("�������߽��ܽ���");
        	
         }
         return item1 ;
    }
    private void initBottom(){
    	
		Drawable mDial = getResources().getDrawable(R.drawable.btn_bianming_normal);
		
		int mDialHeight = mDial.getIntrinsicHeight();
		int high = DipAndPxChange.imageToDip(MainActivity.this, mDialHeight);
        
        int mDialWidth = mDial.getIntrinsicWidth();
        int width = DipAndPxChange.imageToDip(MainActivity.this, mDialWidth);
        
        Button btnBianming = (Button)findViewById(R.id.btn_bianming);
        btnBianming.setWidth(width);
        btnBianming.setHeight(high);
        
        Button btnZhoubian = (Button)findViewById(R.id.btn_zhoubian);
        btnZhoubian.setWidth(width);
        btnZhoubian.setHeight(high);
        btnZhoubian.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, NearbyActivity.class);
				startActivity(intent);
				
			}
		});
        
        Button btnShezhi = (Button)findViewById(R.id.btn_shezhi);
        btnShezhi.setWidth(width);
        btnShezhi.setHeight(high);
        btnShezhi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SetActivity.class);
				startActivity(intent);
				
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_main, menu);
        return true;
    }
}
