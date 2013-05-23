package com.junction.android.morepagerframe;

import com.junction.android.morepagerframe.R;
import com.junction.android.util.SelfLocation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;


public class NearbyActivity extends Activity {
	private WebView webView;
	private Handler handler = new Handler();
	
	private ProgressDialog dlg;
	private Double[] str ;// 经纬度数组
	private int msg_shopDetail = 1 ;// 定位信息查看
	private int msg_locationErr = 2;// 定位异常
	private int msg_locationNull = 3;// 没有手机卡和wifi
	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			 if (msg.what == msg_shopDetail) {// 定位信息查看
					dlg.dismiss();
					// 更新界面
					if(str[0] == null || str[1] == null){
						Toast.makeText(NearbyActivity.this, "定位失败，请检查网络", Toast.LENGTH_LONG).show();
					}else{
						Log.i("loacation", "经度:"+str[1].toString()+" ,纬度:"+str[0].toString());
						
					}
			 }else if(msg.what == msg_locationErr){// 定位异常
					dlg.dismiss();
					Toast.makeText(NearbyActivity.this, "很抱歉，定位失败,请检查网络", Toast.LENGTH_LONG).show();
				}else if(msg.what == msg_locationNull){// 没有手机卡和wifi
					dlg.dismiss();
					Toast.makeText(NearbyActivity.this, "很抱歉，定位失败,请确定插入手机卡或者链接wifi", Toast.LENGTH_LONG).show();
				}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_nearby);
		//getLocation();
		
		webView = (WebView) this.findViewById(R.id.web_map);
		webView.getSettings().setJavaScriptEnabled(true);// 设置支持javaScript
		webView.getSettings().setSupportZoom(true);// 丌支持页面放大功能
		// addJavascriptInterface方法中要绑定的Java对象及方法要运行在另外的线程中，丌能运行在构造他的线程中
		
			webView.addJavascriptInterface(new MyJavaScriptMap(), "itcast");
			webView.loadUrl("file:///android_asset/showmap.html");
		
		
		
	}
	
	private final class MyJavaScriptMap {
		public void call() {
			handler.post(new Runnable() {
				@Override
				public void run() {
					webView.loadUrl("javascript:show('" + "浙江卓信"  + "',"+30.3+","+120.2+")");
//					webView.loadUrl("javascript:initialize()");
				}
			});
		}
		
	}
	
	private void getLocation(){
		dlg = new ProgressDialog(NearbyActivity.this);
		dlg.setMessage("请稍候，正在定位...");
		dlg.show();
		new Thread() {
			public void run() {
				try {
					str = SelfLocation.getLocationAll(NearbyActivity.this);
					Message ms = new Message();
					if(str==null){
						ms.what = msg_locationNull;
					}else{
						ms.what = msg_shopDetail;
					}
					
					myHandler.sendMessage(ms);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					Message ms = new Message();
					ms.what = msg_locationErr;
					myHandler.sendMessage(ms);
				}
			}
		}.start();
	}
}
