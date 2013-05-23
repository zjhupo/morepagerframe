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
	private Double[] str ;// ��γ������
	private int msg_shopDetail = 1 ;// ��λ��Ϣ�鿴
	private int msg_locationErr = 2;// ��λ�쳣
	private int msg_locationNull = 3;// û���ֻ�����wifi
	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			 if (msg.what == msg_shopDetail) {// ��λ��Ϣ�鿴
					dlg.dismiss();
					// ���½���
					if(str[0] == null || str[1] == null){
						Toast.makeText(NearbyActivity.this, "��λʧ�ܣ���������", Toast.LENGTH_LONG).show();
					}else{
						Log.i("loacation", "����:"+str[1].toString()+" ,γ��:"+str[0].toString());
						
					}
			 }else if(msg.what == msg_locationErr){// ��λ�쳣
					dlg.dismiss();
					Toast.makeText(NearbyActivity.this, "�ܱ�Ǹ����λʧ��,��������", Toast.LENGTH_LONG).show();
				}else if(msg.what == msg_locationNull){// û���ֻ�����wifi
					dlg.dismiss();
					Toast.makeText(NearbyActivity.this, "�ܱ�Ǹ����λʧ��,��ȷ�������ֻ�����������wifi", Toast.LENGTH_LONG).show();
				}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_nearby);
		//getLocation();
		
		webView = (WebView) this.findViewById(R.id.web_map);
		webView.getSettings().setJavaScriptEnabled(true);// ����֧��javaScript
		webView.getSettings().setSupportZoom(true);// آ֧��ҳ��Ŵ���
		// addJavascriptInterface������Ҫ�󶨵�Java���󼰷���Ҫ������������߳��У�آ�������ڹ��������߳���
		
			webView.addJavascriptInterface(new MyJavaScriptMap(), "itcast");
			webView.loadUrl("file:///android_asset/showmap.html");
		
		
		
	}
	
	private final class MyJavaScriptMap {
		public void call() {
			handler.post(new Runnable() {
				@Override
				public void run() {
					webView.loadUrl("javascript:show('" + "�㽭׿��"  + "',"+30.3+","+120.2+")");
//					webView.loadUrl("javascript:initialize()");
				}
			});
		}
		
	}
	
	private void getLocation(){
		dlg = new ProgressDialog(NearbyActivity.this);
		dlg.setMessage("���Ժ����ڶ�λ...");
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
