package com.junction.android.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
/**
 * @Description url数据请求类
 * @author 楼周峰
 * @time 2012-06-19
 * @modify author 修改人
 * @modify time 修改时间
 * @modify Description 修改内容描述
 */
public class HttpPost {
	/**
	* @Description url数据请求静态方法
	* @param path url地址
	* @param postString 请求参数
	* @return String 请求返回数据
	* @throws IOException
	*/
	public static String doPost(String path, String postString) throws IOException{
		HttpURLConnection conn = null;
		String result = "";
		URL url = null;
		StringBuffer sb = new StringBuffer();
		try {
			url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();

			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");

			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Charset", "UTF-8");
			
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
			out.write(postString);
			out.flush();
			out.close();

			InputStream in = conn.getInputStream();
			
			InputStreamReader insr = new InputStreamReader(
					in, "UTF-8");
			BufferedReader br = new BufferedReader(insr);
			String data = null;
			result = br.readLine();
			while (result != null) {
				sb.append(result);
				result = br.readLine();
			}
			insr.close();
			
			conn.disconnect();
		} catch (IOException e) {
			conn.disconnect();
			e.printStackTrace();
			throw e;
		}
		return sb.toString();
	}

	/**
	* 通过拼接的方式构造请求内容，实现参数传输以及文件传输
	* @param actionUrl 请求地址
	* @param params 参数map
	* @param files 文件名
	* @return String
	* @throws IOException
	*/
	public static String post(String actionUrl, Map<String, String> params,
	    Map<String, String> files) throws IOException {

	  String BOUNDARY = java.util.UUID.randomUUID().toString();
	  String PREFIX = "--" , LINEND = "\r\n";
	  String MULTIPART_FROM_DATA = "multipart/form-data";
	  String CHARSET = "UTF-8";

	  URL uri = new URL(actionUrl);
	  HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
	  conn.setReadTimeout(5 * 1000); // 缓存的最长时间
	  conn.setDoInput(true);// 允许输入
	  conn.setDoOutput(true);// 允许输出
	  conn.setUseCaches(false); // 不允许使用缓存
	  conn.setRequestMethod("POST");
	  conn.setRequestProperty("connection", "keep-alive");
	  conn.setRequestProperty("Charsert", "UTF-8");
	  conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

	  // 首先组拼文本类型的参数
	  StringBuilder sb = new StringBuilder();
	  
	  DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
	  for (Map.Entry<String, String> entry : params.entrySet()) {
	    sb.append(PREFIX);
	    sb.append(BOUNDARY);
	    sb.append(LINEND);
	    sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
	    sb.append("Content-Type: text/plain; charset=" + CHARSET+LINEND);
	    sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
	    sb.append(LINEND);
	    sb.append(entry.getValue());
	    sb.append(LINEND);
	    outStream.write(sb.toString().getBytes());
	  }

	  
	  
	  // 发送文件数据
	  if(files!=null){
	    int i = 0;
	    for (Map.Entry<String, String> file: files.entrySet()) {
	      StringBuilder sb1 = new StringBuilder();
	      sb1.append(PREFIX);
	      sb1.append(BOUNDARY);
	      sb1.append(LINEND);
	      sb1.append("Content-Disposition: form-data; name=\"file"+(i++)+"\"; filename=\""+file.getKey()+"\""+LINEND);
	      sb1.append("Content-Type: application/octet-stream; charset="+CHARSET+LINEND);
	      sb1.append(LINEND);
	      outStream.write(sb1.toString().getBytes());

	      File fl = new File(file.getValue().toString());
	      InputStream is = new FileInputStream(fl);
	      byte[] buffer = new byte[1024];
	      int len = 0;
	      while ((len = is.read(buffer)) != -1) {
	        outStream.write(buffer, 0, len);
	      }

	      is.close();
	      outStream.write(LINEND.getBytes());
	    }
	  }
	  
	  //请求结束标志
	  byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
	  outStream.write(end_data);
	  outStream.flush();

	  //得到响应码
	  int res = conn.getResponseCode();
	  InputStream in = null;
	  StringBuilder sb2 = new StringBuilder();
	  if (res == 200) {
	    in = conn.getInputStream();
	    int ch;
	    
	    while ((ch = in.read()) != -1) {
	      sb2.append((char) ch);
	    }
	  }
	  return sb2.toString();
	} 
}
