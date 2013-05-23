package com.junction.android.morepagerframe.adapter;

import com.junction.android.morepagerframe.R;
import com.junction.android.util.ValueBean;

import android.content.Context;  
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;  
import android.view.ViewGroup;  
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;  
import android.widget.GridView;  
import android.widget.ImageView;  
import android.widget.ImageView.ScaleType;
/** 
 * RootActivity用于下面菜单栏显示
 */
public class ImageAdapter extends BaseAdapter {  
    private Context context;  

    
    public ImageAdapter(Context context) {  
        this.context = context;  
    }  
  
    public int getCount() {  
        // TODO Auto-generated method stub  
        return ValueBean.getMap().size()+1;  
    }  
  
    public Object getItem(int position) {  
        // TODO Auto-generated method stub  
        return position;  
    }  
  
    public long getItemId(int position) {  
        // TODO Auto-generated method stub  
        return position;  
    }  
      
    
    /** 
     * 图片设置 
     */  
    public View getView(int position, View convertView, ViewGroup parent) {  
        // TODO Auto-generated method stub  
        //Log.e("Seqence", "getView");  
        //imageViews[position].setImageResource(imgeIDs[position]);  
    	ImageView image = new ImageView(context);
    	if(position<ValueBean.getMap().size()){
    		int packagePosition = ValueBean.getMap().get(position);
        	
        	if(ValueBean.getSet().contains(packagePosition)){
        		image.setImageResource(ValueBean.hasArray[packagePosition]);
        	}else{
        		image.setImageResource(ValueBean.noneArray[packagePosition]);
        	}
    	}else{
    		image.setImageResource(R.drawable.ic_tianjia_none);
    	}
    	
    	
    	image.setLayoutParams(new GridView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
          
        return image;  
    }  
  
    
}  
