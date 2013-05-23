package com.junction.android.morepagerframe.adapter;

import com.junction.android.morepagerframe.R;

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
public class ImageLeftAdapter extends BaseAdapter {  
    private Context context;  

    private int[] noneArray = {R.drawable.ic_news,R.drawable.ic_message,
			R.drawable.ic_leftadd}; 

      
    public ImageLeftAdapter(Context context) {  
        this.context = context;  
    }  
  
    public int getCount() {  
        // TODO Auto-generated method stub  
        return noneArray.length;  
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
    	
    	image.setImageResource(noneArray[position]);
    	image.setLayoutParams(new GridView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
          
        return image;  
    }  
  
    
}  
