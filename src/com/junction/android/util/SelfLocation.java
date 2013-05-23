package com.junction.android.util;


import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

/**
 * @Description 定位，地址解析
 * @author 楼周峰
 * @time 2012-06-19
 * @modify author 修改人
 * @modify time 修改时间
 * @modify Description 修改内容描述
 */
public class SelfLocation {
	private static Location location = null;

	private static String strLocationProvider = LocationManager.GPS_PROVIDER;

	private static LocationManager locationManager;

	public final static LocationListener mLocationListener01 = new LocationListener() {

		public void onLocationChanged(Location mylocation) {
			location = mylocation;
			// 更新操作
			cb.locationLoaded();
			removeLocation();

		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	};

	public static void initGPSLocation(Context context,
			LocationCallback callback) {
		cb = callback;
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		locationManager.requestLocationUpdates(strLocationProvider, 100, 0,
				mLocationListener01);

	}

	public static void removeLocation() {
		locationManager.removeUpdates(mLocationListener01);
	}

	public interface LocationCallback {
		public void locationLoaded();
	}

	private static LocationCallback cb;

	/**
	 * 根据经纬度反向解析地址，有时需要多尝试几次
	 * 注意:(摘自：http://code.google.com/intl/zh-CN/apis/maps/faq.html
	 * 提交的地址解析请求次数是否有限制？) 如果在 24 小时时段内收到来自一个 IP 地址超过 15,000 个地址解析请求， 或从一个 IP
	 * 地址提交的地址解析请求速率过快，Google 地图 API 编码器将用 620 状态代码开始响应。 如果地址解析器的使用仍然过多，则从该 IP
	 * 地址对 Google 地图 API 地址解析器的访问可能被永久阻止。
	 * 
	 * @param latitude
	 *            纬度
	 * @param longitude
	 *            经度
	 * @return String 经纬度解析出来的地址
	 */
	public static String geocodeAddr(double latitude, double longitude,
			Context context) {

		String url = String.format(
				"http://ditu.google.cn/maps/geo?output=xml&key=abcdef&q=%s,%s",
				latitude, longitude);
		String addXml = "";
		String addr = "";
		String addinfo = "";
		try {
			addXml = com.junction.android.util.HttpPost.doPost(url, "");
			addr = XmlParser.parse(addXml, "ThoroughfareName");// 地址
			if ("".equals(addr)) {
				addr = XmlParser.parse(addXml, "AddressLine");// 地址
			}
			addinfo = XmlParser.parse(addXml, "address");// 位置全称
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return addr;
	}
	/**
	 * 判断是否支持手机基站定位
	 */
	public static Boolean ifSupportNetWork(Context context){
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		CellLocation cellLocation = tm.getCellLocation();
		if(cellLocation!=null && cellLocation instanceof GsmCellLocation)
			return true ;
		return false ;
	}
	
	/**
	 * 判断是否支持wifi定位
	 */
	public static Boolean ifSupportWifi(Context context){
		
		WifiManager mainWifi = (WifiManager) context
		.getSystemService(Context.WIFI_SERVICE);
		mainWifi.startScan();
		List<ScanResult> wifiList = mainWifi.getScanResults();
		if(wifiList!=null)
			return true ;
		return false ;
	}
	/**
	 * network定位
	 * 
	 * @return JSONArray 返回基站信息。
	 * @throws JSONException
	 */
	public static JSONArray getNetWorkInfo(Context context)
			throws JSONException {
		TelephonyManager tm;
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		
		CellLocation cellLocation = tm.getCellLocation();
		JSONArray array = new JSONArray();
		if (cellLocation != null) {
			if (cellLocation instanceof GsmCellLocation) {
				GsmCellLocation gcl = (GsmCellLocation) tm.getCellLocation();

				int cid = gcl.getCid();
				int lac = gcl.getLac();

				int mcc = Integer.valueOf(tm.getNetworkOperator().substring(0,
						3));
				int mnc = Integer.valueOf(tm.getNetworkOperator().substring(3,
						5));

				JSONObject data = new JSONObject();
				data.put("cell_id", cid); // 25070
				data.put("location_area_code", lac);// 4474
				data.put("mobile_country_code", mcc);// 460
				data.put("mobile_network_code", mnc);// 0
				array.put(data);

			}else{
				return null ;
			}
		}
		return array;
	}

	/**
	 * wifi定位
	 * 
	 * @return JSONArray 返回wifi列表。
	 * @throws JSONException
	 */
	public static JSONArray getWifoInfo(Context context) throws JSONException {

		JSONArray array = new JSONArray();
		WifiManager mainWifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		mainWifi.startScan();
		List<ScanResult> wifiList = mainWifi.getScanResults();
		for (int i = 0; i < wifiList.size(); i++) {
			JSONObject current_data = new JSONObject();
			current_data.put("mac_address", wifiList.get(i).BSSID);
			current_data.put("ssid", wifiList.get(i).SSID);
			current_data.put("signal_strength", wifiList.get(i).level);
			array.put(current_data);
		}
		return array;
	}

	/**
	 * network定位
	 * 
	 * @return String[] String[0] latitude;String[1] longitude;
	 */
	public static Double[] getLocation(Context context) {
		Double[] rls = new Double[2];

		try {

			// 组装JSON查询字符串
			JSONObject holder = new JSONObject();
			holder.put("version", "1.1.0");
			holder.put("host", "maps.google.com");
			holder.put("address_language", "zh_CN");
			holder.put("request_address", true);

			if(ifSupportNetWork(context)){
				JSONArray array = getNetWorkInfo(context);
				if(array!=null)
					holder.put("cell_towers", array);
			}
			if(ifSupportWifi(context)){
				JSONArray array = getWifoInfo(context);
				if(array!=null)
					holder.put("wifi_towers", array);
			}

			String holderStr = holder.toString();

			String json = com.junction.android.util.HttpPost.doPost(
					"http://www.google.com/loc/json", holder.toString());

			JSONObject jo = new JSONObject(json);
			JSONObject ltion = (JSONObject) jo.get("location");
			rls[0] = ltion.getDouble("latitude");
			rls[1] = ltion.getDouble("longitude");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rls;
	}

	/**
	 * cdma定位
	 * */
	public static Double[] getCdmaLocation(Context context){
		
		TelephonyManager tm;
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		CellLocation cellLocation = tm.getCellLocation();

		// 判断CellLocation类型，是gsm或cdma
		if (cellLocation != null && cellLocation instanceof CdmaCellLocation) {
			Double[] rls = new Double[2];
				CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
				double lat = (double) cdmaCellLocation.getBaseStationLatitude() / 14400;
				double lon = (double) cdmaCellLocation
						.getBaseStationLongitude() / 14400;
				rls[0] = lat;
				rls[1] = lon;
				return rls;
			}

		return null ;
		
	}
	
	/**
	 * 定位集合
	 * */
	public static Double[] getLocationAll(Context context){
		Double[] loc =  getCdmaLocation(context);
		if(loc ==null){
			if(ifSupportNetWork(context) || ifSupportWifi(context)){
				loc = getLocation(context);
			}else
				return null ;
		}
		return loc ;
		
	}
	/**
	 * network定位
	 * 
	 * @return String[] String[0] latitude;String[1] longitude;
	 */
	public static Double[] getNetWorkLocation(Context context) {
		Double[] rls = new Double[2];
		TelephonyManager tm;
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		CellLocation cellLocation = tm.getCellLocation();

		// 判断CellLocation类型，是gsm或cdma
		if (cellLocation != null) {
			if (cellLocation instanceof GsmCellLocation) {
				try {
					GsmCellLocation gcl = (GsmCellLocation) tm
							.getCellLocation();

					int cid = gcl.getCid();
					int lac = gcl.getLac();

					int mcc = Integer.valueOf(tm.getNetworkOperator()
							.substring(0, 3));
					int mnc = Integer.valueOf(tm.getNetworkOperator()
							.substring(3, 5));

					// 组装JSON查询字符串
					JSONObject holder = new JSONObject();
					holder.put("version", "1.1.0");
					holder.put("host", "maps.google.com");
					// holder.put("address_language", "zh_CN");
					holder.put("request_address", true);

					JSONArray array = new JSONArray();
					JSONObject data = new JSONObject();
					data.put("cell_id", cid); // 25070
					data.put("location_area_code", lac);// 4474
					data.put("mobile_country_code", mcc);// 460
					data.put("mobile_network_code", mnc);// 0
					array.put(data);
					holder.put("cell_towers", array);

					String holderStr = holder.toString();

					String json = com.junction.android.util.HttpPost
							.doPost("http://www.google.com/loc/json",
									holder.toString());

					JSONObject jo = new JSONObject(json);
					JSONObject ltion = (JSONObject) jo.get("location");
					rls[0] = ltion.getDouble("latitude");
					rls[1] = ltion.getDouble("longitude");
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (cellLocation instanceof CdmaCellLocation) {
				CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
				double lat = (double) cdmaCellLocation.getBaseStationLatitude() / 14400;
				double lon = (double) cdmaCellLocation
						.getBaseStationLongitude() / 14400;
				rls[0] = lat;
				rls[1] = lon;
			}

		}
		return rls;
	}

}
